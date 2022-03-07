package edu.asoldatov.universityproxy.searcher.impl;

import edu.asoldatov.universityproxy.configuration.properties.AuthProperties;
import edu.asoldatov.universityproxy.dto.client.LoginDto;
import edu.asoldatov.universityproxy.exception.AuthException;
import edu.asoldatov.universityproxy.exception.IntegrationException;
import edu.asoldatov.universityproxy.searcher.OmstuSearcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class OmstuSearcherImpl implements OmstuSearcher {

    private static final String SET_COOKIE_HEADER_NAME = "Set-Cookie";
    private static final String COOKIE_HEADER_NAME = "Cookie";

    private static final String COOKIE_SEPARATOR = ";";
    private static final String STUD_SESS_ID_TOKEN_NAME = "STUDSESSID";

    private static final int TOKEN_START_INDEX = 11;
    private static final int TOKEN_FINISH_INDEX = 37;

    private static final String AUTH_FORM_FIELD_NAME = "AUTH_FORM";
    private static final String TYPE_FIELD_NAME = "TYPE";
    private static final String USER_LOGIN_FIELD_NAME = "USER_LOGIN";
    private static final String USER_PASSWORD_FIELD_NAME = "USER_PASSWORD";
    private static final String AUTH_FORM_VALUE = "Y";
    private static final String TYPE_VALUE = "AUTH";

    private final RestTemplate restTemplate;
    private final String loginUrl;
    private final String authUrl;

    public OmstuSearcherImpl(RestTemplate restTemplate, AuthProperties properties) {
        this.restTemplate = restTemplate;
        this.loginUrl = UriComponentsBuilder.fromHttpUrl(properties.getUrl() + properties.getLogin()).toUriString();
        this.authUrl = UriComponentsBuilder.fromHttpUrl(properties.getUrl() + properties.getAuth()).toUriString();
    }

    /**
     * Необходимо пояснить за это.
     * Первый запрос на авторизауию на сайте ОмГТУ отправляется на получение кук.
     * С полученными куками отправляется запрос на авторизацию в личном кабинете.
     * В случае успешной авторизации происходит редирект.
     */
    @Override
    public String login(LoginDto request) {
        try {
            String cookie = doLoginRequest(request.getLogin(), request.getPassword());
            URI redirectUri = doAuthRequest(cookie);
            return doRedirectRequest(redirectUri);
        } catch (RestClientException exception) {
            throw new IntegrationException(String.format("Can not login for user [%s]", request.getLogin()));
        }
    }

    private String doLoginRequest(final String login, final String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add(AUTH_FORM_FIELD_NAME, AUTH_FORM_VALUE);
        map.add(TYPE_FIELD_NAME, TYPE_VALUE);
        map.add(USER_LOGIN_FIELD_NAME, login);
        map.add(USER_PASSWORD_FIELD_NAME, password);

        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, new HttpEntity<>(map, headers), String.class);
        return getCookiesFromHeaders(response.getHeaders());
    }

    private URI doAuthRequest(final String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(COOKIE_HEADER_NAME, cookie);

        ResponseEntity<String> response = restTemplate.postForEntity(authUrl, new HttpEntity<>(headers), String.class);

        if (response.getStatusCode().is3xxRedirection()) {
            return response.getHeaders().getLocation();
        } else {
            log.error("Can not authorize. Status code is [{}], cookie [{}]", response.getStatusCode(), cookie);
            throw new AuthException("Can not to authorize in student portal.");
        }
    }

    private String doRedirectRequest(final URI redirectUri) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(redirectUri, String.class);
        List<String> headers = responseEntity.getHeaders().get(SET_COOKIE_HEADER_NAME);
        if (headers == null) {
            throw new AuthException("Can not authorize. Headers from redirect not set.");
        }
        return getStudSessIdToken(headers);
    }


    private String getCookiesFromHeaders(final HttpHeaders headers) {
        List<String> headerField = headers.get(SET_COOKIE_HEADER_NAME);
        return StringUtils.join(headerField, COOKIE_SEPARATOR);
    }

    private String getStudSessIdToken(final List<String> headers) {
        for (String header : headers) {
            if (header.contains(STUD_SESS_ID_TOKEN_NAME)) {
                return header.substring(TOKEN_START_INDEX, TOKEN_FINISH_INDEX);
            }
        }
        throw new AuthException("Can not authorize. Token not set.");
    }
}

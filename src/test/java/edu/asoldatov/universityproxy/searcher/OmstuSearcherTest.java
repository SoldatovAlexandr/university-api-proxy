package edu.asoldatov.universityproxy.searcher;

import edu.asoldatov.universityproxy.configuration.properties.AuthProperties;
import edu.asoldatov.universityproxy.dto.client.LoginDto;
import edu.asoldatov.universityproxy.exception.AuthException;
import edu.asoldatov.universityproxy.exception.IntegrationException;
import edu.asoldatov.universityproxy.searcher.impl.OmstuSearcherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OmstuSearcherTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final AuthProperties authProperties = mock(AuthProperties.class);
    private final ResponseEntity<String> loginResponseMock = mock(ResponseEntity.class);
    private final HttpHeaders headersMock = mock(HttpHeaders.class);
    private final ResponseEntity<String> authResponseMock = mock(ResponseEntity.class);
    private final ResponseEntity<String> redirectResponseMock = mock(ResponseEntity.class);
    private final HttpHeaders redirectHeadersMock = mock(HttpHeaders.class);

    private OmstuSearcher omstuSearcher;

    private final LoginDto loginDto = LoginDto.builder()
            .login("test@test.ru")
            .password("password")
            .build();

    private final List<String> loginCookie = List.of("PHPSESID=hahaha", "JAVASESID=1223");
    private final URI redirectUri = URI.create("https://test.ru/redirect");
    private static final String LOGIN_URL = "https://test.ru/login";
    private static final String AUTH_URL = "https://test.ru/auth";

    @BeforeEach
    void setUp() {
        when(authProperties.getUrl()).thenReturn("https://test.ru/");
        when(authProperties.getLogin()).thenReturn("login");
        when(authProperties.getAuth()).thenReturn("auth");

        omstuSearcher = new OmstuSearcherImpl(restTemplate, authProperties);
    }

    @Test
    void login_success() {
        List<String> redirectCookie = List.of("STUDSESSID=1vd635vdubeae2f66f86llllj5; path=/", "JAVASESID=1223");

        when(restTemplate.postForEntity(eq(LOGIN_URL), any(), eq(String.class))).thenReturn(loginResponseMock);
        when(loginResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.get("Set-Cookie")).thenReturn(loginCookie);

        when(restTemplate.postForEntity(eq(AUTH_URL), any(), eq(String.class))).thenReturn(authResponseMock);
        when(authResponseMock.getStatusCode()).thenReturn(HttpStatus.MOVED_PERMANENTLY);
        when(authResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.getLocation()).thenReturn(redirectUri);

        when(restTemplate.getForEntity(redirectUri, String.class)).thenReturn(redirectResponseMock);
        when(redirectResponseMock.getHeaders()).thenReturn(redirectHeadersMock);
        when(redirectHeadersMock.get("Set-Cookie")).thenReturn(redirectCookie);

        String token = omstuSearcher.login(loginDto);

        ArgumentCaptor<HttpEntity> loginHttpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        ArgumentCaptor<HttpEntity> authHttpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        verify(restTemplate, times(1))
                .postForEntity(eq(LOGIN_URL), loginHttpEntityCaptor.capture(), eq(String.class));
        verify(restTemplate, times(1))
                .postForEntity(eq(AUTH_URL), authHttpEntityCaptor.capture(), eq(String.class));
        verify(restTemplate, times(1)).getForEntity(redirectUri, String.class);

        assertEquals("1vd635vdubeae2f66f86llllj5", token);
        assertEquals("{AUTH_FORM=[Y], TYPE=[AUTH], USER_LOGIN=[test@test.ru], USER_PASSWORD=[password]}",
                loginHttpEntityCaptor.getValue().getBody().toString());
        assertEquals(List.of("PHPSESID=hahaha;JAVASESID=1223"),
                authHttpEntityCaptor.getValue().getHeaders().get("Cookie"));
    }

    @Test
    void login_AuthResponseException() {
        when(restTemplate.postForEntity(eq(LOGIN_URL), any(), eq(String.class))).thenReturn(loginResponseMock);
        when(loginResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.get("Set-Cookie")).thenReturn(loginCookie);

        when(restTemplate.postForEntity(eq(AUTH_URL), any(), eq(String.class))).thenReturn(authResponseMock);
        when(authResponseMock.getStatusCode()).thenReturn(HttpStatus.OK);

        AuthException authException = assertThrows(AuthException.class, () -> omstuSearcher.login(loginDto));
        assertEquals("Can not to authorize in student portal.", authException.getMessage());
    }

    @Test
    void login_RedirectResponseException() {
        when(restTemplate.postForEntity(eq(LOGIN_URL), any(), eq(String.class))).thenReturn(loginResponseMock);
        when(loginResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.get("Set-Cookie")).thenReturn(loginCookie);

        when(restTemplate.postForEntity(eq(AUTH_URL), any(), eq(String.class))).thenReturn(authResponseMock);
        when(authResponseMock.getStatusCode()).thenReturn(HttpStatus.MOVED_PERMANENTLY);
        when(authResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.getLocation()).thenReturn(redirectUri);

        when(restTemplate.getForEntity(redirectUri, String.class)).thenReturn(redirectResponseMock);
        when(redirectResponseMock.getHeaders()).thenReturn(redirectHeadersMock);
        when(redirectHeadersMock.get("Set-Cookie")).thenReturn(null);

        AuthException authException = assertThrows(AuthException.class, () -> omstuSearcher.login(loginDto));
        assertEquals("Can not authorize. Headers from redirect not set.", authException.getMessage());
    }

    @Test
    void login_StudSessIdNotSet() {
        List<String> redirectCookie = List.of("path=/", "JAVASESID=1223");

        when(restTemplate.postForEntity(eq(LOGIN_URL), any(), eq(String.class))).thenReturn(loginResponseMock);
        when(loginResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.get("Set-Cookie")).thenReturn(loginCookie);

        when(restTemplate.postForEntity(eq(AUTH_URL), any(), eq(String.class))).thenReturn(authResponseMock);
        when(authResponseMock.getStatusCode()).thenReturn(HttpStatus.MOVED_PERMANENTLY);
        when(authResponseMock.getHeaders()).thenReturn(headersMock);
        when(headersMock.getLocation()).thenReturn(redirectUri);

        when(restTemplate.getForEntity(redirectUri, String.class)).thenReturn(redirectResponseMock);
        when(redirectResponseMock.getHeaders()).thenReturn(redirectHeadersMock);
        when(redirectHeadersMock.get("Set-Cookie")).thenReturn(redirectCookie);

        AuthException authException = assertThrows(AuthException.class, () -> omstuSearcher.login(loginDto));
        assertEquals("Can not authorize. Token not set.", authException.getMessage());
    }

    @Test
    void login_RestClientException() {
        when(restTemplate.postForEntity(eq(LOGIN_URL), any(), eq(String.class))).thenThrow(new RestClientException(""));

        IntegrationException exception = assertThrows(IntegrationException.class, () -> omstuSearcher.login(loginDto));
        assertEquals("Can not login for user [test@test.ru]", exception.getMessage());
    }
}

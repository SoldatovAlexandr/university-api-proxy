package edu.asoldatov.universityproxy.searcher.impl;

import edu.asoldatov.universityproxy.common.DataType;
import edu.asoldatov.universityproxy.configuration.properties.EduCabProperties;
import edu.asoldatov.universityproxy.exception.AuthException;
import edu.asoldatov.universityproxy.exception.IntegrationException;
import edu.asoldatov.universityproxy.searcher.EduCabSearcher;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class EduCabSearcherImpl implements EduCabSearcher {

    private static final String STUD_SES_ID_COOKIE_NAME = "STUDSESSID";
    private static final String PATH_FORMAT = "/index.php?r=%s/%s";

    private final EduCabProperties eduCabProperties;

    @Override
    public Document search(String token, DataType type) {
        String path = String.format(PATH_FORMAT, type.getValue(), type.getMode());
        return search(token, path);
    }

    @Override
    public Document search(String token, String path) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(eduCabProperties.getUrl() + path).toUriString();
            Document document = Jsoup.connect(url).cookie(STUD_SES_ID_COOKIE_NAME, token).get();
            if (document.location().equals(url)) {
                return document;
            }
            throw new AuthException("Token expired");
        } catch (IOException e) {
            throw new IntegrationException(String.format("Can not search for path [%s]", path), e);
        }
    }
}

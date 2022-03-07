package edu.asoldatov.universityproxy.searcher.impl;

import edu.asoldatov.universityproxy.searcher.HttpConnectionWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

@Component
public class HttpConnectionWrapperImpl implements HttpConnectionWrapper {

    private static final String GET = "GET";

    @Override
    public HttpURLConnection connect(URI uri) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod(GET);
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        return connection;
    }
}

package edu.asoldatov.universityproxy.searcher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public interface HttpConnectionWrapper {

    HttpURLConnection connect(URI uri) throws IOException;
}

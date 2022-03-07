package edu.asoldatov.universityproxy.searcher;

import edu.asoldatov.universityproxy.common.DataType;
import org.jsoup.nodes.Document;

public interface EduCabSearcher {

    Document search(String token, DataType type);
}

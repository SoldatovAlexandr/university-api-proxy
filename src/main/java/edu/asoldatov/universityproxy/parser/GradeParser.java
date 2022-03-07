package edu.asoldatov.universityproxy.parser;

import edu.asoldatov.universityproxy.dto.client.grade.GradeBookDto;
import org.jsoup.nodes.Document;

public interface GradeParser {

    GradeBookDto parse(Document document);
}

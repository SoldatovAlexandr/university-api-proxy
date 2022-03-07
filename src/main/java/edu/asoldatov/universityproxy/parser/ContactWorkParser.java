package edu.asoldatov.universityproxy.parser;

import edu.asoldatov.universityproxy.dto.client.contactwork.DisciplineDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.ResponseTaskDto;
import org.jsoup.nodes.Document;

import java.util.List;

public interface ContactWorkParser {

    List<DisciplineDto> parseToDiscipline(Document document);

    List<ResponseTaskDto> parseToTask(Document document);
}

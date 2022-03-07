package edu.asoldatov.universityproxy.service.impl;

import edu.asoldatov.universityproxy.common.DataType;
import edu.asoldatov.universityproxy.dto.client.contactwork.DisciplineDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.ResponseTaskDto;
import edu.asoldatov.universityproxy.parser.ContactWorkParser;
import edu.asoldatov.universityproxy.searcher.EduCabSearcher;
import edu.asoldatov.universityproxy.service.ContactWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactWorkServiceImpl implements ContactWorkService {

    private final EduCabSearcher eduCabSearcher;
    private final ContactWorkParser contactWorkParser;

    @Override
    public List<DisciplineDto> search(String token) {
        final Document document = eduCabSearcher.search(token, DataType.CONTACT_WORK);
        return contactWorkParser.parseToDiscipline(document);
    }

    @Override
    public List<ResponseTaskDto> search(String token, String path) {
        final Document document = eduCabSearcher.search(token, path);
        return contactWorkParser.parseToTask(document);
    }
}

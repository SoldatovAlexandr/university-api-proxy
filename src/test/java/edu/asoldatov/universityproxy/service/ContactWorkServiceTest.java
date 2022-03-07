package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.common.DataType;
import edu.asoldatov.universityproxy.dto.client.contactwork.DisciplineDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.ResponseTaskDto;
import edu.asoldatov.universityproxy.parser.ContactWorkParser;
import edu.asoldatov.universityproxy.searcher.EduCabSearcher;
import edu.asoldatov.universityproxy.service.impl.ContactWorkServiceImpl;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ContactWorkServiceTest {

    private static final String TOKEN = "rnkr4vtoagumu61c1hsd2l1h11";
    private static final String BASE_URL = "http://test.ru";
    private static final String PATH = "/index.php?r=remote/read/taskList&discipline=80000000000004AF&time=0";
    private static final Document DOCUMENT = new Document(BASE_URL);
    private static final ResponseTaskDto RESPONSE_TASK_DTO = ResponseTaskDto.builder().build();
    private static final DisciplineDto DISCIPLINE_DTO = DisciplineDto.builder().build();

    private final EduCabSearcher eduCabSearcher = mock(EduCabSearcher.class);
    private final ContactWorkParser contactWorkParser = mock(ContactWorkParser.class);

    private ContactWorkService contactWorkService;

    @BeforeEach
    void setUp() {
        contactWorkService = new ContactWorkServiceImpl(eduCabSearcher, contactWorkParser);
    }

    @Test
    void parseToDiscipline() {
        when(eduCabSearcher.search(TOKEN, DataType.CONTACT_WORK)).thenReturn(DOCUMENT);
        when(contactWorkParser.parseToDiscipline(DOCUMENT)).thenReturn(List.of(DISCIPLINE_DTO));

        List<DisciplineDto> result = contactWorkService.search(TOKEN);
        assertEquals(List.of(DISCIPLINE_DTO), result);
        verify(eduCabSearcher, times(1)).search(TOKEN, DataType.CONTACT_WORK);
        verify(contactWorkParser, times(1)).parseToDiscipline(DOCUMENT);
    }

    @Test
    void parseToTask() {
        when(eduCabSearcher.search(TOKEN, PATH)).thenReturn(DOCUMENT);
        when(contactWorkParser.parseToTask(DOCUMENT)).thenReturn(List.of(RESPONSE_TASK_DTO));

        List<ResponseTaskDto> result = contactWorkService.search(TOKEN, PATH);

        assertEquals(List.of(RESPONSE_TASK_DTO), result);
        verify(eduCabSearcher, times(1)).search(TOKEN, PATH);
        verify(contactWorkParser, times(1)).parseToTask(DOCUMENT);
    }
}

package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.common.DataType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeBookDto;
import edu.asoldatov.universityproxy.parser.GradeParser;
import edu.asoldatov.universityproxy.searcher.EduCabSearcher;
import edu.asoldatov.universityproxy.service.impl.GradeServiceImpl;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GradeServiceTest {

    private static final String TOKEN = "rnkr4vtoagumu61c1hsd2l1h11";
    private static final String BASE_URL = "http://test.ru";
    private static final Document DOCUMENT = new Document(BASE_URL);
    private static final GradeBookDto RESULT = new GradeBookDto();

    private final EduCabSearcher eduCabSearcher = mock(EduCabSearcher.class);
    private final GradeParser gradeParser = mock(GradeParser.class);

    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        when(eduCabSearcher.search(TOKEN, DataType.GRADES)).thenReturn(DOCUMENT);
        when(gradeParser.parse(DOCUMENT)).thenReturn(RESULT);

        gradeService = new GradeServiceImpl(eduCabSearcher, gradeParser);
    }

    @Test
    void search() {
        GradeBookDto result = gradeService.search(TOKEN);

        assertEquals(RESULT, result);
        verify(eduCabSearcher, times(1)).search(TOKEN, DataType.GRADES);
        verify(gradeParser, times(1)).parse(DOCUMENT);
    }
}

package edu.asoldatov.universityproxy.searcher;

import edu.asoldatov.universityproxy.common.ScheduleType;
import edu.asoldatov.universityproxy.configuration.properties.ScheduleProperties;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchTypeDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuLessonDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuTypeDto;
import edu.asoldatov.universityproxy.exception.IntegrationException;
import edu.asoldatov.universityproxy.exception.NotFoundException;
import edu.asoldatov.universityproxy.searcher.impl.ScheduleSearcherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ScheduleSearcherTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ScheduleProperties omstuProperties = mock(ScheduleProperties.class);

    private ScheduleSearcher scheduleSearcher;

    private SearchLessonDto searchLessonDto;
    private SearchTypeDto searchTypeDto;

    private final LocalDate start = LocalDate.of(2000, 1, 10);
    private final LocalDate finish = LocalDate.of(2000, 1, 10);

    private static final OmstuLessonDto OMSTU_LESSON_DTO = OmstuLessonDto.builder().build();
    private static final OmstuLessonDto[] OMSTU_LESSON_RESPONSE = {OMSTU_LESSON_DTO};
    private static final OmstuTypeDto OMSTU_TYPE_DTO = OmstuTypeDto.builder().build();
    private static final OmstuTypeDto[] OMSTU_TYPE_DTOS = {OMSTU_TYPE_DTO};

    @BeforeEach
    void setUp() {
        when(omstuProperties.getUrl()).thenReturn("https://test.ru/");
        when(omstuProperties.getType()).thenReturn("type");
        when(omstuProperties.getSchedule()).thenReturn("schedule");

        scheduleSearcher = new ScheduleSearcherImpl(restTemplate, omstuProperties);

        searchLessonDto = SearchLessonDto.builder()
                .type(ScheduleType.GROUP)
                .oid(12)
                .finish(finish)
                .start(start)
                .build();

        searchTypeDto = SearchTypeDto.builder()
                .type(ScheduleType.GROUP)
                .value("ПИН-181")
                .build();
    }

    @Test
    void search_lesson_success() {
        when(restTemplate.getForObject("https://test.ru/schedule", OmstuLessonDto[].class, "group", 12,
                "2000.01.10", "2000.01.10")).thenReturn(OMSTU_LESSON_RESPONSE);

        List<OmstuLessonDto> response = scheduleSearcher.search(searchLessonDto);

        assertEquals(List.of(OMSTU_LESSON_DTO), response);
        verify(restTemplate, times(1)).getForObject("https://test.ru/schedule",
                OmstuLessonDto[].class, "group", 12, "2000.01.10", "2000.01.10");
    }

    @Test
    void search_lesson_null_response() {
        when(restTemplate.getForObject("https://test.ru/schedule", OmstuLessonDto[].class, "group", 12,
                "2000.01.10", "2000.01.10")).thenReturn(null);

        NotFoundException e = assertThrows(NotFoundException.class, () -> scheduleSearcher.search(searchLessonDto));
        assertEquals("Null response.", e.getMessage());
    }

    @Test
    void search_lesson_IntegrationException() {
        String errorMessage = "Can not find schedule with request [SearchLessonDto(type=GROUP, oid=12," +
                " start=2000-01-10, finish=2000-01-10)]";
        when(restTemplate.getForObject("https://test.ru/schedule", OmstuLessonDto[].class, "group", 12,
                "2000.01.10", "2000.01.10")).thenThrow(new RestClientException(""));

        IntegrationException e = assertThrows(IntegrationException.class, () -> scheduleSearcher.search(searchLessonDto));
        assertEquals(errorMessage, e.getMessage());
    }

    @Test
    void search_types_success() {
        when(restTemplate.getForObject("https://test.ru/type", OmstuTypeDto[].class, "ПИН-181", "group"))
                .thenReturn(OMSTU_TYPE_DTOS);

        List<OmstuTypeDto> response = scheduleSearcher.search(searchTypeDto);

        assertEquals(List.of(OMSTU_TYPE_DTO), response);
        verify(restTemplate, times(1))
                .getForObject("https://test.ru/type", OmstuTypeDto[].class, "ПИН-181", "group");
    }

    @Test
    void search_types_null_response() {
        when(restTemplate.getForObject("https://test.ru/type", OmstuTypeDto[].class, "ПИН-181", "group"))
                .thenReturn(null);

        NotFoundException e = assertThrows(NotFoundException.class, () -> scheduleSearcher.search(searchTypeDto));
        assertEquals("Null response.", e.getMessage());
    }

    @Test
    void search_types_IntegrationException() {
        String errorMessage = "Can not find types with request [SearchTypeDto(type=GROUP, value=ПИН-181)]";
        when(restTemplate.getForObject("https://test.ru/type", OmstuTypeDto[].class, "ПИН-181", "group"))
                .thenThrow(new RestClientException(""));

        IntegrationException e = assertThrows(IntegrationException.class, () -> scheduleSearcher.search(searchTypeDto));
        assertEquals(errorMessage, e.getMessage());
    }
}

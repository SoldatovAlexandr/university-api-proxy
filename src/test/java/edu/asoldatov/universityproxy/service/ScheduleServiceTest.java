package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.schedule.ClientLessonDto;
import edu.asoldatov.universityproxy.dto.client.schedule.ClientTypeDto;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchTypeDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuLessonDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuTypeDto;
import edu.asoldatov.universityproxy.mapper.ScheduleMapper;
import edu.asoldatov.universityproxy.searcher.ScheduleSearcher;
import edu.asoldatov.universityproxy.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    private final ScheduleSearcher scheduleSearcher = mock(ScheduleSearcher.class);
    private final ScheduleMapper scheduleMapper = mock(ScheduleMapper.class);

    private ScheduleService scheduleService;

    private static final OmstuTypeDto OMSTU_TYPE_DTO = OmstuTypeDto.builder().build();
    private static final ClientTypeDto CLIENT_TYPE_DTO = ClientTypeDto.builder().build();
    private static final List<OmstuTypeDto> OMSTU_TYPE_RESPONSE = List.of(OMSTU_TYPE_DTO);
    private static final OmstuLessonDto OMSTU_LESSON_DTO = OmstuLessonDto.builder().build();
    private static final ClientLessonDto CLIENT_LESSON_DTO = ClientLessonDto.builder().build();
    private static final List<OmstuLessonDto> OMSTU_LESSON_RESPONSE = List.of(OMSTU_LESSON_DTO);

    @BeforeEach
    void setUp() {
        scheduleService = new ScheduleServiceImpl(scheduleSearcher, scheduleMapper);
    }

    @Test
    void findTypes() {
        SearchTypeDto searchTypeDto = new SearchTypeDto();
        when(scheduleSearcher.search(searchTypeDto)).thenReturn(OMSTU_TYPE_RESPONSE);
        when(scheduleMapper.to(OMSTU_TYPE_DTO)).thenReturn(CLIENT_TYPE_DTO);

        List<ClientTypeDto> response = scheduleService.findTypes(searchTypeDto);

        verify(scheduleSearcher, times(1)).search(searchTypeDto);
        verify(scheduleMapper, times(1)).to(OMSTU_TYPE_DTO);
        assertEquals(response, List.of(CLIENT_TYPE_DTO));
    }

    @Test
    void findLessons() {
        SearchLessonDto searchLessonDto = new SearchLessonDto();
        when(scheduleSearcher.search(searchLessonDto)).thenReturn(OMSTU_LESSON_RESPONSE);
        when(scheduleMapper.to(OMSTU_LESSON_DTO)).thenReturn(CLIENT_LESSON_DTO);

        List<ClientLessonDto> response = scheduleService.findLessons(searchLessonDto);

        verify(scheduleSearcher, times(1)).search(searchLessonDto);
        verify(scheduleMapper, times(1)).to(OMSTU_LESSON_DTO);
        assertEquals(response, List.of(CLIENT_LESSON_DTO));
    }
}

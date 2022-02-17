package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.ClientLessonDto;
import edu.asoldatov.universityproxy.dto.client.ClientTypeDto;
import edu.asoldatov.universityproxy.dto.client.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.SearchTypeDto;
import edu.asoldatov.universityproxy.mapper.ScheduleMapper;
import edu.asoldatov.universityproxy.searcher.ScheduleSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleSearcher scheduleSearcher;
    private final ScheduleMapper scheduleMapper;

    @Override
    public List<ClientLessonDto> findLessons(SearchLessonDto request) {
        return scheduleSearcher.search(request)
                .stream()
                .map(scheduleMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientTypeDto> findTypes(SearchTypeDto request) {
        return scheduleSearcher.search(request)
                .stream()
                .map(scheduleMapper::to)
                .collect(Collectors.toList());
    }
}

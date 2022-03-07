package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.schedule.ClientLessonDto;
import edu.asoldatov.universityproxy.dto.client.schedule.ClientTypeDto;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchTypeDto;

import java.util.List;

public interface ScheduleService {

    List<ClientLessonDto> findLessons(SearchLessonDto request);

    List<ClientTypeDto> findTypes(SearchTypeDto request);
}

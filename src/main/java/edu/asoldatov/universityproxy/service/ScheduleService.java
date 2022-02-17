package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.ClientLessonDto;
import edu.asoldatov.universityproxy.dto.client.ClientTypeDto;
import edu.asoldatov.universityproxy.dto.client.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.SearchTypeDto;

import java.util.List;

public interface ScheduleService {

    List<ClientLessonDto> findLessons(SearchLessonDto request);

    List<ClientTypeDto> findTypes(SearchTypeDto request);
}

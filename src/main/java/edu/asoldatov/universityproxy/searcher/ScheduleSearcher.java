package edu.asoldatov.universityproxy.searcher;

import edu.asoldatov.universityproxy.dto.client.schedule.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.schedule.SearchTypeDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuLessonDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuTypeDto;

import java.util.List;

public interface ScheduleSearcher {

    List<OmstuLessonDto> search(SearchLessonDto request);

    List<OmstuTypeDto> search(SearchTypeDto request);
}

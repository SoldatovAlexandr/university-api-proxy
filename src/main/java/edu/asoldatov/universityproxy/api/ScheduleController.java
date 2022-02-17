package edu.asoldatov.universityproxy.api;

import edu.asoldatov.universityproxy.dto.client.ClientLessonDto;
import edu.asoldatov.universityproxy.dto.client.ClientTypeDto;
import edu.asoldatov.universityproxy.dto.client.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.SearchTypeDto;
import edu.asoldatov.universityproxy.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping(path = "/lessons/")
    public List<ClientLessonDto> findLessons(final @Valid @RequestBody SearchLessonDto request) {
        log.info("Find lessons with request [{}]", request);
        return scheduleService.findLessons(request);
    }

    @PostMapping(path = "/types/")
    public List<ClientTypeDto> findTypes(final @Valid @RequestBody SearchTypeDto request) {
        log.info("Find types with request [{}]", request);
        return scheduleService.findTypes(request);
    }
}

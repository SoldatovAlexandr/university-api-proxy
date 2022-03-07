package edu.asoldatov.universityproxy.api;

import edu.asoldatov.universityproxy.dto.client.grade.GradeBookDto;
import edu.asoldatov.universityproxy.dto.client.TokenDto;
import edu.asoldatov.universityproxy.service.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("/")
    public GradeBookDto getGrades(@RequestBody TokenDto tokenDto) {
        log.info("Get grades by token {}", tokenDto.getToken());
        return gradeService.search(tokenDto.getToken());
    }
}

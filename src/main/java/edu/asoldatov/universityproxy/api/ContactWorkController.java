package edu.asoldatov.universityproxy.api;

import edu.asoldatov.universityproxy.dto.client.TokenDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.DisciplineDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.RequestTaskDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.ResponseTaskDto;
import edu.asoldatov.universityproxy.service.ContactWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/disciplines")
@RequiredArgsConstructor
public class ContactWorkController {

    private final ContactWorkService contactWorkService;

    @PostMapping(path = "/")
    public List<DisciplineDto> getDisciplines(@RequestBody TokenDto tokenDto) {
        log.info("Get disciplines by token [{}]", tokenDto.getToken());
        return contactWorkService.search(tokenDto.getToken());
    }

    @PostMapping(path = "/tasks/")
    public List<ResponseTaskDto> getTasks(@RequestBody RequestTaskDto requestTaskDto) {
        log.info("Get tasks by token [{}] and by discipline path [{}]", requestTaskDto.getToken(), requestTaskDto.getPath());
        return contactWorkService.search(requestTaskDto.getToken(), requestTaskDto.getPath());
    }
}

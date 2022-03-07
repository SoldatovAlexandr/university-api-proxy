package edu.asoldatov.universityproxy.api;

import edu.asoldatov.universityproxy.dto.client.LoginDto;
import edu.asoldatov.universityproxy.dto.client.TokenDto;
import edu.asoldatov.universityproxy.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/")
    public TokenDto login(@RequestBody LoginDto request) {
        log.info("Try login with request [{}]", request.getLogin());
        return authService.login(request);
    }
}

package edu.asoldatov.universityproxy.service.impl;

import edu.asoldatov.universityproxy.dto.client.LoginDto;
import edu.asoldatov.universityproxy.dto.client.TokenDto;
import edu.asoldatov.universityproxy.searcher.OmstuSearcher;
import edu.asoldatov.universityproxy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final OmstuSearcher omstuSearcher;

    @Override
    public TokenDto login(LoginDto request) {
        final String token = omstuSearcher.login(request);
        return TokenDto.builder()
                .token(token)
                .build();
    }
}

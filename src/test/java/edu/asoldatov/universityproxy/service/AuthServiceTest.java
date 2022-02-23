package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.LoginDto;
import edu.asoldatov.universityproxy.dto.client.TokenDto;
import edu.asoldatov.universityproxy.searcher.OmstuSearcher;
import edu.asoldatov.universityproxy.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private final OmstuSearcher omstuSearcher = mock(OmstuSearcher.class);
    private AuthService authService;
    private final LoginDto loginDto = LoginDto.builder().build();
    private static final String TOKEN = "token";

    @BeforeEach
    void setUp() {
        when(omstuSearcher.login(loginDto)).thenReturn(TOKEN);

        authService = new AuthServiceImpl(omstuSearcher);
    }

    @Test
    void login() {
        TokenDto tokenDto = authService.login(loginDto);

        verify(omstuSearcher, times(1)).login(loginDto);
        assertEquals(tokenDto.getToken(), TOKEN);
    }
}

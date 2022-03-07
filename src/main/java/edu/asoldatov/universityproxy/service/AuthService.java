package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.LoginDto;
import edu.asoldatov.universityproxy.dto.client.TokenDto;

public interface AuthService {

    TokenDto login(LoginDto request);
}

package edu.asoldatov.universityproxy.searcher;

import edu.asoldatov.universityproxy.dto.client.LoginDto;

public interface OmstuSearcher {

    String login(LoginDto dto);
}

package edu.asoldatov.universityproxy.dto.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Error {

    private int errorCode;
    private String message;
    private String detail;

}

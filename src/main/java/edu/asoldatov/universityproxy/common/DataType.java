package edu.asoldatov.universityproxy.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataType {

    CONTACT_WORK("remote", "read"),
    GRADES("student", "index");

    private final String value;
    private final String mode;
}

package edu.asoldatov.universityproxy.common;

import edu.asoldatov.universityproxy.exception.TransformException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleType {

    GROUP("group"),
    PERSON("person"),
    AUDITORIUM("auditorium");

    private final String value;

    public static ScheduleType getByValue(String value) {
        for (ScheduleType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new TransformException(String.format("Can not find schedule type with value [%s]", value));
    }
}

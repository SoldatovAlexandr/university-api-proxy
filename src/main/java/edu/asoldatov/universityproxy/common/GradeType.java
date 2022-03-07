package edu.asoldatov.universityproxy.common;

import edu.asoldatov.universityproxy.exception.TransformException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GradeType {

    EXAM("Экзамены"),
    TEST("Зачёты"),
    COURSE_WORK("Курсовые работы"),
    PRACTICE("Практики"),
    DIF_TEST("Дифференцированный зачет"),
    STATE_EXAM("Государственный экзамен");

    private final String value;

    public static GradeType getTypeByValue(String value) {
        return Arrays.stream(values())
                .filter(v -> value.contains(v.getValue()))
                .findFirst()
                .orElseThrow(() -> new TransformException(String.format("Can not find type by value [%s]", value)));
    }
}

package edu.asoldatov.universityproxy.parser.mapper;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PracticeGradeMapperTest {

    private static final List<String> PARAMS = List.of(
            "Учебная практика", "-", "Отлично", "18.09.2019", "Малков Олег Брониславович", "Да"
    );

    private static final GradeDto EXPECTED_RESULT = GradeDto.builder()
            .type(GradeType.PRACTICE)
            .date("18.09.2019")
            .hours("-")
            .name("Учебная практика")
            .grade("Отлично")
            .teacher("Малков Олег Брониславович")
            .toDiploma("Да")
            .build();

    private PracticeGradeMapper practiceGradeMapper;

    @BeforeEach
    void setUp() {
        practiceGradeMapper = new PracticeGradeMapper();
    }

    @Test
    void to() {
        GradeDto gradeDto = practiceGradeMapper.to(PARAMS, GradeType.PRACTICE);

        assertEquals(EXPECTED_RESULT, gradeDto);
    }
}

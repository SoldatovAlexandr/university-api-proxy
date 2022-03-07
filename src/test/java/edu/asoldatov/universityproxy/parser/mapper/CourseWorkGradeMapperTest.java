package edu.asoldatov.universityproxy.parser.mapper;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseWorkGradeMapperTest {

    private static final List<String> PARAMS = List.of(
            "Операционные системы", "108", "КП", "96", "Отлично", "11.06.2020", "Грицай Александр Сергеевич", "Да"
    );

    private static final GradeDto EXPECTED_RESULT = GradeDto.builder()
            .type(GradeType.COURSE_WORK)
            .date("11.06.2020")
            .hours("108")
            .rating("96")
            .name("Операционные системы")
            .grade("Отлично")
            .teacher("Грицай Александр Сергеевич")
            .toDiploma("Да")
            .build();

    private CourseWorkGradeMapper courseWorkGradeMapper;

    @BeforeEach
    void setUp() {
        courseWorkGradeMapper = new CourseWorkGradeMapper();
    }

    @Test
    void to() {
        GradeDto gradeDto = courseWorkGradeMapper.to(PARAMS, GradeType.COURSE_WORK);

        assertEquals(EXPECTED_RESULT, gradeDto);
    }
}

package edu.asoldatov.universityproxy.parser.mapper;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisciplineGradeMapperTest {

    private static final List<String> PARAMS = List.of(
            "Операционные системы", "72", "16", "60", "Зачтено", "09.01.2020", "Флоренсов Александр Николаевич", "Нет"
    );

    private static final GradeDto EXPECTED_RESULT = GradeDto.builder()
            .type(GradeType.TEST)
            .date("09.01.2020")
            .hours("72")
            .tempRating("16")
            .rating("60")
            .name("Операционные системы")
            .grade("Зачтено")
            .teacher("Флоренсов Александр Николаевич")
            .toDiploma("Нет")
            .build();

    private DisciplineGradeMapper disciplineGradeMapper;

    @BeforeEach
    void setUp() {
        disciplineGradeMapper = new DisciplineGradeMapper();
    }

    @Test
    void to() {
        GradeDto gradeDto = disciplineGradeMapper.to(PARAMS, GradeType.TEST);

        assertEquals(EXPECTED_RESULT, gradeDto);
    }
}

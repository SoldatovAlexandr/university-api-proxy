package edu.asoldatov.universityproxy.parser.mapper;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseWorkGradeMapper implements GradeMapper {

    @Override
    public GradeDto to(List<String> params, GradeType type) {
        return GradeDto.builder()
                .type(type)
                .name(params.get(0))
                .hours(params.get(1))
                .rating(params.get(3))
                .grade(params.get(4))
                .date(params.get(5))
                .teacher(params.get(6))
                .toDiploma(params.get(7))
                .build();
    }
}

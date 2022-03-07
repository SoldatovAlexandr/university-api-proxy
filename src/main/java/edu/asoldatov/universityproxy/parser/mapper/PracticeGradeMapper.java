package edu.asoldatov.universityproxy.parser.mapper;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PracticeGradeMapper implements GradeMapper{

    @Override
    public GradeDto to(List<String> params, GradeType type) {
        return GradeDto.builder()
                .type(type)
                .name(params.get(0))
                .hours(params.get(1))
                .grade(params.get(2))
                .date(params.get(3))
                .teacher(params.get(4))
                .toDiploma(params.get(5))
                .build();
    }
}

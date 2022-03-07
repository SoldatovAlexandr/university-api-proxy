package edu.asoldatov.universityproxy.parser.mapper;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;

import java.util.List;

public interface GradeMapper {

    GradeDto to(List<String> params, GradeType type);
}

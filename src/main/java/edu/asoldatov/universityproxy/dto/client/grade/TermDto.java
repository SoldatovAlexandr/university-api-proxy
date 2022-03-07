package edu.asoldatov.universityproxy.dto.client.grade;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TermDto {

    private List<GradeDto> grades;
}

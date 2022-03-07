package edu.asoldatov.universityproxy.dto.client.grade;

import edu.asoldatov.universityproxy.common.GradeType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GradeDto {

    private String name;
    private String hours;
    private String tempRating;
    private String rating;
    private String grade;
    private String date;
    private String teacher;
    private String toDiploma;
    private GradeType type;
}

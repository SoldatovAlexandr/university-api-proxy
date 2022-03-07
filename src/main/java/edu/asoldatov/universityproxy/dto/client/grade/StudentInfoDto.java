package edu.asoldatov.universityproxy.dto.client.grade;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentInfoDto {

    private String fullName;
    private String gradeBookNumber;
    private String speciality;
    private String group;
    private String learningFormat;
    private String login;
    private String password;
    private String libraryCard;
}

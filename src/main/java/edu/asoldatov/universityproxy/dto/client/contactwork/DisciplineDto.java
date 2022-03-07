package edu.asoldatov.universityproxy.dto.client.contactwork;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DisciplineDto {

    private String name;
    private String teacher;
    private String numberOfTasks;
    private String path;
}

package edu.asoldatov.universityproxy.dto.client.contactwork;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseTaskDto {

    private String comment;
    private String file;
    private String url;
    private String date;
    private String teacher;
}

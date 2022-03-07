package edu.asoldatov.universityproxy.dto.client.contactwork;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RequestTaskDto {
    private String token;
    private String path;
}

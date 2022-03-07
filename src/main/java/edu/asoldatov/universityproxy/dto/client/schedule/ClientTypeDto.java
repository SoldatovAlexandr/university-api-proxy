package edu.asoldatov.universityproxy.dto.client.schedule;

import edu.asoldatov.universityproxy.common.ScheduleType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClientTypeDto {

    private Integer id;
    private String label;
    private String description;
    private ScheduleType type;

}

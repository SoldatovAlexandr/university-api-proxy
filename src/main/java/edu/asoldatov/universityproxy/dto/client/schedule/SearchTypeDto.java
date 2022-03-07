package edu.asoldatov.universityproxy.dto.client.schedule;

import edu.asoldatov.universityproxy.common.ScheduleType;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchTypeDto {
    private ScheduleType type;
    private String value;
}

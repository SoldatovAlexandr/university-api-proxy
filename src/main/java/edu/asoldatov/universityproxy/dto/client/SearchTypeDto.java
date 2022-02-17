package edu.asoldatov.universityproxy.dto.client;

import edu.asoldatov.universityproxy.common.ScheduleType;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchTypeDto {
    private ScheduleType type;
    private String value;
}

package edu.asoldatov.universityproxy.dto.client.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.asoldatov.universityproxy.common.ScheduleType;
import lombok.*;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchLessonDto {

    private ScheduleType type;
    @Min(0)
    private Integer oid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate finish;

}

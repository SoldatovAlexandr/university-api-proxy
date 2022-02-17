package edu.asoldatov.universityproxy.dto.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClientLessonDto {

    private String auditorium;
    private Integer auditoriumOid;
    private String beginLesson;
    private String building;
    private Integer buildingOid;
    private Integer dayOfWeek;
    private String discipline;
    private Integer disciplineOid;
    private String endLesson;
    private String stream;
    private Integer groupOid;
    private String kindOfWork;
    private String lecturer;
    private Integer lecturerOid;
    private String lecturerRank;
    private String parentSchedule;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate date;

}

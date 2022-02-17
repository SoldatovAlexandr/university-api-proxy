package edu.asoldatov.universityproxy.dto.omstu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OmstuLessonDto {

    private String auditorium;
    private Integer auditoriumOid;
    private String beginLesson;
    private String building;
    private Integer buildingOid;
    private Integer dayOfWeek;
    private String discipline;
    private Integer disciplineOid;
    private String endLesson;
    private String group;
    private String stream;
    private String subGroup;
    private Integer groupOid;
    private String kindOfWork;
    private String lecturer;
    private Integer lecturerOid;
    @JsonProperty("lecturer_rank")
    private String lecturerRank;
    @JsonProperty("parentschedule")
    private String parentSchedule;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate date;

    //TODO: нужна десериализация строки из формата 2021-12-07T15:02:08Z00:00
    /*@JsonProperty("createddate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime createdDate;


    @JsonProperty("modifieddate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private String modifiedDate;*/

}

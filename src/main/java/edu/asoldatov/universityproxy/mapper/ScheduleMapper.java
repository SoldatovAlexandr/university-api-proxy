package edu.asoldatov.universityproxy.mapper;

import edu.asoldatov.universityproxy.common.ScheduleType;
import edu.asoldatov.universityproxy.dto.client.ClientLessonDto;
import edu.asoldatov.universityproxy.dto.client.ClientTypeDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuLessonDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuTypeDto;
import edu.asoldatov.universityproxy.exception.TransformException;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ClientLessonDto to(OmstuLessonDto dto) {
        return ClientLessonDto.builder()
                .auditorium(dto.getAuditorium())
                .auditoriumOid(dto.getAuditoriumOid())
                .beginLesson(dto.getBeginLesson())
                .building(dto.getBuilding())
                .buildingOid(dto.getBuildingOid())
                .date(dto.getDate())
                .dayOfWeek(dto.getDayOfWeek())
                .discipline(dto.getDiscipline())
                .disciplineOid(dto.getDisciplineOid())
                .endLesson(dto.getEndLesson())
                .groupOid(dto.getGroupOid())
                .kindOfWork(dto.getKindOfWork())
                .lecturer(dto.getLecturer())
                .lecturerOid(dto.getLecturerOid())
                .lecturerRank(dto.getLecturerRank())
                .parentSchedule(dto.getParentSchedule())
                .stream(getStream(dto))
                .build();
    }

    public ClientTypeDto to(OmstuTypeDto dto) {
        return ClientTypeDto.builder()
                .id(dto.getId())
                .label(dto.getLabel())
                .description(dto.getDescription())
                .type(ScheduleType.getByValue(dto.getType()))
                .build();
    }

    private String getStream(OmstuLessonDto dto) {
        if (dto.getStream() != null) {
            return dto.getStream();
        }
        if (dto.getGroup() != null) {
            return dto.getGroup();
        }
        if (dto.getSubGroup() != null) {
            return dto.getSubGroup();
        }
        throw new TransformException("Empty stream value.");
    }
}

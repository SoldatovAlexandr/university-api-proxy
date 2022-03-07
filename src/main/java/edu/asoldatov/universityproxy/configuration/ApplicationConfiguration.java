package edu.asoldatov.universityproxy.configuration;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.parser.mapper.CourseWorkGradeMapper;
import edu.asoldatov.universityproxy.parser.mapper.DisciplineGradeMapper;
import edu.asoldatov.universityproxy.parser.mapper.GradeMapper;
import edu.asoldatov.universityproxy.parser.mapper.PracticeGradeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Map<GradeType, GradeMapper> gradeMappers(
            CourseWorkGradeMapper courseWorkGradeMapper,
            DisciplineGradeMapper disciplineGradeMapper,
            PracticeGradeMapper practiceGradeMapper
    ) {
        return Map.of(
                GradeType.EXAM, disciplineGradeMapper,
                GradeType.TEST, disciplineGradeMapper,
                GradeType.DIF_TEST, disciplineGradeMapper,
                GradeType.COURSE_WORK, courseWorkGradeMapper,
                GradeType.STATE_EXAM, practiceGradeMapper,
                GradeType.PRACTICE, practiceGradeMapper
        );
    }
}

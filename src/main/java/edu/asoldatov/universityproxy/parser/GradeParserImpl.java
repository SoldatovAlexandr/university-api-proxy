package edu.asoldatov.universityproxy.parser;

import edu.asoldatov.universityproxy.common.GradeType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeBookDto;
import edu.asoldatov.universityproxy.dto.client.grade.GradeDto;
import edu.asoldatov.universityproxy.dto.client.grade.StudentInfoDto;
import edu.asoldatov.universityproxy.dto.client.grade.TermDto;
import edu.asoldatov.universityproxy.exception.TransformException;
import edu.asoldatov.universityproxy.parser.mapper.GradeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class GradeParserImpl implements GradeParser {

    private static final String STUDENT_INFO_CSS_QUERY = "div.jumbotron.bs-example-bg-classes";
    private static final String TERMS_CSS_QUERY = "div.row.tab-pane";
    private static final String PART_TERM_CSS_QUERY = "div.col-xs-12";
    private static final String GRADE_CSS_QUERY = "tr";
    private static final String GRADE_PARAM_CSS_QUERY = "td";
    private static final String FULL_NAME_CSS_QUERY = "h1";
    private static final String STUDENT_PARAM_CSS_QUERY = "p";
    private static final String STUDENT_PARAM_SPLITERATOR = "(</b>|<br>)";
    private static final String PROMETHEUS_PARAM_SPLITERATOR = "([,:])";
    private static final int LIBRARY_CARD_START_INDEX = 5;
    private static final int LIBRARY_CARD_FINISH_INDEX = 10;
    private static final String TAG_B = "<b>";

    private final Map<GradeType, GradeMapper> gradeMappers;

    @Override
    public GradeBookDto parse(final Document document) {
        try {
            return GradeBookDto.builder()
                    .student(getStudentInfo(document.selectFirst(STUDENT_INFO_CSS_QUERY)))
                    .terms(getTerms(document.select(TERMS_CSS_QUERY)))
                    .build();
        } catch (Exception exception) {
            throw new TransformException("Can not parse grade page.", exception);
        }
    }

    private List<TermDto> getTerms(Elements elements) {
        List<TermDto> terms = new ArrayList<>();
        for (Element element : elements) {
            terms.add(getTerm(element));
        }
        return terms;
    }

    private TermDto getTerm(Element element) {
        return TermDto.builder()
                .grades(getPartTerm(element.select(PART_TERM_CSS_QUERY)))
                .build();
    }

    private List<GradeDto> getPartTerm(Elements elements) {
        List<GradeDto> grades = new ArrayList<>();
        for (Element element : elements) {
            grades.addAll(getGrades(element, GradeType.getTypeByValue(element.text())));
        }
        return grades;
    }

    private Collection<GradeDto> getGrades(Element element, GradeType type) {
        Elements gradeElements = element.select(GRADE_CSS_QUERY);
        List<GradeDto> grades = new ArrayList<>();
        for (int i = 1; i < gradeElements.size(); i++) {
            grades.add(getGrade(gradeElements.get(i), type));
        }
        return grades;
    }

    private GradeDto getGrade(Element element, GradeType type) {
        List<String> params = element.select(GRADE_PARAM_CSS_QUERY)
                .stream()
                .map(v -> v.text().trim())
                .collect(Collectors.toList());
        return gradeMappers.get(type).to(params, type);
    }

    private StudentInfoDto getStudentInfo(Element element) {
        final List<String> studentParams = getStudentParams(element.selectFirst(STUDENT_PARAM_CSS_QUERY));
        final List<String> prometheusParams = getPrometheusParams(studentParams.get(4));
        return StudentInfoDto.builder()
                .fullName(element.selectFirst(FULL_NAME_CSS_QUERY).text().trim())
                .gradeBookNumber(studentParams.get(0))
                .speciality(studentParams.get(1))
                .group(studentParams.get(2))
                .learningFormat(studentParams.get(3))
                .login(prometheusParams.get(1))
                .password(prometheusParams.get(3))
                .libraryCard(getLibraryCard(studentParams.get(5)))
                .build();
    }

    private List<String> getPrometheusParams(String s) {
        return Arrays.stream(s.split(PROMETHEUS_PARAM_SPLITERATOR))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private List<String> getStudentParams(Element element) {
        return Arrays.stream(element.toString().split(STUDENT_PARAM_SPLITERATOR))
                .filter(s -> !s.contains(TAG_B))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private String getLibraryCard(String line) {
        return line.substring(LIBRARY_CARD_START_INDEX, LIBRARY_CARD_FINISH_INDEX);
    }
}

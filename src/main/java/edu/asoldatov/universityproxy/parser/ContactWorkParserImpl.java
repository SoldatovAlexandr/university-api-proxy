package edu.asoldatov.universityproxy.parser;

import edu.asoldatov.universityproxy.dto.client.contactwork.DisciplineDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.ResponseTaskDto;
import edu.asoldatov.universityproxy.exception.TransformException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactWorkParserImpl implements ContactWorkParser {

    private static final String DISCIPLINE_CSS_QUERY = "tbody";
    private static final String TASK_CSS_QUERY = "div.container";
    private static final String TR_CSS_QUERY = "tr";
    private static final String TD_CSS_QUERY = "td";
    private static final String LINK_CSS_QUERY = "a";
    private static final String ATTRIBUTE_CSS_QUERY = "href";

    @Override
    public List<DisciplineDto> parseToDiscipline(Document document) {
        try {
            Element element = document.selectFirst(DISCIPLINE_CSS_QUERY);
            Elements elements = element.select(TR_CSS_QUERY);

            List<DisciplineDto> disciplines = new ArrayList<>();

            for (int i = 1; i < elements.size(); i++) {
                disciplines.add(getDiscipline(elements.get(i)));
            }

            return disciplines;
        } catch (Exception exception) {
            throw new TransformException("Can not parse discipline page.", exception);
        }
    }

    @Override
    public List<ResponseTaskDto> parseToTask(Document document) {
        try {
            Element element = document.selectFirst(TASK_CSS_QUERY);
            Elements elements = element.select(TR_CSS_QUERY);

            List<ResponseTaskDto> tasks = new ArrayList<>();

            for (int i = 1; i < elements.size(); i++) {
                tasks.add(getTask(elements.get(i)));
            }

            return tasks;
        } catch (Exception exception) {
            throw new TransformException("Can not parse tasks page.", exception);
        }
    }

    private ResponseTaskDto getTask(Element element) {
        List<Element> elementList = new ArrayList<>(element.select(TD_CSS_QUERY));
        Element link = elementList.get(2).selectFirst(LINK_CSS_QUERY);
        String url = link == null ? StringUtils.EMPTY : link.attr(ATTRIBUTE_CSS_QUERY);

        return ResponseTaskDto.builder()
                .comment(elementList.get(1).text())
                .file(elementList.get(2).text())
                .date(elementList.get(3).text())
                .teacher(elementList.get(4).text())
                .url(url)
                .build();
    }

    private DisciplineDto getDiscipline(Element element) {
        List<Element> elements = new ArrayList<>(element.select(TD_CSS_QUERY));
        return DisciplineDto.builder()
                .name(elements.get(0).text())
                .teacher(elements.get(1).text())
                .numberOfTasks(elements.get(2).text())
                .path(elements.get(3).selectFirst(LINK_CSS_QUERY).attr(ATTRIBUTE_CSS_QUERY))
                .build();
    }
}

package edu.asoldatov.universityproxy.searcher.impl;

import edu.asoldatov.universityproxy.configuration.OmstuProperties;
import edu.asoldatov.universityproxy.dto.client.SearchLessonDto;
import edu.asoldatov.universityproxy.dto.client.SearchTypeDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuLessonDto;
import edu.asoldatov.universityproxy.dto.omstu.OmstuTypeDto;
import edu.asoldatov.universityproxy.exception.IntegrationException;
import edu.asoldatov.universityproxy.exception.NotFoundException;
import edu.asoldatov.universityproxy.searcher.ScheduleSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleSearcherImpl implements ScheduleSearcher {

    private static final String SEARCH_DATE_FORMAT = "yyyy.MM.dd";

    private final RestTemplate restTemplate;
    private final OmstuProperties properties;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SEARCH_DATE_FORMAT);

    @Override
    public List<OmstuLessonDto> search(final SearchLessonDto request) {
        try {
            final String url = properties.getUrl() + properties.getSchedule();
            final OmstuLessonDto[] response = searchLessons(url, request);
            if (response == null) {
                throw new NotFoundException("Null response.");
            }
            return Arrays.asList(response.clone());
        } catch (RestClientException exception) {
            throw new IntegrationException(String.format("Can not find schedule with request [%s]", request));
        }
    }


    @Override
    public List<OmstuTypeDto> search(final SearchTypeDto request) {
        try {
            final String url = properties.getUrl() + properties.getType();
            final OmstuTypeDto[] response = searchTypes(url, request);
            if (response == null) {
                throw new NotFoundException("Null response.");
            }
            return Arrays.asList(response.clone());
        } catch (RestClientException exception) {
            throw new IntegrationException(String.format("Can not find types with request [%s]", request));
        }
    }

    private OmstuTypeDto[] searchTypes(final String url, final SearchTypeDto request) {
        final String type = request.getType().getValue();
        final String value = request.getValue();
        return restTemplate.getForObject(url, OmstuTypeDto[].class, value, type);
    }


    private OmstuLessonDto[] searchLessons(final String url, final SearchLessonDto request) {
        final String type = request.getType().getValue();
        final String start = formatter.format(request.getStart());
        final String finish = formatter.format(request.getFinish());
        final Integer oid = request.getOid();
        return restTemplate.getForObject(url, OmstuLessonDto[].class, type, oid, start, finish);
    }
}

package edu.asoldatov.universityproxy.service.impl;

import edu.asoldatov.universityproxy.common.DataType;
import edu.asoldatov.universityproxy.dto.client.grade.GradeBookDto;
import edu.asoldatov.universityproxy.parser.GradeParser;
import edu.asoldatov.universityproxy.searcher.EduCabSearcher;
import edu.asoldatov.universityproxy.service.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    private final EduCabSearcher eduCabSearcher;
    private final GradeParser gradeParser;

    @Override
    public GradeBookDto search(String cookie) {
        final Document document = eduCabSearcher.search(cookie, DataType.GRADES);
        return gradeParser.parse(document);
    }
}

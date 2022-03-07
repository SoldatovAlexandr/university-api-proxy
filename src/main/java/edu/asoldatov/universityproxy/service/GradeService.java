package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.grade.GradeBookDto;

public interface GradeService {

    GradeBookDto search(String cookie);
}

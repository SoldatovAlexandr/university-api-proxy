package edu.asoldatov.universityproxy.service;

import edu.asoldatov.universityproxy.dto.client.contactwork.DisciplineDto;
import edu.asoldatov.universityproxy.dto.client.contactwork.ResponseTaskDto;

import java.util.List;

public interface ContactWorkService {

    List<DisciplineDto> search(String token);

    List<ResponseTaskDto> search(String token, String path);
}

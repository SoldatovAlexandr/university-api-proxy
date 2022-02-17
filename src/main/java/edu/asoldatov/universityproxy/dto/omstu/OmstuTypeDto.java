package edu.asoldatov.universityproxy.dto.omstu;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OmstuTypeDto {
    private Integer id;
    private String label;
    private String description;
    private String type;
}

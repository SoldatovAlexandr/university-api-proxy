package edu.asoldatov.universityproxy.dto.client;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginDto {

    private String login;
    private String password;

}

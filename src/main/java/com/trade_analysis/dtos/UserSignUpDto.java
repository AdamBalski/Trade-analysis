package com.trade_analysis.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserSignUpDto {
    private String username;
    private String email;
    private String password1;
    private String password2;

    public static UserSignUpDto empty() {
        return new UserSignUpDto("", "", "", "");
    }
}

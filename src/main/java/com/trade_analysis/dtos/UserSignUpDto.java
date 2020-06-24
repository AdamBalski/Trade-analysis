package com.trade_analysis.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class UserSignUpDto {
    private String username;
    private String email;
    private String password1;
    private String password2;

    public static UserSignUpDto empty() {
        return new UserSignUpDto("", "", "", "");
    }
}

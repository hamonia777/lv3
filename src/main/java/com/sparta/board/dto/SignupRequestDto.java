package com.sparta.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "이름은 4~10자로 알파벳 소문자, 숫자로 구성해주세요.")
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+|<>?:{}])[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,15}$",
            message = "비밀번호는 8~15자로 알파벳 대소문자, 숫자, 특수문자로 구성해주세요.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}

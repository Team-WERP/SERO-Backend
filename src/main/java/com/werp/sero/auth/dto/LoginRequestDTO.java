package com.werp.sero.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDTO {
    @NotBlank
    @Schema(description = "이메일", example = "admin@werp.com")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호", example = "kang")
    private String password;
}
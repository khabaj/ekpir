package com.khabaj.ekpir.controllers.dto;

import com.khabaj.ekpir.controllers.validators.FieldsValueMatch;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@FieldsValueMatch.List(
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!"
        ))
public class RegistrationDto {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
    private String verifyPassword;

    public RegistrationDto() {
    }

    public RegistrationDto(String username, String password, String verifyPassword) {
        this.username = username;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}

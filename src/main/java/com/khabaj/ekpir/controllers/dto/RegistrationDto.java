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
public class RegistrationDto extends AccountDto {

    @NotNull
    @NotEmpty
    private String password;
    private String verifyPassword;

    public RegistrationDto() {
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

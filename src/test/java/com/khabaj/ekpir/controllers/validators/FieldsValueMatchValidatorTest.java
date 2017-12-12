package com.khabaj.ekpir.controllers.validators;

import com.khabaj.ekpir.controllers.dto.RegistrationDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FieldsValueMatchValidatorTest {

    @Mock
    FieldsValueMatch constraintAnnotation;
    FieldsValueMatchValidator validator = new FieldsValueMatchValidator();

    @Before
    public void setUp() {
        Mockito.when(constraintAnnotation.field()).thenReturn("password");
        Mockito.when(constraintAnnotation.fieldMatch()).thenReturn("verifyPassword");
        validator.initialize(constraintAnnotation);
    }

    @Test
    public void isValid_whenGivenPasswordsMatching_thenReturnTrue() {

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("pass");
        registrationDto.setVerifyPassword("pass");

        Assert.assertEquals(true, validator.isValid(registrationDto, null));
    }

    @Test
    public void isValid_whenGivenPasswordsNotMatching_thenReturnFalse() {

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setPassword("pass");
        registrationDto.setVerifyPassword("anotherPassword");

        Assert.assertEquals(false, validator.isValid(registrationDto, null));
    }
}
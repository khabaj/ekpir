package com.khabaj.ekpir.controllers.validators;

import com.khabaj.ekpir.controllers.dto.RegistrationRequest;
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

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("pass");
        registrationRequest.setVerifyPassword("pass");

        Assert.assertEquals(true, validator.isValid(registrationRequest, null));
    }

    @Test
    public void isValid_whenGivenPasswordsNotMatching_thenReturnFalse() {

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("pass");
        registrationRequest.setVerifyPassword("anotherPassword");

        Assert.assertEquals(false, validator.isValid(registrationRequest, null));
    }
}
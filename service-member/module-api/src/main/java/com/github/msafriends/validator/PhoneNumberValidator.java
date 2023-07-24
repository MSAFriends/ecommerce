package com.github.msafriends.validator;

import com.github.msafriends.validator.anntations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String regex = "^\\d{2,3}-\\d{3,4}-\\d{4}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.matches(regex);
    }
}

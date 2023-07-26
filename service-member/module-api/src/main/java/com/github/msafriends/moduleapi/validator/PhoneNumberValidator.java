package com.github.msafriends.moduleapi.validator;

import com.github.msafriends.moduleapi.validator.anntations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String regex = "^\\d{2,3}-\\d{3,4}-\\d{4}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber.matches(regex);
    }
}

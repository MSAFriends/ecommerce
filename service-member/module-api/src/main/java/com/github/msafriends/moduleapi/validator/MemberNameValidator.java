package com.github.msafriends.moduleapi.validator;

import com.github.msafriends.moduleapi.validator.anntations.ValidMemberName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MemberNameValidator implements ConstraintValidator<ValidMemberName, String> {
    private final int MIN_NAME_SIZE = 5;
    private final int MAX_NAME_SIZE = 20;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            return false;
        }
        return MIN_NAME_SIZE <= name.length() && name.length() <= MAX_NAME_SIZE;
    }
}

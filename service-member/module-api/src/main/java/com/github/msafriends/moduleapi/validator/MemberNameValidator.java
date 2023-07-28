package com.github.msafriends.moduleapi.validator;

import com.github.msafriends.moduleapi.validator.annotations.ValidMemberName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MemberNameValidator implements ConstraintValidator<ValidMemberName, String> {
    private static final String NAME_REGEX = "^[a-z]+$";
    private static final int MIN_NAME_SIZE = 5;
    private static final int MAX_NAME_SIZE = 20;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return false;
        }
        if (!Pattern.matches(NAME_REGEX, name)) {
            return false;
        }
        return MIN_NAME_SIZE <= name.length() && name.length() <= MAX_NAME_SIZE;
    }
}

package com.github.msafriends.moduleapi.validator.anntations;

import com.github.msafriends.moduleapi.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "The format of the password is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

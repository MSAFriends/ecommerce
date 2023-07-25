package com.github.msafriends.moduleapi.validator.anntations;

import com.github.msafriends.moduleapi.validator.MemberNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MemberNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMemberName {
    String message() default "The format of the name is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


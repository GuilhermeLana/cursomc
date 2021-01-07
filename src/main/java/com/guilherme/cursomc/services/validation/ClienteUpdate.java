package com.guilherme.cursomc.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ClienteUpdatorValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteUpdate {

    String message() default"Errode validação";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}
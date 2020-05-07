package com.euax.desafio.services.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = TaskUpdateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskUpdate {
	
	String message() default "Erro de validação ao alterar task";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}

package com.bancoexterior.parametros.monedas.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.bancoexterior.parametros.monedas.config.Codigos.Annotation;





@Documented
@Constraint(validatedBy = NotEmptyValidate.class)
@Target( { METHOD, FIELD ,ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ANotEmptyValidate {
    String message() default Annotation.OBJECTDEFAULT;
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
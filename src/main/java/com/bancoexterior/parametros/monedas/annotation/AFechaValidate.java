package com.bancoexterior.parametros.monedas.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.bancoexterior.parametros.monedas.config.Codigos.Annotation;
import com.bancoexterior.parametros.monedas.config.Codigos.ParamConfig;




@Documented
@Constraint(validatedBy = FechaValidate.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface AFechaValidate {
    String message() default Annotation.FECHADEFAULT;
    String formato() default ParamConfig.IDSESIONVALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
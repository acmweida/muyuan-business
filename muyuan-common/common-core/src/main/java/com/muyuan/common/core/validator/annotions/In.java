package com.muyuan.common.core.validator.annotions;

import com.muyuan.common.core.validator.impl.AtLeastOneNotEmptyValidator;
import com.muyuan.common.core.validator.impl.InValidator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = InValidator.class)
@Documented
public @interface In {

    String[] value();

    String message() default "参数不在范围内";

}
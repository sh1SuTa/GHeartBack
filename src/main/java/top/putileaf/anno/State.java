package top.putileaf.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import top.putileaf.validation.StateValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented//元注解
@Constraint(validatedBy = {StateValidation.class})//指定提供校验规则的类
@Target({  FIELD })//元注解,可以作用哪些类
@Retention(RUNTIME)//元注解,保留到运行时阶段
public @interface State {
    //提供校验失败后的提示信息
    String message() default "state的值只能是已发布或者草稿";
    //指定分组
    Class<?>[] groups() default { };
    //负载，获取到State注解的附加信息
    Class<? extends Payload>[] payload() default { };
}

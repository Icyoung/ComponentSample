package com.icy.librouter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Icy on 2017/8/11.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface TargetClassName {
    String value();
}

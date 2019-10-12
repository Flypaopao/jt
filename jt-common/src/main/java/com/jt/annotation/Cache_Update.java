package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jt.enums.KEY_ENUM;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache_Update {
	String key()		default "";
	KEY_ENUM keyType()  default KEY_ENUM.AUTO;
	int secondes()		default 0;
}

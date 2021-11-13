package com.studz.repository;

import java.lang.annotation.*;

@Target({
        ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDateSource {
    String dataSource() default "";// 数据源
}

package com.atguigu.ct.common.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: 2020/6/29 这里写注解是为了节省代码，减少put时候的代码重复性，可以通过put一个obj来做，更具通用性。
//@SuppressWarnings()仿照这个注解来写！
//ElementType.TYPE 表示只能加在 类上面！
//RetentionPolicy.RUNTIME 表示只能在运行时用！
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableRef {
    String value();
}

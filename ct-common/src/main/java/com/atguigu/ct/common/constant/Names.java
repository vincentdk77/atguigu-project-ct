package com.atguigu.ct.common.constant;

import com.atguigu.ct.common.bean.Val;

/**
 * 名称常量枚举类
 */
public enum Names implements Val {
    NAMESPACE("ct")
    ,TABLE("ct:calllog")
    ,CF_CALLER("caller")//主叫列簇 todo 分列簇存储，提高主叫与被叫数据单独查询的性能
    ,CF_CALLEE("callee")//被叫列簇
    ,CF_INFO("info")
    ,TOPIC("ct");


    private String name;

    private Names( String name ) {
        this.name = name;
    }


    public void setValue(Object val) {
       this.name = (String)val;
    }

    public String getValue() {
        return name;
    }
}

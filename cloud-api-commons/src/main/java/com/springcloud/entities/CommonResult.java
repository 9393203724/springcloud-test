package com.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 数据库对应实体
@AllArgsConstructor // 全参
@NoArgsConstructor // 空参
public class CommonResult<T> {
    //404 异常编码 not_found 异常信息
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message){
        this(code, message, null);
    }
}
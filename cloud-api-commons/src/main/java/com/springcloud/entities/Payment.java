package com.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // 数据库对应实体
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 空参构造
public class Payment implements Serializable {
    private Long id;
    private String serial;
}

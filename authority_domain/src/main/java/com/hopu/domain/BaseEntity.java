package com.hopu.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author JYF
 * @create 2020/12/7 15:52
 *
 * 基础实体类，定义通用属性，可以不继承
 */
@Data
public class BaseEntity implements Serializable {
    private String id;
    private Date createTime;
    private Date updateTime;
}

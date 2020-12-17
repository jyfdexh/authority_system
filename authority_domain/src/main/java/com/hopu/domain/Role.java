package com.hopu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author JYF
 * @create 2020/12/7 15:56
 * 角色表
 */
@Data
@TableName("t_role")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String role; // 角色名称
    private String remark; // 备注


}
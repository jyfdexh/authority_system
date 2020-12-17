package com.hopu.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author JYF
 * @create 2020/12/7 15:59
 * 角色-菜单表
 */
@TableName("t_role_menu")
@Data
@SuppressWarnings( "all" )
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private String menuId;  // 菜单id
    private String roleId;  // 角色id
}
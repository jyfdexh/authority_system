package com.hopu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hopu.domain.Menu;
import com.hopu.domain.Role;
import com.hopu.mapper.RoleMapper;
import com.hopu.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public void setMenu(String roleId, List<Menu> menus) {

    }
}

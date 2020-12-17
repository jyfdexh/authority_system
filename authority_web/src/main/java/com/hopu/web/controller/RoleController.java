package com.hopu.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hopu.domain.*;
import com.hopu.service.IMenuService;
import com.hopu.service.IRoleMenuService;
import com.hopu.service.IRoleService;
import com.hopu.service.IUserRoleService;
import com.hopu.utils.PageEntity;
import com.hopu.utils.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.hopu.utils.ResponseEntity.error;
import static com.hopu.utils.ResponseEntity.success;

/**
 * @Author JYF
 * @create 2020/12/10 8:52
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IUserRoleService userRoleService;
    //跳转至角色主页
    @RequestMapping("/toRoleList")
    public String toRoleList() {
        return "admin/role/role_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public IPage<Role> listRole(Integer page, Integer limit, Role role) {
        Page<Role> rolePage = new Page<>( page, limit );

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank( role.getRole() )) {
            roleQueryWrapper.like( "role", role.getRole() );
        }

        IPage<Role> roleIPage = roleService.page( rolePage,roleQueryWrapper);
        return roleIPage;
    }

    //跳转至角色添加页面add
    @RequestMapping("/toAddRole")
    public String toAddRole() {
        return "admin/role/role_add";
    }

    @RequestMapping("/addRole")
    @ResponseBody
    public ResponseEntity addRole(Role role) {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq( "role", role.getRole() );
        Role role1 = roleService.getOne( roleQueryWrapper );
        if(null!=role1){
            return ResponseEntity.error( "角色名重复！" );
        }

        role.setId( UUID.randomUUID().toString().replace( "-", "" ).toLowerCase() );
        role.setCreateTime(new Date(  ) );
        roleService.save( role );
        return ResponseEntity.success();
    }

    //跳转至角色修改页面update
    @RequestMapping("/toUpdateRole")
    public String toUpdateRole(Role role,Model model) {
        Role roleById = roleService.getById( role.getId() );
        model.addAttribute( "roleById", roleById );
        return "admin/role/role_update";
    }

    @RequestMapping("/updateRole")
    @ResponseBody
    public ResponseEntity updateRole(Role role) {
        role.setUpdateTime( new Date() );
        boolean b = roleService.updateById( role );
        System.out.println(b);
        return ResponseEntity.success();
    }

    //跳转至角色删除页面delete
    @ResponseBody
    @RequestMapping("/deleteRole")
    public ResponseEntity deleteRole(@RequestBody List<Role> roles) throws Exception {
        try {
            List<String> list = new ArrayList<String>();
            for (Role role : roles) {
                if ("root".equals(role.getRole())) {
                    throw new Exception("root角色不能被删除");
                }
                list.add(role.getId());
            }
            roleService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        return success();
    }

    //跳转至设置角色权限页面
    @RequestMapping("/toSetMenu")
    public String toSetMenu(Role role, Model model) {
        //只拿到了role的id    content : $(this).attr("data-url")+"?id="+id
        model.addAttribute( "role", role );
        return "admin/role/role_setMenu";
    }
    // 分配并保存权限
    @RequestMapping("/setMenu")
    @ResponseBody
    public ResponseEntity save(String roleId,@RequestBody List<Menu> menus){
//        roleService.setMenu(roleId,menus);

        // 避免重复或者无法删除
        // 先清除与当前角色管理的所有权限，然后再重新赋值权限
        roleMenuService.remove( new QueryWrapper<RoleMenu>().eq( "role_id", roleId ) );
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        menus.forEach(menu -> {
            roleMenu.setMenuId(menu.getId());
            roleMenuService.save(roleMenu);
        });
        // 先清除与当前角色管理的所有权限，然后再重新赋值权限
//        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
//
//        menus.forEach(menu -> {
//            RoleMenu roleMenu =new RoleMenu();
//            roleMenu.setRoleId(roleId);
//            roleMenu.setMenuId(menu.getId());
//            roleMenuService.save(roleMenu);
//        });
        return success();
    }

    @RequestMapping("/roleList")
    @ResponseBody
    public PageEntity list(String userId){
        // 先查询指定用户已经有哪些角色
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));

        // 查询所有角色信息
        List<Role> list = roleService.list();

        // 判断用户哪些角色已经绑定，添加LAY_CHECKED字段为true
//        list.forEach(role -> {
//            List<String> roleIds = userRoleList.stream().map(userRole -> userRole.getRoleId()).collect( Collectors.toList());
//            if(roleIds.contains(role.getId())){
//                role.setLAY_CHECKED(true);
//            }
//        });
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        list.forEach(role -> {
            // 先需要把对象转换为JSON格式
//            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(role));
            final JSONObject jsonObject = (JSONObject) JSON.toJSON( role );
            // 判断是否已经有了对应的权限
            List<String> roleIds = userRoleList.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
            if(roleIds.contains(role.getId())){
                jsonObject.put("LAY_CHECKED",true);
            }
            jsonObjects.add(jsonObject);
        });

        return new PageEntity(jsonObjects.size(),jsonObjects);
//        return new PageEntity(list.size(),list);
    }

}

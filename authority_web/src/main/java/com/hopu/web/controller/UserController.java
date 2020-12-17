package com.hopu.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hopu.domain.Role;
import com.hopu.domain.User;
import com.hopu.service.IUserService;
import com.hopu.utils.ResponseEntity;
import com.hopu.utils.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.hopu.utils.ResponseEntity.error;
import static com.hopu.utils.ResponseEntity.success;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/toUserList")
    @RequiresPermissions("user:list")
    public String toUserList() {
        return "admin/user/user_list";
    }

    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("user:list")
    public IPage<User> listPage(Integer page, Integer limit, User user, Model model) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (null != user) {
            if (StringUtils.isNotBlank( user.getUserName() )) {
                userQueryWrapper.like( "user_name", user.getUserName() );
            }
            if (StringUtils.isNotBlank( user.getTel() )) {
                userQueryWrapper.like( "tel", user.getTel() );
            }
            if (StringUtils.isNotBlank( user.getEmail() )) {
                userQueryWrapper.like( "email", user.getEmail() );
            }
        }

        IPage<User> userIPage = userService.page( new Page<>( page, limit ), userQueryWrapper );

        return userIPage;
    }

    //跳转至添加用户页面
    @RequestMapping("/toUserAdd")
    @RequiresPermissions("user:add")
    public String toUserAdd() {
        return "admin/user/user_add";
    }

    /**
     * 添加用户
     */
    @ResponseBody
    @RequestMapping("/addUser")
    @RequiresPermissions("user:add")
    public ResponseEntity addUser(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq( "user_name", user.getUserName() );
        User user2 = userService.getOne( userQueryWrapper );
        if (null != user2) {
            return error( "用户名已存在" );
//            public static ResponseEntity error(String msg){
//                ResponseEntity entity = new ResponseEntity();
//                entity.result = false;
//                entity.msg = msg;
//                return entity;
//            }
        }
        user.setId( UUID.randomUUID().toString().replace( "-", "" ).toLowerCase().substring( 0,3 ) );
        user.setSalt( UUID.randomUUID().toString().replace( "-", "" ).toLowerCase().substring( 0,3 ) );
        user.setCreateTime( new Date() );
        ShiroUtils.encPass( user );
        userService.save( user );
        return success();
//        public static ResponseEntity success(){
//            return new ResponseEntity();
//           ResponseEntity() -->       private boolean result = true;
//                                      private String msg = "操作成功";
//                                      private Object data;
//        }
    }

    //跳转至修改更新用户页面

    @RequestMapping("/toUserUpdate")
    @RequiresPermissions("user:update")
    public String toUserUpdate(User user,Model model) {
        User userById = userService.getById( user.getId() );
        model.addAttribute( "userById", userById );
        return "admin/user/user_update";
    }
    //修改更新用户

    @RequestMapping("/updateUser")
    @RequiresPermissions("user:update")
    @ResponseBody
    public ResponseEntity updateUser(User user) {
        ShiroUtils.encPass( user );
        user.setUpdateTime( new Date() );
        boolean b = userService.updateById( user );
        System.out.println(b);
        return success();
    }
    /**
     * 根据多个id删除
     */
//    @Test
//    public void deleteBatchIds(){
//        List<Long> ids = Arrays.asList(1241614720021913633L,1241614720021913667L);
//        int rows = userService.removeByIds(ids);
//        System.out.println("删除条数："+rows);
//    }


    /**
     * 删除（支持批量删除）
     */
    @ResponseBody
    @RequestMapping("/deleteUser")
    @RequiresPermissions("user:delete")
    public ResponseEntity delete(@RequestBody ArrayList<User> users){
        try {
            List<String> list = new ArrayList<String>();
            for (User user : users) {
                if ("root".equals(user.getUserName())) {
                    throw new Exception("root账号不能被删除");
                }
                list.add(user.getId());
            }
            userService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        return success();
    }

    // 跳转到用户角色分配页面
    @RequestMapping("/toSetRole")
    @RequiresPermissions("user:setRole")
    public String toSetRolePage(@RequestParam(value = "id") String userId, Model model){
        model.addAttribute("userId",userId);
        return "admin/user/user_setRole";
    }

    // 分配并保存角色
    @RequestMapping("/setRole")
    @RequiresPermissions("user:setRole")
    @ResponseBody
    public ResponseEntity setRole(String userId,@RequestBody List<Role> roles){
        userService.setRole(userId,roles);

        return success();
    }

}

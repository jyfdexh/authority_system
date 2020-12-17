package com.hopu.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hopu.domain.*;
import com.hopu.service.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 三、认证
 认证其实也就是登录,具体步骤如下：

 获取当前的 Subject,调用 SecurityUtils.getSubject();   (Subject：任何可以与应用交互的“用户”；拿到当前”对象“).
 测试当前的用户是否已经被认证. 即是否已经登录. 调用 Subject 的 isAuthenticated()
 若没有被认证, 则把用户名和密码封装为 UsernamePasswordToken 对象
 1). 创建一个表单页面
 2). 把请求提交到 SpringMVC 的 Handler
 3). 获取用户名和密码.
 4). 执行登录: 调用 Subject 的 login(AuthenticationToken) 方法.
 5). 自定义 Realm 的方法, 从数据库中获取对应的记录, 返回给 Shiro.
 实际上需要继承 org.apache.shiro.realm.AuthenticatingRealm 类
 实现 doGetAuthenticationInfo(AuthenticationToken) 方法.
 6). 由 shiro 完成对密码的比对.
 */

/** Controller中的UsernamePasswordToken中的数据传到了这里，所以*/
public class MyRealm extends AuthorizingRealm{
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IUserRoleService userRoleService;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println( "authenticationToken = " + authenticationToken );
        //1 把AuthenticationToken转换成UsernamePasswordToken对象,获取用户名和密码存令牌token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //2 从UsernamePasswordToken对象中获取到username
        String username = token.getUsername();
        //3 调用数据库，从数据库中查询到username对应的用户记录
        User user = userService.getOne(new QueryWrapper<User>().eq( "user_name",username ));

        //5 根据用户的情况信息决定是否抛出AuthenticationException异常
//        if ("monster".equals(username)) {
//            throw new LockedAccountException("用户被锁定");
//        }
        //4 若用户不存在，可以抛出UnknownAccountException异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }

  //6 根据用户的情况，来构建AuthenticationInfo对象返回,AuthenticationInfo是一个接口，通常使用的实现类是SimpleAuthenticationInfo
        // principals：认证的实体信息，//可传username也可传user对象!
        // credentials：密码，
        // credentialsSalt:盐值,
        // realmName:当前realm对象的name，调用父类的getName()方法即可
        Object principals = user;
//     principals = User(userName=root, password=e010a01273a19025e3aa62c9099353bf, salt=9d0743c9a7e245dd85ff87544cbccb08, nickname=root, userImg=, tel=17688561711, sex=-1, email=123456@qq.com, status=on)
        Object credentials = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username+user.getSalt());
        String realmName = getName();
//     realmName = com.hopu.shiro.MyRealm_0
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principals, credentials, credentialsSalt, realmName);
        System.out.println( "info = " + info );
        return info;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//      principalCollection = User(userName=root, password=e010a01273a19025e3aa62c9099353bf, salt=9d0743c9a7e245dd85ff87544cbccb08, nickname=root, userImg=, tel=17688561711, sex=-1, email=123456@qq.com, status=on)
        // 应该先查询用户
        //此例中user1=user=principalCollection
        final User user1 = (User) super.getAvailablePrincipal( principalCollection );
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 根据对应用户，才可以查询他有的角色和权限
        // 先查询角色
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        ArrayList<String> roles = new ArrayList<>();
        userRoleList.forEach(userRole -> {
            Role role = roleService.getById(userRole.getRoleId());
            roles.add(role.getRole());
        });
        // 接着，查询权限
        ArrayList<String> permissions = new ArrayList<>();
        userRoleList.forEach(userRole -> {
            List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", userRole.getRoleId()));
            roleMenuList.forEach(roleMenu -> {
                Menu menu = menuService.getById(roleMenu.getMenuId());//空指针了，测试时数据库放了不存在的menu_id关联上了role_id
                if (null!=menu) {
                    permissions.add(menu.getPermiss());
                }
            });
        });

        // 核心是：返回的simpleAuthorizationInfo对象必须封装对应的角色和权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }


}

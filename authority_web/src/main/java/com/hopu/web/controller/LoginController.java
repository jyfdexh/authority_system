package com.hopu.web.controller;

import com.hopu.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@SuppressWarnings("all")
public class LoginController {

//    @RequestMapping("/login")
//    public String login(User user, Model model, HttpServletRequest request, HttpSession httpSession) {
//        System.out.println( "执行了/user/login" );
////        拿到当前用户对象(Subject：任何可以与应用交互的“用户”；).
//        Subject currentUser = SecurityUtils.getSubject();//currentUser = org.apache.shiro.web.subject.support.WebDelegatingSubject@92d9538
//        if (!currentUser.isAuthenticated()) {
//            //当没有认证的时候讲用户名和密码封装成 UsernamePasswordToken对象
//            UsernamePasswordToken token = new UsernamePasswordToken( user.getUserName(), user.getPassword() );
//            try {
//                //执行登录
//                currentUser.login( token );//token = org.apache.shiro.authc.UsernamePasswordToken - root, rememberMe=false
//                User user1 = (User) currentUser.getPrincipal();//user1 = User(userName=root, password=e010a01273a19025e3aa62c9099353bf, salt=9d0743c9a7e245dd85ff87544cbccb08, nickname=root, userImg=, tel=17688561711, sex=-1, email=123456@qq.com, status=on)
//                currentUser.getSession().setAttribute( "user", user1 );
//                System.out.println( "user1 = " + user1 );
////                return "admin/index";
//            } catch (AuthenticationException ae) {
////                token.getPrincipal()->拿到用户名
//                String msg = "账户[" + token.getPrincipal() + "]的用户名或密码错误！";
//                model.addAttribute( "msg", msg );
//                System.out.println( "msg = " + msg );
////                return "forward:/login.jsp";
//            }
//        }
//        return "redirect:/user/toIndex";
////        return "redirect:/WEB-INF/jsp/admin/index.jsp";
//    }

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "admin/index";
    }
    @RequestMapping("/login")
    public String login2(User user, Model model, HttpServletRequest request, HttpSession httpSession) {
        System.out.println( "执行了/user/login" );
        Subject currentUser = SecurityUtils.getSubject();//currentUser = org.apache.shiro.web.subject.support.WebDelegatingSubject@92d9538
            UsernamePasswordToken token = new UsernamePasswordToken( user.getUserName(), user.getPassword() );
            try {
                //执行登录
                currentUser.login( token );//token = org.apache.shiro.authc.UsernamePasswordToken - root, rememberMe=false
                User user1 = (User) currentUser.getPrincipal();//user1 = User(userName=root, password=e010a01273a19025e3aa62c9099353bf, salt=9d0743c9a7e245dd85ff87544cbccb08, nickname=root, userImg=, tel=17688561711, sex=-1, email=123456@qq.com, status=on)
                request.getSession().setAttribute( "user", user1 );
                System.out.println( "user1 = " + user1 );
                return "/admin/index";
            } catch (AuthenticationException ae) {
//                token.getPrincipal()->拿到用户名
                String msg = "账户[" + token.getPrincipal() + "]的用户名或密码错误！";
                model.addAttribute( "msg", msg );
                System.out.println( "msg = " + msg );
                return "forward:/login.jsp";
            }
//        return "redirect:/WEB-INF/jsp/admin/index.jsp";
    }
}

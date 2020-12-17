package com.hopu.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hopu.domain.Menu;
import com.hopu.domain.Role;
import com.hopu.domain.RoleMenu;
import com.hopu.service.IMenuService;
import com.hopu.service.IRoleMenuService;
import com.hopu.service.IRoleService;
import com.hopu.utils.IconFontUtils;
import com.hopu.utils.PageEntity;
import com.hopu.utils.ResponseEntity;
import com.hopu.utils.UUIDUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.hopu.utils.ResponseEntity.error;
import static com.hopu.utils.ResponseEntity.success;

/**
 * @Author JYF
 * @create 2020/12/10 9:05
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService roleMenuService;

    /**
     * 跳转至菜单主页
     */
    @RequestMapping("/toMenuList")
    @RequiresPermissions( "menu:list" )
    public String toMenuList() {
        return "admin/menu/menu_list";
    }

    /**
     * 三级菜单列表查询(不做分页)
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions( "menu:list" )
    public PageEntity menuList() {
        List<Menu> parentMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", "0" ) );
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        findChildMenus( parentMenuList, menuArrayList );
        return new PageEntity( menuArrayList.size(), menuArrayList );
    }

    /**
     * 私有方法，todo 递归循环查询儿子菜单列表
     */
    private List<Menu> findChildMenus(List<Menu> parentMenuList, ArrayList<Menu> menuArrayList) {
        for (Menu menu : parentMenuList) {
            if (!menuArrayList.contains( menu )) {
                menuArrayList.add( menu );
            }
            List<Menu> childMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", menu.getId() ) );
            menu.setNodes( childMenuList );
            if (childMenuList.size() > 0) {
                menuArrayList = (ArrayList<Menu>) findChildMenus( childMenuList, menuArrayList );
            }
        }
        return menuArrayList;
    }

    /**
     * 跳转至菜单添加页面还做了2个重要的功能：查询父级菜单、查询所有iconFont图标。
     * 这是因为菜单添加页面中父级菜单选项和菜单图标选项需要根据已有的进行选择。
     */
    @RequestMapping("/toMenuAdd")
    @RequiresPermissions( "menu:add" )
    public String toMenuAdd(Model model) {
//        1,查询父级菜单
        List<Menu> parentMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", "0" ) );
        parentMenuList.forEach( menu -> {
            List<Menu> childMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", menu.getId() ) );
            menu.setNodes( childMenuList );
        } );
        // 2、查询所有字体图标(查询所有字体图片class类)
        List<String> allIconFont = IconFontUtils.getIconFont();
        model.addAttribute( "menuList", parentMenuList );
        model.addAttribute( "allIconFont", allIconFont );
        return "admin/menu/menu_add";
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/addMenu")
    @RequiresPermissions( "menu:add" )
    public ResponseEntity addMenu(Menu menu) {
        menu.setId( UUID.randomUUID().toString().replace( "-", "" ).toLowerCase().substring( 0,3 ) );
        menu.setCreateTime( new Date() );
        menuService.save( menu );
        return success();
    }

    /**
     * 向菜单修改页面跳转
     */
    @GetMapping("/toMenuUpdate")
    @RequiresPermissions( "menu:update" )
    public String toUpdatePage(@RequestParam(value = "id") String menuId, Model model){
        // 先查询要修改的菜单信息
        Menu menu = menuService.getById(menuId);

        // 查询图标信息
        List<String> iconFont = IconFontUtils.getIconFont();

        // 1、查询父级目录（不需要查询第三级菜单）
        // 1.1、先查询顶级父目录
        List<Menu> pMenus = menuService.list(new QueryWrapper<Menu>().eq("pid", "0"));
        // 1.2、查询并封装对应的子菜单
        pMenus.forEach(menu2 -> {
            List<Menu> childMenus = menuService.list(new QueryWrapper<Menu>().eq("pid", menu2.getId()));
            menu2.setNodes(childMenus);
        });

        // 数据放在request域对象中
        // pageScope < requestScope < sessionScope < applicationScope
        model.addAttribute("menu",menu);
        model.addAttribute("iconFont",iconFont);
        model.addAttribute("list",pMenus);

        return "admin/menu/menu_update";
    }
    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/updateMenu")
    @RequiresPermissions( "menu:update" )
    public ResponseEntity update(Menu menu){
        menu.setUpdateTime(new Date());
        menuService.updateById(menu);
        return success();
    }
    //跳转至角色删除页面delete
    @ResponseBody
    @RequestMapping("/deleteRole")
    @RequiresPermissions( "menu:delete" )
    public ResponseEntity deleteRole(@RequestBody List<Menu> menus) throws Exception {
        try {
            List<String> list = new ArrayList<String>();
            for (Menu menu : menus) {
                if ("system".equals(menu.getPermiss())) {
                    throw new Exception("system权限不能被删除");
                }
                list.add(menu.getId());
            }
            menuService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        return success();
    }

    @RequestMapping("/MenuListInRole")
    @ResponseBody
    public PageEntity menuList(String roleId){
        // 查询当前角色已经关联了的权限
        List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", roleId));

        // 如果不涉及到子菜单关联
        List<Menu> allMenuList = menuService.list();

        //  此处循环的作用就是为了判断角色已有权限，然后添加一个LAY_CHECKED字段，前端layui表格才能自动勾选
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        allMenuList.forEach(menu -> {
            // 先需要把对象转换为JSON格式
            JSONObject menuJsonObject = JSONObject.parseObject(JSONObject.toJSONString(menu));
            // 判断是否已经有了对应的权限
            List<String> menuIds = roleMenuList.stream().map( RoleMenu::getMenuId ).collect( Collectors.toList());
            if(menuIds.contains(menu.getId())){
                menuJsonObject.put("LAY_CHECKED",true);
            }
            jsonObjects.add(menuJsonObject);
        });

        return new PageEntity(jsonObjects.size(),jsonObjects);
    }
















}


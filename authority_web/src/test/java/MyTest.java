import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hopu.domain.Menu;
import com.hopu.domain.RoleMenu;
import com.hopu.domain.User;
import com.hopu.mapper.UserMapper;
import com.hopu.service.IMenuService;
import com.hopu.service.IRoleMenuService;
import com.hopu.service.IUserService;
import com.hopu.utils.IconFontUtils;
import com.hopu.utils.PageEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author JYF
 * @create 2020/12/8 10:56
 */

//@SuppressWarnings( "all" )
@RunWith(SpringJUnit4ClassRunner.class) // 指定测试运行器
@ContextConfiguration("classpath:applicationContext.xml") // 指定配置文件
public class MyTest {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;

    @Test
    public void t1() {
        userService.list().forEach( System.out::println );
//        System.out.println("1");
    }

    @Test
    public void t2() {
        IPage<User> page = userService.page( new Page<>( 2, 2 ) );
        System.out.println( page );
    }

    //UUID
    @Test
    public void t3() {
        String s = UUID.randomUUID().toString();
        System.out.println( s );
        String s2 = UUID.randomUUID().toString().replace( "-", "" ).toLowerCase();
        System.out.println( s2 );
    }

    @Test
    public void t4() {
        List<Long> ids = Arrays.asList( 1241614720021913633L, 1241614720021913667L );
        int i = userMapper.deleteBatchIds( ids );
//        int deleteBatchIds(@Param("coll") Collection<? extends Serializable > idList);
        boolean b = userService.removeByIds( ids );
//        boolean removeByIds(Collection<? extends Serializable> idList);
//        public boolean removeByIds(Collection<? extends Serializable> idList) {
//            return SqlHelper.delBool(this.baseMapper.deleteBatchIds(idList));
//        }
    }


    /**
     * 递归，找出数组最大的值
     * <p>
     * arrays 数组
     * L      左边界，第一个数
     * R      右边界，数组的长度
     */
    @Test
    public void t5() {
        int[] arrays = {2, 4, 9, 5, 1};
        System.out.println( "公众号：Java3y：" + findMax( arrays, 0, arrays.length - 1 ) );
    }

    public static int findMax(int[] arrays, int L, int R) {

        //如果该数组只有一个数，那么最大的就是该数组第一个值了
        if (L == R) {
            return arrays[L];
        } else {

            int a = arrays[L];
            int b = findMax( arrays, L + 1, R );//找出整体的最大值

            if (a > b) {
                return a;
            } else {
                return b;
            }
        }


    }


    /**
     * 递归练习：使用递归列出windows目录下的所有文件夹及所有文件（不包括隐藏目录及文件）
     */
    @Test
    public void t6() {
        getAllFile( new File( "D:\\微信存储\\WeChat Files\\wxid_m2fyysarx2jr22\\FileStorage\\File\\2020-12" ) );
    }

    public static void getAllFile(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                // 如果获取的File类型是目录，则进行递归调用
                if (f.isDirectory()) {
                    System.out.println( "目录：" + f );
                    getAllFile( f );
                } else {
                    // 如果获取的File类型是文件，则直接打印输出
                    System.out.println( "文件:" + f );
                }
            }
        }
    }

    @Test
    public void t7() {
        Integer i = null;
        System.out.println( i.toString() );
        int controller = 1;
    }

    @Test
    public void t8() {
        List<Menu> menus = getMenus( "0" );
        menus.forEach( System.out::println );
    }

    public List<Menu> getMenus(String pid) {
        List<Menu> menus = menuService.list( new QueryWrapper<Menu>().eq( "pid", pid ) );
        for (Menu m : menus) {
            m.setNodes( getMenus( m.getId() ) );
        }
        return menus;
    }
//***************menus.forEach( System.out::println )为：(1条)************
//    Menu(pid=0, id=06c,
//      nodes=[Menu(pid=06c, id=2,
//           nodes=[Menu(pid=2, id=451, nodes=[]),
//                  Menu(pid=2, id=5cb,nodes=[]),
//                  Menu(pid=2, id=e2e, nodes=[]),
//                  Menu(pid=2, id=ef8,nodes=[])])
//
//            ,Menu(pid=06c, id=8a,
//           nodes=[Menu(pid=8a, id=3d7, nodes=[]),
//                  Menu(pid=8a, id=8b0, nodes=[]),
//                  Menu(pid=8a, id=cb8, nodes=[]),
//                  Menu(pid=8a, id=eb9, nodes=[])]),
//
//            ,Menu(pid=06c, id=d0,
//           nodes=[Menu(pid=d0, id=6b5, nodes=[]),
//                  Menu(pid=d0, id=cbf,nodes=[]),
//                  Menu(pid=d0, id=ec4, nodes=[]])]
//         )


    //**********findChildMenu( pList, menus ).forEach( System.out::println )为:(15条)********
//    Menu(pid=0, id=06d,
//         nodes=[Menu(pid=06d, id=2,
//           nodes=[ Menu(pid=2, id=451, nodes=[]),
//                   Menu(pid=2, id=5cb, nodes=[]),
//                   Menu(pid=2, id=e2e, nodes=[]),
//                   Menu(pid=2, id=ef8, nodes=[])]),
//                Menu(pid=06d, id=8a0,
//           nodes=[ Menu(pid=8a0, id=3d7, nodes=[]),
//                   Menu(pid=8a0, id=8b0, nodes=[]),
//                   Menu(pid=8a0, id=cb8, nodes=[]),
//                   Menu(pid=8a0, id=eb9, nodes=[])]),
//                Menu(pid=06d, id=d05,
//           nodes=[ Menu(pid=d05, id=6b5, nodes=[]),
//                   Menu(pid=d05, id=cbf, nodes=[]),
//                   Menu(pid=d05, id=ec4, nodes=[])])])
//
//    Menu(pid=06d, id=2,
//         nodes=[Menu(pid=2, id=451, nodes=[]),
//                Menu(pid=2, id=5cb, nodes=[]),
//                Menu(pid=2, id=e2e, nodes=[]),
//                Menu(pid=2, id=ef8, nodes=[])])
//    Menu(pid=2, id=451, nodes=[])
//    Menu(pid=2, id=5cb, nodes=[])
//    Menu(pid=2, id=e2e, nodes=[])
//    Menu(pid=2, id=ef8, nodes=[])
//    Menu(pid=06d, id=8a0,
//         nodes=[Menu(pid=8a0, id=3d7, nodes=[]),
//                Menu(pid=8a0, id=8b0, nodes=[]),
//                Menu(pid=8a0, id=cb8, nodes=[]),
//                Menu(pid=8a0, id=eb9, nodes=[])])
//    Menu(pid=8a0, id=3d7, nodes=[])
//    Menu(pid=8a0, id=8b0, nodes=[])
//    Menu(pid=8a0, id=cb8, nodes=[])
//    Menu(pid=8a0, id=eb9, nodes=[])
//    Menu(pid=06d, id=d05,
//         nodes=[Menu(pid=d05, id=6b5, nodes=[]),
//                Menu(pid=d05, id=cbf, nodes=[]),
//                Menu(pid=d05, id=ec4, nodes=[])])
//    Menu(pid=d05, id=6b5, nodes=[])
//    Menu(pid=d05, id=cbf, nodes=[])
//    Menu(pid=d05, id=ec4, nodes=[])
    @Test
    public void t9() {
// 先查询父菜单
        List<Menu> pList = menuService.list( new QueryWrapper<Menu>
                ().eq( "pid", "0" ) );
// 接着，根据父菜单id查询对应的所有子菜单，把子菜单封装到父菜单对象的属性nodes中
// 需求：最终返回的是各个菜单集合
        ArrayList<Menu> menus = new ArrayList<>();
        findChildMenu( pList, menus ).forEach( System.out::println );
// return new PageEntity(menuList.size(),menuList);
//        return new PageEntity(menus.size(),menus);
// // 如果不涉及到子菜单关联
// List<Menu> list = menuService.list();
// return new PageEntity(list.size(),list);
    }

    // 私有方法，循环查询儿子菜单列表
    private List<Menu> findChildMenu(List<Menu> pList, List<Menu> menus) {

        for (Menu menu : pList) {
            if (!menus.contains( menu )) {
                menus.add( menu );
            }
            String pId = menu.getId();
            List<Menu> childList = menuService.list( new QueryWrapper<Menu>().eq( "pid", pId ) );
            menu.setNodes( childList );
            if (childList.size() > 0) { // 递归调用
                menus = findChildMenu( childList, menus );
            }
        }
        return menus;
    }

    @Test
    public void menuList() {
        List<Menu> parentMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", "0" ) );
        ArrayList<Menu> menuArrayList = new ArrayList<>();
        findChildMenus( parentMenuList, menuArrayList );
        System.out.println( menuArrayList );
    }

    /**
     * 私有方法，循环查询儿子菜单列表
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

    @Test
    public void t10() {
        List<Menu> parentMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", "0" ) );
        parentMenuList.forEach( menu -> {
            List<Menu> childMenuList = menuService.list( new QueryWrapper<Menu>().eq( "pid", menu.getId() ) );
            menu.setNodes( childMenuList );
        } );
        parentMenuList.forEach( System.out::println );
    }

    @Test
    public void t11() {
        List<String> iconFont = IconFontUtils.getIconFont();
        System.out.println( "iconFont = " + iconFont );
        iconFont.forEach( System.out::println );
    }

    @Test
    public void t12() {
        String s = UUID.randomUUID().toString();
        if (s.contains( "1" )) {
            int a = 1;
        }
        Boolean flag = true;
    }

    @Test
    public void t13() {
        // 查询当前角色已经关联了的权限
        List<RoleMenu> roleMenuList = roleMenuService.list( new QueryWrapper<RoleMenu>().eq( "role_id", "e7b5123189684533bddfa842f81c1f0d" ) );

        // 如果不涉及到子菜单关联
        List<Menu> list = menuService.list();

        //  此处循环的作用就是为了判断角色已有权限，然后添加一个LAY_CHECKED字段，前端layui表格才能自动勾选
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        list.forEach( menu -> {
            // 先需要把对象转换为JSON格式 （普通）
//            JSONObject jsonObject = JSONObject.parseObject( JSONObject.toJSONString( menu ) );
//            对象转换为JSON格式 （FastJson）
            final JSONObject jsonObject = (JSONObject) JSON.toJSON( menu );
            System.out.println( "jsonObject = " + jsonObject );
//            System.out.println( "jsonObject = " + jsonObject );
            // 判断是否已经有了对应的权限
//            List<String> menuIds = roleMenuList.stream().map( roleMenu -> roleMenu.getMenuId() ).collect( Collectors.toList() );
//            if (menuIds.contains( menu.getId() )) {
//                jsonObject.put( "LAY_CHECKED", true );
//            }
//            jsonObjects.add( jsonObject );
        } );
//        System.out.println( "jsonObjects = " + jsonObjects );
    }

    @Test
    public void t14() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
// 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.println( "squaresList = " + squaresList );//squaresList = [9, 4, 49, 25]

// 获取空字符串的数量
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println( "count = " + count );//count = 2

//limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);//10个数乱序

//sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序：
        Random random2 = new Random();
        random2.ints().limit(10).sorted().forEach(System.out::println);//10个数从小到大排序

//parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
        List<String> strings2 = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
// 获取空字符串的数量
        long count2 = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println( "count2 = " + count2 );//count2 = 2

//Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：

        List<String>strings3 = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings3.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);//筛选列表:        [abc, bc, efg, abcd, jkl]
        String mergedString = strings3.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);//合并字符串:  abc, bc, efg, abcd, jkl
    }

    @Test
    public void t15() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        final Stream<Integer> stream = numbers.stream();
        System.out.println( "stream = " + stream );//stream = java.util.stream.ReferencePipeline$Head@2c07545f
        final Stream<Integer> integerStream = numbers.stream().map( i -> i );
        System.out.println( "integerStream = " + integerStream );//integerStream = java.util.stream.ReferencePipeline$3@32c726ee
        final List<Integer> collect = numbers.stream().map( i -> 2*i ).collect( Collectors.toList() );
        System.out.println( "collect = " + collect );//collect = [6, 4, 4, 6, 14, 6, 10]
    }

    @Test
    //查一个用户的所有角色和所有权限SQL
    public void t16() {
//        select user_name,nickname,trm.role_id,role,menu_id,menu_name,permiss
//        from t_user join t_user_role tur on t_user.id = tur.user_id join t_role tr on tur.role_id = tr.id
//        join t_role_menu trm on tur.role_id = trm.role_id join t_menu tm on trm.menu_id = tm.id;
    }

    @Test
    public void t17() {
        String originalName="abc123.jpg";
        String prefix=originalName.substring(originalName.lastIndexOf(".")+1);
        System.out.println( "prefix = " + prefix );
    }

    @Test
    public void t18() {

    }

    @Test
    public void t19() {

    }
}

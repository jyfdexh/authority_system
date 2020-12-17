import com.hopu.domain.Menu;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JYF
 * @create 2020/12/8 10:56
 */

//@SuppressWarnings( "all" )
@RunWith(SpringJUnit4ClassRunner.class) // 指定测试运行器
@ContextConfiguration("classpath:applicationContext.xml") // 指定配置文件
public class test2{

    @Test
    public void tt() {
        List<String> strings = new ArrayList<>();
        List<Menu> menus = new ArrayList<>();
        System.out.println( "menus = " + menus );

    }


    @Test
    public void t2(){
        /*参数分别是：加密算法、密码、盐值、加密次数*/
        Object result = new SimpleHash("MD5", "123456", "zhangsan", 1024);
        System.out.println(result);

    }
    @Test
    public void t3(){

    }  @Test
    public void t4(){

    }  @Test
    public void t5(){

    }  @Test
    public void t6(){

    }  @Test
    public void t7(){

    }  @Test
    public void t8(){

    }  @Test
    public void t9(){

    }  @Test
    public void t10(){

    }  @Test
    public void t11(){

    }  @Test
    public void t12(){

    }  @Test
    public void t13(){

    }  @Test
    public void t14(){

    }  @Test
    public void t15(){

    }  @Test
    public void t16(){

    }  @Test
    public void t17(){

    }  @Test
    public void t18(){

    }  @Test
    public void t19(){

    }
}
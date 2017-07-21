package wang.mh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import wang.mh.dao.JDBCTemplate;

/**
 * Created by 明辉 on 2017/7/20.
 * 多个数据源配置
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        testConnect();

    }

    public static void testConnect(){

        //mysql
        JdbcTemplate primary = JDBCTemplate.getPrimary();
        String sql = "select count(1) from city";
        Long num = primary.queryForObject(sql, Long.class);

        //postgrsql
        JdbcTemplate second = JDBCTemplate.getSecond();
        String sql2 = "select count(1) from users";
        Long num2 = second.queryForObject(sql2, Long.class);

        System.out.println("city:"+num);
        System.out.println("users:"+num2);

    }
}

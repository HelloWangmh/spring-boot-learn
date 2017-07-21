package wang.mh.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by 明辉 on 2017/7/20.
 */
@Component
@EnableTransactionManagement  //开启事务注解的支持
public class JDBCTemplate {

    private static JdbcTemplate primaryTemplate;

    private static JdbcTemplate secondTemplate;


    @Autowired
    public JDBCTemplate(@Qualifier("primaryDataSource")DataSource primaryDataSource,
                        @Qualifier("secondDataSource")DataSource secondDataSource){
        primaryTemplate = new JdbcTemplate(primaryDataSource);
        secondTemplate = new JdbcTemplate(secondDataSource);
    }

    public static JdbcTemplate getPrimary(){
        return primaryTemplate;
    }

    public static JdbcTemplate getSecond(){
        return secondTemplate;
    }


}

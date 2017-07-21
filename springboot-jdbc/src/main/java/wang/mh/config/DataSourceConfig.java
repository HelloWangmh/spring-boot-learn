package wang.mh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by 明辉 on 2017/7/20.
 */
@Configuration
public class DataSourceConfig {


    @Bean(value = "primaryDataSource")
    @ConfigurationProperties(prefix = "datasource.primary")
    @Primary   //同接口若存在多个实现  在autowire的时候优先选择这个
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();

    }


    @Bean(value = "secondDataSource")
    @ConfigurationProperties(prefix = "datasource.secondary")
    public DataSource secondDataSource(){
        return DataSourceBuilder.create().build();
    }

}

package wang.mh.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import wang.mh.model.Person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一个接口存在多个子类实例化的时候,可以通过
 * : @Primary,@Qualifier("personCondition")解析冲突,后者等价于直接更改bean变量名为创建bean的方法名
 * : @Qualifier可以与@Bean一起,更改bean默认限定名
 * bean作用域四个,单例,原型,request,session
 */
@Configuration
@Slf4j
@PropertySource("classpath:/myProper.properties")
public class BeanConfig {

    @Autowired
    private Environment environment;


    /**
     * spring表达式语言
     * 通过java中类的属性或方法获取值
     * 通过bean的id获取创建的bean的属性或方法获取值
     * 支持运算符,三元运算符
     * 支持正则表达式   (data) matches (RegExp)
     * 可以操作集合  通过[]获取集合或字符串的值
     */
    @Bean
    public Person testSpEL(@Value("#{T(java.lang.System).currentTimeMillis()}") long curr,
                           @Value("#{testPlaceHolder.name?.toUpperCase() + 'test'}")String name,
                           @Value("#{myList.size()}")int size){
        Person p = new Person(name);
        log.info("person通过testSpEL实例化 : {}, time : {}, myList size : {}", p, curr, size);
        return p;
    }

    @Bean
    public List<Integer>  myList(){
        return Stream.of(0, 1, 2, 3, 4).collect(Collectors.toList());
    }


    /**
     * 属性占位符
     * @param name
     * @return
     */
    @Bean
    public Person testPlaceHolder( @Value("${name}")String name){
        Person p = new Person(name);
        log.info("person通过testPlaceHolder实例化 : {}", p);
        return p;
    }

    /**
     * Environment  可以获取到定义的属性,激活的profile
     * @return
     */
    @Bean
    public Person testEnvironment() throws IllegalAccessException, InstantiationException {
        String name = environment.getProperty("name", "default name");
        Class<Person> clazz = environment.getPropertyAsClass("person.class", Person.class);
        Person p = clazz.newInstance();
        p.setName(name);
        log.info("person通过testEnvironment实例化 : {}", p);
        return p;
    }

    @Bean
    @Conditional(isProdCondition.class)  //满足这个条件才会创建bean
    public Person personCondition(){
        Person p = new Person("isProdCondition");
        log.info("person通过prodPersonCondition实例化 : {}", p);
        return p;
    }


    @Bean
    @Profile("dev")                     //profile为dev才会实例化,内部也是通过condition实现
    public Person personProfile(){
        Person p = new Person("personProfile");
        log.info("person通过prodPersonProfile实例化 : {}", p);
        return p;
    }

}


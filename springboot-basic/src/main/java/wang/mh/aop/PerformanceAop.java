package wang.mh.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * spring aop
 * Advice : 具体执行的内容
 * Join point :插入切面中的那个点
 * Pointcut : where
 * Aspect : Advice + Pointcut
 * execution()用来匹配方法,还有一些指示器用来限制范围
 *
 * caution:不要定义多个相同方法名,不能参数的方法,会导致IllegalArgumentException
 */
@Aspect
@Slf4j
@Component
public class PerformanceAop {

    private Integer num = 0;

    @Pointcut("execution(* wang.mh.service.impl.Performance.perform(..))")
    public void performance(){}

    /**
     * 处理通知中的参数
     * @param second
     */
    @Pointcut("execution(* wang.mh.service.impl.Performance.performForSecond(int)) " +
              "&& args(second)")
    public void performanceForSecond(int second){}

    @Before("performanceForSecond(second)")
    public void before(int second){
        log.info("start to before");
    }

    @After("performance()")
    public void after(){
        log.info("start to after");
    }

    @AfterReturning("performance()")
    public void AfterReturning(){
        log.info("start to AfterReturning");
    }

    /**
     * 环绕通知
     */
    @Around("performance()")
    public void around(ProceedingJoinPoint point){
        log.info("start to around");
        num++;
        try {
            //调用运行的方法
            point.proceed();

            log.info("start to around, num : {}", num);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

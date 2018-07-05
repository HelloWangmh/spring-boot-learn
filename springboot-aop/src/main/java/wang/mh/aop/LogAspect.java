package wang.mh.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private ThreadLocal<Long> localTime = new ThreadLocal<>(); //根据线程记录每个请求完成的时间

    @Pointcut("execution(* wang.mh.web.*.*(..))")
    public void log() {}

    @Before("log()")
    public void beforeRecordLog(JoinPoint joinPoint) {
        localTime.set(System.currentTimeMillis());
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = getCurrentRequest();
        log.info("IP : {}", request.getRemoteAddr());
        log.info("Args : {}", Arrays.toString(args));
    }

    @AfterReturning(pointcut = "log()", returning = "result") //正常返回才会到这里
    public void afterReturn(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = getCurrentRequest();
        log.info("result : {}", result);
        long costTime = System.currentTimeMillis() - localTime.get();
        log.info("URI : {}, cost time : {}", request.getRequestURI(), costTime);
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}

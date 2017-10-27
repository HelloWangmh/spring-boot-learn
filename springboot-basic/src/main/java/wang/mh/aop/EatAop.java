package wang.mh.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;
import wang.mh.service.EatFoodImpl;
import wang.mh.service.impl.EatFood;

/**
 * 通过代理暴露其他接口,即为对象或类增加新的方法
 */
@Aspect
@Component
public class EatAop {

    @DeclareParents(value = "wang.mh.service.impl.Performance+",
                    defaultImpl = EatFoodImpl.class)
    public static EatFood eatFood;
}

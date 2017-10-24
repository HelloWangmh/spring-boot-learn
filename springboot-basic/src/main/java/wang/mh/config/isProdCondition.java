package wang.mh.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 实现Condition接口
 */
public class isProdCondition implements Condition{
    /**
     *
     * @param conditionContext 可以获取加载的bean,property,resource
     * @param annotatedTypeMetadata 可以检查带有@Bean注解的方法上还有什么注解
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(conditionContext.getEnvironment().getProperty("isDev").equals("true")){
            return true;
        }
        return false;
    }
}

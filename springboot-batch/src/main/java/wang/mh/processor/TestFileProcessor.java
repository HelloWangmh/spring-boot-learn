package wang.mh.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@StepScope
public class TestFileProcessor implements ItemProcessor<String,Void> {

    @Override
    public Void process(String s) throws Exception {
       log.info("data:{}",s);
        return null;
    }
}

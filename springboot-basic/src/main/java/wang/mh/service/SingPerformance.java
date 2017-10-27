package wang.mh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wang.mh.service.impl.Performance;

@Component
@Slf4j
public class SingPerformance implements Performance {

    @Override
    public void perform() {
        log.info("start to sing perform");
    }

    @Override
    public void performForSecond(int second) {

    }
}

package wang.mh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wang.mh.service.impl.Performance;


@Slf4j
@Component
public class DancePerformance implements Performance {
    @Override
    public void perform() {
        log.info("start to dance perform");
    }

    @Override
    public void performForSecond(int second) {
        log.info("start to dance perform for {}s" , second);
    }
}

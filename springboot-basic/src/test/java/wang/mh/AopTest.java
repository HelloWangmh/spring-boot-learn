package wang.mh;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wang.mh.service.impl.Performance;

public class AopTest extends BaseTest{

    @Autowired
    private Performance dancePerformance;
    @Autowired
    private Performance singPerformance;

    @Test
    public void testAop(){
        dancePerformance.performForSecond(10);

    }
}

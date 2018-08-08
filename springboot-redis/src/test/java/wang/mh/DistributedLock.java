package wang.mh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLock {

    @Autowired
    private StringRedisTemplate redisTemplate;
    private int count;

    @Test
    public void notLock() throws Exception {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                int time = 0;
                while (time < 10000) {
                    count++;
                    time++;
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("result : " + count + " cost time : " + (System.currentTimeMillis() - start));
    }

    @Test
    public void lock() throws Exception {
        long start = System.currentTimeMillis();
        String key = "lock";
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                ValueOperations<String, String> vp = redisTemplate.opsForValue();
                Boolean success = vp.setIfAbsent(key, "1");
                while (! success) {
                    success = vp.setIfAbsent(key, "1");
                }
                vp.getOperations().expire(key, 5, TimeUnit.SECONDS); //设置过期时间
                int time = 0;
                while (time < 10000) {
                    count++;
                    time++;
                }
                vp.getOperations().delete(key);//释放锁
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("result : " + count + " cost time : " + (System.currentTimeMillis() - start));
    }
}

package wang.mh;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QueueTest extends BaseTest{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void blockQueue() throws Exception {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        String key = "queue";
        operations.getOperations().delete(key);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {  //队列中写
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    operations.leftPush(key, Thread.currentThread().getName() + "-" + j);
                }
                latch.countDown();
            }, "write-" + i).start();
        }

        for (int i = 0; i < 2; i++) {  //从队列中读
            new Thread(() -> {
               while (true) {
                   String result = operations.rightPop(key, 5, TimeUnit.SECONDS);
                   if (result == null) break;
                   System.out.println("read : " + result);
               }
            }).start();
        }

        latch.await();
        while (!operations.range(key, 0, 0).isEmpty()) {
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

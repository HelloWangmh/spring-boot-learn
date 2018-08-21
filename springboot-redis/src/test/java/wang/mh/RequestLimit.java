package wang.mh;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.concurrent.TimeUnit;

public class RequestLimit extends BaseTest {

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     *  通过zset简单限流
     *  缺点 : 若次数很多,那么会占用很多空间
     */
    @Test
    public void simpleLimit() throws Exception {
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        for (int i = 0; i < 15; i++) {
            TimeUnit.MILLISECONDS.sleep(100);
            boolean allowed = isAllowed("123", "hello", 60, 5);
            if (!allowed) {
                System.out.println("not allowed i : " + i);
            }
        }
    }

    private boolean isAllowed(String userId, String actionKey, int period, int maxCount) {
        String key = String.format("request:%s:%s", userId, actionKey);
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        long nowTs = System.currentTimeMillis();
        operations.add(key, String.valueOf(nowTs), nowTs);
        operations.removeRangeByScore(key, 0, nowTs - (period * 1000));
        operations.getOperations().expire(key, period, TimeUnit.SECONDS);  //这样可以丢弃掉过期的key
        Long count = operations.zCard(key);
        return count <= maxCount;
    }
}

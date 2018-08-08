package wang.mh;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 明辉 on 2017/7/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void operateString(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","hello");
        System.out.println(valueOperations.get("name"));
        valueOperations.append("name",",world");
        System.out.println(valueOperations.get("name"));
    }

    @Test
    public void operateHash(){
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("person","age","12");
        hashOperations.put("person","name","wmh");
        hashOperations.put("person","color","black");
        System.out.println(hashOperations.keys("person"));
        System.out.println(hashOperations.values("person"));
        List<Object> list = Lists.newArrayList();
        list.add("age");
        list.add("name");
        System.out.println(hashOperations.multiGet("person",list)
        );
    }

    @Test
    public void operateList(){
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list","1");
        listOperations.leftPush("list","2");
        listOperations.leftPush("list","3");
        System.out.println(listOperations.index("list",1));
        System.out.println(listOperations.size("list"));
        listOperations.set("list",4,"4");
        //如果key存在则推入  否则不操作
        listOperations.leftPushIfPresent("list","5");
    }

    @Test
    public void operateSet(){
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add("set1","a", "a", "b", "c", "d");
        setOperations.add("set2","a", "b", "b");
        System.out.println(setOperations.members("set1"));
        System.out.println(setOperations.difference("set1","set2"));
    }

    @Test
    public void operateScoreSet(){
        String key = "zset";
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.removeRange(key, 0, -1);
        zSetOperations.add(key,"a",10);
        zSetOperations.add(key,"a",10);
        zSetOperations.add(key,"b",50);
        zSetOperations.add(key,"c",90);
        System.out.println(zSetOperations.range(key,0, -1));
        Set<ZSetOperations.TypedTuple<String>> zset = zSetOperations.rangeByScoreWithScores(key, 0, 100);
        for (ZSetOperations.TypedTuple<String> tuple : zset) {
            System.out.println(tuple.getValue() + "-->" +tuple.getScore());
        }
    }

    @Test
    public void publish(){
        redisTemplate.convertAndSend("testChannel","hello world");
    }


    @Autowired
    private CountDownLatch countDownLatch;
    @Test
    public void testSubscribe() throws InterruptedException {
        //redisTemplate.convertAndSend("testChannel","hello world");
        countDownLatch.await();
    }

}

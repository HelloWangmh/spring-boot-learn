package wang.mh;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 明辉 on 2017/7/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPublish {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        System.out.println( redisTemplate.keys("*"));
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //valueOperations.set("testRedisTemplate","testRedisTemplate");
        System.out.println(valueOperations.get("testRedisTemplate"));
    }

    @Test
    public void testString(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("testString","testString");
        System.out.println(valueOperations.get("testString"));
        valueOperations.append("testString","123");
        System.out.println(valueOperations.get("testString"));

    }

    @Test
    public void testHash(){
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("person","age","12");
        hashOperations.put("person","name","王明辉");
        hashOperations.put("person","color","black");

        System.out.println(hashOperations.keys("person"));
        System.out.println(hashOperations.values("person"));
        List<Object> list = Lists.newArrayList();
        list.add("age");
        list.add("name");
        System.out.println(        hashOperations.multiGet("person",list)
        );

    }

    @Test
    public void testList(){
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
//        listOperations.leftPush("testList","1");
//        listOperations.leftPush("testList","2");
//        listOperations.leftPush("testList","3");
//
//        System.out.println(listOperations.index("testList",1));
//        System.out.println(listOperations.size("testList"));
//        listOperations.set("testList",4,"4444");
        //如果key存在则推入  否则不操作
        listOperations.leftPushIfPresent("testList2","33");
    }

    @Test
    public void testSet(){
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add("testSet","1","2","2","王明辉","郝雯雯","王大锤");
        setOperations.add("testSet2","111","222","222","王明辉","郝雯雯","王大锤");
        System.out.println(setOperations.distinctRandomMembers("testSet",2));
        System.out.println(setOperations.difference("testSet","testSet2"));
    }

    @Test
    public void testScoreSet(){
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("testZset","wang",10);
        zSetOperations.add("testZset","ming",90);
        zSetOperations.add("testZset","hui",5);
        System.out.println(zSetOperations.count("testZset",10,90));
    }

    @Test
    public void testPublish(){
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

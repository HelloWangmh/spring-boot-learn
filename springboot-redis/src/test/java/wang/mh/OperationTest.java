package wang.mh;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 明辉 on 2017/7/22.
 */

public class OperationTest extends BaseTest{

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void execute() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.set("name".getBytes(), "hello".getBytes());
            return null;
        });
    }

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
        System.out.println(zSetOperations.zCard(key));
        Set<ZSetOperations.TypedTuple<String>> zset = zSetOperations.rangeByScoreWithScores(key, 0, 100);
        for (ZSetOperations.TypedTuple<String> tuple : zset) {
            System.out.println(tuple.getValue() + "-->" +tuple.getScore());
        }
    }

    @Test
    public void pipeline() {
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<List<Object>>) (RedisConnection connection) -> {
            connection.openPipeline();
            connection.set("name".getBytes(), "wmh".getBytes());
            List<Object> list = connection.closePipeline();
            return connection.closePipeline();
        });
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    /**
     *  hyperLogLog类似一个set集合 但是比set集合占用内存更少,统计时候可能会有误差
     */
    @Test
    public void hyperLogLog() {
        HyperLogLogOperations<String, String> operations = redisTemplate.opsForHyperLogLog();
        String key = "hyperLogLog";
        operations.delete(key);
        for (int i = 0; i < 10000; i++) {
            operations.add(key, String.valueOf(i));
            operations.add(key, String.valueOf(i)); //可以去重
        }
        System.out.println("size : " + operations.size(key));
    }

    /**
     *  geo内部实现是一个zset geo的member是其value,坐标会映射成一个整数作为score
     */
    @Test
    public void geoHash() {
        GeoOperations<String, String> operations = redisTemplate.opsForGeo();
        String key = "geo";
        operations.geoAdd(key, new Point(116.3971840738, 39.9179756573), "故宫");
        operations.geoAdd(key, new Point(116.4136404358, 40.0494298610), "立水桥");
        Distance distance = operations.geoDist(key, "故宫", "立水桥", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance.getValue() + distance.getUnit());
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

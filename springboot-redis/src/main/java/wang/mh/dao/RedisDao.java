package wang.mh.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by 明辉 on 2017/7/21.
 */
@Component
public class RedisDao {


    @Autowired
    private RedisTemplate redisTemplate;

    public Set queryAll(){
        return redisTemplate.keys("*");
    }
}

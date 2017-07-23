package wang.mh.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.mh.dao.RedisDao;

import java.util.Set;

/**
 * Created by 明辉 on 2017/7/22.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private RedisDao redisDao;

    @RequestMapping(value = "/testRedis")
    public Set testRedis(){
        return redisDao.redisTest();
    }



}

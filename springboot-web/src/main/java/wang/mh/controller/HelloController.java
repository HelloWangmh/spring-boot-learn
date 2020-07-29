package wang.mh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    private List<int[]> list = new ArrayList<>();

    @RequestMapping(value = "/hello")
    public Map hello(String name){

        System.out.println(name);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @RequestMapping(value = "/testHeap")
    public String testHeap(Integer count){
        if (count == null) {
            count = 1000;
        }
        for (int i = 0; i < count; i++) {
            list.add(new int[10]);
        }
        return String.valueOf(list.size());
    }
}

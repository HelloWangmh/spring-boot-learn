package wang.mh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    private List<int[]> list = new ArrayList<>();

    @RequestMapping(value = "/hello")
    public String hello(String name){
        return "hello, " + name;
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

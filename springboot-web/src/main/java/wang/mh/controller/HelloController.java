package wang.mh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.mh.exception.TestException;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    private List<int[]> list = new ArrayList<>();

    @RequestMapping(value = "/hello")
    @ResponseBody
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

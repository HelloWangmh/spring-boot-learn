package wang.mh.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wang.mh.exception.TestException;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HelloController {

    private List<int[]> list = new ArrayList<>();

    private static String data;
    private static List<String> datas;

    private volatile int i = 0;
    static {


    }

    @RequestMapping(value = "/readLine")
    public void readLine( HttpServletResponse response) throws IOException {
        while (i > (datas.size() - 1)) {
            i = i - (datas.size() - 1);
        }
        String data = datas.get(i);
        response.addHeader("Cache-Control", "private");
        PrintWriter writer = response.getWriter();
        writer.print(data);
    }



    @RequestMapping(value = "/hello")
    @ResponseBody
    public Map hello(String name){
        System.out.println(name);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @RequestMapping(value = "/testExc")
    public String testExc(String name){
        if (StringUtils.isEmpty(name)){
            throw new TestException();
        }
        return name;
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

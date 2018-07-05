package wang.mh.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(String name) throws Exception {
        Random random = new Random();
        TimeUnit.SECONDS.sleep(random.nextInt(3));
        return "Hello " + name;
    }
}

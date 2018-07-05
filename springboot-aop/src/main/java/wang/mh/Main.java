package wang.mh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Main Args : {}", Arrays.toString(args));
        SpringApplication.run(Main.class, args);
    }
}

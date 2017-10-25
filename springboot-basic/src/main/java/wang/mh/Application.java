package wang.mh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wang.mh.model.Person;

@SpringBootApplication
public class Application {

    @Autowired
    private Person personCondition;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}

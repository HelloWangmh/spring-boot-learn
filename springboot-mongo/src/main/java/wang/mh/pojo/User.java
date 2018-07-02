package wang.mh.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user")
@Setter
@Getter
@ToString
public class User {

    @Id
    private String id;

    private String name;

    private int age;

    private LocalDateTime updateTime;
}

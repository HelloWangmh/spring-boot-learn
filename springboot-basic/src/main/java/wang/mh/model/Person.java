package wang.mh.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() {

    }
}

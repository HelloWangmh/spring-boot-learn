package wang.mh.service;

import org.springframework.stereotype.Component;
import wang.mh.service.impl.EatFood;
@Component
public class EatFoodImpl implements EatFood{

    @Override
    public void eat() {
        System.out.println("start to eat");
    }
}

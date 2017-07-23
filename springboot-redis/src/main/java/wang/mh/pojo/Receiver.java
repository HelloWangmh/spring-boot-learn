package wang.mh.pojo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;


/**
 * Created by 明辉 on 2017/7/23.
 */
public class Receiver {

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        System.out.println(message+"=====");
        latch.countDown();
    }
}

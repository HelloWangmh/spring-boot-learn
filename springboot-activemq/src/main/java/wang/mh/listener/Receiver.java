package wang.mh.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.stereotype.Component;

/**
 * Created by 明辉 on 2017/8/4.
 */
@Component
public class Receiver {



    @JmsListener(destination = "topic-name")
    public void  receiveMessage(String message){
        System.out.println("接收到的message:"+message);
    }

    @JmsListener(destination = "topic-name")
    public void  receiveMessage2(String message){
        System.out.println("接收到的message2:"+message);
    }

}

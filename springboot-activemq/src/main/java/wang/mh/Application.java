package wang.mh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import wang.mh.pojo.MsgQueue;

import javax.jms.*;

/**
 * Created by 明辉 on 2017/8/4.
 */
@SpringBootApplication
public class Application implements CommandLineRunner{

    @Autowired
    private JmsTemplate jmsTemplate;



    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... strings) throws Exception {
        Message message = jmsTemplate.sendAndReceive("my-destination", new MsgQueue());

       jmsTemplate.send(new Topic() {
           @Override
           public String getTopicName() throws JMSException {
               return "topic-name";
           }
       },new MsgQueue());



    }
}

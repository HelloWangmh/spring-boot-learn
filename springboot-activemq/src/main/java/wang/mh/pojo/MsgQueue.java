package wang.mh.pojo;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by 明辉 on 2017/8/4.
 */
public class MsgQueue implements MessageCreator{
    @Override
    public Message createMessage(Session session) throws JMSException {

        return session.createTextMessage("my message");
    }
}

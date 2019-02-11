package wang.mh;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wang.mh.dao.UserMapper;
import wang.mh.pojo.User;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //开启事务
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Rollback //结束后回滚数据 其实在test的时候默认就会回滚 不想回滚的话可以用@Commit 或 @Rollback(false)
    public void query() {
        userMapper.insert("wmh", 25);
        User user = userMapper.findByName("wmh");
        Assert.assertNotNull(user);
        Assert.assertEquals(25, user.getAge().intValue());

        List<User> users = userMapper.selectOnlyName();
        for (User u : users) {
            Assert.assertNull(u.getId());
            Assert.assertNull(u.getAge());
            Assert.assertNotNull(u.getName());
        }
    }
}

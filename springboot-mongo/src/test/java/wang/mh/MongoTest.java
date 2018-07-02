package wang.mh;

import com.google.common.collect.Lists;
import com.mongodb.BulkWriteResult;
import com.mongodb.WriteResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import wang.mh.pojo.BathUpdateOptions;
import wang.mh.pojo.MongoUpdate;
import wang.mh.pojo.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testUpsert(){
        LocalDateTime now = LocalDateTime.now();
        Criteria criteria = Criteria.where("name").is("hui");
        Update u = new Update();
        u.set("age", 17);
        u.set("updateTime", now);
        WriteResult writeResult = mongoTemplate.upsert(new Query(criteria), u, User.class);
        System.out.println(writeResult.isUpdateOfExisting());
        System.out.println(writeResult.getN());
    }

    @Test
    public void testDelete(){
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED,  User.class);
        Criteria c;
        c = Criteria.where("name").is("hui");
        bulkOps.remove(new Query(c));
        BulkWriteResult result = bulkOps.execute();
        System.out.println(result.getRemovedCount());
    }

    @Test
    public void testFindIn(){
        Criteria criteria = Criteria.where("age").in(Lists.newArrayList(17));
        List<User> users = mongoTemplate.find(new Query(criteria), User.class);
        System.out.println(users.size());
        System.out.println(users);
    }

    @Test
    public void testBatchUpdate(){
        long start = System.currentTimeMillis();
        int num = 0;
        for (int i = 0; i < 50; i++) {
            BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, User.class);
            for (int j = 0; j < 1000; j++) {
                num++;
                Criteria criteria = Criteria.where("age").is(num);
                Update u = new Update();
                u.set("name", "w" + i);
                bulkOps.upsert(new Query(criteria), u);
            }
            bulkOps.execute();
        }

        long end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "ms"); //20710ms 513230
    }

    @Test
    public void testBatchUpdate2(){
        long start = System.currentTimeMillis();

        for (int i = 0; i < 50000; i++) {
            Criteria criteria = Criteria.where("age").is(i);
            Update u = new Update();
            u.set("name", "m" + i);
            mongoTemplate.upsert(new Query(criteria), u, User.class);
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "ms"); //22961ms 521824
    }

    @Test
    public void testBatchUpdate3(){
        long start = System.currentTimeMillis();
        int num = 0;
        for (int i = 0; i < 50; i++) {
            List<BathUpdateOptions> list = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                num++;
                Criteria criteria = Criteria.where("age").is(num);
                Update u = new Update();
                u.set("name", "m" + i);
                BathUpdateOptions options = new BathUpdateOptions(new Query(criteria), u, true, true);
                list.add(options);
            }
            MongoUpdate.bathUpdate(mongoTemplate, User.class, list);
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "ms"); // 507131
    }

    public List<User> getUsers(){
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("test");
            list.add(user);
        }
        return list;
    }
}

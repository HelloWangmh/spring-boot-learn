package wang.mh.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
@Getter
@Setter
@AllArgsConstructor
public class BathUpdateOptions {
    private Query query;
    private Update update;
    private boolean upsert = false;
    private boolean multi = false;
}

package wang.mh.reader;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wang.mh.schedule.BatchSchedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@StepScope
@Slf4j
public class TestFileReader implements ItemReader<String>{

    private List<String>  datas = Lists.newArrayList();

    @Value("#{jobParameters['ts']}")
    private Long ts;


    @BeforeStep
    public void init() throws Exception {
        InputStream is = TestFileReader.class.getResourceAsStream("/test.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String data;
        while ((data = br.readLine())!=null){
            datas.add(data);
        }
        TimeUnit.SECONDS.sleep(30);
        BatchSchedule.isRunning = false;
    }

    @Override
    public String read() throws Exception{
        if(datas.isEmpty()){
            return null;
        }else {
            return datas.remove(0) + ",now:"+ts;
        }
    }
}

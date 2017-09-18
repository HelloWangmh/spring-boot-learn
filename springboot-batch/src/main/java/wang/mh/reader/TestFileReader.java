package wang.mh.reader;

import com.google.common.collect.Lists;
import org.hsqldb.lib.ReaderInputStream;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
@StepScope
public class TestFileReader implements ItemReader<String>{

    private List<String>  datas = Lists.newArrayList();

    @Value("#{jobParameters['ts']}")
    private Long ts;

    @BeforeStep
    public void init() throws IOException {
        InputStream is = TestFileReader.class.getResourceAsStream("/test.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String data = null;
        while ((data = br.readLine())!=null){
            datas.add(data);
        }
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

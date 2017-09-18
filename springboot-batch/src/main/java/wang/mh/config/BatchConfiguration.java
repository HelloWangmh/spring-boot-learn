package wang.mh.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import wang.mh.processor.TestFileProcessor;
import wang.mh.reader.TestFileReader;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job(TestFileReader reader,TestFileProcessor processor) throws Exception {

        Step step1 = stepBuilderFactory.get("step1")
                .<String, Void>chunk(1)
                .reader(reader)
                .processor(processor)
                .build();


        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }


    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepo) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.initialize();

        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepo);
        simpleJobLauncher.setTaskExecutor(taskExecutor);
        return simpleJobLauncher;
    }



}

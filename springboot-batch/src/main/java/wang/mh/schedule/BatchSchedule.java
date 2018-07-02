package wang.mh.schedule;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class BatchSchedule {


    @Autowired
    private JobLauncher jobLauncher2;
    @Autowired
    private Job job1;
    @Autowired
    private Job job2;


    public static volatile boolean isRunning;


    @Scheduled(cron = "0/10 * * * * *")
    public void start() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException {
        if (isRunning) {
            System.out.println("is running, stop");
            return;
        }
        isRunning = true;
        jobLauncher2.run(job1, newTsJobParams());
        //jobLauncher2.run(job2,newTsJobParams());
    }




    // 新创建一个JobParameters实例，里面只放一个时间戳
    private JobParameters newTsJobParams() {
        return new JobParametersBuilder()
                .addLong("ts", System.currentTimeMillis())
                .toJobParameters();
    }
}

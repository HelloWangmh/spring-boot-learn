package wang.mh.schedule;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchSchedule {


    @Autowired
    private JobLauncher jobLauncher2;
    @Autowired
    private Job job1;
    @Autowired
    private Job job2;




    @Scheduled(cron = "0 * * * * *")
    public void start() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        System.out.printf("job-name:%s%n",job1.getName()); //job1
        jobLauncher2.run(job1,newTsJobParams());
        jobLauncher2.run(job2,newTsJobParams());
    }




    // 新创建一个JobParameters实例，里面只放一个时间戳
    private JobParameters newTsJobParams() {
        return new JobParametersBuilder()
                .addLong("ts", System.currentTimeMillis())
                .toJobParameters();
    }
}
package pt.com.primeit.poc.jobrunnr.jobs;

import org.springframework.stereotype.Component;

@Component
public class EmailJob implements Job {

    @Override
    @org.jobrunr.jobs.annotations.Job( name = "EmailJob", retries = 5)
    public void execute() {
        System.out.println("send email " + Thread.currentThread().getName() );
    }
}

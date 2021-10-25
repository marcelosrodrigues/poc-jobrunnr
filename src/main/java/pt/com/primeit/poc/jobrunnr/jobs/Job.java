package pt.com.primeit.poc.jobrunnr.jobs;

public interface Job {
    @org.jobrunr.jobs.annotations.Job(name = "EmailJob", retries = 5)
    void execute();
}

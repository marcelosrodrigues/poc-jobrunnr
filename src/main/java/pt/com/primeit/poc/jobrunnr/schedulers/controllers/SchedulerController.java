package pt.com.primeit.poc.jobrunnr.schedulers.controllers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jobrunr.jobs.RecurringJob;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.com.primeit.poc.jobrunnr.jobs.Job;
import pt.com.primeit.poc.jobrunnr.schedulers.entities.Execution;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedulers")
@Slf4j
public class SchedulerController {

    @Autowired
    private StorageProvider storageProvider;


    @SneakyThrows
    @PostMapping
    public ResponseEntity<Execution> add(@RequestBody(required = true) Execution jobDefinition ) {

        log.info("create a schedule to {}" , jobDefinition);

        val clazz = Class.forName(jobDefinition.getJob().getClassName());
        val job = (Job)clazz.getDeclaredConstructor().newInstance();

        jobDefinition.setId(UUID.randomUUID());

        BackgroundJob.scheduleRecurrently(jobDefinition.getId().toString(),
                jobDefinition.getCronExpression(),
                () -> job.execute() );



        return ResponseEntity.ok(jobDefinition);
    }

    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<Execution>> list() {

        log.info("list all job executions");
        List<RecurringJob> jobs = storageProvider.getRecurringJobs();
        List<Execution> definitions = jobs.stream()
            .map( job -> Execution.builder(UUID.fromString(job.getId()),
                    job.getCronExpression(), null, null, null )
        ).collect(Collectors.toList());

        return ResponseEntity.ok(definitions);
    }

    @SneakyThrows
    @DeleteMapping
    public ResponseEntity delete(@RequestBody(required = true) UUID jobId ){

        log.info("delete the execution {}" , jobId);

        BackgroundJob.delete(jobId);
        return ResponseEntity.ok().build();

    }



}


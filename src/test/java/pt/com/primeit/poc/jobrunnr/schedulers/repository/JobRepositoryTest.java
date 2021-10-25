package pt.com.primeit.poc.jobrunnr.schedulers.repository;

import lombok.val;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.repository.config.BootstrapMode;
import pt.com.primeit.poc.jobrunnr.jobs.EmailJob;
import pt.com.primeit.poc.jobrunnr.schedulers.entities.Job;

@OverrideAutoConfiguration(enabled = true)
@DataJpaTest(bootstrapMode = BootstrapMode.LAZY, excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= StorageProvider.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= JobScheduler.class)},
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RedisCacheConfiguration.class)
)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE,
        replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JobRepositoryTest {

    @Autowired
    private JobRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void save() {

        val job = Job.builder()
                .generateId()
                .withName("EmailJoB")
                .withClass(EmailJob.class.getName());

        repository.save(job);

    }

}
package pt.com.primeit.poc.jobrunnr;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.storage.nosql.redis.JedisRedisStorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@SpringBootApplication
@EnableCaching
public class JobrunnrApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobrunnrApplication.class, args);
	}

	@Bean
	RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(5))
				.disableCachingNullValues()
				.serializeValuesWith(RedisSerializationContext
						.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}

	@Bean
	StorageProvider storageProvider(JobMapper jobMapper) {
		JedisRedisStorageProvider storageProvider = new JedisRedisStorageProvider();
		storageProvider.setJobMapper(jobMapper);
		return storageProvider;
	}

	@Bean
	JobScheduler initJobRunr(ApplicationContext context, JobMapper jobMapper) {

		return JobRunr.configure()
				.useJobActivator(context::getBean)
				.useStorageProvider(storageProvider(jobMapper))
				.useBackgroundJobServer()
				.initialize()
				.getJobScheduler();

	}
}

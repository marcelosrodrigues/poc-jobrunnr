package pt.com.primeit.poc.jobrunnr.repositories;

import lombok.val;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.repository.config.BootstrapMode;
import pt.com.primeit.poc.jobrunnr.entities.Application;
import pt.com.primeit.poc.jobrunnr.entities.Tenant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@OverrideAutoConfiguration(enabled = true)
@DataJpaTest(bootstrapMode = BootstrapMode.LAZY, excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= StorageProvider.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= JobScheduler.class)},
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RedisCacheConfiguration.class)
  )
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE,
        replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TenantRepositoryTest {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ApplicationRepository systemRepository;

    private Collection<Application> systems = new ArrayList<>();

    @BeforeAll
    public void beforeAll() {

        IntStream.range(0,5).forEachOrdered( i -> {
            val system = Application.builder()
                    .generateId()
                    .withName(format("SYSTEM_%s", i));

            IntStream.range(0, 5).forEachOrdered( n -> {
                val tenant = Tenant.builder()
                        .generateId()
                        .withName(format("TEST_%s", n));
                tenantRepository.save(tenant);
                system.add(tenant);
            });

            systemRepository.save(system);
            systems.add(system);
        });

    }

    @Test
    public void findAllTenantBySystem() {

        val system = systems.stream()
                .findAny()
                .orElseThrow();

        val tenants = tenantRepository.findBySystem(system.getId())
                .orElseThrow();

        assertFalse(tenants.isEmpty());
        assertEquals( 5, tenants.size());

    }

}
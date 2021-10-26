package pt.com.primeit.poc.jobrunnr.schedulers.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.primeit.poc.jobrunnr.schedulers.entities.Schedule;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, UUID> {

    @CacheEvict(value = "schedule", key = "#execution.getId()")
    Schedule save(Schedule schedule);

    @CachePut(value = "schedule", key = "#id")
    Optional<Schedule> findById(UUID id);

    @Cacheable( value = "schedules-by-tenant", key = "#id")
    @Query("SELECT e FROM Schedule e JOIN e.tenant t where t.id = :id")
    Optional<Collection<Schedule>> findSchedulesByTenant(@Param("id") UUID id);
}

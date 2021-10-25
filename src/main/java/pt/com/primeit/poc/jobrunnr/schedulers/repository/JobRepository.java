package pt.com.primeit.poc.jobrunnr.schedulers.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.primeit.poc.jobrunnr.schedulers.entities.Job;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends CrudRepository<Job, UUID> {

    @CachePut(value = "job", key = "#id")
    Optional<Job> findById(UUID id);

    @CachePut(value = "job", key = "#job.getId()")
    Job save(Job job);

    @Cacheable(cacheNames = "jobs")
    Iterable<Job> findAll();

    @Cacheable( value = "jobs-by-tenant", key = "#id")
    @Query("SELECT j FROM Tenant t JOIN t.jobs j where t.id = :id")
    Optional<Collection<Job>> findByTenant(@Param("id") UUID id);

}

package pt.com.primeit.poc.jobrunnr.schedulers.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.primeit.poc.jobrunnr.schedulers.entities.Execution;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExecutionRepository extends CrudRepository<Execution, UUID> {

    @CacheEvict(value = "execution", key = "#execution.getId()")
    Execution save(Execution execution);

    @CachePut(value = "execution", key = "#id")
    Optional<Execution> findById(UUID id);

    @Cacheable( value = "schedules-by-tenant", key = "#id")
    @Query("SELECT e FROM pt.com.primeit.poc.jobrunnr.schedulers.entities.Execution e JOIN FETCH e.tenant t where t.id :id")
    Optional<Collection<Execution>> findSchedulesByTenant(@Param("id") UUID id);
}

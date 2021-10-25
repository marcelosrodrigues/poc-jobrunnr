package pt.com.primeit.poc.jobrunnr.repositories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.com.primeit.poc.jobrunnr.entities.Application;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, UUID> {

    @CachePut(value = "application", key = "#id")
    Optional<Application> findById(UUID id );

    @CacheEvict(value = "application", key = "#application.getId()")
    Application save(Application system);

    @Cacheable(cacheNames = "applications")
    Iterable<Application> findAll();
}

package pt.com.primeit.poc.jobrunnr.repositories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.com.primeit.poc.jobrunnr.entities.Tenant;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, UUID> {

    @CachePut(value = "tenant", key = "#id")
    Optional<Tenant> findById(UUID id);

    @CacheEvict(value = "tenant", key = "#tenant.getId()")
    Tenant save(Tenant tenant);

    @Cacheable( value = "tenants-by-system", key = "#id")
    @Query("SELECT t from System s inner join s.tenants t where s.id = :id")
    Optional<Collection<Tenant>> findBySystem(@Param("id") UUID id);

    @Cacheable(cacheNames = "tenants")
    Iterable<Tenant> findAll();



}

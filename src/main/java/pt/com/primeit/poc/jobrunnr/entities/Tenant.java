package pt.com.primeit.poc.jobrunnr.entities;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.cache.annotation.Cacheable;
import pt.com.primeit.poc.jobrunnr.schedulers.entities.Job;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor(staticName = "builder")
@RequiredArgsConstructor(staticName = "builder")
@EqualsAndHashCode(of = "id")
@Cacheable("tenant")
@Entity
public class Tenant implements Serializable {

    @Id
    @Type(type="pg-uuid")
    private UUID id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "jobs_by_tenant", uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "job_id"}) ,
            joinColumns = @JoinColumn(name = "tenant_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id" , referencedColumnName = "id"))
    private Set<Job> jobs = new HashSet<>();

    public Tenant generateId() {
        id = UUID.randomUUID();
        return this;
    }

    public Tenant withName(String name) {
        this.name = name;
        return this;
    }

    public Tenant add(Job job) {
        this.jobs.add(job);
        return this;
    }

}

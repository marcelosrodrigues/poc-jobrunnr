package pt.com.primeit.poc.jobrunnr.entities;

import lombok.*;
import org.hibernate.annotations.*;

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
@Cacheable()
@Entity
public class Application implements Serializable {

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
    @JoinTable(name = "tenant_by_system",
            uniqueConstraints = @UniqueConstraint(columnNames = {"system_id", "tenant_id"}),
            joinColumns = @JoinColumn(name = "system_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id" , referencedColumnName = "id"))
    private Set<Tenant> tenants = new HashSet<>();

    public Application generateId() {
        id = UUID.randomUUID();
        return this;
    }

    public Application withName(String name) {
        this.name = name;
        return this;
    }

    public Application add(Tenant tenant){
        this.tenants.add(tenant);
        return this;
    }
}

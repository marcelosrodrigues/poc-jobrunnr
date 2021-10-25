package pt.com.primeit.poc.jobrunnr.schedulers.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor(staticName = "builder")
@EqualsAndHashCode(of = "id")
@Entity
@Cacheable("job")
public class Job {

    @Id
    @Type(type="pg-uuid")
    private UUID id;


    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "className")
    private String className;

    public Job generateId() {
        id = UUID.randomUUID();
        return this;
    }

    public Job withName( String name) {
        this.name = name;
        return this;
    }

    public Job withClass( String className ) {
        this.className = className;
        return this;
    }

}

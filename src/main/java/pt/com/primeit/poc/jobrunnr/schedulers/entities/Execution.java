package pt.com.primeit.poc.jobrunnr.schedulers.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import pt.com.primeit.poc.jobrunnr.entities.*;
import pt.com.primeit.poc.jobrunnr.entities.Application;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor(staticName = "builder")
@EqualsAndHashCode(of = "id")
@Entity
public class Execution implements Serializable {

    @Id
    @Type(type="pg-uuid")
    private UUID id;

    @NonNull
    @Column()
    private String cronExpression;

    @ManyToOne(optional = false)
    private Tenant tenant;

    @ManyToOne(optional = false)
    private Application application;

    @ManyToOne(optional = false)
    private Job job;

}

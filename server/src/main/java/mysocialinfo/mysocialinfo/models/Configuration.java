package mysocialinfo.mysocialinfo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Configuration extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "socialnetworkid")
    private long socialNetworkId;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "configuration",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<Parameter> parameters;

    public Configuration() {
    }

    public Configuration(String name, long socialNetworkId) {
        this.name = name;
        this.socialNetworkId = socialNetworkId;
    }
}

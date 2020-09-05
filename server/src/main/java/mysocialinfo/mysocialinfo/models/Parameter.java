package mysocialinfo.mysocialinfo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Parameter extends BaseEntity {

    //            foreignKey = @ForeignKey(name = "put_name_here_If_You_HaveAForeignKeyConstraint"))
    @ManyToOne
    @JoinColumn(name = "configuration_id")
    private Configuration configuration;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @Column(name = "value")
    private String value;

    public Parameter() {
    }

    public Parameter(Type type, String value) {
        this.type = type;
        this.value = value;
    }
}

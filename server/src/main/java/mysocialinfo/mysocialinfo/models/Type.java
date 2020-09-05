package mysocialinfo.mysocialinfo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Type  extends BaseEntity{

    @Column(name = "name")
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }
}

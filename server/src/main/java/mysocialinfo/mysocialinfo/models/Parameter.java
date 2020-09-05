package mysocialinfo.mysocialinfo.models;

import javax.persistence.*;

@Entity
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long configuration_id;

    @ManyToOne
    @JoinColumn(name="type_id", foreignKey = @ForeignKey(name = "put_name_here_If_You_HaveAForeignKeyConstraint"))
    private Type type;
    private String value;

    public Parameter() {
    }

    public Parameter(long id, Type type, String value, long configuration_id) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.configuration_id = configuration_id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getConfiguration_id() {
        return configuration_id;
    }

    public void setConfiguration_id(long configuration_id) {
        this.configuration_id = configuration_id;
    }
}

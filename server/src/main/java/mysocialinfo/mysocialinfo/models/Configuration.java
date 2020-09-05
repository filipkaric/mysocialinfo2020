package mysocialinfo.mysocialinfo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.LinkedList;

@Entity
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LinkedList<Parameter> parameters;
    private String name;

    public Configuration() {
    }

    public Configuration(long id, LinkedList<Parameter> parameters, String name) {
        this.id = id;
        this.parameters = parameters;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LinkedList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(LinkedList<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

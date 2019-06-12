package rest.jsonObjects;

public class JSONingredient {
    String id;
    String name;

    public JSONingredient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public JSONingredient(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

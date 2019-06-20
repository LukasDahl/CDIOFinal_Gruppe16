/*
Forfatter: Lukas
Ansvar: Denne klasse får information om produkter fra hjemmesiden, og sender dem videre til databasen.
 */

package rest.jsonObjects;

public class JSONproduct {
    String id;
    String name;

    public JSONproduct(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public JSONproduct() {
    }

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

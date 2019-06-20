/*
Forfatter: Lukas
Ansvar: Denne klasse får information om ingredienshistorien fra hjemmesiden, og sender dem videre til databasen.
 */

package rest.jsonObjects;

public class JSONingredientHistory {
    String id;
    String name;
    String userID;
    String date;

    public JSONingredientHistory(String id, String name, String userID, String date) {
        this.id = id;
        this.name = name;
        this.userID = userID;
        this.date = date;
    }

    public JSONingredientHistory() {
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

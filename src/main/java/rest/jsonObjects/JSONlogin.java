/*
Forfatter: Lukas
Ansvar: Denne klasse får login informationer fra hjemmesiden, og sender dem videre til databasen.
 */

package rest.jsonObjects;

public class JSONlogin {
    String login_ID;

    public JSONlogin(String login_ID) {
        this.login_ID = login_ID;
    }

    public JSONlogin() {
    }

    public String getLogin_ID() {
        return login_ID;
    }

    public void setLogin_ID(String login_ID) {
        this.login_ID = login_ID;
    }
}


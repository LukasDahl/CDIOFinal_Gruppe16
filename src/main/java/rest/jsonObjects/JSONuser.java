/*
Forfatter: Lukas
Ansvar: Denne klasse får information om brugere fra hjemmesiden, og sender dem videre til databasen.
 */

package rest.jsonObjects;

public class JSONuser {

	String id;
	String username;
	String ini;
	String cpr;
	String admin;
	String role;
	String aktiv;

	public JSONuser(String id, String username, String ini, String cpr, String admin,String role,String aktiv){
		this.id = id;
		this.username = username;
		this.ini = ini;
		this.cpr = cpr;
		this.admin = admin;
		this.role = role;
		this.aktiv = aktiv;
	}

	public JSONuser(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getCpr() {
		return cpr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAktiv() {
		return aktiv;
	}

	public void setAktiv(String aktiv) {
		this.aktiv = aktiv;
	}
}

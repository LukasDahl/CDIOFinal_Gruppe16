package database.dto;

public class JSONuser {

	String id;
	String username;
	String ini;
	String cpr;
	String password;
	String role;

	public JSONuser(String id, String username, String ini, String cpr, String password, String role){
		this.id = id;
		this.username = username;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.role = role;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

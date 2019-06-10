package csv.dto;

import java.util.List;

public interface IUserDTO extends Comparable<IUserDTO>{
	int getUserId();

	void setUserId(int userId);

	String getUserName();

	void setUserName(String userName);

	String getIni();

	void setIni(String ini);

	String getCpr();

	void setCpr(String cpr);

	String getPassword();

	void setPassword(String password);

	List<String> getRoles();

	void setRoles(List<String> roles);

	void addRole(String role);

	boolean removeRole(String role);
}

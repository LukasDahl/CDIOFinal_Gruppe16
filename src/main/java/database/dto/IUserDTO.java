package database.dto;

import java.util.List;

public interface IUserDTO {
	int getUserId();

	void setUserId(int userId);

	String getUserName();

	void setUserName(String userName);

	int getUserIni();

	void setUserIni(int userIni);

	int getUserCPR();

	void setUserCPR(int userCPR);

	void setAdmin(boolean admin);

	boolean isAdmin();

	void setLabo(boolean labo);

	boolean isLabo();

	void setPLeader(boolean pLeader);

	boolean isPLeader();

	void setPharma(boolean pharma);

	boolean isPharma();


}

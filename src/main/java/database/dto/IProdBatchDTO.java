/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder og atributter der skal være i et "data transfer object" for produktbatches.
*/

package database.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface IProdBatchDTO {
	int getProdBatchId();

	void setProdBatchId(int prodBatchId);

	int getUserId();

	void setUserId(int userId);

	int getRecipeId();

	void setRecipeId(int recipeId);

	Date getDate();

	void setDate(Date date);

	int getStatus();

	void setStatus(int status);

	List<Integer> getMatList();

	void setMatList(List<Integer> matList);

	List<Integer> getLabList();

	void setLabList(List<Integer> labList);

	List<Double> getNettoList();

	void setNettoList(List<Double> nettoList);

	List<Double> getTaraList();

	void setTaraList(List<Double> taraList);

	List<Timestamp> getDateList();

	void setDateList(List<Timestamp> dateList);
}

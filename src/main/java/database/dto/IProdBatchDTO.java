package database.dto;

import java.sql.Date;
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

	List<Integer> getMatList();

	void setMatList(List<Integer> matList);

	List<Integer> getLabList();

	void setLabList(List<Integer> labList);
}

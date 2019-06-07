package database.dto;

import java.sql.Date;
import java.util.List;

public interface IRecipeDTO {
	int getRecipeId();

	void setRecipeId(int recipeId);

	int getProductId();

	void setProductId(int productId);

	List<Double> getAmount();

	void setAmount(List<Double> amount);

	Date getDate();

	void setDate(Date date);

	List<Integer> getPharmaList();

	void setPharmaList(List<Integer> pharmaList);

	List<Integer> getIngList();

	void setIngList(List<Integer> ingList);
}

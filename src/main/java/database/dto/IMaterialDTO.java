package database.dto;

import java.sql.Date;

public interface IMaterialDTO {

	int getMaterialBatchId();

	void setMaterialBatchId(int materialBatchId);

	int getIngredientId();

	void setIngredientId(int ingredientId);

	int getUserId();

	void setUserId(int userId);

	double getAmount();

	void setAmount(double amount);

	Date getDate();

	void setDate(Date date);

	boolean getOrder();

	void setOrder(boolean order);

}

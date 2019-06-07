package database.dto;

public interface IIngredientDTO {
	int getIngredientId();

	void setIngredientId(int userId);

	String getIngredientName();

	void setIngredientName(String userName);

	double getMargin();

	void setMargin(double margin);

	boolean getActive();

	void setActive(boolean active);
}

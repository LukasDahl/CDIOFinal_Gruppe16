package database.dto;

public interface IIngredientDTO {
	int getIngredientId();

	void setIngredientId(int userId);

	String getIngredientName();

	void setIngredientName(String userName);

	boolean getActive();

	void setActive(boolean active);
}

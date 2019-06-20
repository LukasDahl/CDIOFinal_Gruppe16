/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder og atributter der skal være i et "data transfer object" for ingredienser.
*/

package database.dto;

public interface IIngredientDTO {
	int getIngredientId();

	void setIngredientId(int userId);

	String getIngredientName();

	void setIngredientName(String userName);

	boolean getActive();

	void setActive(boolean active);
}

/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder og atributter der skal være i et "data transfer object" for ingredienserhistorikken.
*/

package database.dto;

import java.sql.Date;

public interface IIngredientHistoryDTO {
    int getIngredientId();

    void setIngredientId(int ingredientId);

    String getIngredientName();

    void setIngredientName(String ingredientName);

    Date getDate();

    void setDate(Date date);

    int getUserId();

    void setUserId(int userId);
}

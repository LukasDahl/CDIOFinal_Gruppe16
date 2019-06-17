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

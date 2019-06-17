package database.dto;

import java.sql.Date;

public class IngredientHistoryDTO implements IIngredientHistoryDTO {
    int ingredientId;
    String ingredientName;
    Date date;
    int userId;

    @Override
    public int getIngredientId() {
        return ingredientId;
    }

    @Override
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }
}

package database.dal;

import database.dto.*;

import java.util.List;

public interface IIngredientHistoryDAO extends IDALException{
    //Create
    void createIngredientEntry(IIngredientHistoryDTO ingredient) throws DALException;

    //Read
    List<IIngredientHistoryDTO> getIngredientList(int ingredientId) throws DALException;

}


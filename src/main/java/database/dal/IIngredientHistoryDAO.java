/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder der skal være i et "data access object" for ingredienshistorikken.
*/

package database.dal;

import database.dto.*;

import java.util.List;

public interface IIngredientHistoryDAO extends IDALException{
    //Create
    void createIngredientEntry(IIngredientHistoryDTO ingredient) throws DALException;

    //Read
    List<IIngredientHistoryDTO> getIngredientList(int ingredientId) throws DALException;

}


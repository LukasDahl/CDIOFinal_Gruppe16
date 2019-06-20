/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder der skal være i et "data access object" for ingredienser.
*/

package database.dal;

import database.dto.*;

import java.util.List;

public interface IIngredientDAO extends IDALException{
	//Create
	void createIngredient(IIngredientDTO ingredient) throws DALException;

	//Read
	IIngredientDTO getIngredient(int ingredientId) throws DALException;
	List<IIngredientDTO> getIngredientList() throws DALException;

	//Update
	void updateIngredient(IIngredientDTO ingredient) throws DALException;

}

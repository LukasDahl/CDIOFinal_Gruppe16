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

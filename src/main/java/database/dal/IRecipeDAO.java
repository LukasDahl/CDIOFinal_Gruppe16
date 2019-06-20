/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder der skal være i et "data access object" for opskrifter.
*/

package database.dal;

import database.dto.*;
import java.util.List;

public interface IRecipeDAO extends IDALException{
	//Create
	void createRecipe(IRecipeDTO recipe) throws DALException;

	//Read
	IRecipeDTO getRecipe(int recipeId) throws DALException;
	List<IRecipeDTO> getRecipeList() throws DALException;

}

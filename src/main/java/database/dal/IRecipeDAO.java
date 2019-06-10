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

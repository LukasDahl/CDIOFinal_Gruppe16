package database.dal;

import database.dto.*;

import java.util.List;

public interface IMaterialDAO extends IDALException{
	//
	void createMaterial(IMaterialDTO material) throws DALException;
	//Read
	IMaterialDTO getMaterial(int materialId) throws DALException;

	List<IMaterialDTO> getMaterialList() throws DALException;
	//Update
	void updateMaterial(IMaterialDTO material) throws DALException;


}

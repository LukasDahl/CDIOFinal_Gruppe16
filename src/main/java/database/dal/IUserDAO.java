package database.dal;

import dto.IUserDTO;

import java.util.List;

public interface IUserDAO extends IDALException{
	//Create
	void createUser(IUserDTO user) throws DALException;
	//Read
	IUserDTO getUser(int userId) throws DALException;

	List<IUserDTO> getUserList() throws DALException;
	//Update
	void updateUser(IUserDTO user) throws DALException;

}
package database.dal;

import database.dto.*;

import java.util.List;

public interface IProdBatchDAO extends IDALException{
	//Create
	void createProdBatch(IProdBatchDTO prodBatch) throws DALException;

	//Read
	IProdBatchDTO getProdBatch(int prodBatchId) throws DALException;
	List<IProdBatchDTO> getProdBatchList() throws DALException;
}

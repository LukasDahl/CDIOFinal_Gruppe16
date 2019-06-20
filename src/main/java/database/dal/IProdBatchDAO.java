/*
Forfatter: Lukas
Ansvar: Klassen er oprettet som interface og beskriver hvilke metoder der skal være i et "data access object" for produktbatches.
*/

package database.dal;

import database.dto.*;

import java.util.List;

public interface IProdBatchDAO extends IDALException{
	//Create
	void createProdBatch(IProdBatchDTO prodBatch) throws DALException;
	void finishProdBatch(IProdBatchDTO prodBatch) throws DALException;
	void closeProdBatch(IProdBatchDTO prodBatch, int state) throws DALException;

	//Read
	IProdBatchDTO getProdBatch(int prodBatchId) throws DALException;
	List<IProdBatchDTO> getProdBatchList() throws DALException;
}

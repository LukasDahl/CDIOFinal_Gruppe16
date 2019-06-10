package database.dal;

import database.dto.*;

import java.util.List;

public interface IProductDAO extends IDALException{

    void createProduct(IProductDTO product) throws DALException;

    IProductDTO getProduct(int productId) throws DALException;

    List<IProductDTO> getProductList() throws DALException;

    void updateProduct(IProductDTO product) throws DALException;

}
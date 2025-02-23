package lk.ijse.gdse71.dreamlandkids.bo.custom;

import lk.ijse.gdse71.dreamlandkids.bo.SuperBO;
import lk.ijse.gdse71.dreamlandkids.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException;

    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException;

    boolean deleteSupplier(String supplierId) throws SQLException;

    boolean existSupplier(String id) throws SQLException;

    ArrayList<SupplierDTO> getAllSupplier() throws SQLException;

    String getNextSupplierId() throws SQLException, ClassNotFoundException;

}

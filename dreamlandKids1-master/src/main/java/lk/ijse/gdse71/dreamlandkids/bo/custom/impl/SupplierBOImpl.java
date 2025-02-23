package lk.ijse.gdse71.dreamlandkids.bo.custom.impl;

import lk.ijse.gdse71.dreamlandkids.bo.custom.SupplierBO;
import lk.ijse.gdse71.dreamlandkids.dao.DAOFactory;
import lk.ijse.gdse71.dreamlandkids.dao.custom.SupplierDAO;
import lk.ijse.gdse71.dreamlandkids.dto.EmployeeDTO;
import lk.ijse.gdse71.dreamlandkids.dto.SupplierDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Employee;
import lk.ijse.gdse71.dreamlandkids.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO= (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierDAO.save(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getNic(),supplierDTO.getEmail(),supplierDTO.getPhone()));

    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierDAO.update(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getName(),supplierDTO.getNic(),supplierDTO.getEmail(),supplierDTO.getPhone()));

    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public boolean existSupplier(String id) throws SQLException {
        return supplierDAO.exist(id);
    }

    @Override
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException {
        ArrayList<SupplierDTO> allSuppliers = new ArrayList<>();
        ArrayList<Supplier> all=supplierDAO.getAll();
        for(Supplier s: all){
            SupplierDTO supplierDTO = new SupplierDTO(
                    s.getSupplierId(),
                    s.getName(),
                    s.getNic(),
                    s.getEmail(),
                    s.getPhone()
            );
            allSuppliers.add(supplierDTO);
        }
        return allSuppliers;
    }

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewId();

    }
}

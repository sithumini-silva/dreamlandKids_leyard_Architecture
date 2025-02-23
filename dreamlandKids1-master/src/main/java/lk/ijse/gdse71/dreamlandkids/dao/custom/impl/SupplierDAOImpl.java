package lk.ijse.gdse71.dreamlandkids.dao.custom.impl;

import lk.ijse.gdse71.dreamlandkids.dao.SQLUtil;
import lk.ijse.gdse71.dreamlandkids.dao.custom.SupplierDAO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Supplier;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public boolean exist(String id) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT supplier_id FROM supplier WHERE supplier_id=?",id);
        return rs.next();
    }

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO supplier(supplier_id,name,nic,email,phone) VALUES(?,?,?,?,?)",entity.getSupplierId(),entity.getName(),entity.getNic(),entity.getEmail(),entity.getPhone());
    }

    @Override
    public boolean delete(String supplierId) throws SQLException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supplier_id=?",supplierId);
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute("UPDATE supplier SET name=?, nic=?, email=?, phone=? WHERE supplier_id=?",
                entity.getName(), entity.getNic(), entity.getEmail(), entity.getPhone(), entity.getSupplierId());
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> allSuppliers = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM supplier");
        while (rs.next()) {
            Supplier supplier = new Supplier(rs.getString("supplier_id"),rs.getString("name"),rs.getString("nic"),rs.getString("email"),rs.getString("phone"));
            allSuppliers.add(supplier);
        }
        return allSuppliers;

    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select supplier_id from Supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

}

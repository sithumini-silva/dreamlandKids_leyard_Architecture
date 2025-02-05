package lk.ijse.gdse71.dreamlandkids.dao.custom;

import lk.ijse.gdse71.dreamlandkids.dao.CrudDAO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    public Customer search(String id) throws SQLException;

}

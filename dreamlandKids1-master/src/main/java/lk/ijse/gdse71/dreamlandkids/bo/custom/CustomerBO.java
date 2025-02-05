package lk.ijse.gdse71.dreamlandkids.bo.custom;

import lk.ijse.gdse71.dreamlandkids.bo.SuperBO;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean existCustomer(String id) throws SQLException;

    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;

    boolean deleteCustomer(String customerId) throws SQLException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException;

    Customer findById(String selectedCustomerId) throws SQLException;

//    String getNextCustomerId();
}

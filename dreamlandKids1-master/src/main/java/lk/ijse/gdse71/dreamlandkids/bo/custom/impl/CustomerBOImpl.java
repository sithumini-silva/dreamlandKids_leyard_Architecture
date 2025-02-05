package lk.ijse.gdse71.dreamlandkids.bo.custom.impl;

import lk.ijse.gdse71.dreamlandkids.bo.custom.CustomerBO;
import lk.ijse.gdse71.dreamlandkids.dao.DAOFactory;
import lk.ijse.gdse71.dreamlandkids.dao.custom.CustomerDAO;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean existCustomer(String id) throws SQLException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.save(new Customer(dto.getCustomerId(),dto.getName(),dto.getNic(),dto.getEmail(),dto.getPhone()));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCustomerId(),dto.getName(),dto.getNic(),dto.getEmail(),dto.getPhone()));
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all=customerDAO.getAll();
        for(Customer c: all){
            CustomerDTO customerDTO = new CustomerDTO(
                    c.getCustomerId(),
                    c.getName(),
                    c.getNic(),
                    c.getEmail(),
                    c.getPhone()
            );
            allCustomers.add(customerDTO);
        }
        return allCustomers;
    }

    @Override
    public Customer findById(String selectedCustomerId) throws SQLException {
        if(customerDAO==null){
            throw new NullPointerException("CustomerDAO is null");
        }
        Customer c=customerDAO.search(selectedCustomerId);
        return new Customer(c.getCustomerId(),c.getName(),c.getNic(),c.getEmail(),c.getPhone());
    }
}

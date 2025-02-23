package lk.ijse.gdse71.dreamlandkids.bo.custom.impl;

import lk.ijse.gdse71.dreamlandkids.bo.BOFactory;
import lk.ijse.gdse71.dreamlandkids.bo.custom.CustomerBO;
import lk.ijse.gdse71.dreamlandkids.bo.custom.OrderBO;
import lk.ijse.gdse71.dreamlandkids.dao.DAOFactory;
import lk.ijse.gdse71.dreamlandkids.dao.custom.CustomerDAO;
import lk.ijse.gdse71.dreamlandkids.dao.custom.ItemDAO;
import lk.ijse.gdse71.dreamlandkids.dao.custom.OrderDAO;
import lk.ijse.gdse71.dreamlandkids.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.dreamlandkids.db.DBConnection;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Item;
import lk.ijse.gdse71.dreamlandkids.entity.Order;
import lk.ijse.gdse71.dreamlandkids.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO= (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO= (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    ItemDAO itemDAO= (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);


    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.save(new Order(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getOrderDate(),orderDTO.getOrderDetailsDTOS()));

            if (isOrderSaved) {
                boolean isOrderDetailListSaved = saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<Customer> customerData=customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS=new ArrayList<>();
        for (Customer customer : customerData) {
            customerDTOS.add(new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getNic(),customer.getEmail(),customer.getPhone()));
        }
        return customerDTOS;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<Item> itemData=itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS=new ArrayList<>();
        for (Item item : itemData) {
            itemDTOS.add(new ItemDTO(item.getItemId(),item.getItemName(),item.getQuantity(),item.getPrice()));
        }
        return itemDTOS;
    }

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }


    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            boolean isOrderDetailsSaved =orderDetailDAO.saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = itemDAO.reduceQty(orderDetailsDTO);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }
}

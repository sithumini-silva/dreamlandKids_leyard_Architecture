package lk.ijse.gdse71.dreamlandkids.dao;

import lk.ijse.gdse71.dreamlandkids.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse71.dreamlandkids.bo.custom.impl.SupplierBOImpl;
import lk.ijse.gdse71.dreamlandkids.dao.custom.CustomerDAO;
import lk.ijse.gdse71.dreamlandkids.dao.custom.impl.*;

import javax.swing.plaf.PanelUI;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)? daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,EMPLOYEE,ITEM,SUPPLIER,ORDER,ORDER_DETAILS
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE :
                return new EmployeeDAOImpl();
            case SUPPLIER :
                return new SupplierDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}

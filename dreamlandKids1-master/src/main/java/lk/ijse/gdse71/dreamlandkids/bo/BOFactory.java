package lk.ijse.gdse71.dreamlandkids.bo;

import lk.ijse.gdse71.dreamlandkids.bo.custom.impl.*;
import lk.ijse.gdse71.dreamlandkids.dao.custom.impl.ItemDAOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,SUPPLIER,EMPLOYEE,ITEM,ORDER
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER :
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER :
                return new SupplierBOImpl();
            case ITEM :
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            default:
                return null;
        }
    }
}

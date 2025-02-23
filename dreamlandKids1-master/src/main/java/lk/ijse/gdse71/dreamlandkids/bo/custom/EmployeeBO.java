package lk.ijse.gdse71.dreamlandkids.bo.custom;

import lk.ijse.gdse71.dreamlandkids.bo.SuperBO;
import lk.ijse.gdse71.dreamlandkids.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean existEmployee(String id) throws SQLException;

    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;

    boolean deleteEmployee(String employeeId) throws SQLException;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;

    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException;

    String getNextEmployeeId() throws SQLException, ClassNotFoundException;



}

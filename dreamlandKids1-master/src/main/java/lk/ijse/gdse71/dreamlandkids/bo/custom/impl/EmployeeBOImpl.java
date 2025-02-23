package lk.ijse.gdse71.dreamlandkids.bo.custom.impl;

import lk.ijse.gdse71.dreamlandkids.bo.custom.EmployeeBO;
import lk.ijse.gdse71.dreamlandkids.dao.DAOFactory;
import lk.ijse.gdse71.dreamlandkids.dao.custom.EmployeeDAO;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.EmployeeDTO;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean existEmployee(String id) throws SQLException {
        return employeeDAO.exist(id);
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.save(new Employee(employeeDTO.getEmployeeId(),employeeDTO.getName(),employeeDTO.getNic(),employeeDTO.getEmail(),employeeDTO.getPhone()));
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.update(new Employee(employeeDTO.getEmployeeId(),employeeDTO.getName(),employeeDTO.getNic(),employeeDTO.getEmail(),employeeDTO.getPhone()));

    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException {
        ArrayList<EmployeeDTO> allEmployees = new ArrayList<>();
        ArrayList<Employee> all=employeeDAO.getAll();
        for(Employee e: all){
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    e.getEmployeeId(),
                    e.getName(),
                    e.getNic(),
                    e.getEmail(),
                    e.getPhone()
            );
            allEmployees.add(employeeDTO);
        }
        return allEmployees;
    }

    @Override
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewId();
    }
}

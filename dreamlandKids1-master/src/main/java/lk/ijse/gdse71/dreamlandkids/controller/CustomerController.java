package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.dreamlandkids.bo.BOFactory;
import lk.ijse.gdse71.dreamlandkids.bo.custom.CustomerBO;
import lk.ijse.gdse71.dreamlandkids.db.DBConnection;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class CustomerController  implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<CustomerTM, String> colPhone;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    CustomerBO customerBo= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);


    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }
    }
    public void initialize() throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCustomerId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
    }


    private void loadTableData() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerBo.getAllCustomers();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();


        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerId(),
                    customerDTO.getName(),
                    customerDTO.getNic(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone()
            );
            customerTMS.add(customerTM);
        }

        tblCustomer.setItems(customerTMS);
    }

    public void loadNextCustomerId() throws SQLException, ClassNotFoundException {
        String nextCustomerId = customerBo.getNextCustomerId();
        lblCustomerId.setText(nextCustomerId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            CustomerDTO customerDTO = new CustomerDTO(
                    id,
                    name,
                    nic,
                    email,
                    phone
            );


        if (btnSave.getText().equalsIgnoreCase("Save")) {
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                } else {
                    customerBo.saveCustomer(new CustomerDTO(id, name, nic, email, phone));
                    tblCustomer.getItems().add(new CustomerTM(id, name, nic, email, phone));
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }
        initialize();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lblCustomerId.setText(customerTM.getCustomerId());
            txtName.setText(customerTM.getName());
            txtNic.setText(customerTM.getNic());
            txtEmail.setText(customerTM.getEmail());
            txtPhone.setText(customerTM.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    boolean existCustomer(String id) throws SQLException {
        return customerBo.existCustomer(id);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();

        try {
            boolean isDeleted=customerBo.deleteCustomer(customerId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Failed customer deleted"+customerId).show();
        }
        initialize();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    nic,
                    email,
                    phone
            );

            try {
                if (!existCustomer(customerId)) {
                    new Alert(Alert.AlertType.ERROR, "Can't update. Customer does not exist.").show();
                    return;
                }

                boolean updateSuccess = customerBo.updateCustomer(customerDTO);
                if (updateSuccess) {
                   loadTableData();
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update customer.").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    public void generateAllCustomerReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/reports/CustomerReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    public void orderReportOnAction(ActionEvent actionEvent) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();

        if (customerTM == null) {
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/reports/CustomerOrderReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Customer_Id", customerTM.getCustomerId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }


    @FXML
    public void openSendMailModel(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMailView.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Email");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load UI..!");
            e.printStackTrace();
        }
    }

}

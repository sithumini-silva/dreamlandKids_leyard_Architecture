package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.dreamlandkids.bo.BOFactory;
import lk.ijse.gdse71.dreamlandkids.bo.custom.SupplierBO;
import lk.ijse.gdse71.dreamlandkids.dto.SupplierDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.SupplierTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController  implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM,String> colEmail;

    @FXML
    private TableColumn<SupplierTM,String> colName;

    @FXML
    private TableColumn<SupplierTM,String> colNic;

    @FXML
    private TableColumn<SupplierTM,String> colPhone;

    @FXML
    private TableColumn<SupplierTM,String> colSupplierId;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtSupmail;

    @FXML
    private TextField txtSupNic;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSupPhone;

    SupplierBO supplierBO= (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        boolean isDeleted=supplierBO.deleteSupplier(supplierId);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted"+supplierId).show();
            refreshPage();
        }

    }

    boolean existSupplier(String id) throws SQLException {
        return supplierBO.existSupplier(id);
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();
        String name = txtSupName.getText();
        String nic = txtSupNic.getText();
        String email = txtSupmail.getText();
        String phone = txtSupPhone.getText();

        txtSupName.setStyle(txtSupName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupNic.setStyle(txtSupNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupmail.setStyle(txtSupmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupPhone.setStyle(txtSupPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtSupName.getStyle());
            txtSupName.setStyle(txtSupName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }
        if (!isValidNic) {
            txtSupNic.setStyle(txtSupNic.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtSupmail.setStyle(txtSupmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtSupPhone.setStyle(txtSupPhone.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplierId,
                    name,
                    nic,
                    email,
                    phone
            );

            if (btnSave.getText().equalsIgnoreCase("Save")) {
                try {
                    if (existSupplier(supplierId)) {
                        new Alert(Alert.AlertType.ERROR, supplierId + " already exists").show();
                    } else {
                        supplierBO.saveSupplier(new SupplierDTO(supplierId, name, nic, email, phone));
                        tblSupplier.getItems().add(new SupplierTM(supplierId, name, nic, email, phone));
                        refreshPage();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();
        String name = txtSupName.getText();
        String nic = txtSupNic.getText();
        String email = txtSupmail.getText();
        String phone = txtSupPhone.getText();

        txtSupName.setStyle(txtSupName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupNic.setStyle(txtSupNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupmail.setStyle(txtSupmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtSupPhone.setStyle(txtSupPhone.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtSupName.getStyle());
            txtSupName.setStyle(txtSupName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }
        if (!isValidNic) {
            txtSupNic.setStyle(txtSupNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }
        if (!isValidEmail) {
            txtSupmail.setStyle(txtSupmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtSupPhone.setStyle(txtSupPhone.getStyle() + ";-fx-border-color: red;");
        }
        if (isValidName && isValidNic && isValidEmail && isValidPhone) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplierId,
                    name,
                    nic,
                    email,
                    phone
            );
            try {
                if (!existSupplier(supplierId)) {
                    new Alert(Alert.AlertType.ERROR, "Can't update. Supplier does not exist.").show();
                    return;
                }

                boolean updateSuccess = supplierBO.updateSupplier(supplierDTO);
                if (updateSuccess) {
                    loadTableData();
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update supplier.").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierTM supplierTM = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTM != null) {
            lblSupplierId.setText(supplierTM.getSupplierId());
            txtSupName.setText(supplierTM.getName());
            txtSupNic.setText(supplierTM.getNic());
            txtSupmail.setText(supplierTM.getEmail());
            txtSupPhone.setText(supplierTM.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load supplier id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextSupplierId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtSupName.setText("");
        txtSupNic.setText("");
        txtSupmail.setText("");
        txtSupPhone.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<SupplierDTO> supplierDTOS = supplierBO.getAllSupplier();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getName(),
                    supplierDTO.getNic(),
                    supplierDTO.getEmail(),
                    supplierDTO.getPhone()
            );
            supplierTMS.add(supplierTM);
        }
        tblSupplier.setItems(supplierTMS);
    }

    private void loadNextSupplierId() throws SQLException, ClassNotFoundException {
        String nextSupplierId = supplierBO.getNextSupplierId();
        lblSupplierId.setText(nextSupplierId);
    }
}

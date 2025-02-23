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
import lk.ijse.gdse71.dreamlandkids.bo.custom.ItemBO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.ItemTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private TableColumn<ItemTM,String> colItemId;

    @FXML
    private TableColumn<ItemTM,String> colName;

    @FXML
    private TableColumn<ItemTM,Double> colPrice;

    @FXML
    private TableColumn<ItemTM,Integer> colQuantity;

    @FXML
    private Label lblItemId;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    ItemBO itemBO= (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);


    @FXML
    void btnDeleteItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = lblItemId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = itemBO.deleteItem(itemId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted").show();
                refreshPage();
            }
        }
    }
    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemId.getText();
        String name = txtName.getText();
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Double price = Double.valueOf(txtPrice.getText());

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (isValidName) {
            ItemDTO itemDTO = new ItemDTO(
                    itemId,
                    name,
                    quantity,
                    price
            );
            if (btnSaveItem.getText().equalsIgnoreCase("Save")) {
                try {
                    if (existItem(itemId)) {
                        new Alert(Alert.AlertType.ERROR, itemId + " already exists").show();
                    } else {
                        itemBO.saveItem(new ItemDTO(itemId, name, quantity, price));
                        tblItem.getItems().add(new ItemTM(itemId, name, quantity, price));
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

    boolean existItem(String id) throws SQLException {
        return itemBO.existItem(id);
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemId.getText();
        String name = txtName.getText();
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Double price = Double.valueOf(txtPrice.getText());

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (isValidName) {
            ItemDTO itemDTO = new ItemDTO(
                    itemId,
                    name,
                    quantity,
                    price
            );
            try {
                if (!existItem(itemId)) {
                    new Alert(Alert.AlertType.ERROR, "Can't update. Item does not exist.").show();
                    return;
                }

                boolean updateSuccess = itemBO.updateItem(itemDTO);
                if (updateSuccess) {
                    loadTableData();
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Item updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update item.").show();
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
        ItemTM itemTM = tblItem.getSelectionModel().getSelectedItem();
        if (itemTM != null) {
            lblItemId.setText(itemTM.getItemId());
            txtName.setText(itemTM.getName());
            txtQuantity.setText(String.valueOf(itemTM.getQuantity()));
            txtPrice.setText(String.valueOf(itemTM.getPrice()));

            btnSaveItem.setDisable(true);

            btnDeleteItem.setDisable(false);
            btnUpdateItem.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextItemId();
        loadTableData();

        btnSaveItem.setDisable(false);
        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);

        txtName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");

    }

    private void loadTableData() throws SQLException {
        ArrayList<ItemDTO> itemDTOS = itemBO.getAllItems();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for (ItemDTO itemDTO : itemDTOS) {
            ItemTM itemTM = new ItemTM(
                    itemDTO.getItemId(),
                    itemDTO.getItemName(),
                    itemDTO.getQuantity(),
                    itemDTO.getPrice()

            );
            itemTMS.add(itemTM);
        }
        tblItem.setItems(itemTMS);
    }

    private void loadNextItemId() throws SQLException, ClassNotFoundException {
        String nextItemId = itemBO.getNextItemId();
        lblItemId.setText(nextItemId);
    }
}

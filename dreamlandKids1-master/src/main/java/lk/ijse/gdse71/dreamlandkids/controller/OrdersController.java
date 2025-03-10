package lk.ijse.gdse71.dreamlandkids.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.dreamlandkids.bo.BOFactory;
import lk.ijse.gdse71.dreamlandkids.bo.custom.CustomerBO;
import lk.ijse.gdse71.dreamlandkids.bo.custom.ItemBO;
import lk.ijse.gdse71.dreamlandkids.bo.custom.OrderBO;
import lk.ijse.gdse71.dreamlandkids.dto.CustomerDTO;
import lk.ijse.gdse71.dreamlandkids.dto.ItemDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDTO;
import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lk.ijse.gdse71.dreamlandkids.dto.tm.CartTM;
import lk.ijse.gdse71.dreamlandkids.entity.Customer;
import lk.ijse.gdse71.dreamlandkids.entity.Item;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private ComboBox<String> cmbItemId;
    @FXML
    private TableView<CartTM> tblCart;
    @FXML
    private TableColumn<CartTM, String> colItemId;
    @FXML
    private TableColumn<CartTM, String> colName;
    @FXML
    private TableColumn<CartTM, Integer> colQuantity;
    @FXML
    private TableColumn<CartTM, Double> colPrice;
    @FXML
    private TableColumn<CartTM, Double> colTotal;
    @FXML
    private TableColumn<?, ?> colAction;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblItemName;
    @FXML
    private Label lblItemPrice;
    @FXML
    private Label lblItemQty;
    @FXML
    private Label lblOrderId;
    @FXML
    private Label orderDate;
    @FXML
    private TextField txtAddToCartQty;


    OrderBO orderBO= (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    CustomerBO customerBO= (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ItemBO itemBO= (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(orderBO.getNextOrderId());
        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemId();

        lblItemName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();
        tblCart.refresh();
    }


    private void loadItemId() throws SQLException {
        try {
            cmbItemId.getItems().clear();
            ArrayList<ItemDTO> allItems = orderBO.getAllItems();

            for (ItemDTO i : allItems) {
                cmbItemId.getItems().add(i.getItemId());
            }

            if (!cmbItemId.getItems().isEmpty()) {
                cmbItemId.getSelectionModel().select(0);
            }

            System.out.println("ComboBox items: " + cmbItemId.getItems());
            System.out.println("Selected Customer after loading: " + cmbItemId.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load customer data..!").show();
        }
    }

    private void loadCustomerIds() throws SQLException {
        try {
            cmbCustomerId.getItems().clear();
            ArrayList<CustomerDTO> allCustomers = orderBO.getAllCustomers();

            for (CustomerDTO c : allCustomers) {
                cmbCustomerId.getItems().add(c.getCustomerId());
            }

            if (!cmbCustomerId.getItems().isEmpty()) {
                cmbCustomerId.getSelectionModel().select(0);
            }

            System.out.println("ComboBox items: " + cmbCustomerId.getItems());
            System.out.println("Selected Customer after loading: " + cmbCustomerId.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load customer data..!").show();
        }
    }




    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return;
        }

        String cartQtyString = txtAddToCartQty.getText();

        String qtyPattern = "^[0-9]+$";

        if (!cartQtyString.matches(qtyPattern)){
            new Alert(Alert.AlertType.ERROR, "Please enter valid quantity..!").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblItemQty.getText());

        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return;
        }

        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {

            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);

                tblCart.refresh();
                return;
            }
        }

        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        btn.setOnAction(actionEvent -> {

            cartTMS.remove(newCartTM);

            tblCart.refresh();
        });

        cartTMS.add(newCartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {

            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = orderBO.saveOrder(orderDTO);


        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();


            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        System.out.println("Selected Customer ID: " + selectedCustomerId);

        if (selectedCustomerId != null && !selectedCustomerId.isEmpty()) {
            Customer customer = customerBO.findById(selectedCustomerId);

            if (customer != null) {
                lblCustomerName.setText(customer.getName());
            } else {
                new Alert(Alert.AlertType.WARNING, "Customer not found!").show();
            }
        }
    }






    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        if (selectedItemId != null && !selectedItemId.isEmpty()) {
           Item item = itemBO.findById(selectedItemId);

            if (item != null) {
                lblItemName.setText(item.getItemName());
                lblItemQty.setText(String.valueOf(item.getQuantity()));
                lblItemPrice.setText(String.valueOf(item.getPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Customer not found!").show();
            }

        }
    }

}


package lk.ijse.gdse71.dreamlandkids.entity;

import lk.ijse.gdse71.dreamlandkids.dto.OrderDetailsDTO;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String customerId;
    private Date orderDate;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}

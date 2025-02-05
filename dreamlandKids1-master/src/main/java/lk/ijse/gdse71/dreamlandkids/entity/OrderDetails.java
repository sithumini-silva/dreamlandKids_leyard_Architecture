package lk.ijse.gdse71.dreamlandkids.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetails {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}

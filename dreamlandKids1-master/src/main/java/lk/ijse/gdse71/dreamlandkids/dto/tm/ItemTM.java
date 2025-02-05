package lk.ijse.gdse71.dreamlandkids.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemTM {
    private String itemId;
    private String name;
    private int quantity;
    private double price;

    public ItemTM(String itemId, String itemName, int quantity, double price, Object o) {
        this.itemId = itemId;
        this.name = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}

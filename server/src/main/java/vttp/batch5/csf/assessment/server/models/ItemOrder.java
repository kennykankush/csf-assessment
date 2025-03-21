package vttp.batch5.csf.assessment.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrder {

    private String id;
    private double price;
    private int quantity;
    
}

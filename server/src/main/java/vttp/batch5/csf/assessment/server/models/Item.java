package vttp.batch5.csf.assessment.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String id;
    private String name;
    private double price;
    private String description;
    private int quantity = 0;
    
}

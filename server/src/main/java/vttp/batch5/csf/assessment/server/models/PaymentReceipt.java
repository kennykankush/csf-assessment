package vttp.batch5.csf.assessment.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceipt {

    private String payment_id;
    private String order_id;
    private long timestamp;
    private double total;
    
}

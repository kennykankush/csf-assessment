package vttp.batch5.csf.assessment.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private String payment_id;
    private String payer;
    private String payee;
    private double payment;
    
}

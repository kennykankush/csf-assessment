package vttp.batch5.csf.assessment.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vttp.batch5.csf.assessment.server.models.ItemOrder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
    private List<ItemOrder> items;
    
}

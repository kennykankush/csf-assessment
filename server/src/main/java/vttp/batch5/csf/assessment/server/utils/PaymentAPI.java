package vttp.batch5.csf.assessment.server.utils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.batch5.csf.assessment.server.configuration.Constant;
import vttp.batch5.csf.assessment.server.dto.PaymentDTO;


@Service
public class PaymentAPI {

    @Autowired
    private Constant constant;

    public ResponseEntity<String> doPayment(String orderId, String name, double total) throws JsonProcessingException{
        RestTemplate restTemplate = new RestTemplate();
        // PaymentDTO payment = new PaymentDTO();
        // payment.setPayment_id(orderId);
        // payment.setPayer(name);
        // payment.setPayee("test");
        // payment.setPayment(total);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authenticate", name);
        headers.setContentType(MediaType.APPLICATION_JSON);
        

        String json = new JSONObject()
                  .put("order_id", orderId)
                  .put("payer", name)
                  .put("payee", constant.secretSauce)
                  .put("payment", total).toString();

        System.out.println(json);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://payment-service-production-a75a.up.railway.app/api/payment", entity, String.class);

        return response;
        

    }
    
    
}

package vttp.batch5.csf.assessment.server.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import vttp.batch5.csf.assessment.server.dto.LoginRequest;
import vttp.batch5.csf.assessment.server.models.Item;
import vttp.batch5.csf.assessment.server.models.ItemOrder;
import vttp.batch5.csf.assessment.server.models.PaymentReceipt;
import vttp.batch5.csf.assessment.server.services.RestaurantService;
import vttp.batch5.csf.assessment.server.utils.PaymentAPI;



@RestController
@RequestMapping("/api")
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private PaymentAPI paymentService;

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping("/getmenu")
  public ResponseEntity<Object> getMenus() {

    System.out.println("Getting Menu From Mongo");

    List<Item> menu = restaurantService.getMenu();
    return ResponseEntity.ok().body(menu);
  }
  


  // TODO: Task 4
  @PostMapping("/food_order")
  public ResponseEntity<Object> postFoodOrder(@RequestBody String payload) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsons = mapper.readTree(payload);

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername(jsons.get("username").asText());
    loginRequest.setPassword(jsons.get("password").asText());

    JsonNode arrItem = jsons.get("items");
    List<ItemOrder> items = new ArrayList<>();

    if (arrItem.isArray()){
      for (JsonNode jsonNode: arrItem){
        ItemOrder item = new ItemOrder();
        item.setId(jsonNode.get("id").asText());
        item.setPrice(jsonNode.get("price").asDouble());
        item.setQuantity(jsonNode.get("quantity").asInt());

        items.add(item);
      }
    }

    loginRequest.setItems(items);

    boolean loginCheck = restaurantService.checkLogin(loginRequest.getUsername(), loginRequest.getPassword());

    Map<String, String> response = new HashMap<>();
    
    if(!loginCheck){

      response.put("message", "Incorrect username or Password");

      return ResponseEntity.badRequest().body(response);
    }

    String orderId = UUID.randomUUID().toString().substring(0, 8);

    double total = 0;

    for(ItemOrder item: loginRequest.getItems()){
      total += item.getPrice();
    }

    ResponseEntity<String> paymentStatus = paymentService.doPayment(orderId, loginRequest.getUsername(), total);
    System.out.println(paymentStatus.getStatusCode());
    System.out.println(paymentStatus.getBody());

    if (paymentStatus.getStatusCode() == HttpStatusCode.valueOf(202)){
      System.out.println("ok payment succeed");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(paymentStatus.getBody());

        PaymentReceipt receipt = new PaymentReceipt();
        receipt.setPayment_id(responseJson.get("payment_id").asText());
        receipt.setTimestamp(responseJson.get("timestamp").asLong());
        receipt.setOrder_id(responseJson.get("order_id").asText());
        receipt.setTotal(total);

        restaurantService.insertIntoOrder(receipt, loginRequest, total);
        restaurantService.insertIntoMongo(loginRequest, receipt, total);

        return ResponseEntity.ok().body(receipt);

    }

    if (paymentStatus.getStatusCode() == HttpStatusCode.valueOf(404)){

        return ResponseEntity.ok().body(paymentStatus.getBody());

    }


    return ResponseEntity.badRequest().body(paymentStatus.getBody());

  
    }

    
  }


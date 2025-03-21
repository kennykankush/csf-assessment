package vttp.batch5.csf.assessment.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.csf.assessment.server.dto.LoginRequest;
import vttp.batch5.csf.assessment.server.models.Item;
import vttp.batch5.csf.assessment.server.models.PaymentReceipt;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private OrdersRepository ordersRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;


  // TODO: Task 2.2
  // You may change the method's signature
  public List<Item> getMenu() {
    return ordersRepository.getMenu();

  }
  
  // TODO: Task 4
  public boolean checkLogin(String username, String password){
    return restaurantRepository.checkLogin(username, password);
  }

  public void insertIntoOrder(PaymentReceipt receipt, LoginRequest loginRequest, double total){
    restaurantRepository.insertIntoOrder(receipt, loginRequest, total);
    
  }

  public void insertIntoMongo(LoginRequest loginRequest, PaymentReceipt paymentReceipt, double total){
    ordersRepository.insertIntoMongo(loginRequest, paymentReceipt, total);
  }


}

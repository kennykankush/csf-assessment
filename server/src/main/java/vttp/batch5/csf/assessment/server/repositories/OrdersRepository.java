package vttp.batch5.csf.assessment.server.repositories;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.dto.LoginRequest;
import vttp.batch5.csf.assessment.server.models.Item;
import vttp.batch5.csf.assessment.server.models.ItemOrder;
import vttp.batch5.csf.assessment.server.models.PaymentReceipt;


@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  Native MongoDB query here
  // db.menu.find().sort({ name: 1 })
  public List<Item> getMenu() {
    Query query = new Query();
    query.with(Sort.by(Sort.Direction.ASC, "name"));

    List<Document> documents = mongoTemplate.find(query, Document.class, "menu");
    List<Item> items = new ArrayList<>();

    for(Document document: documents){
      Item item = new Item();
      item.setId(document.getString("_id"));
      item.setName(document.getString("name"));
      item.setPrice((Double) document.get("price"));
      item.setDescription(document.getString("description"));
      items.add(item);
    }
    System.out.println("FROM REPO: GETTING MENU: " + items);

    return items;
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  //  Native MongoDB query here
  public void insertIntoMongo(LoginRequest loginRequest, PaymentReceipt paymentReceipt, double total){
    Document document = new Document();
    document.put("_id", paymentReceipt.getOrder_id());
    document.put("order_id", paymentReceipt.getOrder_id());
    document.put("payment_id", paymentReceipt.getPayment_id());
    document.put("username", loginRequest.getUsername());
    document.put("total", total);
    Date date = new Date(paymentReceipt.getTimestamp());
    document.put("timestamp", date);
    List<Document> documents = new ArrayList<>();
    
    for(ItemOrder item: loginRequest.getItems()){
      Document itemDoc = new Document();
      itemDoc.put("id", item.getId());
      itemDoc.put("price", item.getPrice());
      itemDoc.put("quantity", item.getQuantity());
      documents.add(itemDoc);

    }
    document.put("items", documents);

    mongoTemplate.insert(document, "orders");
  }
  
}

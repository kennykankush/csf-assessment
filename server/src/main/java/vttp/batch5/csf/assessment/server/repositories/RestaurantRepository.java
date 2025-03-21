package vttp.batch5.csf.assessment.server.repositories;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.dto.LoginRequest;
import vttp.batch5.csf.assessment.server.models.PaymentReceipt;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkLogin(String username, String password){
        int count = jdbcTemplate.queryForObject(SqlQuery.LOGIN_CHECK, Integer.class, username, password);

        if (count == 1){
            return true;
        }

        return false;
    }

    public void insertIntoOrder(PaymentReceipt receipt, LoginRequest loginRequest, double total){
        Date date = new Date(receipt.getTimestamp());
        jdbcTemplate.update(SqlQuery.ORDER_UPLOAD, receipt.getOrder_id(), receipt.getPayment_id(), date, total, loginRequest.getUsername());
    }
    

}

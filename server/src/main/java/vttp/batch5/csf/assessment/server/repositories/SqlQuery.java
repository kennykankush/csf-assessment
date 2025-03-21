package vttp.batch5.csf.assessment.server.repositories;

public class SqlQuery {

    public static final String LOGIN_CHECK = """
            SELECT COUNT(*) FROM customers WHERE username= ? AND password= ?
            """;
    
    public static final String ORDER_UPLOAD = """
            INSERT INTO place_orders (order_id, payment_id, order_date, total, username)
            VALUES (?,?,?,?,?)
            """;
}

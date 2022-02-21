import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable{

    private String orderDescription;
    private String orderId;
    private String deleveryAddress;
    private String orderDate;
    private Double Amount;
    private String deleveryDateAndTime;

    public Order(String orderId, String orderDescription, String deleveryAddress, String orderDate, Double amount,String deleveryDateAndTime) {
        this.orderDescription = orderDescription;
        this.orderId = orderId;
        this.deleveryAddress = deleveryAddress;
        this.orderDate = orderDate;
        this.Amount = amount;
        this.deleveryDateAndTime = deleveryDateAndTime;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeleveryAddress() {
        return deleveryAddress;
    }

    public void setDeleveryAddress(String deleveryAddress) {
        this.deleveryAddress = deleveryAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getDeleveryDateAndTime() {
        return deleveryDateAndTime;
    }

    public void setDeleveryDateAndTime(String deleveryDateAndTime) {
        this.deleveryDateAndTime = deleveryDateAndTime;
    }

}

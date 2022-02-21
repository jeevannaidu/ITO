import java.util.List;

public interface OrderManagement {

    public void addOrder(List<Order> list);

    public List<Order> viewOrder();

    public Order viewOrder(List<Order> list, String orderId);

    public void sortOrder(List<Order> list);

    public List<Order> deleteOrderById(String orderId);

    public void markAsDelivered(List<Order> list);

    public void generateReport();

}

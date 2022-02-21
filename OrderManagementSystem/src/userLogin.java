import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class userLogin {
    public static void main(String[] args){

                Scanner sc = new Scanner(System.in);
                List<Order> list = new ArrayList<>();
                OrderManagementImplementation impl = new OrderManagementImplementation();
                File file = impl.getFile();
                if(file.length()!=0)
                    list = impl.addToList();
                if(list.size()!=0){
                    impl.viewOrder();
                }
                while (true) {

                    System.out.println("====================================================================================================================================================");
                    System.out.println("*****Order Management System*****");
                    System.out.println("\t1.Add Order\n \t2.View Order List\n \t3.View Order By Id\n \t4.Sort Order\n\t" +
                            "5.Delete Order By Id\n\t6.Mark as Delivered\n\t7.Generate Report\n\t8.Exit");
                    System.out.println("====================================================================================================================================================");

                    int choice;

                    try {
                        System.out.print("Enter Option:");
                        choice = sc.nextInt();
                    }catch (InputMismatchException e){
                        System.err.println("Restart and Enter Valid option Between 1 to 8");
                        break;
                    }

                    switch (choice) {
                        case 1: //Add Order

                            while(true) {
                                String s;
                                do{
                                    try{

                                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                                        System.out.println("Enter Order Details:");
                                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

                                        System.out.print("Order Id:");
                                        String orderId = sc.next();
                                        System.out.print("Order Description:");
                                        String orderDescription = sc.next();
                                        System.out.print("Delivery Address:");
                                        String deliveryAddress = sc.next();
                                        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
                                        LocalDateTime now = LocalDateTime.now();
                                        String date = FORMATTER.format(now);
                                        System.out.println("Order Date:" + date);
                                        System.out.print("Amount:");
                                        double amount = sc.nextDouble();
                                        String deleveryDateAndTime = "---";
                                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

                                        if(list.size()!=0){
                                            boolean exist = false;
                                            Iterator<Order> itr = list.listIterator();

                                            do {
                                                Order order = itr.next();
                                                if (orderId.equals(order.getOrderId())) {
                                                    exist = true;
                                                    break;
                                                }
                                            } while (itr.hasNext());

                                            if(exist){
                                                System.out.println("!!!Duplicate Order Id. Please enter unique order id!!!");
                                                s = "y";
                                            }

                                            else{
                                                list.add( new Order(orderId, orderDescription, deliveryAddress, date, amount, deleveryDateAndTime ));
                                                impl.addOrder(list);
                                                System.out.println("Order Added Successfully");

                                                do{
                                                    System.out.println("Do you want to mark another Order as Delivered(Y/N):");
                                                    s = sc.next();
                                                    s = s.toUpperCase();
                                                    if(s.length()>1)
                                                        System.out.println("Enter Valid Option");
                                                    else {
                                                        if (s.charAt(0) == 'N')
                                                            break;
                                                        else if (s.charAt(0) == 'Y') {
                                                            break;
                                                        }
                                                        else{
                                                            System.out.println("Enter Valid Option");
                                                        }
                                                    }
                                                }while (true);
                                            }

                                        }else{
                                            list.add( new Order(orderId, orderDescription, deliveryAddress, date, amount, deleveryDateAndTime));
                                            impl.addOrder(list);
                                            System.out.println("Order Added Successfully");
                                            do{
                                                System.out.println("Do you want to mark another Order as Delivered(Y/N):");
                                                s = sc.next();
                                                s = s.toUpperCase();
                                                if(s.length()>1)
                                                    System.out.println("Enter Valid Option");
                                                else {
                                                    if (s.charAt(0) == 'N')
                                                        break;
                                                    else if (s.charAt(0) == 'Y') {
                                                        break;
                                                    }
                                                    else{
                                                        System.out.println("Enter Valid Option");
                                                    }
                                                }
                                            }while (true);

                                        }
                                        break;
                                    }catch (InputMismatchException e){
                                        System.out.println("Enter valid Input");
                                        sc.nextLine();
                                    }
                                }while(true);

                                if (s.charAt(0) == 'N')
                                    break;

                            }
                            break;

                        case 2: //View Order List
                            if(list.size()!=0) {
                                list = impl.viewOrder();
                            }else{
                                System.out.println("Order list is empty________!");
                            }
                            break;

                        case 3: // View By OrderId
                            if(list.size()!=0){
                                System.out.print("Enter Order Id:");
                                String odrId = sc.next();
                                impl.viewOrder(list,odrId);
                            }else{
                                System.out.println("Order list is empty________!");
                            }
                            break;

                        case 4: // Sort Orders
                            if(list.size()!=0){
                                impl.sortOrder(list);
                            }else{
                                System.out.println("Order list is empty________!");
                            }

                            break;

                        case 5: // Delete Order By Id
                            if(list.size()!=0) {
                                System.out.println("Enter Order Id");
                                String ord = sc.next();
                                impl.deleteOrderById(ord);
                            }else{
                                System.out.println("Order list is empty____!");
                            }

                            break;

                        case 6: // Mark as Delivered
                            impl.markAsDelivered(list);
                            break;

                        case 7: // Generate Report
                            if(list.size()!=0)
                                impl.generateReport();
                            else
                                System.out.println("Order list is empty____!");
                            break;

                        case 8: // Exit
                            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("......Thank You......");
                            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.exit(0);
                            break;

                        default: //Invalid Option
                            System.out.println("Enter Valid Option");

                    }
                }
    }
}

//NotDeleveredList :
                       /*  List<Order> list1 = new ArrayList<>();

                            list1=  impl.notDelevered();
                            System.out.println("Size of the list : " +list1.size());
                            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("Order Id \t \t | Order Desc \t \t | Address \t \t \t | Order Date \t \t \t \t \t \t \t | Amount  \t \t \t | Delivery Datetime");
                            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                            for(Order ordList : list1) {
                                System.out.println(ordList.getOrderId().concat("     ").substring(0, 5) + " \t \t \t | " +
                                        ordList.getOrderDescription().concat("     ").substring(0, 5) + " \t \t \t | " +
                                        ordList.getDeleveryAddress().concat("     ").substring(0, 5) + " \t \t \t | " + ordList.getOrderDate() + " \t \t | "
                                        + ordList.getAmount() + " \t \t \t | " +
                                        ordList.getDeleveryDateAndTime());
                            }*/
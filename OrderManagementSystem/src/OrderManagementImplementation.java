import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class OrderManagementImplementation implements OrderManagement {


    private static final String path = "C:\\Users\\Sai Jeevan Naidu\\New folder\\Order.txt";

    @Override
    public void addOrder(List<Order> list) {
        try{
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Order o:list) {
                oos.writeObject(o);
            }
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
    LocalDateTime now = LocalDateTime.now();
    String date = FORMATTER.format(now);

    private static final String path1 = "C:\\Users\\Sai Jeevan Naidu\\New folder\\Order report " ;

    Scanner sc = new Scanner(System.in);

    @Override
    public List<Order> viewOrder(){
        List<Order> list = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Order order;
            while(true){
                try{
                    order = (Order) ois.readObject();
                    list.add(order);
                }catch (EOFException e){
                    break;
                }
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Order Id \t \t | Order Desc \t \t | Address \t \t \t | Order Date \t \t \t \t \t \t \t | Amount  \t \t \t | Delivery Datetime");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Order ordList : list) {
                System.out.println(ordList.getOrderId().concat("     ").substring(0, 5) + " \t \t \t | " +
                        ordList.getOrderDescription().concat("     ").substring(0, 5) + " \t \t \t | " +
                        ordList.getDeleveryAddress().concat("     ").substring(0, 5) + " \t \t \t | " + ordList.getOrderDate() + " \t \t | "
                        + ordList.getAmount() + " \t \t \t | " +
                        ordList.getDeleveryDateAndTime());
            }
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Order viewOrder(List<Order> list, String orderId) {
        Order o = null;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        if(o==null) {
            System.out.println("-----------------------------Order Not Exist----------------------------");
        }else {
            System.out.println("Order Details");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("OderID\t\t\t : " + o.getOrderId());
            System.out.println("Oder Desc\t\t : " + o.getOrderDescription());
            System.out.println("Delivery Address : " + o.getDeleveryAddress());
            System.out.println("Order Date\t\t : " + o.getOrderDate());
            System.out.println("Amount\t\t\t : " + o.getAmount());
            System.out.println("Delivery Date\t : " + o.getDeleveryDateAndTime());
        }
        return o;
    }

    @Override
    public void sortOrder(List<Order> list) {

        while(true) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("*****Choose Sort Order Property*****");
            System.out.println("\t1.OrderId\n \t2.Order Description\n \t3.Delivery Address\n \t4.Order Date\n\t" +
                    "5.Amount\n\t6.Delivery Time\n\t7.Exit");
            int opt = 0;
            try {
                System.out.print("Enter Option:");
                opt = new Scanner(System.in).nextInt();
            } catch (InputMismatchException em) {
                System.err.println("Input Mismatch!!!!!");
            }

            if (opt == 1) {
                list.sort((o1, o2) -> (o2.getOrderId().toLowerCase().compareTo(o1.getOrderId().toLowerCase())) * -1);
                sortedList("Order Id", list);
            } else if (opt == 2) {
                list.sort((o1, o2) -> (o2.getOrderDescription().toLowerCase().compareTo(o1.getOrderDescription().toLowerCase())) * -1);
                sortedList("Order Description", list);
            } else if (opt == 3) {
                list.sort((o1, o2) -> (o2.getDeleveryAddress().toLowerCase().compareTo(o1.getDeleveryAddress().toLowerCase())) * -1);
                sortedList("Delivery Address", list);
            } else if (opt == 4) {
                list.sort((o1, o2) -> ((o2.getOrderDate().compareTo(o1.getOrderDate()))) * -1);
                sortedList("Order Date", list);
            } else if (opt == 5) {
                list.sort(Comparator.comparingDouble(Order::getAmount));
                sortedList("Amount",list);
            }else if(opt==6) {
                try {
                    list.sort((o1, o2) -> {
                        if (o1.getDeleveryDateAndTime() != null && o2.getDeleveryDateAndTime()!= null) {
                            return (o2.getDeleveryDateAndTime().compareTo(o1.getDeleveryDateAndTime())*-1);
                        } else {
                            return 0;
                        }
                    });
                    sortedList("Delivery Datetime",list);
                } catch (NullPointerException e) {
                    System.out.println("Please Complete All deliveries____");
                }
            }else if(opt==7)
                break;
            else {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Choose Option between 1 to 7");
            }
        }

    }

    @Override
    public List<Order> deleteOrderById(String orderId) {
        List<Order> list;
        list = addToList();
        Order o;
        int count = 0;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
                list.remove(o);
                count++;
                break;
            }
        }
        try {
            new FileWriter(path, false).close();
//            File file = new File(path);
//            System.out.println(file.length());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Emptying Exception");
        }
        addOrder(list);
        if(count!=0){
            System.out.println("Order Deleted Successfully");
        }else{
            System.out.println("Order with OrderId "+orderId +" does not exist...!");
        }
        return list;
    }

    @Override
    public void markAsDelivered(List<Order> list) {
        String s;

        if(list.size()!=0){
            do{
                System.out.print("Enter Order Id:");
                String odrId = sc.next();
                Order order123 =getOrderById(odrId);

                if(order123 != null){
                    if(!order123.getDeleveryDateAndTime().equals("---")){
                        System.out.println("Order is already delivered on :"+order123.getDeleveryDateAndTime());
                    }else{
                        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
                        LocalDateTime now = LocalDateTime.now();
                        String date = FORMATTER.format(now);
                        order123.setDeleveryDateAndTime(date);
                        list = deleteOrderById(odrId);
                        // deleveredList.add(order123);
                        list.add(order123);
                        addOrder(list);
                        System.out.println("Successfully Delivered");
                    }
                }else{
                    System.out.println("Order with id "+odrId+" does not exist...!");
                }
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
                if (s.charAt(0) == 'N')
                    break;
//                else if (s.charAt(0) == 'Y')
//                    continue;
            }while(true);
        }else {
            System.out.println("Order list is empty________!");
        }
    }

    @Override
    public void generateReport() {
        List<Order> list = addToList();
        List<Order> temp = new ArrayList<>();
        List<Integer> index = new ArrayList<>();

        File file = new File(path1);
        try {
            FileWriter fwrite = new FileWriter(path1);

            if(file.exists()){
                file.delete();
                fwrite.write("----------------------------------------------------\n");
            }
            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            String date = FORMATTER.format(now);
            fwrite.write("================================================================\n");
            fwrite.write(file.getName()+" :: Order_Report "+date+"\n");
            fwrite.write("================================================================\n");
            Iterator<Order> itr = list.listIterator();

            while (itr.hasNext()) {
                Order order = itr.next();
                System.out.println(order.hashCode());
                if (order.getDeleveryDateAndTime() != null && !(temp.contains(order))) {
                    temp.add(order);
//                              System.out.println(order);
                    fwrite.write("Order Id\t  : " + order.getOrderId()+"\n");
                    fwrite.write("Order Description : " + order.getOrderDescription()+"\n");
                    fwrite.write("Delivery Address  : " + order.getDeleveryAddress()+"\n");
                    fwrite.write("Order Date\t  : " + order.getOrderDate()+"\n");
                    fwrite.write("Amount\t \t  : " + order.getAmount()+"\n");
                    fwrite.write("Delivery Datetime : " + order.getDeleveryDateAndTime()+"\n");
                    fwrite.write("-----------------------------------------------------\n");
                }
            }
            System.out.println("Order report file Updated");
            index= new ArrayList<>();
            fwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Order report error");
        }


    }

    public List<Order> addToList(){
        List<Order> list = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Order order;
            while(true){
                try{
                    order = (Order) ois.readObject();
                    list.add(order);
                }catch (EOFException e){
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void sortedList(String s, List<Order> list){
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Successfully Sorted by "+s+":");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Order Id \t \t | Order Desc \t \t | Address \t \t \t | Order Date \t \t \t \t \t \t | Amount  \t \t \t | Delivery Datetime");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

        for(Order ordList : list)
        {
            System.out.println(ordList.getOrderId().concat("     ").substring(0, 5) + " \t \t \t | " +
                    ordList.getOrderDescription().concat("     ").substring(0, 5) + " \t \t \t | " +
                    ordList.getDeleveryAddress().concat("     ").substring(0, 5) + " \t \t \t | " + ordList.getOrderDate() + " \t | "
                    + ordList.getAmount() + " \t \t \t | " +
                    ordList.getDeleveryDateAndTime());
        }
    }

    public File getFile(){
        return new File(path);
    }

    public Order getOrderById(String orderId){
        List<Order> list = addToList();
        Order o = null;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
            }
        }
        return o;
    }

  /*  public List<Order> notDelevered(){
        System.out.println("not Delevered List");
        List<Order> notDeliveredList = new ArrayList<>();
        List<Order> list = new ArrayList<>();
        FileInputStream fis = null;
        Order order;
        try {
            System.out.println("not Delevered List");
            fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
           while(true){
              try{ order = (Order)ois.readObject();
               if(!order.getDeleveryDateAndTime().equals("---")) {
                   notDeliveredList.add(order);
                   System.out.println("adding");
               }}catch (EOFException e){
                  break;
              }
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return notDeliveredList;
    }*/


}



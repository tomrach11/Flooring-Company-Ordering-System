package com.tr.flooring.ui;

import com.tr.flooring.dto.Order;
import com.tr.flooring.dto.Product;
import com.tr.flooring.dto.TaxRate;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

public class FlooringView {

    UserIO io;

    public FlooringView (UserIO io) {
        this.io = io;
    }

    public String getMenuChoice(){
        io.println("\n<<Flooring Program>>");
        io.println("1. Display Orders");
        io.println("2. Add an Order");
        io.println("3. Edit an Order");
        io.println("4. Remove an Order");
        io.println("5. Save Current Work");
        io.println("6. Quit");
        return io.readString("Please enter option above [1-6]: ");
    }

    public void displayOrderByDate(List<Order> orderList) {
        Consumer<Order> printOrder = (Order order) -> {
            io.print("- Order ID: " + order.getOrderID());
            io.print("|State: " + order.getState());
            io.print("|Customer Name: " + order.getCustomerName());
            io.print("|Product Type: " + order.getProductType());
            io.print("|Area: " + order.getProductArea());
            io.print("|Material Cost: " + order.getTotalMaterialCost());
            io.print("|Labor Cost: " + order.getTotalLaborCost());
            io.print("|Tax: " + order.getTotalTax());
            io.println("|Total Cost: " + order.getTotalCost());
        };
        if (orderList == null) {
            io.println("No Order Found");
        } else {
            io.println("\nList:");
            orderList.forEach(printOrder);
        }
    }

    public LocalDate getOrderDate(){
        LocalDate orderDate = io.readLocalDate("Please enter Order Date (MM/DD/YYYY): ");
        return orderDate;
    }

    public Order getOrderInfo(List<Product> productList, List<TaxRate> taxRateList) {

        Consumer<TaxRate> printState = (TaxRate taxRate) -> {
            io.print(taxRate.getState() + " ");
        };

        Consumer<Product> printProductType = (Product product) -> {
            io.print(product.getProductType() + " ");
        };

        String customerName = io.readString("Please enter Name: ");

        io.print("\nAvailable States: ");
        taxRateList.forEach(printState);
        io.println("");
        String stateName = io.readString("Please enter State above: ");

        io.print("\nAvailable Products Type: ");
        productList.forEach(printProductType);
        io.println("");
        String productType = io.readString("Please enter Product Type above: ");

        double productArea = io.readDouble("Please enter Area: ");

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setState(stateName.toUpperCase());
        order.setProductType(productType.toLowerCase());
        order.setProductArea(productArea);

        return order;
    }

    public boolean getConfirm(Order order, String message){

        io.println("\nOrder Confirmation: ");
        io.println("\tCustomer Name: " + order.getCustomerName());
        io.println("\tState: " + order.getState());
        io.println("\tProduct Type: " + order.getProductType());
        io.println("\tArea: " + order.getProductArea());
        io.println("\tMaterial Cost: " + order.getTotalMaterialCost());
        io.println("\tLabor Cost: " + order.getTotalLaborCost());
        io.println("\tTax: " + order.getTotalTax());
        io.println("\tTotal Cost: " + order.getTotalCost());

        String userInput = io.readString(message + " [Y/N]: ");
        if (userInput.equalsIgnoreCase("Y")) {
            return true;
        }
        else {
            io.println("\n--Cancelled.");
            return false;
        }

    }

    public boolean getConfirm(String message){

        String userInput = io.readString(message + " [Y/N]: ");
        if (userInput.equalsIgnoreCase("Y")) {
            return true;
        }
        else {
            io.println("\n--Cancelled.");
            return false;
        }

    }

    public int getOrderId(){
        return io.readInt("Please Enter Order ID: ");
    }

    public Order getEditInfo(Order order, List<Product> productList, List<TaxRate> taxRateList){
        //show order info
        io.println("\nCurrent Order Info.: ");
        io.println("\tCustomer Name: " + order.getCustomerName());
        io.println("\tState: " + order.getState());
        io.println("\tProduct Type: " + order.getProductType());
        io.println("\tArea: " + order.getProductArea());
        io.println("\tMaterial Cost: " + order.getTotalMaterialCost());
        io.println("\tLabor Cost: " + order.getTotalLaborCost());
        io.println("\tTax: " + order.getTotalTax());
        io.println("\tTotal Cost: " + order.getTotalCost() + "\n");
        //consumer for show list of tax and product
        Consumer<TaxRate> printState = (TaxRate taxRate) -> {
            io.print(taxRate.getState() + " ");
        };

        Consumer<Product> printProductType = (Product product) -> {
            io.print(product.getProductType() + " ");
        };
        //get info
        String customerName = io.readString("Please enter Name [" + order.getCustomerName() + "]: ");

        io.print("\nAvailable States: "); // print list of state
        taxRateList.forEach(printState);
        io.println("");
        String stateName = io.readString("Please enter State above [" + order.getState() + "]: ");

        io.print("\nAvailable Products Type: "); // print list of product
        productList.forEach(printProductType);
        io.println("");
        String productType = io.readString("Please enter Product Type above [" + order.getProductType() + "]: ");

        double productArea = io.readDouble("Please enter Area [" + order.getProductArea() + "]: ");

        if (customerName.length() > 0) {
            order.setCustomerName(customerName);
        }
        if (stateName.length() > 0) {
            order.setState(stateName.toUpperCase());
        }
        if (productType.length() > 0) {
            order.setProductType(productType.toLowerCase());
        }
        if (productArea != 0) {
            order.setProductArea(productArea);
        }

        return order;
    }

    public void invalidUserInput(String userInput) {
        io.println("\nError Invalid Input: '" + userInput + "' is not valid.");
    }

    public void displayErrorMessage(String message) {
        io.println(message);
    }

//Banner
    public void displayOrderBanner() {
        io.println("\nDisplay Orders Menu: ");
    }
    public void addOrderBanner() {
        io.println("\nAdd Order Menu: ");
    }
    public void addSuccessBanner() {
        io.println("\nOrder Added.");
    }
    public void editOrderBanner() {
        io.println("Edit Order Menu: ");
    }
    public void editSuccessBanner() {
        io.println("Order edited.");
    }
    public void removeOrderBanner() {
        io.println("\nRemove Order Menu: ");
    }
    public void removeSuccessBanner() {
        io.println("Order removed.");
    }
    public void saveBanner() {
        io.println("\nSave Date Menu: ");
    }
    public void saveSuccessBanner() {
        io.println("Data saved.");
    }
    public void exitBanner() {
        io.println("\nProgram is shutting down ...");
    }
    public void pressEnterTOContinue() {
        io.readString("\nPress enter to continue.");
    }

}

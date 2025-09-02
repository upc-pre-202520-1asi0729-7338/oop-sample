package com.acme.oop;

import com.acme.oop.crm.domain.model.aggregates.Customer;
import com.acme.oop.sales.domain.model.aggregates.SalesOrder;
import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.Address;
import com.acme.oop.shared.domain.model.valueobjects.Money;

import java.math.BigDecimal;
import java.util.Currency;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        // Shared context
        Address address = new Address("Street", "123", "City", "12345", "USA");
        System.out.println("First Address: " + address);
        Address anotherAddress = new Address("Street", "456", "Anytown", "12345", "USA");
        System.out.println("Second Address: " + anotherAddress);
        // CRM context
        System.out.println("Creating a customer...");
        Customer customer = new Customer("John Doe", "john.doe@gmail.com", address);
        System.out.println("Customer contact info: " + customer.getContactInfo());
        System.out.println("Updating customer contact info...");
        customer.updateContactInfo(customer.getEmail(), anotherAddress);
        System.out.println("Customer contact info: " + customer.getContactInfo());

        // Sales context
        System.out.println("Creating a sales order...");
        SalesOrder order = new SalesOrder(customer.getId());
        Money price = new Money(new BigDecimal("29.99"), Currency.getInstance("USD"));
        ProductId productId = new ProductId();
        order.addItem(productId, 2, price);
        System.out.println("Sales order total: " + order.getOrderTotalAmountAsString());
    }
}
package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.CustomerId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * SalesOrder aggregate root representing a sales order in the Sales bounded context.
 * It contains order details, customer information, and a list of order items.
 *
 * @author Open-Source Applications Development Team
 * @version 1.0
 */
public class SalesOrder {
    @Getter
    private final UUID id;
    @Getter
    private final CustomerId customerId;
    @Getter
    private LocalDateTime orderDate;
    private final List<SalesOrderItem> items;
    @Getter
    private Money totalAmount;

    /**
     * Constructor to create a new SalesOrder.
     * @param customerId the {@link CustomerId} ID of the customer placing the order
     */
    public SalesOrder(@NonNull CustomerId customerId) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.totalAmount = Money.zero();
    }

    /**
     * Adds an item to the sales order.
     * @param productId the {@link ProductId} ID of the product being ordered
     * @param quantity the quantity of the product being ordered
     * @param unitPrice the {@link Money} unit price of the product being ordered
     */
    public void addItem(@NonNull ProductId productId, int quantity, @NonNull Money unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        if (unitPrice.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Unit price must be greater than zero");
        SalesOrderItem item = new SalesOrderItem(productId, quantity, unitPrice);
        items.add(item);
        totalAmount = calculateOrderTotalAmount();
    }

    /**
     * Calculates the total amount of the sales order by summing up the amounts of all order items.
     * @return the total {@link Money} amount of the sales order
     */
    public Money calculateOrderTotalAmount() {
        return this.items.stream().map(SalesOrderItem::calculateItemAmount)
                .reduce(Money.zero(), Money::add);
    }

    /**
     * Sets the order date of the sales order.
     * @param orderDate the {@link LocalDateTime} order date to set
     * @return the updated {@link SalesOrder} instance
     */
    public SalesOrder withOrderDate(@NonNull LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    /**
     * Gets the total amount of the sales order as a string.
     * @return the total amount of the sales order in string format
     */
    public String getOrderTotalAmountAsString() {
        return this.totalAmount + " " + this.totalAmount.currency().getCurrencyCode();
    }

}

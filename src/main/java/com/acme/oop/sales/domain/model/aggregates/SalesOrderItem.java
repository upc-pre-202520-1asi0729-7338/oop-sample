package com.acme.oop.sales.domain.model.aggregates;

import com.acme.oop.sales.domain.model.valueobjects.ProductId;
import com.acme.oop.shared.domain.model.valueobjects.Money;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * SalesOrderItem represents an item in a sales order, including product details, quantity, and pricing.
 * It provides functionality to calculate the total amount for the item based on quantity and unit price.
 * @author Open-Source Applications Development Team
 * @version 1.0
 */
@Getter
public class SalesOrderItem {
    private final ProductId productId;
    @Setter private int quantity;
    @Setter private Money unitPrice;

    /**
     * Constructor for SalesOrderItem.
     * @param productId The {@link ProductId} ID of the product.
     * @param quantity The quantity of the product ordered.
     * @param unitPrice The unit price of the product as a {@link Money} value object.
     * @throws IllegalArgumentException if quantity is less than or equal to zero, or if unitPrice is less than or equal to zero or has an invalid currency.
     */
    public SalesOrderItem(@NonNull ProductId productId, int quantity, @NonNull Money unitPrice) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");
        if (unitPrice.amount().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Unit price must be greater than zero");
        if (Objects.isNull(unitPrice.currency()) || Objects.isNull(unitPrice.currency().getCurrencyCode())
                || unitPrice.currency().getCurrencyCode().isBlank())
            throw new IllegalArgumentException("Unit price must have a valid currency");
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Calculates the total amount for this sales order item based on quantity and unit price.
     * @return The total amount as a {@link Money} value object.
     */
    public Money calculateItemAmount() {
        return unitPrice.multiply(quantity);
    }
}

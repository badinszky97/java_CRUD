package com.oanda.v20.order;

import com.google.gson.annotations.SerializedName;
import com.oanda.v20.transaction.TransactionID;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderListResponse
 */
public class OrderListResponse {

    /**
     * OrderListResponse Constructor
     * <p>
     * Construct a new OrderListResponse
     */
    private OrderListResponse() {
    }

    @SerializedName("orders") private ArrayList<Order> orders;

    /**
     * Get the orders
     * <p>
     * The list of Order detail objects
     * <p>
     * @return the orders
     * @see Order
     */
    public List<Order> getOrders() {
        return this.orders;
    }

    @SerializedName("lastTransactionID") private TransactionID lastTransactionID;

    /**
     * Get the lastTransactionID
     * <p>
     * The ID of the most recent Transaction created for the Account
     * <p>
     * @return the lastTransactionID
     * @see TransactionID
     */
    public TransactionID getLastTransactionID() {
        return this.lastTransactionID;
    }
}

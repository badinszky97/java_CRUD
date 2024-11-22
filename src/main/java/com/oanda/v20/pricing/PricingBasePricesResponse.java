package com.oanda.v20.pricing;

import com.google.gson.annotations.SerializedName;
import com.oanda.v20.pricing_common.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * PricingBasePricesResponse
 */
public class PricingBasePricesResponse {

    /**
     * PricingBasePricesResponse Constructor
     * <p>
     * Construct a new PricingBasePricesResponse
     */
    private PricingBasePricesResponse() {
    }

    @SerializedName("prices") private ArrayList<Price> prices;

    /**
     * Get the prices
     * <p>
     * The list of prices that satisfy the request.
     * <p>
     * @return the prices
     * @see Price
     */
    public List<Price> getPrices() {
        return this.prices;
    }
}

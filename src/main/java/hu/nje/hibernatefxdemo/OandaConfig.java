package hu.nje.hibernatefxdemo;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;

import java.lang.reflect.Field;
import java.util.*;

public class OandaConfig {

    public final String DIRECTION_BUY = "Vétel";
    public final String DIRECTION_SELL = "Eladás";
    public static final String OANDA_URL = "https://api-fxpractice.oanda.com";
    public static final String OANDA_TOKEN = "18b2ce5ab82d691bb718b784bd7f9e2e-f998c71868f62d26cef502d8935b3493";
    public static final AccountID OANDA_ACCOUNT_ID = new AccountID("101-004-30405209-001");
    private final Context context;
    private final AccountID accountID;

    public OandaConfig() {
        this.context = new ContextBuilder(OandaConfig.OANDA_URL)
                .setToken(OandaConfig.OANDA_TOKEN)
                .setApplication("Oanda")
                .build();
        this.accountID = OandaConfig.OANDA_ACCOUNT_ID;
    }

    public List<Item> accountInformation() {
        try {
            final List<Item> items = new ArrayList<>();
            final AccountSummary summary = this.context.account.summary(accountID).getAccount();

            Field[] declaredFields = summary.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                items.add(new Item(declaredField.getName(), declaredField.get(summary)));
            }

            System.out.println("summary = " + summary);
            return items;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Item> getActualPrice(final Collection<String> instruments)    {
        try {
            final List<Item> items = new ArrayList<>();
            PricingGetRequest request = new PricingGetRequest(accountID, instruments);
            PricingGetResponse resp = context.pricing.get(request);

            ClientPrice price = resp.getPrices().get(0);
            Field[] declaredFields = price.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                items.add(new Item(declaredField.getName(), declaredField.get(price)));
            }
            return items;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getHistoricalPrice(final String instrument, String dateFrom, String dateTo)  {
        try {
            final List<String> items = new ArrayList<>();
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(instrument));
            request.setFrom(dateFrom);
            request.setTo(dateTo);
            request.setCount(30L); // 10 adat L: long adattípus
            InstrumentCandlesResponse resp = context.instrument.candles(request);
            for(Candlestick candle: resp.getCandles())
                items.add(candle.toString());
            return items;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setOpenPosition(final String instrument, final double units) {
        try {
            final var marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(new InstrumentName(instrument));
            marketorderrequest.setUnits(units);

            final var request = new OrderCreateRequest(this.accountID);
            request.setOrder(marketorderrequest);
            final OrderCreateResponse response = this.context.order.create(request);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean setClosePosition(final String PositionId ) {
        try {
            TradeSpecifier tradeSpecifier =  new TradeSpecifier(PositionId);
            this.context.trade.close(new TradeCloseRequest(this.accountID, tradeSpecifier));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Trade> getOpenPositions()    {
        try {
            final List<Trade> trades = this.context.trade.listOpen(this.accountID).getTrades();
            return trades;
        } catch (Exception e)   {
            throw new RuntimeException(e);
        }
    }

}
package hu.nje.hibernatefxdemo;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import java.util.List;
import java.util.Properties;

public class OandaConfig {
    public static final String OANDA_URL = "https://api-fxpractice.oanda.com";
    public static final String OANDA_TOKEN = "18b2ce5ab82d691bb718b784bd7f9e2e-f998c71868f62d26cef502d8935b3493";
    public static final String OANDA_ACCOUNT_ID = "101-004-30405209-001";
    public final List<String> defaultCurrencyCodes = List.of("EUR", "USD", "JPY", "GBP", "CHF", "HUF");

    private final Context context;
    private final AccountID accountID;

    public OandaService() {

        this.context = new ContextBuilder(OANDA_URL)
                .setToken(OANDA_TOKEN)
                .setApplication("Java Alkalmazások - Elmélet Beadandó")
                .build();

        this.accountID = new AccountID(OANDA_ACCOUNT_ID);
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
    
}

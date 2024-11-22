package com.oanda.v20;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;

public class Summary {
    public static void main(String[] args) {
        Context ctx = new Context("https://api-fxpractice.oanda.com","18b2ce5ab82d691bb718b784bd7f9e2e-f998c71868f62d26cef502d8935b3493");
        try {
            AccountSummary summary = ctx.account.summary(new
                    AccountID("101-004-30405209-001")).getAccount();
            System.out.println(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

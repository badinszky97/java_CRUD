module hu.nje.hibernatefxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.activation;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires com.sun.xml.ws;
    requires java.persistence;
    requires httpcore;
    requires httpclient;
    requires com.google.gson;
    requires java.desktop;

//    opens com.oanda.v20.account to gson;
    opens hu.nje.hibernatefxdemo to javafx.fxml, org.hibernate.orm.core;
    opens com.oanda.v20.account to com.google.gson;
    opens com.oanda.v20.pricing to com.google.gson;
    opens com.oanda.v20.pricing_common to com.google.gson;
    opens com.oanda.v20.order to com.google.gson;
    opens com.oanda.v20.trade to com.google.gson;
    opens com.oanda.v20.transaction to com.google.gson;
    opens com.oanda.v20.instrument to com.google.gson;

    exports hu.nje.hibernatefxdemo;
    exports com.oanda.v20.primitives;
    exports com.oanda.v20.transaction;
    exports com.oanda.v20.pricing;
    exports com.oanda.v20.pricing_common;
    exports com.oanda.v20.order;
    exports com.oanda.v20.trade;
}

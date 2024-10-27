module hu.nje.hibernatefxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.sql;


    opens hu.nje.hibernatefxdemo to javafx.fxml, org.hibernate.orm.core;
    exports hu.nje.hibernatefxdemo;
}

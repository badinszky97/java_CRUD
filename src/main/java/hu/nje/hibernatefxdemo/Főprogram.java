package hu.nje.hibernatefxdemo;

import java.lang.module.Configuration;
import java.sql.SQLException;

public class Főprogram {

    public static void main( String[] args ) throws SQLException {
//        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        JavaElmeletiBeadando.start(args);
    }
}

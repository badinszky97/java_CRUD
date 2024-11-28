package hu.nje.hibernatefxdemo;

public class oandaAccount {
    private String KeyColumn;
    private String ValueColumn;

    public oandaAccount(String keyColumn, String valueColumn) {
        KeyColumn = keyColumn;
        ValueColumn = valueColumn;
    }

    public String getKeyColumn() {
        return KeyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        KeyColumn = keyColumn;
    }

    public String getValueColumn() {
        return ValueColumn;
    }

    public void setValueColumn(String valueColumn) {
        ValueColumn = valueColumn;
    }

}
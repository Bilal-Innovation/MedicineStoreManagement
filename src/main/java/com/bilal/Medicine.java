package com.bilal;

public class Medicine {
    private int id;
    private String name;
    private String formula;
    private double price;
    private String type;
    private int quantity;

    public Medicine(String name, String formula, double price, String type, int quantity) {
        this.name = name;
        this.formula = formula;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormula() {
        return formula;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Medicine{id=" + id + ", name='" + name + "', formula='" + formula + "', price=" + price +
                ", type='" + type + "', quantity=" + quantity + "}";
    }
}
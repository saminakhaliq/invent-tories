package com.example.bilhaghedeon.finalprojectghedeonkhaliq.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "items_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long itemID;

    private String name;
    private String description;
    private int quantity;
    private double price;
    private String barcode;

    public Item(String barcode, String name, String description, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.barcode = barcode;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String toString(){
        String format = "Item: %s \n\n\nDescription: %s \n\n\nQuantity: %d ";
        String itemString = String.format(format, name, description, quantity);
        return itemString;

    }
    public Item(){}
}

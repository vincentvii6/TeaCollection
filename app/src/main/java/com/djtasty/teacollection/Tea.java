package com.djtasty.teacollection;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tea_collection_table" )
public class Tea {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String type;

    private int quantity;


    public Tea(String name, String type, int quantity) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}

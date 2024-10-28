package com.example;
import com.example.AppClasses.HashTable;

public class Runner {
    public static void main(String[] args) {
        HashTable table = new HashTable();
        table.insert(12);
        table.insert(15);
        table.insert(13);
        table.insert(1);
        table.insert(166);
        table.insert(989898);
        table.insert(989898);
        table.insert(554);
        table.insert(252353);
        table.insert(4575674);
        table.insert(467486742);
        table.insert(97587856);
        table.insert(31231234);
        table.insert(234567654);
        table.insert(42);
        table.insert(1001);
        table.insert(88888);
        table.insert(2048);
        table.insert(123456);
        System.out.println(table.toString());
        table.testHashFunction();
    }
}

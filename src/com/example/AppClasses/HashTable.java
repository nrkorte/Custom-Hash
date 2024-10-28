package com.example.AppClasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class HashTable {
    private static final int PRIME_NUMBER = 31;
    private ArrayList<LinkedList<Integer>> buckets;
    public int maxSize = 10;
    private int full_buckets;
    public HashTable () {
        buckets = new ArrayList<>();
        int i = 0;
        while (buckets.size() < maxSize) {
            buckets.add(null);
            i++;
        }
    }
    private int hash (int value) {
        int tmp = 0;
        try {
            String input = new String(Files.readAllBytes(Paths.get("/Users/nick/Desktop/code_projects/Custom-Hash/src/key.txt")));
            input = input.concat(Integer.toString(value));
            for (int i = 0; i < input.length(); i++) {
                tmp = (tmp * PRIME_NUMBER + ((int) input.charAt(i))) % this.maxSize;
            }
            return Math.abs(tmp) % this.maxSize;

        } catch (IOException e) {
            System.out.println("Could not find input file:\n\n" + e);
            return -1;
        }
    }
    public boolean insert (int value) {
        int index = this.hash(value);
        if (buckets.get(index) == null) {
            buckets.set(index, new LinkedList<>());
            full_buckets++;
            if ((double) full_buckets / (double) maxSize > 0.75) {
                this.resize();
            }
        }
        if (buckets.get(index) != null) {
            ListIterator<Integer> iterator = buckets.get(index).listIterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(value)) return false;
            }
            iterator.add(value);
        } else {
            buckets.set(index, new LinkedList<>());
            buckets.get(index).add(value);
        }
        return true;
    }
    public boolean remove (int value) {
        int index = this.hash(value);
        if (buckets.get(index) == null) return false;
        ListIterator<Integer> iterator = buckets.get(index).listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(value)) {
                iterator.remove();
                if (buckets.get(index).isEmpty()) {
                    buckets.set(index, null);
                    full_buckets--;
                }
                return true;
            }
        }
        return false;
    }

    public void resize () {
        this.maxSize = (int) (this.maxSize * 1.5);
        ArrayList<LinkedList<Integer>> new_bucks = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            new_bucks.add(null);
        }
        for (LinkedList<Integer> bucket : buckets) {
            if (bucket != null) {
                for (int value : bucket) {
                    int new_index = hash(value);
                    if (new_bucks.get(new_index) == null) {
                        new_bucks.set(new_index, new LinkedList<Integer>());
                        new_bucks.get(new_index).add(value);
                    } else {
                        ListIterator iterator = new_bucks.get(new_index).listIterator();
                        while (iterator.hasNext()) {
                            iterator.next();
                        }
                        iterator.add(value);
                    }
                }
            }
        }
        buckets = new_bucks;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashTable Contents:\n");

        for (int i = 0; i < maxSize; i++) {
            sb.append("Bucket " + i + ": ");
            if (buckets.get(i) != null) {
                ListIterator<Integer> iterator = buckets.get(i).listIterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next());
                    if (iterator.hasNext()) {
                        sb.append(" -> ");
                    }
                }
            } else {
                sb.append("null");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void testHashFunction() {
        int tableSize = this.maxSize;
        int PRIME_NUMBER = 31;

        for (int bucketIndex = 0; bucketIndex < buckets.size(); bucketIndex++) {
            LinkedList<Integer> bucket = buckets.get(bucketIndex);

            if (bucket != null) {
                for (int value : bucket) {
                    int expected = expectedHash(value, tableSize, PRIME_NUMBER);
                    int actual = hash(value);

                    if (actual == expected) {
                        System.out.println("Test passed for value " + value + " in bucket " + bucketIndex + ": expected bucket " + expected + ", got " + actual);
                    } else {
                        System.out.println("Test failed for value " + value + " in bucket " + bucketIndex + ": expected bucket " + expected + ", got " + actual);
                    }
                }
            }
        }
    }

    private int expectedHash(int value, int maxSize, int primeNumber) {
        int tmp = 0;
        try {
            String input = new String(Files.readAllBytes(Paths.get("/Users/nick/Desktop/code_projects/Custom-Hash/src/key.txt")));
            input = input.concat(Integer.toString(value));
            for (int i = 0; i < input.length(); i++) {
                tmp = (tmp * primeNumber + ((int) input.charAt(i))) % maxSize;
            }
            return Math.abs(tmp) % maxSize;
        } catch (IOException e) {
            System.out.println("Could not find input file:\n\n" + e);
            return -1;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

import com.yefrig.databasemanagementsystem.tree.BPlusTreeRow;

/**
 *
 * @author yefri
 */
public class Struct {

    private String name;
    private String key;
    private Struct next;
    private ListColumn columns;
    private BPlusTreeRow tree;

    public Struct(){
        this.next=null;
    }
    public void insertRow(ListColumn data) {
        this.tree.insert(0,data);
    }

    public String getName() {
        return name;
    }

    public ListColumn getColumns() {
        return columns;
    }

    public BPlusTreeRow getTree() {
        return tree;
    }

    public void setColumns(ListColumn columns) {
        this.columns = columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Struct getNext() {
        return next;
    }

    public void setNext(Struct next) {
        this.next = next;
    }
    

}

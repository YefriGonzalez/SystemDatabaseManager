/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

import com.yefrig.databasemanagementsystem.tree.BPlusTree;
import javax.swing.JFrame;

/**
 *
 * @author yefri
 */
public class Struct {

    private String name;
    private String key;
    private Struct next;
    private ListColumn columns;
    private int cont;
    private BPlusTree tree;

    public Struct() {
        this.cont=1;
        this.next = null;
        this.tree = new BPlusTree(4);
    }
 
    /**
     * Metodo que recibe el dato a guardar y su llave primaria para referencia
     * @param data
     * @param primaryKey
     */
    public void InsertRow(ListColumn data, String primaryKey) {
        this.tree.insert(data, cont);
        cont++;
    }

    public BPlusTree getTree() {
        return this.tree;
    }

    public String getName() {
        return name;
    }

    public ListColumn getColumns() {
        return columns;
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

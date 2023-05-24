/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

/**
 *
 * @author yefri
 */
public class NodeColumn {
    private String name;
    private String type;
    private NodeColumn next;
    public NodeColumn(String name, String type) {
        this.name = name;
        this.type = type;
        this.next=null;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public NodeColumn getNext() {
        return next;
    }

    public void setNext(NodeColumn next) {
        this.next = next;
    }
    
    @Override
    public String toString(){
        return "Nombre: "+name+"; Valor: "+type;
    }
}

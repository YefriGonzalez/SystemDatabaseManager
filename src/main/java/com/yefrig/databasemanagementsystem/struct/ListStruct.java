/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

/**
 *
 * @author yefri
 */
public class ListStruct {

    private Struct head;

    public ListStruct() {
        this.head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insertAtEnd(Struct struct) {
        Struct newStruct = struct;
        if (isEmpty()) {
            head = newStruct;
            return;
        }
        Struct current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newStruct);
    }

    public Struct getStruct(String name) {
        Struct current = head;
        while (current != null) {
            if (current.getName().equals(name)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    public String printList() {
        String text="";
        Struct current = head;
        while (current != null) {
            text+="Tabla: "+current.getName()+", Clave: "+current.getKey() + ", Columnas: \n"+current.getColumns().printList();
            text+="----------------------------------------------------------------------------------\n";
            current = current.getNext();
        }
        
        return text;
    }
}

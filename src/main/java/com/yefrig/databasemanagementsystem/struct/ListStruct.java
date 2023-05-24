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

    /**
     * Metodo que insertar Estructura a lista de estructuras
     * Realiza el recorrido hasta encontra una estructura que su siguiente sea nulo y asigna la estructura
     * @param struct
      */
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

    /**
     * FUncion que realiza la busqueda de una estructura por  su nombre
     * @param name
     * @return  Struct
      */
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

    /**
     * Funcion que retorna la lista enlazada de estructuras con su informacion
     * @return  String
      */
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

/**
 *
 * @author yefri
 */
public class ListColumn {

    private NodeColumn head;

    public ListColumn() {
        this.head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Funcion que inserta un nodo de tipo columna
     *
     * @param nodeColumn
     */
    public void insertAtEnd(NodeColumn nodeColumn) {
        NodeColumn newNode = nodeColumn;
        if (isEmpty()) {
            head = newNode;
            return;
        }
        NodeColumn current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);
    }

    /**
     * Funcion que retorna un nodo buscado por su nombre
     *
     * @param name
     * @return NodeColumn
     */
    public NodeColumn getNode(String name) {
        NodeColumn current = head;
        while (current != null) {
            if (current.getName().equals(name)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Funcion que retorna el nodo que contiene la llave primaria
     *
     * @return Int
     */
    public NodeColumn getPrimaryKey() {
        NodeColumn current = head;
        while (current != null) {
            if (current.isPrimaryKey()) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Funcion que retorna un text con el recorrido de las columnas y su
     * informacion
     *
     * @return String
     */
    public String printList() {
        String text = "";
        NodeColumn current = head;
        while (current != null) {
            text += "Nombre de columna: " + current.getName() + "; Tipo de dato: " + current.getType() + "; Llave primaria: " + current.isPrimaryKey() + "\n";
            current = current.getNext();
        }
        return text;
    }

    @Override
    public String toString() {
        String text = "";
        NodeColumn current = head;
        while (current != null) {
            text += current.getName() + ": " + current.getType() + ", ";
            current = current.getNext();
        }
        return text;
    }

    /**
     * Funcion que retorna la cantidad de columnas que tiene la lista
     *
     * @return Int
     */
    public int getLength() {
        int cant = 0;
        NodeColumn current = head;
        while (current != null) {
            cant++;
            current = current.getNext();
        }
        return cant;
    }

    public NodeColumn getHead() {
        return head;
    }

}

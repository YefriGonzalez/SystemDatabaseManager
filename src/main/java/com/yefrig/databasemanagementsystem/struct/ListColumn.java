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
    
    public NodeColumn getPrimaryKey(){
        NodeColumn current=head;
        while(current!=null){
            if(current.isPrimaryKey()){
                return current;
            }
            current=current.getNext();
        }
        return null;
    }

    public String printList() {
        String text = "";
        NodeColumn current = head;
        while (current != null) {
            text += "Nombre de columna: " + current.getName() + "; Tipo de dato: " + current.getType() + "; Llave primaria: "+current.isPrimaryKey()+"\n";
            current = current.getNext();
        }
        return text;
    }

    @Override
    public String toString(){
         String text = "";
        NodeColumn current = head;
        while (current != null) {
            text += current.getName() + ": " + current.getType() + ", ";
            current = current.getNext();
        }
        return text;
    }
    
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

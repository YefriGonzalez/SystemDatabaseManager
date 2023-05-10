/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yefrig.databasemanagementsystem.struct;

import com.yefrig.databasemanagementsystem.front.StartJFrame;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author yefri
 */
public class FileHandler {

    private ListStruct listStruct;

    public FileHandler() {
        listStruct = new ListStruct();
    }

    public void fileAddStruct(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList nodeList = document.getElementsByTagName("estructura");
            for (int i = 0; i < nodeList.getLength(); i++) {//Recorrido de las estructuras encontrada
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Struct struct = new Struct(); //Estructura 
                    ListColumn list = new ListColumn(); //Lista de campos de la estructura
                    NodeList childNodes = node.getChildNodes();
                    String clave = null; //Clave de la tabla

                    for (int j = 0; j < childNodes.getLength(); j++) { //Recorrido de los hijos de una estructura
                        Node childNode = childNodes.item(j);

                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = childNode.getNodeName();//Nombre del nodo
                            String valueNode = childNode.getTextContent().replaceAll(" ", "");//Valor del nodo
                            switch (nodeName) {
                                case "tabla":
                                    struct.setName(valueNode);
                                    break;
                                case "clave":
                                    clave = valueNode;
                                    break;
                                case "relacion":
                                    
                                    break;
                                default:
                                    NodeColumn nc = new NodeColumn(nodeName, valueNode);
                                    list.insertAtEnd(nc);
                                    break;
                            }
                        }
                    }
                    if (clave != null) {
                        NodeColumn c = list.getNode(clave);
                        if (c != null) {
                            struct.setKey(clave);
                            struct.setColumns(list);
                            listStruct.insertAtEnd(struct);
                            System.out.println("Estructura creada");
                        } else {
                            System.out.println("No tiene los datos completos");
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(StartJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        listStruct.printList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echivuniv;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
public class PaginaSecretara extends Frame{
    JFrame f;
     PaginaSecretara() {
     f=new JFrame("Pagina Secretara");
     Button b1=new Button("Inregistreaza un profesor/decan");
     b1.setBounds(450,150,300,40);
     Button b3=new Button("Vizualizare document");
     b3.setBounds(450,200,300,40);
     b1.setFont(new Font("Verdana",Font.PLAIN,20));
     b3.setFont(new Font("Verdana",Font.PLAIN,20));
     ImageIcon icon = new ImageIcon("UOC_img.jpg");
     JLabel limg=new JLabel(icon);
     limg.setBounds(1181,455,100,100);
     f.add(limg);
     f.add(b3);
     f.add(b1);
     f.setBounds(300,200,1300,600);
     f.setLayout(null);
     f.getContentPane().setBackground(new Color(128, 191, 255));
     f.setVisible(true);
     f.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         {
               f.dispose();
             try {
                 PaginaLogare p=new PaginaLogare();
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(PaginaSecretara.class.getName()).log(Level.SEVERE, null, ex);
             }
          }
        }
      });
      b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        try {
            InregistrareProfesor i=new InregistrareProfesor();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaginaSecretara.class.getName()).log(Level.SEVERE, null, ex);
        }
            f.dispose();             
        }
     });
       b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        try {
            Document d=new Document(1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaginaSecretara.class.getName()).log(Level.SEVERE, null, ex);
        }
            f.dispose();             
        }
     });
    }
}

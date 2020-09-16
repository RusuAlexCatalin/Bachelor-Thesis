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
public class PaginaProfesor extends Frame {
    JFrame f;
     PaginaProfesor(){
     f=new JFrame("Pagina profesor");
    Button b1=new Button("Planuri de invatamant");
     b1.setBounds(450,100,350,40);
     Button b2=new Button("Echivalare fost student al facultatii");
     b2.setBounds(450,150,350,40);
     Button b3=new Button("Echivalare student extern");
     b3.setBounds(450,200,350,40);
     Button b4=new Button("Vizualizare documente");
     b4.setBounds(450,250,350,40);
     b1.setFont(new Font("Verdana",Font.PLAIN,20));
     b2.setFont(new Font("Verdana",Font.PLAIN,20));
     b3.setFont(new Font("Verdana",Font.PLAIN,20));
     b4.setFont(new Font("Verdana",Font.PLAIN,20));
     ImageIcon icon = new ImageIcon("UOC_img.jpg");
     JLabel limg=new JLabel(icon);
     limg.setBounds(1181,455,100,100);
     f.add(limg);
     f.add(b2);
     f.add(b1);
     f.add(b3);
     f.add(b4);
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
                 Logger.getLogger(PaginaProfesor.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
        }
      });
      b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        
        try {
            PlanInvatamant l=new PlanInvatamant();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaginaProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.dispose();             
        }
     });
      b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        try {
        EchivFostStudent l=new EchivFostStudent();
         } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaginaProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.dispose();             
        }
     });
            b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
    
        EchivStudentExtern l=new  EchivStudentExtern();
        
        f.dispose();             
        }
     });
             b4.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
    
        try {
            Document d=new  Document(2);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaginaProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        f.dispose();             
        }
     });
    }
}

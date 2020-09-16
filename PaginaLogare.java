/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echivuniv;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
public class PaginaLogare extends Frame {
   JFrame f;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String grad;
   PaginaLogare() throws ClassNotFoundException, NoSuchAlgorithmException{
    try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");//Establishing Connection
    }catch ( SQLException e ){System.out.println(e);}
     f=new JFrame("Pagina Logare");     
     f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     MessageDigest digest = MessageDigest.getInstance("SHA-256"); 
     Label lusern=new Label("Username:");
     Label lpass=new Label("Parola:");
     Label lbl=new Label("Pagina logare");
     lbl.setFont(new Font("Verdana",Font.PLAIN,30));
     lusern.setFont(new Font("Verdana",Font.PLAIN,18));
     lpass.setFont(new Font("Verdana",Font.PLAIN,18));
     TextField userntxt=new TextField();
     JPasswordField passtxt=new JPasswordField();
     userntxt.setFont(new Font("Verdana",Font.PLAIN,18));
     passtxt.setFont(new Font("Verdana",Font.PLAIN,18));
     Button b1=new Button("Log In");
     b1.setFont(new Font("Verdana",Font.PLAIN,15));
     lbl.setBounds(500,50,300,50);
     lusern.setBounds(450,150,100,50);
     lpass.setBounds(450,200,70,50);
     userntxt.setBounds(600,160,200,25);
     passtxt.setBounds(600,210,200,25);
     b1.setBounds(700,250,100,30);
     ImageIcon icon = new ImageIcon("UOC_img.jpg");
     JLabel limg=new JLabel(icon);
     limg.setBounds(1181,455,100,100);
     f.add(limg);
     f.add(lbl);
     f.add(lusern);
     f.add(lpass);
     f.add(passtxt);
     f.add(userntxt);
     f.add(b1);
     f.setBounds(300,200,1300,600);
     f.setLayout(null);
     f.getContentPane().setBackground(new Color(128, 191, 255));
     f.setVisible(true);
      b1.addActionListener(new ActionListener(){ 
    public void actionPerformed(ActionEvent e){
        int verif=0;
        if(userntxt.getText().equals("")){Erori er=new Erori("Completati username-ul!",0);verif=1;}
        else if(passtxt.getText().equals("")){Erori er=new Erori("Completati parola!",0);verif=1;}
        if(verif==0)
        { try{
            byte[] encodedhash = digest.digest(passtxt.getText().getBytes(StandardCharsets.UTF_8));
             pstmt=con.prepareStatement("Select * From DateLogare");
             rs= pstmt.executeQuery();
               while(rs.next()){
                  if(userntxt.getText().equals(rs.getString("username"))&& bytesToHex(encodedhash).equals(rs.getString("parola")))
                  {
                      grad=rs.getString("Grad_Didactic");
                      break;
                  }
               }
               }catch(Exception f){System.out.println(f);}
            if(grad==null){Erori er=new Erori("Username sau parola gresite!",0);}
            else if(grad.equals("secretara"))
            {
               PaginaSecretara p=new PaginaSecretara();
                f.dispose();
            }
            else {
                PaginaProfesor p=new PaginaProfesor();
                 f.dispose();
            }               
        }
        }
     });
}
   private static String bytesToHex(byte[] hash) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
    String hex = Integer.toHexString(0xff & hash[i]);
    if(hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
    }
}

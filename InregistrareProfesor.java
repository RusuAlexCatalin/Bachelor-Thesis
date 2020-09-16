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
import java.util.Calendar;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.border.TitledBorder;
public class InregistrareProfesor extends Frame {
     JFrame f,frame;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private int id,ok=1;
    int an = Calendar.getInstance().get(Calendar.YEAR);
    int luna=Calendar.getInstance().get(Calendar.MONTH);
     InregistrareProfesor() throws ClassNotFoundException, NoSuchAlgorithmException{
         super("Inregistrare Profesor");
         try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");//Establishing Connection
              System.out.println("Connected Successfully");
    }catch ( SQLException e ){System.out.println(e);}
     f=new JFrame("Inregistrare Profesor");
     if(luna>=10) an++;
     MessageDigest digest = MessageDigest.getInstance("SHA-256");
     JPanel panel=new JPanel();
     panel.setLayout(null);
     panel.setBounds(100,30,450,450);
     TitledBorder title;
     title = BorderFactory.createTitledBorder("Inregistrare profesor din comisia de echivalare");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,18));
     panel.setBorder(title);
     panel.setBackground(new Color(128, 191, 255));
     JPanel panel2=new JPanel();
     panel2.setLayout(null);
     panel2.setBounds(800,30,450,300);
     title = BorderFactory.createTitledBorder("Actualizare Decan al facultatii");
     panel2.setBorder(title);
     title.setTitleFont(new Font("Verdana",Font.PLAIN,18));
     panel2.setBackground(new Color(128, 191, 255));
     Label lprof=new Label("Lista Profesori: ");
     lprof.setBounds(20,30,150,50);
     Label luser=new Label("Username: ");
     luser.setBounds(20,80,100,50);
     Label lpass=new Label("Parola: ");
     lpass.setBounds(20,130,100,50);
     Label lmail=new Label("E-mail: ");
     lmail.setBounds(20,180,100,50);
     Label lgrad=new Label("Grad didactic: ");
     lgrad.setBounds(20,230,150,50);
     Label lnume=new Label("Nume: ");
     lnume.setBounds(20,280,100,50);
     Label lpnume=new Label("Prenume: ");
     lpnume.setBounds(20,330,100,50);
     Label ldec=new Label("Lista Decani: ");
     ldec.setBounds(20,30,100,50);
     Label lgrad2=new Label("Grad didactic: ");
     lgrad2.setBounds(20,80,150,50);
     Label lnume2=new Label("Nume: ");
     lnume2.setBounds(20,130,100,50);
     Label lpnume2=new Label("Prenume: ");
     lpnume2.setBounds(20,180,100,50);
     TextField userntxt=new TextField();
     TextField passtxt=new TextField();
     TextField mailtxt=new TextField();
     TextField gradtxt=new TextField();
     TextField numetxt=new TextField();
     TextField pnumetxt=new TextField();
     userntxt.setBounds(170,95,200,25);
     passtxt.setBounds(170,145,200,25);
     mailtxt.setBounds(170,195,200,25);
     gradtxt.setBounds(170,235,200,25);
     numetxt.setBounds(170,295,200,25);
     pnumetxt.setBounds(170,345,200,25);
     Button b1=new Button("Confirmare");
     b1.setBounds(270,400,100,30);
     Choice c=new Choice();
     c.setBounds(170,45,200,20);
     TextField gradtxt2=new TextField();
     TextField numetxt2=new TextField();
     TextField pnumetxt2=new TextField();
     gradtxt2.setBounds(170,95,200,25);
     numetxt2.setBounds(170,145,200,25);
     pnumetxt2.setBounds(170,195,200,25);
     Button b2=new Button("Confirmare");
     b2.setBounds(270,250,100,30);
     Choice c2=new Choice();
     c2.setBounds(170,45,200,20);
     panel.add(luser);
     panel.add(lpass);
     panel.add(lmail);
     panel.add(userntxt);
     panel.add(passtxt);
     panel.add(mailtxt);
     panel.add(b1);
     panel.add(lgrad);
     panel.add(lnume);
     panel.add(lpnume);
     panel.add(gradtxt);
     panel.add(numetxt);
     panel.add(pnumetxt);
     panel.add(lprof);
     panel.add(c);
     panel2.add(b2);
     panel2.add(lgrad2);
     panel2.add(lnume2);
     panel2.add(lpnume2);
     panel2.add(gradtxt2);
     panel2.add(numetxt2);
     panel2.add(pnumetxt2);
     panel2.add(ldec);
     panel2.add(c2);
     c.add("");
     c2.add("");
     try{
     pstmt=con.prepareStatement("Select Nume,Prenume,Decan FROM DateLogare WHERE Nume IS NOT NULL");
     rs= pstmt.executeQuery();
     while(rs.next())
     {
        if(rs.getInt(3)==0) c.add(rs.getString(2)+" "+rs.getString(1).toUpperCase());
        else c2.add(rs.getString(2)+" "+rs.getString(1).toUpperCase());
     }
     }catch(Exception f){System.out.println(f);}
     c.setFont(new Font("Verdana",Font.PLAIN,18));
     c2.setFont(new Font("Verdana",Font.PLAIN,18));
     luser.setFont(new Font("Verdana",Font.PLAIN,18));    
     lpass.setFont(new Font("Verdana",Font.PLAIN,18));  
     userntxt.setFont(new Font("Verdana",Font.PLAIN,18));    
     passtxt.setFont(new Font("Verdana",Font.PLAIN,18));
     lmail.setFont(new Font("Verdana",Font.PLAIN,18));          
     mailtxt.setFont(new Font("Verdana",Font.PLAIN,18));        
     lgrad.setFont(new Font("Verdana",Font.PLAIN,18));           
     gradtxt.setFont(new Font("Verdana",Font.PLAIN,18)); 
     lnume.setFont(new Font("Verdana",Font.PLAIN,18));
     numetxt.setFont(new Font("Verdana",Font.PLAIN,18));
     lpnume.setFont(new Font("Verdana",Font.PLAIN,18));
     pnumetxt.setFont(new Font("Verdana",Font.PLAIN,18));
     b1.setFont(new Font("Verdana",Font.PLAIN,18));
     lprof.setFont(new Font("Verdana",Font.PLAIN,18));
     lgrad2.setFont(new Font("Verdana",Font.PLAIN,18));           
     gradtxt2.setFont(new Font("Verdana",Font.PLAIN,18)); 
     lnume2.setFont(new Font("Verdana",Font.PLAIN,18));
     numetxt2.setFont(new Font("Verdana",Font.PLAIN,18));
     lpnume2.setFont(new Font("Verdana",Font.PLAIN,18));
     pnumetxt2.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setFont(new Font("Verdana",Font.PLAIN,18));
     ldec.setFont(new Font("Verdana",Font.PLAIN,18));
     ImageIcon icon = new ImageIcon("UOC_img.jpg");
     JLabel limg=new JLabel(icon);
     limg.setBounds(1181,455,100,100);
     f.add(limg);
     f.add(panel);
     f.add(panel2);
     f.setBounds(300,200,1300,600);
     f.setLayout(null);
     f.getContentPane().setBackground(new Color(128, 191, 255));
     f.setVisible(true);
     f.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         {
               f.dispose();
               PaginaSecretara p=new PaginaSecretara();
         }
        }
      });
      c.addItemListener(new ItemListener(){  
        public void itemStateChanged(ItemEvent ie){
            try{
             pstmt=con.prepareStatement("Select * FROM DateLogare WHERE Nume='"+c.getSelectedItem().substring(c.getSelectedItem().lastIndexOf(" ")+1,c.getSelectedItem().length()).toLowerCase()+"'");
             rs= pstmt.executeQuery();
             rs.next();
             userntxt.setText(rs.getString(2));
             passtxt.setText(rs.getString(3));
             mailtxt.setText(rs.getString(4));
             gradtxt.setText(rs.getString("Grad_Didactic"));
             numetxt.setText(rs.getString(6));
             pnumetxt.setText(rs.getString(7));
             }catch(Exception f){System.out.println(f);} 
        }
        });
       c2.addItemListener(new ItemListener(){  
        public void itemStateChanged(ItemEvent ie){
                        try{
             pstmt=con.prepareStatement("Select * FROM DateLogare WHERE Nume='"+c2.getSelectedItem().substring(c2.getSelectedItem().lastIndexOf(" ")+1,c2.getSelectedItem().length()).toLowerCase()+"' AND Decan=yes");
             rs= pstmt.executeQuery();
             rs.next();
             gradtxt2.setText(rs.getString("Grad_Didactic"));
             numetxt2.setText(rs.getString(6));
             pnumetxt2.setText(rs.getString(7));
             }catch(Exception f){System.out.println(f);}
        }
        });
      b1.addActionListener(new ActionListener(){ 
    public void actionPerformed(ActionEvent e){
                 int verif=0;
                 String[] s={""};
                 if(!mailtxt.getText().equals("")){s=mailtxt.getText().split("@");}
        if(userntxt.getText().equals("")){Erori er=new Erori("Completeaza username!",0);verif=1;}
        else if(passtxt.getText().equals("")){Erori er=new Erori("Completeaza parola!",0);verif=1;}
        else if(mailtxt.getText().equals("")){Erori er=new Erori("Completeaza E-mail!",0);verif=1;}
        else if(s.length==1){Erori er=new Erori("E-mail completat gresit!",0);verif=1;}
        else if(gradtxt.getText().equals("")){Erori er=new Erori("Completeaza gradul didactic!",0);verif=1;}
        else if((gradtxt.getText().matches(".*\\d+.*"))){Erori er=new Erori("Grad didactic completat gresit!",0);verif=1;}
        else if(numetxt.getText().equals("")){Erori er=new Erori("Completeaza numele!",0);verif=1;} 
        else if((numetxt.getText().matches(".*\\d+.*"))){Erori er=new Erori("Nume completat gresit!",0);verif=1;}
        else if(pnumetxt.getText().equals("")){Erori er=new Erori("Completeaza prenumele!",0);verif=1;}
        else if((pnumetxt.getText().matches(".*\\d+.*"))){Erori er=new Erori("Prenume completat gresit!",0);verif=1;}
        try{
            String anCurent=String.valueOf(an-1)+"-"+String.valueOf(an);
        pstmt=con.prepareStatement("Select * FROM DateLogare");
             rs= pstmt.executeQuery();
               while(rs.next())
               {
                   if(rs.getString(5)!=null)
                   {
                       if(numetxt.getText().equals(rs.getString(6)) && gradtxt.getText().equals(rs.getString(5)) && pnumetxt.getText().equals(rs.getString(7)) && anCurent.equals(rs.getString(9)))
                       {
                           Erori er=new Erori("Contul exista deja!",0);verif=1;    
                       }
                   }
               }
        }catch(Exception f){System.out.println(f);} 
        if(verif==0){
            try{
             byte[] encodedhash = digest.digest(passtxt.getText().getBytes(StandardCharsets.UTF_8));
             pstmt=con.prepareStatement("Select Max(ID) FROM DateLogare");
             rs= pstmt.executeQuery();
               rs.next();
                     id=Integer.parseInt(rs.getString(1));
               pstmt=con.prepareStatement("INSERT INTO DATELOGARE VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
              pstmt.setInt(1,id+1);
              pstmt.setString(2,userntxt.getText());
              pstmt.setString(3,bytesToHex(encodedhash));
              pstmt.setString(4,mailtxt.getText());
              pstmt.setString(5,gradtxt.getText());
              pstmt.setString(6,numetxt.getText());
              pstmt.setString(7,pnumetxt.getText());
              pstmt.setInt(8,0);
              pstmt.setString(9,String.valueOf(an-1)+"-"+String.valueOf(an));
              pstmt.executeUpdate(); 
               }catch(Exception f){System.out.println(f);} 
             Erori er=new Erori("Profesorul a fost inregistrat!",1);
        } 
    }
     });
      b2.addActionListener(new ActionListener(){ 
    public void actionPerformed(ActionEvent e){
        int verif=0;
        if(gradtxt2.getText().equals("")){Erori er=new Erori("Completeaza gradul didactic!",0);verif=1;}
        else if((gradtxt2.getText().matches(".*\\d+.*"))){Erori er=new Erori("Grad didactic completat gresit!",0);verif=1;}
        else if(numetxt2.getText().equals("")){Erori er=new Erori("Completeaza numele!",0);verif=1;}
        else if((numetxt2.getText().matches(".*\\d+.*"))){Erori er=new Erori("Nume completat gresit!",0);verif=1;} 
        else if(pnumetxt2.getText().equals("")){Erori er=new Erori("Completeaza prenumele!",0);verif=1;}
        else if((pnumetxt2.getText().matches(".*\\d+.*"))){Erori er=new Erori("Prenume completat gresit!",0);verif=1;}
        try{
            String anCurent=String.valueOf(an-1)+"-"+String.valueOf(an);
        pstmt=con.prepareStatement("Select * FROM DateLogare WHERE Decan=yes");
             rs= pstmt.executeQuery();
               while(rs.next())
               {
                   if(rs.getString(5)!=null)
                   {
                       if(numetxt2.getText().equals(rs.getString(6)) && gradtxt2.getText().equals(rs.getString(5)) && pnumetxt2.getText().equals(rs.getString(7)) && anCurent.equals(rs.getString(9)))
                       {
                           Erori er=new Erori("Contul exista deja!",0);verif=1;
                           break;
                       }
                   }
               }
        }catch(Exception f){System.out.println(f);} 
        if(verif==0){
            try{
             pstmt=con.prepareStatement("Select Max(ID) FROM DateLogare");
             rs= pstmt.executeQuery();
               rs.next();
                     id=Integer.parseInt(rs.getString(1));
               pstmt=con.prepareStatement("INSERT INTO DATELOGARE VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
              pstmt.setInt(1,id+1);
              pstmt.setString(2,"");
              pstmt.setString(3,"");
              pstmt.setString(4,"");
              pstmt.setString(5,gradtxt2.getText());
              pstmt.setString(6,numetxt2.getText());
              pstmt.setString(7,pnumetxt2.getText());
              pstmt.setInt(8,1);
              pstmt.setString(9,String.valueOf(an-1)+"-"+String.valueOf(an));
              pstmt.executeUpdate();
               }catch(Exception f){System.out.println(f);}
             Erori er=new Erori("Date Decan actualizate!",1);
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

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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.TableColumn;
public class StareStudent extends Frame {
    JFrame f;
    private String[][] data;
    private String nume,prenume,init,spec,res="",echiv="",ann;
    int an1=0,an2=0,an3=0,an=1;
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int luna=Calendar.getInstance().get(Calendar.MONTH);
    TextArea area=new TextArea();
    StareStudent(String[][] data,String nume,String prenume,String init,String spec,String ann){
        this.data=data;
        this.nume=nume;
        this.prenume=prenume;
        this.init=init;
        this.spec=spec;
        this.ann=ann;
        f=new JFrame("Stare Student");
        if(luna>=10) year++;
        for(int i=0;i<data.length;i++)
        {if(!data[i][4].equals("ADM"))
            {if(Integer.parseInt(data[i][4])>=5 && Integer.parseInt(data[i][0])==1) an1=an1+Integer.parseInt(data[i][4]);
                if(Integer.parseInt(data[i][4])>=5 && Integer.parseInt(data[i][0])==2) an2=an2+Integer.parseInt(data[i][4]);
                if(Integer.parseInt(data[i][4])>=5 && Integer.parseInt(data[i][0])==3) an3=an3+Integer.parseInt(data[i][4]);
            }
        }
        if(an1>=31)an++;
        if(an2>=31)an++;
        if(an==1)
        {
            for(int i=0;i<data.length;i++)
        {
            if(data[i][4].equals("ADM")) echiv=echiv+data[i][1]+"(Anul "+data[i][0]+"),";
            else if(Integer.parseInt(data[i][0])>=an && Integer.parseInt(data[i][4])>=5) echiv=echiv+data[i][1]+"(Anul "+data[i][0]+"),";
        }
        }
        else 
        {
        for(int i=0;i<data.length;i++)
        {
             if(data[i][4].equals("ADM"))
             {if(Integer.parseInt(data[i][0])>=an ) echiv=echiv+data[i][1]+"(Anul "+data[i][0]+"),";}
             else if(Integer.parseInt(data[i][0])>=an && Integer.parseInt(data[i][4])>=5) echiv=echiv+data[i][1]+"(Anul "+data[i][0]+"),";
             else if(Integer.parseInt(data[i][0])<an && Integer.parseInt(data[i][4])<5) res=res+data[i][1]+"(Anul "+data[i][0]+"),";
        }
        }
        if(an!=1 && !res.equals(""))
        {res=res.substring(0,res.length()-1);}
        if(!echiv.equals(""))
        echiv=echiv.substring(0,echiv.length()-1);
        Button b1=new Button("Confirma");
        String s1,s2;
        s1="Cu restanta la urmatoarele discipline: "+res;
        s2= "Si cu urmatoarele materii echivalate in ani superiori: "+echiv;
        int n=0;
        int x=s2.length()/75;
        for(int i=0;i<x;i++)
        {
            s2=s2.substring(0,n+75)+"\n"+s2.substring(n+75,s2.length());
            n=n+75;
        }
        x=s1.length()/75;
        n=0;
        for(int i=0;i<x;i++)
        {
            s1=s1.substring(0,n+75)+"\n"+s1.substring(n+75,s1.length());
            n=n+75;
        }
        area=new TextArea("In urma echivalarii realizate de program a studentului:"+nume+" "+prenume+"\n"+
                          "Initiala tatalui: "+init+"\n"+
                          "Specializare : "+spec+"\n"+
                          "An universitar :"+String.valueOf(year-1)+"-"+String.valueOf(year)+"\n"+
                          "Acesta va fi inscris in anul "+String.valueOf(an)+"\n"+s1+"\n"+s2);
                          
        area.setBounds(20,50, 620,200);
        b1.setBounds(250,300,150,25);
        area.setEditable(false);
        area.setFont(new Font("Verdana",Font.PLAIN,18));
        b1.setFont(new Font("Verdana",Font.PLAIN,18));
        f.add(area);
        f.add(b1);
        f.setBounds(700,300,700,400);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(128, 191, 255));
        f.setVisible(true);
        f.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         {
               f.dispose();
          }
        }
      });
        b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
             try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://D:\\Programs\\java\\EchivUniv\\BDEchivUniv.accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }     
        try{
      Statement s=con.createStatement();
      s.execute("CREATE TABLE ["+nume+" "+init.toUpperCase()+" "+prenume+" "+spec+" "+ann+" "+(year-1)+"-"+year+"] (An INTEGER,Disciplina CHAR(255),Semestru INTEGER,Disciplina_Echivalata CHAR(255),Nota CHAR(255) ,Numar_Credite INTEGER)");
         pstmt=con.prepareStatement("INSERT INTO ["+nume+" "+init.toUpperCase()+" "+prenume+" "+spec+" "+ann+" "+(year-1)+"-"+year+"] VALUES ( ?, ?, ?, ?, ?,?)");
      for(int i=0;i<data.length;i++)
     {
              pstmt.setInt(1,Integer.parseInt(data[i][0]));
              pstmt.setString(2,data[i][1]);
              pstmt.setInt(3,Integer.parseInt(data[i][2]));
              pstmt.setString(4,data[i][3]);
              if(data[i][4].equals("ADM"))pstmt.setString(5,"ADM");
              else pstmt.setString(5, data[i][4]);
              pstmt.setInt(6,Integer.parseInt(data[i][5]));
              pstmt.executeUpdate();
     }
       Erori er=new Erori("Echivalarea a fost salvata!",1);
           }catch(Exception f){System.out.println(f);} 
           f.dispose();
    }
     });
    }
}

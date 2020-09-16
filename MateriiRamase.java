
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
public class MateriiRamase extends Frame{
    private String data2[][];
    private Object data[][];
    private String spec;
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
     JFrame f;
    public MateriiRamase(Object data[][],String data2[][],String spec){
        this.data=data;
        this.data2=data2; 
        this.spec=spec;
        f=new JFrame("Materii ramase");
        Choice c=new Choice();
        Choice c2=new Choice();
        Button b1=new Button("Salveaza");
        Button b2=new Button("Confirmare");
        Label l=new Label("Unele materii nu au putut fi echivalate");
        Label l2=new Label("Echivalare manuala este necesara.");
        Label lm=new Label("Materie din cadrul UOC");
        Label lm2=new Label("Materie neechivalata");
        c.add("");
        c2.add("");
        for(int i=0;i<data.length;i++)
        {
            if((!data[i][0].equals("-") || data[i][0]==null) && Integer.parseInt(data[i][1].toString())>4)  c.add(data[i][0].toString());
        }
         for(int i=0;i<data2.length;i++)
        {
            if(data2[i][3]==null) {c2.add(data2[i][1]);}
            else if(data2[i][3].equals("")) {c2.add(data2[i][1]);}
           
        }
         b2.setBounds(100,200,120,25);
         b1.setBounds(300,200,120,25);
         c.setBounds(280,100,250,20);
         c2.setBounds(280,150,250,20);
         l.setBounds(120,20,320,20);
         l2.setBounds(130,40,290,20);
         lm.setBounds(50,150,190,20);
         lm2.setBounds(50,100,170,20);
         b2.setFont(new Font("Verdana",Font.PLAIN,18));
         b1.setFont(new Font("Verdana",Font.PLAIN,18));
         c.setFont(new Font("Verdana",Font.PLAIN,18));
         c2.setFont(new Font("Verdana",Font.PLAIN,18));
         l.setFont(new Font("Verdana",Font.PLAIN,18));
         lm.setFont(new Font("Verdana",Font.PLAIN,18));
         lm2.setFont(new Font("Verdana",Font.PLAIN,18));
         l2.setFont(new Font("Verdana",Font.PLAIN,18));
         f.add(c);
         f.add(l);
         f.add(l2);
         f.add(lm);
         f.add(lm2);
         f.add(b2);
         f.add(b1);
         f.add(c2);
        f.setBounds(700,400,600,300);
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
     b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        for(int i=0;i<data2.length;i++)
        {
            if(data2[i][1].equals(c2.getSelectedItem())) {
                data2[i][3]=c.getSelectedItem();
            for(int j=0;j<data.length;j++){
                String s;
                if(c2.getSelectedItem().toLowerCase().equals("educatie fizica")) s=c2.getSelectedItem();
                else s=c2.getSelectedItem().substring(0,c2.getSelectedItem().lastIndexOf(" ")).toLowerCase();
           if(s.equals("educatie fizica") && data[j][0].equals(c.getSelectedItem())) data2[i][4]="ADM";
           else if(data[j][0].equals(c.getSelectedItem()))
                data2[i][4]=data[j][1].toString();
            }
            }  
        }
        c.remove(c.getSelectedItem());
        c2.remove(c2.getSelectedItem());
         }
     });
     b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
       f.dispose();
         try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://"+spec+".accdb");//Establishing Connection              
        }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
         int n=0,ok=1,j=0,nr=0,y=0;
         String s="",col="";
         try{
             for(int i=1;i<=3;i++)
             {nr=0;
              pstmt=con.prepareStatement("SELECT * FROM [Materii echivalate Anul "+i+"] ORDER BY Semestru ASC,Disciplina_UOC ASC");
              rs= pstmt.executeQuery();
             rsmd=rs.getMetaData();
             n = rsmd.getColumnCount();
             while(rs.next())
             {
                 if(rs.getString(n)!=null) nr++;
                 
             }
               while(j<data2.length)
                {  
                    if(Integer.parseInt(data2[j][0])==i)
                    {
                        pstmt=con.prepareStatement("SELECT * FROM [Materii echivalate Anul "+i+"] ORDER BY Semestru ASC,Disciplina_UOC ASC");
                        rs= pstmt.executeQuery();
                        while(rs.next())
                        {
                            if(data2[j][1].equals(rs.getString(3))) {s=rs.getString(3);break;}
                        }
                         if(nr>=1 && y!=i)
                        {
                            col=rsmd.getColumnName(n);
                            String x=col.substring(0,col.lastIndexOf("_")+1)+String.valueOf(Integer.parseInt(col.substring(col.lastIndexOf("_")+1,col.length()))+1);
                            pstmt=con.prepareStatement("ALTER TABLE [Materii echivalate Anul "+i+"] ADD "+x+" TEXT(255)");
                            pstmt.executeUpdate();
                            pstmt=con.prepareStatement("UPDATE [Materii echivalate Anul "+i+"] SET "+x+"=? WHERE Disciplina_UOC=?");
                            pstmt.setString(1,data2[j][3]);
                            pstmt.setString(2,s);
                            pstmt.executeUpdate();
                        }
                        pstmt=con.prepareStatement("SELECT * FROM [Materii echivalate Anul "+i+"] WHERE Disciplina_UOC='"+s+"'");
                        rs= pstmt.executeQuery();
                        rs.next();
                        for(int k=3;k<=n;k++)
                        {
                            if(rs.getString(k)==null || rs.getString(k).equals("")) {ok=0;col=rsmd.getColumnName(k);break;}
                            else if(rs.getString(k).equals(data2[j][3])) {ok=1;break;}
                            else {ok=2;col=rsmd.getColumnName(k);}
                        }
                        if(ok==0 && Integer.parseInt(data2[j][4])>=5) { 
                        pstmt=con.prepareStatement("UPDATE [Materii echivalate Anul "+i+"] SET "+col+"=? WHERE Disciplina_UOC=?");
                        pstmt.setString(1,data2[j][3]);
                        pstmt.setString(2,s);
                        pstmt.executeUpdate();
                        }
                    }
                    else break;
                    j++; 
                    y=i;
                }
             }
              }catch(Exception f){System.out.println(f);}
    }
     });
    }
    public String[][] getData(){ return data2;}
}

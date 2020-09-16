
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class Optionale extends Frame{
    JFrame f;
    private String plan,an,spec,plan2;
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private String data[][];
    Optionale(String an,String plan,String spec,String[][] data) throws ClassNotFoundException{
        this.plan=plan;
        this.an=an;
        this.spec=spec;
        this.data=data;
     try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+spec+".accdb");
    }catch ( SQLException e ){System.out.println(e);}
     f=new JFrame("Optionale");
     Choice c=new Choice();
     Choice c2=new Choice();
     Label l=new Label("Alegerea optionalelor"); 
     Button b1=new Button("Iesire");
     Button b2=new Button("Confirmare");
     Label lm=new Label("Nume optional:");
     Label lm2=new Label("Materia disponibile:");
     c.add("");
     c2.add("");
     b2.setBounds(50,200,120,25);
     b1.setBounds(250,200,120,25);
     c.setBounds(200,100,250,20);
     c2.setBounds(200,150,250,20);
     l.setBounds(140,20,250,20);
     lm.setBounds(20,100,150,20);
     lm2.setBounds(20,150,160,20);
     for(int i=0;i<data.length;i++)
     {if(data[i][1].length()>=12)
     { if(data[i][1].substring(0,8).equals("Optional")) 
         {if(data[i][1].substring(10,11).equals(":")||data[i][1].substring(10,11).equals("("))
             c.add(data[i][1].substring(0,10));
             else 
                 if(data[i][1].substring(11,12).equals(":")||data[i][1].substring(11,12).equals("("))
              c.add(data[i][1].substring(0,11));
         }
     }
     }
     b1.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setFont(new Font("Verdana",Font.PLAIN,18));
     c.setFont(new Font("Verdana",Font.PLAIN,18)); 
     c2.setFont(new Font("Verdana",Font.PLAIN,18));
     lm.setFont(new Font("Verdana",Font.PLAIN,18));
     lm2.setFont(new Font("Verdana",Font.PLAIN,18));
     l.setFont(new Font("Verdana",Font.PLAIN,18));
     f.add(b1);
     f.add(b2);
     f.add(c);
     f.add(c2);
     f.add(lm);
     f.add(lm2);
     f.add(l);
     f.setBounds(700,400,500,300);
     f.setLayout(null);
     f.getContentPane().setBackground(new Color(128, 191, 255));
     f.setVisible(true);
      c.addItemListener(new ItemListener(){  
        public void itemStateChanged(ItemEvent ie){
            c2.removeAll();
            try{
     pstmt=con.prepareStatement("SELECT * FROM Optionale");
     rs= pstmt.executeQuery();
     c2.add("");
     while(rs.next())
     {
     if(rs.getString(Integer.parseInt(c.getSelectedItem().substring(9,c.getSelectedItem().length())))!=null)
     {
         if(!rs.getString(Integer.parseInt(c.getSelectedItem().substring(9,c.getSelectedItem().length()))).equals(""))
         c2.add(rs.getString(Integer.parseInt(c.getSelectedItem().substring(9,c.getSelectedItem().length()))));
     }
         
     }
     }catch(Exception f){System.out.println(f);}
        }
    });
       f.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         {
               f.dispose();
          }
        }
      });
        b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
       f.dispose();
    }
     });
        b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
       for(int i=0;i<data.length;i++)
     {
         if(data[i][1].substring(0,10).equals(c.getSelectedItem())) 
         data[i][1]=c.getSelectedItem()+":"+c2.getSelectedItem();
     }
       c.remove(c.getSelectedItem());
       c2.removeAll();
    }
     });
    }    
     public String[][] getData(){ return data;}
}

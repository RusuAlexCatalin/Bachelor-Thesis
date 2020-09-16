/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echivuniv;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class ModificaOptionale extends Frame {
    JFrame f;
    JScrollPane sp=new JScrollPane();
    DefaultTableModel model = new DefaultTableModel();
    JTable tabel  = new JTable(model);
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    String spec;
    int n,m;
    int[] columnsWidth;
    String[] v;
    String[][] data;
    ModificaOptionale(String spec)
    {
        this.spec=spec;
    f=new JFrame("Modifica Optionale");
    try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+spec+".accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
            try{ 
            int j=0,i;
             pstmt=con.prepareStatement("SELECT COUNT(*) FROM Optionale");
              rs= pstmt.executeQuery();
             rs.next();
             m=rs.getInt(1);
             v=new String[m];
             pstmt=con.prepareStatement("SELECT * FROM Optionale");
             rs= pstmt.executeQuery();
             rsmd=rs.getMetaData();
             n = rsmd.getColumnCount();
             i=0;
             while(rs.next())
             {
                 v[i]=rs.getString(n);
                 i++;
             }
             
            for (i = 1; i <= n-1; i++) 
                model.addColumn(rsmd.getColumnLabel(i));
            
            data=new String[m][n];
            Object[] row=new Object[n];
            i=0;
            pstmt=con.prepareStatement("SELECT * FROM Optionale");
             rs= pstmt.executeQuery();
               while(rs.next())
               {
                 while(j<n-1){
                     data[i][j]=rs.getString(j+1);
                    
                     j++;
                 }
                 i++;
                   if(j>=n-1){j=0; }
               }
               for(i=0;i<m;i++)
               { 
                   for(j=0;j<n-1;j++)
                    {
                       row[j]=data[i][j];
                    }
                   model.addRow(row);
               }
                int imp=1400/(n-1);
                columnsWidth=new int[n-1];
                for(i=0;i<n-1;i++)
                    columnsWidth[i]=imp;
                i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
                f.remove(sp);
                f.remove(tabel);
                f.revalidate(); 
                f.repaint();
                tabel.setFont(new Font("Verdana",Font.PLAIN,15));
                tabel.setRowHeight(20);
                DefaultTableCellRenderer centerRenderer = (DefaultTableCellRenderer)tabel.getDefaultRenderer(Object.class);
                centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
                sp=new JScrollPane(tabel);
                sp.setLocation(30, 30);
                sp.setSize(new Dimension(1400, 350));
                f.add(sp);
        }catch(Exception f){System.out.println(f);}
    Button b1=new Button("Adaugare");
    Button b2=new Button("Stergere");
    Button b3=new Button("Salveaaza");
    b1.setBounds(400,400,160,30);
    b2.setBounds(600,400,160,30); 
    b3.setBounds(800,400,160,30);
    b1.setFont(new Font("Verdana",Font.PLAIN,18));
    b2.setFont(new Font("Verdana",Font.PLAIN,18));
    b3.setFont(new Font("Verdana",Font.PLAIN,18));
    f.add(b1);
    f.add(b2);
    f.add(b3);
    f.setBounds(200,300,1500,500);
    f.getContentPane().setBackground(new Color(128, 191, 255));
    f.setLayout(null);
    f.setVisible(true);
    b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
             f.getContentPane().remove(sp);
        f.getContentPane().remove(tabel);
        f.revalidate(); 
        f.repaint(); 
       model.insertRow(model.getRowCount(), new Object[] {  });
        int i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
        sp=new JScrollPane(tabel);
        sp.setLocation(30, 30);
        sp.setSize(new Dimension(1400, 350));
        f.add(sp);
        }
     });
     b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
             f.getContentPane().remove(sp);
        f.getContentPane().remove(tabel);
        f.revalidate(); 
        f.repaint();
       int i = tabel.getSelectedRow();
                if(i >= 0){
                    model.removeRow(i);
                }
         i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
        sp=new JScrollPane(tabel);
        sp.setLocation(30, 30);
        sp.setSize(new Dimension(1400, 350));
        f.add(sp);                        
        }
    });
      b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        String s="";
        Object[][] Data = new Object[tabel.getRowCount()+1][tabel.getColumnCount()];
       for(int i=0;i<tabel.getRowCount();i++)
       {
           for(int j=0;j<tabel.getColumnCount();j++)
           {
              Data[i][j]=tabel.getValueAt(i, j);
           }
       }
       try{
           int i=1;
           while(m>tabel.getRowCount())
           {  pstmt=con.prepareStatement("DELETE FROM Optionale WHERE ID="+v[v.length-i]);
              pstmt.executeUpdate();
              m--;
              i++;
           }
           for(i=0;i<n;i++)
           {
               s=s+"?,";
           }
           s=s.substring(0,s.length()-1);
           int j=tabel.getRowCount();
            while(m<tabel.getRowCount())
           {  pstmt=con.prepareStatement("INSERT INTO Optionale VALUES ("+s+")");
           for(i=0;i<n-1;i++)
           {if(Data[j][i]==null)pstmt.setString(i+1,"");
             else pstmt.setString(i+1,Data[j][i].toString());
           }
           pstmt.setInt(n,Integer.parseInt(v[v.length-1])+1);
              pstmt.executeUpdate();
              m++;
              j--;
           }
        pstmt=con.prepareStatement("SELECT * FROM Optionale");
        rs= pstmt.executeQuery();
        i=0;
        while(rs.next())
        {
            for(j=0;j<tabel.getColumnCount();j++)
                if(Data[i][j]!=null)
                {if(!Data[i][j].equals(rs.getString(j+1)))
                    {
                        pstmt=con.prepareStatement("UPDATE Optionale SET [Optional "+(j+1)+"]=? WHERE ID=?");
                        pstmt.setString(1,Data[i][j].toString());
                        pstmt.setString(2,rs.getString(tabel.getColumnCount()+1));
                        pstmt.executeUpdate();
                    }
                }
              i++;  
        }
       }catch(Exception f){System.out.println(f);}
    }
    });
    }
}

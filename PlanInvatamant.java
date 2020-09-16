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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class PlanInvatamant extends Frame {
    JFrame f;
    String data[][];
    JScrollPane sp=new JScrollPane();
    DefaultTableModel model = new DefaultTableModel();
    JTable tabel  = new JTable(model);
    Optionale l;
    int[] columnsWidth = {40,360 , 80, 40,40, 40,40, 40,40, 100,100};
    int n,m;
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private String[] v;
    int an = Calendar.getInstance().get(Calendar.YEAR);
    int luna=Calendar.getInstance().get(Calendar.MONTH);
     PlanInvatamant() throws ClassNotFoundException{
     f=new JFrame("Planuri de invatamant");
     if(luna>=10) an++;
     Choice cAn=new Choice();
     Choice cPlan=new Choice();
     Choice cSpec=new Choice();
     Label lplan=new Label("Anul universitar:");
     Label lan=new Label("An de studiu :");
     Label lspec=new Label("Spcializarea:");
     Button b1=new Button("Vizualizare");
     Button b2=new Button("Adauga plan");
     Button b3=new Button("Schimba optionale");
     Button b4=new Button("Adauga");
     Button b5=new Button("Sterge");
     Button b6=new Button("Reactualizare tabel");
     Button b7=new Button("Modifica optionale");
     Label lplan2=new Label("Anul universitar:");
     b2.setBounds(40,90,160,25);
     b3.setBounds(40,120,160,30);
     lplan2.setBounds(50,20,200,20);
     TextField antxt=new TextField();
     antxt.setBounds(40,50,160,25);
     JPanel panel=new JPanel();
     JPanel panel2=new JPanel();
     panel.setLayout(null);
     panel.setBounds(1000,300,250,130);
     panel.setBackground(new Color(128, 191, 255));
     panel2.setLayout(null);
     panel2.setBounds(1000,90,250,200);
     panel2.setBackground(new Color(128, 191, 255));
     TitledBorder title;
     title = BorderFactory.createTitledBorder("Adugare plan de invatamant");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,15));
     panel.setBorder(title);
     panel.add(lplan2);
     panel.add(antxt);
     panel.add(b2);
     title = BorderFactory.createTitledBorder("Modifica plan de invatamant");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,15));
     panel2.setBorder(title);
     title = BorderFactory.createTitledBorder("Modifica lista de optionale");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,15));
     panel2.add(b4);
     panel2.add(b5);
     panel2.add(b3);
     panel2.add(b6);
     b1.setBounds(840,25,120,30);
     b4.setBounds(40,40,160,30);
     b5.setBounds(40,80,160,30);
     b6.setBounds(40,160,160,30);
     b7.setBounds(1000,25,160,30);
     lplan.setBounds(570,30,150,20);
     cPlan.setBounds(710,25,120,20);
     lan.setBounds(320,30,150,20);
     cAn.setBounds(440,25,120,20);
     lspec.setBounds(50,30,150,20);
     cSpec.setBounds(160,25,150,20);
     cAn.add("");
     cAn.add("1");
     cAn.add("2");
     cAn.add("3");
     cSpec.add("Informatica");
     cSpec.add("Matematica");
     cSpec.add("Mate-Info");
     cSpec.add("ComputerScience");
     lplan.setFont(new Font("Verdana",Font.PLAIN,18));
     lan.setFont(new Font("Verdana",Font.PLAIN,18));
     lspec.setFont(new Font("Verdana",Font.PLAIN,18));
     cSpec.setFont(new Font("Verdana",Font.PLAIN,18));
     cAn.setFont(new Font("Verdana",Font.PLAIN,18));
     cPlan.setFont(new Font("Verdana",Font.PLAIN,18));
     b4.setFont(new Font("Verdana",Font.PLAIN,18));
     b1.setFont(new Font("Verdana",Font.PLAIN,18));
     b5.setFont(new Font("Verdana",Font.PLAIN,18));
     b6.setFont(new Font("Verdana",Font.PLAIN,18));
     b3.setFont(new Font("Verdana",Font.PLAIN,18));
     lplan2.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setFont(new Font("Verdana",Font.PLAIN,18));
     b7.setFont(new Font("Verdana",Font.PLAIN,18));
     antxt.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setEnabled(false);
     b3.setEnabled(false);
     b4.setEnabled(false);
     b5.setEnabled(false);
     b6.setEnabled(false);
     antxt.setEnabled(false);
     lplan2.setEnabled(false);
     f.add(panel);
     f.add(b7);
     f.add(b1);
     f.add(cSpec);
     f.add(cAn);
     f.add(cPlan);
     f.add(lspec);
     f.add(lan);
     f.add(lplan);
     f.add(panel2);
     f.setBounds(300,200,1300,600);
     f.setLayout(null);
     f.getContentPane().setBackground(new Color(128, 191, 255));
     f.setVisible(true);
     f.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         {
               f.dispose();
               PaginaProfesor p=new PaginaProfesor();
         }
        }
      });
     b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
         model = new DefaultTableModel();
         tabel  = new JTable(model);
         int ok=0;
        try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cSpec.getSelectedItem()+".accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(cAn.getSelectedItem().equals("")){Erori er=new Erori("Alegeti un an de studiu!",0);ok=1;}
        if(ok==0)
        try{ b2.setEnabled(true);
     b3.setEnabled(true);
     b4.setEnabled(true);
     b5.setEnabled(true);
     antxt.setEnabled(true);
     lplan2.setEnabled(true);
            int j=0,i;
             pstmt=con.prepareStatement("SELECT * FROM [Plan de invatamant Anul "+cAn.getSelectedItem()+" "+cPlan.getSelectedItem()+"]");
             rs= pstmt.executeQuery();
             rsmd=rs.getMetaData();
             n = rsmd.getColumnCount();
             pstmt=con.prepareStatement("SELECT COUNT(*) FROM [Plan de invatamant Anul "+cAn.getSelectedItem()+" "+cPlan.getSelectedItem()+"]");
              rs= pstmt.executeQuery();
             rs.next();
             m=rs.getInt(1);
            for (i = 1; i <= n; i++) 
                model.addColumn(rsmd.getColumnLabel(i));
            
            data=new String[m][n];
            Object[] row=new Object[n];
            i=0;
            pstmt=con.prepareStatement("SELECT * FROM [Plan de invatamant Anul "+cAn.getSelectedItem()+" "+cPlan.getSelectedItem()+"]");
             rs= pstmt.executeQuery();
               while(rs.next())
               {
                 while(j<n){
                     data[i][j]=rs.getString(j+1);
                    
                     j++;
                 }
                 i++;
                   if(j>=n){j=0; }
               }
               for(i=0;i<m;i++)
               { 
                   for(j=0;j<n;j++)
                    {
                       row[j]=data[i][j];
                    }
                   model.addRow(row);
               }
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
                sp.setLocation(30, 100);
                sp.setSize(new Dimension(920, 350));
                f.add(sp);
        }catch(Exception f){System.out.println(f);}
    }
     });
     cAn.addItemListener(new ItemListener(){  
        public void itemStateChanged(ItemEvent ie){
            cPlan.removeAll();
             try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cSpec.getSelectedItem()+".accdb");//Establishing Connection
             }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
           DatabaseMetaData md;
               try {
                   md = con.getMetaData();
                    ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
                if(!(rs.getString(3).substring(0,1).equals("O"))&& !(rs.getString(3).substring(0,1).equals("M")))
                {  
                     if(rs.getString(3).substring(24,25).equals(cAn.getSelectedItem()))
                {
                    cPlan.add(rs.getString(3).substring(26));
                }
                }
            }
               } catch (SQLException ex) {
                   Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
               } 
        }  
    });
     b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
            final JOptionPane pane;
            final JDialog dia;
         String[] s2;int d,d2,min=2017,max=0,ok=0;
        try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cSpec.getSelectedItem()+".accdb");//Establishing Connection
             }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
     DatabaseMetaData md = con.getMetaData();
     ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
                     s2=rs.getString(3).split(" ");
               if(s2[0].equals("Plan") && s2.length==6)
                    {  if(s2[4].equals(cAn.getSelectedItem()))
                       {String[] s3=s2[5].split("-");
                       if(min>Integer.parseInt(s3[0]))min=Integer.parseInt(s3[0]);
                       if(max<Integer.parseInt(s3[1]))max=Integer.parseInt(s3[1]);
                       }
                    }
                }
        }catch ( Exception ex ){System.out.println(f);}  
        s2=antxt.getText().split("-");
        if(antxt.getText().equals("")){Erori er=new Erori("Completati anul universitar!",0);ok=1;}
        else if((antxt.getText().matches("[A-z]+"))){Erori er=new Erori("An universitar completat gresit!(yyyy-yyyy)",0);ok=1;}
        else if(s2.length!=2){Erori er=new Erori("An universitar completat gresit!(yyyy-yyyy)",0);ok=1;}
        else try {d=Integer.parseInt(s2[0]);
        d2=Integer.parseInt(s2[1]);
        if(min<=d && max>d){Erori er=new Erori("Anul universitar "+d+"-"+d2+" deja exista!",0);ok=1;}
        if((d2-d)!=1){Erori er=new Erori("An universitar completat gresit!(yyyy-yyyy)",0);ok=1;}
        }
 catch(NumberFormatException ex){Erori er=new Erori("An universitar completat gresit!(yyyy-yyyy)",0);ok=1;}
       if(ok==0)
       { try{  
       Object[][] Data = new Object[tabel.getRowCount()][tabel.getColumnCount()];
       for(int i=0;i<tabel.getRowCount();i++)
       {
           for(int j=0;j<tabel.getColumnCount();j++)
           {
              Data[i][j]=tabel.getValueAt(i, j);
           }
       }
      Statement s=con.createStatement();
      s.execute("CREATE TABLE [Plan de invatamant Anul "+cAn.getSelectedItem()+" "+antxt.getText()+"] (NrCrt INTEGER PRIMARY KEY, Disciplina CHAR(255), Semestru INTEGER null, C INTEGER null,S INTEGER null,L INTEGER null,P INTEGER null,CR INTEGER null, Curs INTEGER null, Aplicatii INTEGER null, Total_Ore INTEGER null)");
      pstmt=con.prepareStatement("INSERT INTO [Plan de invatamant Anul "+cAn.getSelectedItem()+" "+antxt.getText()+"] VALUES ( ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?)");
      for(int i=0;i<tabel.getRowCount();i++)
     {
              pstmt.setInt(1,i+1);
              pstmt.setString(2,Data[i][1].toString());
              if(Data[i][2]==null)
              pstmt.setNull(3, Types.NULL);
              else pstmt.setInt(3,Integer.valueOf(Data[i][2].toString()));
              for(int j=3;j<=11;j++)
              {
                  if(Data[i][j-1]==null)
              pstmt.setNull(j, Types.NULL);
              else pstmt.setInt(j,Integer.valueOf(Data[i][j-1].toString()));
              }
              pstmt.executeUpdate();
     }
       }catch ( SQLException f ){System.out.println(f);}  
       Erori er=new Erori("Adaugare reusita!",1);
       }
    }
     });
          b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){  
             b6.setEnabled(true);
        try {
             l=new Optionale(cAn.getSelectedItem(),cPlan.getSelectedItem(),cSpec.getSelectedItem(),data);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
     });
          b4.addActionListener(new ActionListener(){ 
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
        sp.setLocation(30, 100);
        sp.setSize(new Dimension(920, 350));
        f.add(sp);
        }
     });
           b5.addActionListener(new ActionListener(){ 
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
        sp.setLocation(30, 100);
        sp.setSize(new Dimension(920, 350));
        f.add(sp);                        
        }
     });    
        b6.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        model = new DefaultTableModel();
            tabel  = new JTable(model);
            try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
            con= DriverManager.getConnection("jdbc:ucanaccess://"+cSpec.getSelectedItem()+".accdb");//Establishing Connection
            pstmt=con.prepareStatement("SELECT COUNT(*) FROM [Plan de invatamant Anul "+cAn.getSelectedItem()+" "+cPlan.getSelectedItem()+"]");
            rs= pstmt.executeQuery();
            for (int i = 1; i <= n; i++) 
                model.addColumn(rsmd.getColumnLabel(i));
            }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
                Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
            }
            Object[] row=new Object[n];
            data=l.getData();
            for(int i=0;i<m;i++)
            {
                for(int j=0;j<n;j++)
                {
                    row[j]=data[i][j];
                }
                model.addRow(row);
            }
            int i=0;
            for (int width : columnsWidth) {
                TableColumn column = tabel.getColumnModel().getColumn(i++);
                column.setMinWidth(width);
                column.setMaxWidth(width);
                column.setPreferredWidth(width);
            }
            tabel.setFont(new Font("Verdana",Font.PLAIN,15));
            tabel.setRowHeight(20);
            DefaultTableCellRenderer centerRenderer = (DefaultTableCellRenderer)tabel.getDefaultRenderer(Object.class);
            centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
            f.remove(sp);
            f.remove(tabel);
            f.revalidate();
            f.repaint();
            sp=new JScrollPane(tabel);
            sp.setLocation(30, 100);
            sp.setSize(new Dimension(920, 350));
            f.add(sp);
      }
     });     
        b7.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
            ModificaOptionale opt=new ModificaOptionale(cSpec.getSelectedItem());
        }
     });    
     }
}

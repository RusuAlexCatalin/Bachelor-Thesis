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
public class EchivFostStudent extends Frame {
       JFrame f;
       int an = Calendar.getInstance().get(Calendar.YEAR),ann;
       int luna=Calendar.getInstance().get(Calendar.MONTH);
       private Connection con;
       private PreparedStatement pstmt;
       private Statement stmt;
       private ResultSet rs;
       private ResultSetMetaData rsmd;
       String data[][],Data2[][]; 
       JScrollPane sp=new JScrollPane();
       JTable tabel = new JTable();
    EchivFostStudent() throws ClassNotFoundException{
        f=new JFrame("Echivalare fost student UOC");
        if(luna>=10) an++;
     Label lan=new Label("Perioada in care studentul a studiat");
     Label lann=new Label("Anul in care acesta isi va continua studiile");
     Label lspec=new Label("Specializare studiata");
     Label lnume=new Label("Nume");
     Label lprenume=new Label("Prenume");
     Label linit=new Label("Initiala tatalui");
     Label lspec2=new Label("Specializare echivalata");
     TextField txtnume=new TextField();
     TextField txtprenume=new TextField();
     TextField txtinit=new TextField();
     Choice cspec2=new Choice();
     Button b1=new Button("Ok");
     Button b2=new Button("Echivaleaza student");
     Button b3=new Button("Memoreaza datele");
     Choice can=new Choice();
     Choice cann=new Choice();
     Choice cspec=new Choice();
     cspec.setBounds(400,30,150,20);
     can.setBounds(400,70,150,20);
     cann.setBounds(400,110,150,20);
     b1.setBounds(50,150,150,25);
     b2.setBounds(350,510,200,30);
     b3.setBounds(600,510,200,30);
     lspec.setBounds(50,30,200,20);
     lan.setBounds(50,70,300,20);
     lann.setBounds(50,110,350,20);
     lnume.setBounds(600,30,150,20);
     lprenume.setBounds(600,70,150,20);
     linit.setBounds(600,110,150,20);
     lspec2.setBounds(600,150,200,20);
     cspec2.setBounds(800,150,150,20);
     txtnume.setBounds(800,30,150,25);
     txtprenume.setBounds(800,70,150,25);
     txtinit.setBounds(800,110,150,25);
     cspec2.add("");
     cspec2.add("Informatica");
     cspec2.add("ComputerScience");
     cspec2.add("Mate-Info");
     cspec2.add("Matematica");
     cspec.add("");
     cspec.add("Informatica");
     cspec.add("ComputerScience");
     cspec.add("Mate-Info");
     cspec.add("Matematica");
     cann.add("");
     cann.add("1");
     cann.add("2");
     cann.add("3");
     lan.setFont(new Font("Verdana",Font.PLAIN,18));
     lann.setFont(new Font("Verdana",Font.PLAIN,18));
     lspec.setFont(new Font("Verdana",Font.PLAIN,18));
     cann.setFont(new Font("Verdana",Font.PLAIN,18));
     cspec.setFont(new Font("Verdana",Font.PLAIN,18));
     cspec2.setFont(new Font("Verdana",Font.PLAIN,18));
     can.setFont(new Font("Verdana",Font.PLAIN,18));
     b1.setFont(new Font("Verdana",Font.PLAIN,18));
     lnume.setFont(new Font("Verdana",Font.PLAIN,18));
     lprenume.setFont(new Font("Verdana",Font.PLAIN,18));
     lspec2.setFont(new Font("Verdana",Font.PLAIN,18));
     linit.setFont(new Font("Verdana",Font.PLAIN,18));
     txtnume.setFont(new Font("Verdana",Font.PLAIN,18));
     txtprenume.setFont(new Font("Verdana",Font.PLAIN,18));
     txtinit.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setFont(new Font("Verdana",Font.PLAIN,18));
     b3.setFont(new Font("Verdana",Font.PLAIN,18));
     ImageIcon icon = new ImageIcon("UOC_img.jpg");
     JLabel limg=new JLabel(icon);
     limg.setBounds(1181,455,100,100);
     b2.setEnabled(false);
     b3.setEnabled(false);
     f.add(can);
     f.add(lnume);
     f.add(lspec2);
     f.add(cspec2);
     f.add(lprenume);
     f.add(linit);
     f.add(txtnume);
     f.add(txtprenume);
     f.add(txtinit);
     f.add(lan);
     f.add(lspec);
     f.add(lann);
     f.add(b1);
     f.add(b2);
     f.add(b3);
     f.add(cspec);
     f.add(cann);
     f.add(limg);
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
     cspec.addItemListener(new ItemListener(){  
        public void itemStateChanged(ItemEvent ie){
             can.removeAll();
            can.add("");
            for(int i=2017;i<an;i++)
            {
                can.add(String.valueOf(i)+"-"+String.valueOf(i+1));
            }
        
        }
});
          b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){   
        int ok=0;
        if(cspec.getSelectedItem().equals("")){Erori er=new Erori("Alege o specializare!",0);ok=1;}
        else if(can.getSelectedItem().equals("")){Erori er=new Erori("Alege o perioada!",0);ok=1;}  
        else if(cann.getSelectedItem().equals("")){Erori er=new Erori("Alege un an!",0);ok=1;}
        if(ok==0){
        try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cspec.getSelectedItem()+".accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        b2.setEnabled(true);
        try{int s=Integer.parseInt(can.getSelectedItem().substring(0,4)); 
            int j=0,i,n=0,x=0;
        String col[]={"Disciplina","Nota"};
        for( i=1;i<=3;i++){
             pstmt=con.prepareStatement("SELECT COUNT(*) FROM [Plan de invatamant Anul "+i+" "+String.valueOf(s+i-1)+"-"+String.valueOf(s+i)+"]");
              rs= pstmt.executeQuery();
             rs.next();
             n=n+rs.getInt(1);
                   }
            int[] columnsWidth = {400,40};
            data=new String[n][2];
            for( i=1;i<=3;i++)
            { pstmt=con.prepareStatement("SELECT Disciplina FROM [Plan de invatamant Anul "+i+" "+String.valueOf(s+i-1)+"-"+String.valueOf(s+i)+"] ORDER BY Semestru ASC,Disciplina ASC");
             rs= pstmt.executeQuery();
               while(rs.next())
               {
                     data[x][j]=rs.getString(j+1);
                     j++;
                      data[x][j]="0";
                 x++;
                   j=0; 
               }
            }    
                 f.remove(sp);
           f.remove(tabel);
           f.revalidate(); 
           f.repaint();
                tabel=new JTable(data,col);
                i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
                tabel.setFont(new Font("Verdana",Font.PLAIN,15));
                tabel.setRowHeight(20);
                sp=new JScrollPane(tabel);
                sp.setLocation(50, 200);
                sp.setSize(new Dimension(460, 300));
                f.add(sp);     
                Data2 = new String[x][6];
                x=0;
                ann=an-Integer.parseInt(cann.getSelectedItem())+1;
                for( i=1;i<=3;i++){  
             pstmt=con.prepareStatement("SELECT Disciplina,Semestru,CR FROM [Plan de invatamant Anul "+i+" "+(ann-2+i)+"-"+(ann-1+i)+"] ORDER BY Semestru ASC,Disciplina ASC");
             rs= pstmt.executeQuery();
              while(rs.next())
               { 
                Data2[x][0]=String.valueOf(i);
                Data2[x][1]=rs.getString(1);
                Data2[x][2]=String.valueOf(rs.getInt(2));
                Data2[x][5]=String.valueOf(rs.getInt(3));
                x++;
               }
                }
        }catch(Exception f){System.out.println(f);} 
        }
    }
     });
           b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        int verif=0,num=0;
        if(txtnume.getText().equals("")){Erori er=new Erori("Completeaza numele!",0);verif=1;}
        else if((txtnume.getText().matches(".*\\d+.*"))){Erori er=new Erori("Nume completat gresit!",0);verif=1;}
        else if(txtprenume.getText().equals("")){Erori er=new Erori("Completeaza prenumele!",0);verif=1;} 
        else if((txtprenume.getText().matches(".*\\d+.*"))){Erori er=new Erori("Prenume completat gresit!",0);verif=1;}
        else if(txtinit.getText().equals("")){Erori er=new Erori("Completeaza initiala tatatlui!",0);verif=1;}
        else if((txtinit.getText().matches(".*\\d+.*"))){Erori er=new Erori("Initiala tatatlui completata gresit!",0);verif=1;}
        else if(txtinit.getText().length()!=1){Erori er=new Erori("Initiala tatalui completata gresit!",0);verif=1;}
        else if(cspec2.getSelectedItem().equals("")){Erori er=new Erori("Alege o specializare!",0);verif=1;}
        for(int i=0;i<data.length;i++)
        {
               try{
            num = Integer.parseInt(data[i][1]);
            if(num<0){Erori er=new Erori("Notele trebuie sa fie numere intregi!",0);verif=1;break;}
            if(0>num || 10<num){Erori er=new Erori("Nota trebuie sa fie intre 0 si 10!",0);verif=1;break;}
            } catch (NumberFormatException em) {Erori er=new Erori("Notele trebuie sa fie numere intregi!",0);verif=1;break;}
        }
        if(verif==0){
        int m=0;
        b3.setEnabled(true);
        try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cspec2.getSelectedItem()+".accdb");//Establishing Connection
               for(int i=1;i<=3;i++){
              pstmt=con.prepareStatement("SELECT COUNT(*) FROM [Materii echivalate Anul "+i+"]");
              rs= pstmt.executeQuery();
              rs.next();
             m=m+rs.getInt(1);
               }
        }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
    }
        Object[][] Data = new Object[tabel.getRowCount()][tabel.getColumnCount()];
       for(int i=0;i<tabel.getRowCount();i++)
       {
           for(int j=0;j<tabel.getColumnCount();j++)
           {
              Data[i][j]=tabel.getValueAt(i, j);
           }
       }
       int n=0;
       String s1,s2,s3;
       String[] opt1,opt2,opt3;
       try{ m=0;
               for(int i=1;i<=3;i++){
             pstmt=con.prepareStatement("SELECT * FROM [Materii echivalate Anul "+i+"] ORDER BY Semestru ASC,Disciplina_UOC ASC");
             rs= pstmt.executeQuery();
             rsmd=rs.getMetaData();
             n = rsmd.getColumnCount();
             while(rs.next())
             {int j,ok=1,ok1=0,ok2=0,p=0;
             for( j=0;j<Data.length;j++)
              {   if(!Data[j][0].equals("-"))
                {if(Data[j][0]!=null && Data[j][1]!=null)
                    if(Integer.parseInt(Data[j][1].toString())>4)
                    {     opt1=Data[j][0].toString().split(" ");
                     if(opt1[0].equals("Optional") || opt1[0].equals("Elective")) s1=Data[j][0].toString().substring(Data[j][0].toString().indexOf(" ")+3,Data[j][0].toString().length());
                     else s1=Data[j][0].toString();
                        for(int x1=3;x1<=n;x1++)
                       {if(rs.getString(x1)!=null)
                            { opt2=rs.getString(x1).split(" ");
                                if(opt2[0].equals("Optional") || opt2[0].equals("Elective"))
                                  s2=rs.getString(x1).substring(rs.getString(x1).indexOf(" ")+3,rs.getString(x1).length());
                                else s2=rs.getString(x1);
                                if(s1.equals(s2))
                                {
                                    ok1=1;
                                    break;
                                }
                            }
                       }
                            for(int x1=3;x1<=n;x1++)
                            {if(rs.getString(x1)!=null)
                                {opt2=rs.getString(x1).split(" ");
                                if(opt2[0].equals("Optional") || opt2[0].equals("Elective"))
                                 s2=rs.getString(x1).substring(rs.getString(x1).indexOf(" ")+3,rs.getString(x1).length());
                                else s2=rs.getString(x1);
                                    for(int x=0;x<Data2.length;x++)
                                    {opt3=Data2[x][1].split(" ");
                                    if(opt3[0].equals("Optional") || opt3[0].equals("Elective")) s3=Data2[x][1].substring(Data2[x][1].indexOf(" ")+3,Data2[x][1].length());
                                    else s3=Data2[x][1];
                                       if(s3.equals(s2) && Data2[x][3]==null)
                                        {
                                            ok2=1;
                                            p=x;
                                            break;
                                        } 
                                    }
                                }
                            }
                        if(ok1==1 && ok2==1)
                        {
                            Data2[p][3]=Data[j][0].toString();
                            Data2[p][4]=Data[j][1].toString();
                          Data[j][0]="-";
                          ok=0;
                          break;
                        }
                    }
                }
                if(ok==0) break;
              }
             if(ok==0)
             {   String[] s=Data2[m][1].split(" ");
                if(s[0].toLowerCase().equals("educatie")) 
                        if(s[1].toLowerCase().equals("fizica")) Data2[m][4]="ADM";
                   }
             else {Data2[m][4]="0";Data2[m][3]="";}
                 m++;
             }
            }
               }catch(Exception f){System.out.println(f);}
       int nr=0;
           for(int i=0;i<Data.length;i++)
           {
               if(!Data[i][0].equals("-") && Integer.parseInt(Data[i][1].toString())>4) nr++;
           }
           if(nr>0){
               MateriiRamase x=new MateriiRamase(Data,Data2,cspec2.getSelectedItem());
           String col[]={"An","Disciplina_UOC","Semestru","Disciplina_Echivalata","Nota","NrCredite"};
               int[] columnsWidth = {40,400,60,400,40,60};
           f.remove(sp);
           f.remove(tabel);
           f.revalidate(); 
           f.repaint();
                tabel=new JTable(x.getData(),col);
                int i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
               tabel.setFont(new Font("Verdana",Font.PLAIN,15));
               tabel.setRowHeight(20);
               sp=new JScrollPane(tabel);
               sp.setLocation(50, 200);
               sp.setSize(new Dimension(1000, 300));
               f.add(sp);
           }
           else
           {
               String col[]={"An","Disciplina_UOC","Semestru","Disciplina_Echivalata","Nota","NrCredite"};
               int[] columnsWidth = {40,400,60,400,40,60};
           f.remove(sp);
           f.remove(tabel);
           f.revalidate(); 
           f.repaint();
                tabel=new JTable(Data2,col);
                int i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
               tabel.setFont(new Font("Verdana",Font.PLAIN,15));
               tabel.setRowHeight(20);
               sp=new JScrollPane(tabel);
               sp.setLocation(50, 200);
               sp.setSize(new Dimension(1000, 300));
               f.add(sp);
           }
        }
    }
     });
            b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        int verif=0;
             try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        } 
              try{
                  String[] student;
                  String anCurent=(an-1)+"-"+an;
     DatabaseMetaData md = con.getMetaData();
     ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
               {    student=rs.getString(3).split(" ");
                if(txtnume.getText().equals(student[0]) && txtinit.getText().toUpperCase().equals(student[1]) && txtprenume.getText().equals(student[2]) && cspec2.getSelectedItem().equals(student[3]) && cann.getSelectedItem().equals(student[4]) && anCurent.equals(student[5]))
                {
                    Erori er=new Erori("Studentul exista deja!",0);verif=1;
                }
               }
             }
      }catch(Exception f){System.out.println(f);}
              if(verif==0){
    StareStudent s=new StareStudent(Data2,txtnume.getText(),txtprenume.getText(),txtinit.getText(),cspec2.getSelectedItem(),cann.getSelectedItem());
              }
    }
     });
    }
}

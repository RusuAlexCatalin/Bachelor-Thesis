
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class EchivStudentExtern extends Frame {
    JFrame f;
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private int n=0,nr=0;
    int an = Calendar.getInstance().get(Calendar.YEAR),ann;
    int luna=Calendar.getInstance().get(Calendar.MONTH);
    String[][] data,Data2; 
    JScrollPane sp=new JScrollPane();
    JTable tabel=new JTable();
    JTable tabel2 = new JTable();
    EchivStudentExtern(){
     f=new JFrame("Echivalare student extern");
     if(luna>=10) an++;
     Button b1=new Button("Confirma");
     Button b2=new Button("Adauga disciplina");
     Button b3=new Button("Echivaleaza student");
     Button b4=new Button("Memoreaza datele");
     Label lnume=new Label("Nume");
     Label lprenume=new Label("Prenume");
     Label linit=new Label("Initiala tatalui");
     Label ldisc=new Label("Disciplina");
     Label lnota=new Label("Nota obtinuta");
     Label lspec=new Label("Specializarea la care urmeaza sa fie inscris");
     Label lan=new Label("Anul in care se inscrie studentul:");
     TextField txtnume=new TextField();
     TextField txtprenume=new TextField();
     TextField txtdisc=new TextField();
     TextField txtnota=new TextField();
     TextField txtinit=new TextField();
     Choice cspec=new Choice();
     Choice can=new Choice();
     cspec.add("");
     cspec.add("Informatica");
     cspec.add("ComputerScience");
     cspec.add("Mate-Info");
     cspec.add("Matematica");
     can.add("");
     can.add("1");
     can.add("2");
     can.add("3");
     b1.setBounds(1050,80,170,30);
     b2.setBounds(110,160,170,30);
     b3.setBounds(110,200,170,30);
     b4.setBounds(110,250,170,30);
     cspec.setBounds(1000,30,150,20);
     lspec.setBounds(650,30,350,20);
     lan.setBounds(600,80,260,20);
     can.setBounds(870,80,150,20);
     lnume.setBounds(20,30,50,20);
     txtnume.setBounds(80,30,100,25);
     lprenume.setBounds(180,30,80,20);
     txtprenume.setBounds(270,30,150,25);
     linit.setBounds(430,30,120,20);
     txtinit.setBounds(550,30,100,25);
     ldisc.setBounds(20,30,100,20);
     txtdisc.setBounds(20,60,260,25);
     lnota.setBounds(20,95,100,20);
     txtnota.setBounds(20,115,260,25);
     JPanel panel=new JPanel();
     panel.setLayout(null);
     panel.setBounds(30,30,1250,120);
     TitledBorder title;
     title = BorderFactory.createTitledBorder("Date student");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,15));
     panel.setBorder(title);
     panel.setBackground(new Color(128, 191, 255));
     panel.add(lnume);
     panel.add(txtnume);
     panel.add(lprenume);
     panel.add(txtprenume);
     panel.add(linit);
     panel.add(txtinit);
     panel.add(b1);
     panel.add(cspec);
     panel.add(lspec);
     panel.add(lan);
     panel.add(can);
     JPanel panel2=new JPanel();
     panel2.setLayout(null);
     panel2.setBounds(30,200,300,300);
     title = BorderFactory.createTitledBorder("Discipline studiate");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,15));
     panel2.setBorder(title);
     panel2.setBackground(new Color(128, 191, 255));
     panel2.add(ldisc);
     panel2.add(txtdisc);
     panel2.add(lnota);
     panel2.add(txtnota);
     panel2.add(b2);
     panel2.add(b3);
     panel2.add(b4);
     lnume.setFont(new Font("Verdana",Font.PLAIN,18));
     lprenume.setFont(new Font("Verdana",Font.PLAIN,18));
     txtnume.setFont(new Font("Verdana",Font.PLAIN,18));
     txtprenume.setFont(new Font("Verdana",Font.PLAIN,18));
     linit.setFont(new Font("Verdana",Font.PLAIN,18));
     txtinit.setFont(new Font("Verdana",Font.PLAIN,18));
     lspec.setFont(new Font("Verdana",Font.PLAIN,18));
     cspec.setFont(new Font("Verdana",Font.PLAIN,18));
     lan.setFont(new Font("Verdana",Font.PLAIN,18));
     can.setFont(new Font("Verdana",Font.PLAIN,18));
     b1.setFont(new Font("Verdana",Font.PLAIN,18));
     ldisc.setFont(new Font("Verdana",Font.PLAIN,18));
     txtdisc.setFont(new Font("Verdana",Font.PLAIN,18));
     lnota.setFont(new Font("Verdana",Font.PLAIN,18));
     txtnota.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setFont(new Font("Verdana",Font.PLAIN,18));
     b3.setFont(new Font("Verdana",Font.PLAIN,18));
     b4.setFont(new Font("Verdana",Font.PLAIN,18));
     txtdisc.setEnabled(false);
     txtnota.setEnabled(false);
     b2.setEnabled(false);
     b3.setEnabled(false);
     b4.setEnabled(false);
     f.add(panel);
     f.add(panel2);
     f.setBounds(300,200,1300,600);
     f.setLayout(null);
     f.getContentPane().setBackground(new Color(128, 191, 255));
     f.setVisible(true);
     data=new String[n][2];
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
                int verif=0;
        if(txtnume.getText().equals("")){Erori er=new Erori("Completeaza numele!",0);verif=1;}
        else if((txtnume.getText().matches(".*\\d+.*"))){Erori er=new Erori("Nume completat gresit!",0);verif=1;}
        else if(txtprenume.getText().equals("")){Erori er=new Erori("Completeaza prenumele!",0);verif=1;} 
        else if((txtprenume.getText().matches(".*\\d+.*"))){Erori er=new Erori("Prenume completat gresit!",0);verif=1;}
        else if(txtinit.getText().equals("")){Erori er=new Erori("Completeaza initiala tatatlui!",0);verif=1;}
        else if((txtinit.getText().matches(".*\\d+.*"))){Erori er=new Erori("Initiala tatalui completata gresit!",0);verif=1;}
        else if(txtinit.getText().length()!=1){Erori er=new Erori("Initiala tatalui completata gresit!",0);verif=1;}
        else if(cspec.getSelectedItem().equals("")){Erori er=new Erori("Alege o specializare!",0);verif=1;}
        else if(can.getSelectedItem().equals("")){Erori er=new Erori("Alege un an!",0);verif=1;}
        if(verif==0){
        n=0;
                 try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cspec.getSelectedItem()+".accdb");
        for(int i=1;i<=3;i++){
      pstmt=con.prepareStatement("SELECT COUNT(*) FROM [Materii echivalate Anul "+i+"]");
         rs= pstmt.executeQuery();
         rs.next();
         n=n+rs.getInt(1);
         data=new String[n][2];
           }
         }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
         }
                 nr=0;
                 b2.setEnabled(true);
                 txtdisc.setEnabled(true);
                 txtnota.setEnabled(true);
        }
    }
     });
                b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        int verif=0,d,num=0;
        if(txtdisc.getText().equals("")){Erori er=new Erori("Completeaza disciplina!",0);verif=1;}
        else if(txtnota.getText().equals("")){Erori er=new Erori("Completeaza nota!",0);verif=1;} 
        else try {d=Integer.parseInt(txtnota.getText());
        if(5>d || 10<d){Erori er=new Erori("Nota trebuie sa fie intre 5 si 10!",0);verif=1;}
        }
 catch(NumberFormatException ex){Erori er=new Erori("Nota completata gresit!",0);verif=1;}
        if(verif==0){
                 data[nr][0]=txtdisc.getText();
                 data[nr][1]=txtnota.getText();
                 nr++;
        String col[]={"Disciplina","Nota"};
      int[] columnsWidth = {400,40};
        f.remove(sp);
        f.remove(tabel);
        f.revalidate(); 
        f.repaint();
                tabel=new JTable(data,col);
                int i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
                tabel.setFont(new Font("Verdana",Font.PLAIN,15));
                tabel.setRowHeight(20);
                tabel.setEnabled(false);
                sp=new JScrollPane(tabel);
                sp.setLocation(350, 200);
                sp.setSize(new Dimension(460, 300));
                f.add(sp);
                }
        b3.setEnabled(true);
        b4.setEnabled(true);
    }
     }); 
                b4.addActionListener(new ActionListener(){ 
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
                if(txtnume.getText().equals(student[0]) && txtinit.getText().toUpperCase().equals(student[1]) && txtprenume.getText().equals(student[2]) && cspec.getSelectedItem().equals(student[3]) && can.getSelectedItem().equals(student[4]) && anCurent.equals(student[5]))
                {
                    Erori er=new Erori("Studentul exista deja!",0);verif=1;
                }
               }
             }
      }catch(Exception f){System.out.println(f);}
              if(verif==0){
    StareStudent s=new StareStudent(Data2,txtnume.getText(),txtprenume.getText(),txtinit.getText(),cspec.getSelectedItem(),can.getSelectedItem());
              }
    }
     });
               b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        int m=0,k=1;
     for(int i=0;i<tabel.getRowCount();i++)
     {
         if(tabel.getValueAt(i, 0)!=null)m++;
     }
     Object[][] Data = new Object[m][tabel.getColumnCount()];
         Data2 = new String[tabel.getRowCount()][6];
        for(int i=0;i<tabel.getRowCount();i++)
       {
           for(int j=0;j<tabel.getColumnCount();j++)
           {if(tabel.getValueAt(i, j)!=null)
            {Data[i][j]=tabel.getValueAt(i, j);
            }
           }
       } 
       try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cspec.getSelectedItem()+".accdb");
            }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
                    Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
             try{
               int x=0;
                ann=an-Integer.parseInt(can.getSelectedItem())+1;
                for(int i=1;i<=3;i++){
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
              try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://"+cspec.getSelectedItem()+".accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ez) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ez);
            }
     String s1,s2,s3;
     String[] opt1,opt2,opt3;
     try{m=0;int a=0;
           for(int i=1;i<=3;i++){
             pstmt=con.prepareStatement("SELECT * FROM [Materii echivalate Anul "+i+"] ORDER BY Semestru ASC,Disciplina_UOC ASC");
             rs= pstmt.executeQuery();
             rsmd=rs.getMetaData();
             a = rsmd.getColumnCount();
             while(rs.next())
             {int j,ok=1,ok1=0,ok2=0,p=0;
             for( j=0;j<Data.length;j++)
              {   if(!Data[j][0].equals("-"))
                {if(Data[j][0]!=null && Data[j][1]!=null)
                    if(Integer.parseInt(Data[j][1].toString())>4)
                    {     opt1=Data[j][0].toString().split(" ");
                     if(opt1[0].equals("Optional")) s1=Data[j][0].toString().substring(Data[j][0].toString().indexOf(" ")+3,Data[j][0].toString().length());
                     else s1=Data[j][0].toString();
                        for(int x1=3;x1<=a;x1++)
                       {if(rs.getString(x1)!=null)
                            { opt2=rs.getString(x1).split(" ");
                                if(opt2[0].equals("Optional"))
                                    s2=rs.getString(x1).substring(rs.getString(x1).indexOf(" ")+3,rs.getString(x1).length());
                                else s2=rs.getString(x1);
                                if(s1.equals(s2))
                                {
                                    ok1=1;
                                    break;
                                }
                            }
                       }
                            for(int x1=3;x1<=a;x1++)
                            {if(rs.getString(x1)!=null)
                                {opt2=rs.getString(x1).split(" ");
                                if(opt2[0].equals("Optional"))
                                   s2=rs.getString(x1).substring(rs.getString(x1).indexOf(" ")+3,rs.getString(x1).length());
                                else s2=rs.getString(x1);
                                    for(int x=0;x<Data2.length;x++)
                                    {opt3=Data2[x][1].split(" ");
                                    if(opt3[0].equals("Optional")) s3=Data2[x][1].substring(Data2[x][1].indexOf(" ")+3,Data2[x][1].length());
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
             {String s=Data2[m][1].substring(0,Data2[m][1].lastIndexOf(" ")).toLowerCase();
                if(Data2[m][1].toLowerCase().equals("educatie fizica"))Data2[m][4]="ADM";
                if(s.equals("educatie fizica"))Data2[m][4]="ADM";
               }
             else {Data2[m][4]="0";Data2[m][3]="";}
              m++;
             }                     
       }
           }catch(Exception f){System.out.println(f);}
     int b=0;
     for(int i=0;i<Data.length;i++)
           {
               if(!Data[i][0].equals("-") && Integer.parseInt(Data[i][1].toString())>4) b++;
           }
         if(b>0){
               MateriiRamase x=new MateriiRamase(Data,Data2,cspec.getSelectedItem());
           String col[]={"An","Disciplina_UOC","Semestru","Disciplina_Echivalata","Nota","NrCredite"};
               int[] columnsWidth = {40,330,60,330,40,60};
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
               sp.setLocation(350, 200);
               sp.setSize(new Dimension(880, 300));
               f.add(sp);
           }
           else
           {
               String col[]={"An","Disciplina_UOC","Semestru","Disciplina_Echivalata","Nota","NrCredite"};
               int[] columnsWidth = {40,330,60,330,40,60};
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
               sp.setLocation(350, 200);
               sp.setSize(new Dimension(880, 300));
               f.add(sp);
           }
     nr=0;
     data=new String[n][2];
    }
     });
    }
    }
    

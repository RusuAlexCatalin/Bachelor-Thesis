/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echivuniv;
import java.awt.*;
import java.awt.event.*;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
public class Document extends Frame {
    JFrame f;
    String nume,prenume,init,spec,univ,res="",echiv="";
    int nr;
    String data[][];
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private DatabaseMetaData md;
    private ResultSetMetaData rsmd;
    private int ok;
    JScrollPane sp=new JScrollPane();
    DefaultTableModel model = new DefaultTableModel();
    JTable tabel  = new JTable(model);
    int[] columnsWidth = {170,250 , 80,90};
    Document(int ok) throws ClassNotFoundException
    {this.ok=ok;
     f=new JFrame("Vizualizare documente");
     try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");//Establishing Connection
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
     JPanel panel=new JPanel();
     panel.setLayout(null);
     panel.setBounds(30,30,1200,100);
     panel.setBackground(new Color(128, 191, 255));
     TitledBorder title;
     title = BorderFactory.createTitledBorder("Filtrare dupa numele studentilor");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,18));
     panel.setBorder(title);
     JPanel panel2=new JPanel();
     panel2.setLayout(null);
     panel2.setBounds(30,150,1100,350);
     panel2.setBackground(new Color(128, 191, 255));
     title = BorderFactory.createTitledBorder("Filtrare dupa specializare");
     title.setTitleFont(new Font("Verdana",Font.PLAIN,18));
     panel2.setBorder(title);
     Label lst=new Label("Nume student:");
     Label lspec=new Label("Specializare:");
     Button b1=new Button("Vizualizare document");
     Button b2=new Button("Confirma");
     Button b3=new Button("Vizualizeaza document");
     Button bsterge1=new Button("Sterge student");
     Button bsterge2=new Button("Sterge student");
     b1.setBounds(470,45,200,30);
     b2.setBounds(450,30,120,30);
     b3.setBounds(650,150,200,30);
     bsterge1.setBounds(700,45,150,30);
     bsterge2.setBounds(650,200,200,30);
     Choice c=new Choice();
     Choice c2=new Choice();
     c.setBounds(150,45,300,20);
     c2.setBounds(140,30,300,20);
     lst.setBounds(30,50,120,20);
     lspec.setBounds(30,30,110,20);
     c.add("");
     try{
     md = con.getMetaData();
     ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
                   c.add(rs.getString(3));
                }
      }catch(Exception f){System.out.println(f);}
     c2.add("");
     c2.add("Informatica");
     c2.add("Matematica");
     c2.add("Mate-info");
     c2.add("Computer Science");
     panel.add(lst);
     panel.add(c);
     panel.add(b1);
     panel.add(bsterge1);
     panel2.add(lspec);
     panel2.add(c2);
     panel2.add(b2);
     panel2.add(b3);
     panel2.add(bsterge2);
     b1.setFont(new Font("Verdana",Font.PLAIN,18));
     b2.setFont(new Font("Verdana",Font.PLAIN,18));
     b3.setFont(new Font("Verdana",Font.PLAIN,18));
     bsterge1.setFont(new Font("Verdana",Font.PLAIN,18));
     bsterge2.setFont(new Font("Verdana",Font.PLAIN,18));
     lst.setFont(new Font("Verdana",Font.PLAIN,18));
     lspec.setFont(new Font("Verdana",Font.PLAIN,18));
     c.setFont(new Font("Verdana",Font.PLAIN,18));
     c2.setFont(new Font("Verdana",Font.PLAIN,18));
     ImageIcon icon = new ImageIcon("UOC_img.jpg");
     JLabel limg=new JLabel(icon);
     limg.setBounds(1181,455,100,100);
     b3.setVisible(false);
     bsterge2.setVisible(false);
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
               if(ok==1)
               {PaginaSecretara p=new PaginaSecretara();}
               else if(ok==2) {PaginaProfesor p1=new PaginaProfesor();}
          }
        }
      });
      b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        if(c.getSelectedItem().equals("")){Erori er=new Erori("Alege un student!",0);} 
       else VizualizareDocument(c.getSelectedItem());
    }
     });
       b2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        if(c2.getSelectedItem().equals("")){Erori er=new Erori("Alege o specializare!",0);}
        else{
        b3.setVisible(true);
        bsterge2.setVisible(true);
        model = new DefaultTableModel();
        tabel  = new JTable(model);
        String[] s;
        int nr=0;
            try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
      try{
        DefaultTableCellRenderer centerRenderer = (DefaultTableCellRenderer)tabel.getDefaultRenderer(Object.class);
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
     md = con.getMetaData();
     rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
                  nr++;
                }
     data=new String[nr][4];
     int i=0;
     rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
               { s=rs.getString(3).split(" ");
               for(int j=0;j<4;j++)
                   if(j==0)data[i][j]=s[0]+" "+s[1]+" "+s[2];
                   else data[i][j]=s[j+2];
               i++;
               }
                }
        model.addColumn("Nume");
        model.addColumn("Specilizare");
        model.addColumn("An");
        model.addColumn("An Universitar");
        Object[] row=new Object[4];
        for(i=0;i<data.length;i++)
        {        
            if(data[i][1].equals(c2.getSelectedItem()))
            {  for(int j=0;j<4;j++)
              { 
                  row[j]=data[i][j];
              }
            model.addRow(row);
            }
        }
         i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
                tabel.setFont(new Font("Verdana",Font.PLAIN,15));
                tabel.setRowHeight(20);
                panel2.remove(sp);
                panel2.remove(tabel);
                panel2.revalidate(); 
                panel2.repaint();
                sp=new JScrollPane(tabel);
                sp.setLocation(50, 100);
                sp.setSize(new Dimension(590, 200));
                panel2.add(sp);
      }catch(Exception f){System.out.println(f);}
        }
    }
     });
             b3.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        if(tabel.getSelectedRow()==(-1)){Erori er=new Erori("Alege un student!",0);}
        else{ String s="";
       for(int j=0;j<4;j++)
           s=s+data[tabel.getSelectedRow()][j]+" ";
       s=s.substring(0,s.length()-1);
       VizualizareDocument(s);
        }
    }
     });
             bsterge1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
        if(c.getSelectedItem().equals("")){Erori er=new Erori("Alege un student!",0);} 
        else{String s=c.getSelectedItem();
        c.removeAll();
       Sterge(s);
       c.add("");
     try{
     md = con.getMetaData();
     ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
                   c.add(rs.getString(3));
                }
      Erori er=new Erori("Studentul a fost sters!",1);
      }catch(Exception f){System.out.println(f);}
        }
    }
     });
             bsterge2.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){
         if(tabel.getSelectedRow()==(-1)){Erori er=new Erori("Alege un student!",0);}
         else{String s1="";
       for(int j=0;j<4;j++)
           s1=s1+data[tabel.getSelectedRow()][j]+" ";
       s1=s1.substring(0,s1.length()-1);
       Sterge(s1);
        String[] s;
        int nr=0;
            try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
      try{
         model = new DefaultTableModel();
         tabel  = new JTable(model);
        DefaultTableCellRenderer centerRenderer = (DefaultTableCellRenderer)tabel.getDefaultRenderer(Object.class);
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
     md = con.getMetaData();
     rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
                  nr++;
                }
     data=new String[nr][4];
     int i=0;
     rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
               { s=rs.getString(3).split(" ");
               for(int j=0;j<4;j++)
                   if(j==0)data[i][j]=s[0]+" "+s[1]+" "+s[2];
                   else data[i][j]=s[j+2];
               i++;
               }
                }
        model.addColumn("Nume");
        model.addColumn("Specilizare");
        model.addColumn("An");
        model.addColumn("An Universitar");
        Object[] row=new Object[4];
        for(i=0;i<data.length;i++)
        {        
            if(data[i][1].equals(c2.getSelectedItem()))
            {  for(int j=0;j<4;j++)
              { 
                  row[j]=data[i][j];
              }
            model.addRow(row);
            }
        }
         i=0;
                for (int width : columnsWidth) {
            TableColumn column = tabel.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
                panel2.remove(sp);
                panel2.remove(tabel);
                panel2.revalidate(); 
                panel2.repaint();
                sp=new JScrollPane(tabel);
                sp.setLocation(50, 100);
                sp.setSize(new Dimension(590, 200));
                panel2.add(sp);
      }catch(Exception f){System.out.println(f);}
        c.removeAll();
       c.add("");
     try{
     md = con.getMetaData();
     ResultSet rs = md.getTables(null, null, "%", null);
                while (rs.next()) {
               if(!rs.getString(3).equals("DateLogare"))
                   c.add(rs.getString(3));
                }
       Erori er=new Erori("Studentul a fost sters!",1);
      }catch(Exception f){System.out.println(f);}
       }
    }
     });
    }
    private static void setRun (XWPFRun run , String fontFamily , int fontSize , String text , boolean bold ) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setText(text);
        run.setBold(bold);
    }
    private void VizualizareDocument(String s1)
    {
        String s=s1;
        String[] nume=s.split(" ");
         try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
            con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");
         }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
        XWPFDocument document = new XWPFDocument();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setTop(BigInteger.valueOf(1440*39/100));
        pageMar.setBottom(BigInteger.valueOf(1440*79/100));
        CTDocument1 doc = document.getDocument();
        CTBody body = doc.addNewBody();
        body.addNewSectPr();
        CTSectPr section = body.getSectPr();
        if(!section.isSetPgSz()) {
            section.addNewPgSz();
        }
        CTPageSz pageSize = section.getPgSz();
        pageSize.setOrient(STPageOrientation.LANDSCAPE);
        pageSize.setW(BigInteger.valueOf(842 * 20));
        pageSize.setH(BigInteger.valueOf(595 * 20));
        
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
        XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
  if (headerFooterPolicy == null) headerFooterPolicy = document.createHeaderFooterPolicy();
        XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
        XWPFTable table3 = footer.createTable(1,4);
        for(int i=0;i<4;i++)
         {XWPFTableRow row = table3.getRow(0);
             CTTblWidth tbl3Width = table3.getRow(0).getCell(i).getCTTc().addNewTcPr().addNewTcW();
         if(i==0){tbl3Width.setW(BigInteger.valueOf(1440*345/100));row.getCell(i).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(i).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.LEFT); setRun(paragraph7.createRun() , "Times New Roman" , 12, nume[0].toUpperCase()+" "+nume[1].toUpperCase()+". "+nume[2].toUpperCase(), true);}
         if(i==1){tbl3Width.setW(BigInteger.valueOf(1440*184/100));row.getCell(i).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(i).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 12, "FESS" , true);}
         if(i==2){tbl3Width.setW(BigInteger.valueOf(1440*229/100));row.getCell(i).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(i).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 12, String.valueOf(nume[5]) , true);}
         if(i==3){tbl3Width.setW(BigInteger.valueOf(1440*229/100));row.getCell(i).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(i).addParagraph();
             paragraph7.setAlignment(ParagraphAlignment.RIGHT);XWPFRun run = paragraph7.createRun();
            run = paragraph7.createRun();  
            run.setBold(true);
            run.setFontFamily("Times New Roman");
            run.setFontSize(12);
            run.getCTR().addNewFldChar().setFldCharType(org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType.BEGIN);
            run = paragraph7.createRun(); 
            run.setBold(true);
            run.setFontFamily("Times New Roman");
            run.setFontSize(12);
            run.getCTR().addNewInstrText().setStringValue("PAGE \\* MERGEFORMAT");
            run = paragraph7.createRun();  
            run.setBold(true);
            run.setFontFamily("Times New Roman");
            run.setFontSize(12);
            run.getCTR().addNewFldChar().setFldCharType(org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType.END);
         }
         tbl3Width.setType(STTblWidth.DXA);
         }
        table3.setBottomBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table3.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table3.setInsideVBorder(XWPFTable.XWPFBorderType.NONE,WIDTH, 0, spec);
        table3.setRightBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table3.setLeftBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table3.setTopBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        CTTblWidth tableIndentation = table3.getCTTbl().getTblPr().addNewTblInd();
        tableIndentation.setW(BigInteger.valueOf(-1440*30/100));
        XWPFTable table2 = document.createTable(1,3);
        table2.setBottomBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table2.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table2.setInsideVBorder(XWPFTable.XWPFBorderType.NONE,WIDTH, 0, spec);
        table2.setRightBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table2.setLeftBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        table2.setTopBorder(XWPFTable.XWPFBorderType.NONE, WIDTH, 0, spec);
        XWPFParagraph paragraph =document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run.setText("Nume student: "+nume[0].toUpperCase()+" "+nume[1].toUpperCase()+". "+nume[2].toUpperCase());
        run.addBreak();
        run.addBreak();
        run.setText("Facultatea de Matematica si Informatica");
        run.addBreak();
        run.setText("Specializarea "+nume[3]);
        run.addBreak();
        if(nume[4].equals("1"))
            {run.setText("Anul I");
            run.addBreak();
            }
        else if(nume[4].equals("2"))
            {run.setText("Anul II");
            run.addBreak();
            }
        else 
            {run.setText("Anul III");
            run.addBreak();
            }
        run.setText("Anul universitar "+nume[5]);
        XWPFParagraph paragraph2 =document.createParagraph();
        paragraph2.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph2.createRun();
        run.setBold(true);
        run.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run.setText("FIŞA DE EVALUARE A SITUAŢIEI ŞCOLARE");
        XWPFParagraph paragraph3 =document.createParagraph();
        paragraph3.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph3.createRun();
        run.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run.setText("Aprobat Decan,");
        run.addBreak();
        String[] s2=nume[5].split("-");
        int an=Integer.parseInt(s2[0]);
         try{
            pstmt=con.prepareStatement("SELECT 'Grad Didactic',Nume,Prenume FROM DateLogare WHERE Decan=yes AND An_Universitar='"+nume[5]+"'");
              rs=pstmt.executeQuery();
              rs.next();
              run.setText(rs.getString(1)+" "+rs.getString(3)+" "+rs.getString(2).toUpperCase());
              run.addBreak();
              run.addBreak();
            }catch(Exception f){System.out.println(f);}
        run.setText("Vizat Comisia de Evaluare,");
        run.addBreak();
        run.addBreak();
        try{int y=0,i=0;
            pstmt=con.prepareStatement("SELECT Grad_Didactic,Nume,Prenume FROM DateLogare WHERE Decan=no AND An_Universitar='"+nume[5]+"' ORDER BY Grad_Didactic DESC,Nume ASC");
              rs= pstmt.executeQuery();
             while(rs.next())
             {
                y++;
             }
              pstmt=con.prepareStatement("SELECT Grad_Didactic,Nume,Prenume FROM DateLogare WHERE Decan=no AND An_Universitar='"+nume[5]+"' ORDER BY Grad_Didactic DESC,Nume ASC");
              rs= pstmt.executeQuery();
             while(rs.next())
             {
                 run.setText(rs.getString(1)+" "+rs.getString(3)+" "+rs.getString(2).toUpperCase());
                 if(i+1!=y)
                 {
                 run.addBreak();
                 }
                 i++;
             }
            }catch(Exception f){System.out.println(f);}       
        XWPFParagraph paragraph4 =document.createParagraph();
        paragraph4.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph4.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setFontFamily("Times New Roman");
        run.setText("PLAN DE ÎNVĂŢĂMÂNT ");
        run = paragraph4.createRun();
        run.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run.setText("DUPĂ CURRICULA EUROPEANĂ ");
         XWPFTableRow tableRow = table2.getRow(0);
         tableRow.getCell(0).setParagraph(paragraph);
         tableRow.getCell(1).setParagraph(paragraph2);
         tableRow.getCell(2).setParagraph(paragraph3);
         XWPFDocument doc3 = paragraph2.getDocument();
         int pPos2 = doc3.getPosOfParagraph(paragraph2);    
         doc3.removeBodyElement(pPos2);  
         pPos2 = doc3.getPosOfParagraph(paragraph);    
         doc3.removeBodyElement(pPos2);
         pPos2 = doc3.getPosOfParagraph(paragraph3);    
         doc3.removeBodyElement(pPos2);
         tableRow.setHeight(1440*124/100);
         for(int i=0;i<3;i++)
         { CTTblWidth tbl2Width = table2.getRow(0).getCell(i).getCTTc().addNewTcPr().addNewTcW();
         if(i==0)tbl2Width.setW(BigInteger.valueOf(1440*382/100));
         if(i==1)tbl2Width.setW(BigInteger.valueOf(1440*303/100));
         if(i==2)tbl2Width.setW(BigInteger.valueOf(1440*313/100));
         tbl2Width.setType(STTblWidth.DXA);
         }
   for (int k=1;k<=3;k++)
       {
           try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
            con= DriverManager.getConnection("jdbc:ucanaccess://"+nume[3]+".accdb");
         }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            pstmt=con.prepareStatement("SELECT COUNT(*) FROM [Plan de invatamant Anul "+k+" "+(an-Integer.parseInt(nume[4])+k)+"-"+(an-Integer.parseInt(nume[4])+k+1)+"]");
              rs= pstmt.executeQuery();
              rs.next();
            nr=rs.getInt(1);
            }catch(Exception f){System.out.println(f);}
        XWPFParagraph paragraph5 =document.createParagraph();
        paragraph5.setSpacingAfter(0);
        paragraph5.setSpacingAfterLines(0);
        paragraph5.setSpacingBefore(0);
        paragraph5.setSpacingBeforeLines(0);
        paragraph5.setSpacingLineRule(LineSpacingRule.EXACT);
        paragraph5.setVerticalAlignment(TextAlignment.TOP);
        run = paragraph5.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setFontFamily("Times New Roman");
        if(k==1)run.setText("Anul I "+String.valueOf((an-Integer.parseInt(nume[4])+k))+"-"+String.valueOf((an-Integer.parseInt(nume[4])+k+1)));
        if(k==2)run.setText("Anul II "+String.valueOf((an-Integer.parseInt(nume[4])+k))+"-"+String.valueOf((an-Integer.parseInt(nume[4])+k+1)));
        if(k==3)run.setText("Anul III "+String.valueOf((an-Integer.parseInt(nume[4])+k))+"-"+String.valueOf((an-Integer.parseInt(nume[4])+k+1)));
        XWPFTable table = document.createTable(nr+3,14);
        XWPFTableRow tableRowOne = table.getRow(0);
        CTTblLayoutType type = table.getCTTbl().getTblPr().addNewTblLayout();
        type.setType(STTblLayoutType.FIXED);
        int numCells = tableRowOne.getTableCells().size();
        for (int i = 0; i < numCells; i++) {
         if(i==2 ||i==7){
        XWPFTableCell cell = table.getRow(0).getCell(i);
         if (cell.getCTTc().getTcPr() == null) cell.getCTTc().addNewTcPr();
         XWPFTableCell cell2 = table.getRow(0).getCell(i+1);
         if (cell2.getCTTc().getTcPr() == null) cell2.getCTTc().addNewTcPr();
          XWPFTableCell cell3 = table.getRow(0).getCell(i+2);
         if (cell3.getCTTc().getTcPr() == null) cell3.getCTTc().addNewTcPr();
        CTHMerge hMerge = CTHMerge.Factory.newInstance();
        hMerge.setVal(STMerge.RESTART);
        table.getRow(0).getCell(i).getCTTc().getTcPr().setHMerge(hMerge);
        CTHMerge hMerge1 = CTHMerge.Factory.newInstance();
        hMerge1.setVal(STMerge.CONTINUE);
        table.getRow(0).getCell(i+1).getCTTc().getTcPr().setHMerge(hMerge1);
        table.getRow(0).getCell(i+2).getCTTc().getTcPr().setHMerge(hMerge1);
         }
         else if((i<2 || i>4) && (i<7 || i>9)){
             XWPFTableCell cell = table.getRow(0).getCell(i);
             if (cell.getCTTc().getTcPr() == null) cell.getCTTc().addNewTcPr();
             XWPFTableCell cell2 = table.getRow(1).getCell(i);
             if (cell2.getCTTc().getTcPr() == null) cell2.getCTTc().addNewTcPr();
             CTVMerge vmerge = CTVMerge.Factory.newInstance();
             vmerge.setVal(STMerge.RESTART);
             table.getRow(0).getCell(i).getCTTc().getTcPr().setVMerge(vmerge);
             CTVMerge vmerge1 = CTVMerge.Factory.newInstance();
             vmerge.setVal(STMerge.CONTINUE);
             table.getRow(1).getCell(i).getCTTc().getTcPr().setVMerge(vmerge1);
         }
        }
        for (int i = 0; i < table.getNumberOfRows(); i++) {
        XWPFTableRow row = table.getRow(i);
        if(i==0) row.setHeight((int)(1440*2/10)); 
        if(i==1) row.setHeight((int)(1440*17/100));
        else row.setHeight((int)(1440*20/100));
        row.getCtRow().getTrPr().getTrHeightArray(0).setHRule(STHeightRule.EXACT);         
             }
         numCells = tableRowOne.getTableCells().size();
        for(int j=0;j<numCells;j++)
        { CTTblWidth tblWidth = table.getRow(0).getCell(j).getCTTc().addNewTcPr().addNewTcW();
        XWPFTableRow row = table.getRow(0);
             if(j==1){tblWidth.setW(BigInteger.valueOf(1440*211/100));row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "DISCIPLINE ANUL .I." , false);}
             if(j==0) {tblWidth.setW(BigInteger.valueOf(1440*4/10));row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "Nr. \ncrt" , false);}
            if(j==2){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "Nr. total ore" , false);}
             if(j== 5 || j==13)tblWidth.setW(BigInteger.valueOf(1440*59/100));
             if(j==5){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "ECTS \nalocate" , false);}
             if(j==6){tblWidth.setW(BigInteger.valueOf(1440*225/100));row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); 
               setRun(paragraph7.createRun() , "Times New Roman" , 10, "DISCIPLINE STUDIATE \nEchivalate/Recunoscute" , false);}
             if(j==7){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "Nr. total ore" , false);}
             if(j==10){tblWidth.setW(BigInteger.valueOf(1440*69/100));XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); row.getCell(j).removeParagraph(0);setRun(paragraph7.createRun() , "Times New Roman" , 10, "ECTS \nobținute" , false);}
             if(j==11){tblWidth.setW(BigInteger.valueOf(1440*69/100));row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "Note \nobținute" , false);}
             if(j==12){tblWidth.setW(BigInteger.valueOf(1440*155/100));row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "Examene "+"\n"+"de sustinut" , false);}
             if(j==13){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "Credite \nrest." , false);}
        tblWidth.setType(STTblWidth.DXA);
        }
        for(int j=0;j<numCells;j++)
        { CTTblWidth tblWidth = table.getRow(1).getCell(j).getCTTc().addNewTcPr().addNewTcW();
        XWPFTableRow row = table.getRow(1);
             if(j== 2 || j==3 || j==4)tblWidth.setW(BigInteger.valueOf(1440*32/100));
              if(j== 7 || j==8 || j==9)tblWidth.setW(BigInteger.valueOf(1440*31/100));
              if(j==2 || j==7){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "C" , false);}
              if(j==3 || j==8){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "S" , false);}
              if(j==4 || j==9){row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
               paragraph7.setAlignment(ParagraphAlignment.CENTER); setRun(paragraph7.createRun() , "Times New Roman" , 10, "LP" , false);}
        tblWidth.setType(STTblWidth.DXA);
        }
        tableIndentation = table.getCTTbl().getTblPr().addNewTblInd();
        tableIndentation.setW(BigInteger.valueOf(-1440*5/10));
        tableIndentation.setType(STTblWidth.DXA);
        if(k<3){
        paragraph = document.createParagraph();
        run=paragraph.createRun();
        run.addBreak(BreakType.PAGE);
        run.addBreak(BreakType.TEXT_WRAPPING);
        }
        try{
            pstmt=con.prepareStatement("SELECT * FROM [Plan de invatamant Anul "+k+" "+(an-Integer.parseInt(nume[4])+k)+"-"+(an-Integer.parseInt(nume[4])+k+1)+"]");
              rs= pstmt.executeQuery();
              int i=2;
             while(rs.next())
             {
                     for(int j=1;j<=6;j++)
                     {
                         XWPFTableRow row = table.getRow(i);
                         if(j==2)
                            {row.getCell(j-1).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j-1).addParagraph();
                            if(rs.getString(j).toLowerCase().length()>26) row.setHeight((int)(1440*35/100));
                           setRun(paragraph7.createRun() , "Times New Roman" , 10, rs.getString(j) , false);}
                         else if(j==1)
                            {row.getCell(j-1).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j-1).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                           setRun(paragraph7.createRun() , "Times New Roman" , 10, rs.getString(j) , false);}
                         else if(j==6)
                            {row.getCell(j-1).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j-1).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            row.getCell(j-1).getCTTc().addNewTcPr().addNewShd().setFill("eaeaea");
                           setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(rs.getInt(8)) , true);}
                         else
                            {row.getCell(j-1).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j-1).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            if(String.valueOf(rs.getInt(j+1)).equals("0")) setRun(paragraph7.createRun() , "Times New Roman" , 10, "0" , false);
                          else setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(rs.getInt(j+1)) , false);}
                     }
                     i++;
             }
                }catch(Exception f){System.out.println(f);}
        for(int i=2;i<table.getNumberOfRows()-1;i++)
             {XWPFTableRow row = table.getRow(i);
                 row.getCell(10).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(10).addParagraph();
                  setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(0) , true);
                  paragraph7.setAlignment(ParagraphAlignment.CENTER);
                  row.getCell(10).getCTTc().addNewTcPr().addNewShd().setFill("eaeaea");
                  row.getCell(13).removeParagraph(0);paragraph7 = row.getCell(13).addParagraph();
                  setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(0) , true);
                  paragraph7.setAlignment(ParagraphAlignment.CENTER);
                  row.getCell(13).getCTTc().addNewTcPr().addNewShd().setFill("eaeaea");
             }
       try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
            con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");
         }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(PlanInvatamant.class.getName()).log(Level.SEVERE, null, ex);
        }
             try{
            pstmt=con.prepareStatement("SELECT * FROM ["+s1+"] WHERE An="+k);
            rs= pstmt.executeQuery();
            
            while(rs.next())
            {   
                for(int i=2;i<table.getNumberOfRows();i++)
                {
                    XWPFTableRow row = table.getRow(i);
                    if(rs.getString(2).equals(row.getCell(1).getText()) && row.getCell(11).getText().equals(""))
                    {
                        if(rs.getString(5).equals("ADM"))
                        {
                            row.getCell(10).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(10).addParagraph();
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(rs.getInt(6)) , true);
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            row.getCell(11).removeParagraph(0);paragraph7 = row.getCell(11).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, rs.getString(5) , false);
                            row.getCell(6).removeParagraph(0);paragraph7 = row.getCell(6).addParagraph();
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, rs.getString(4) , false);
                            row.getCell(13).removeParagraph(0);paragraph7 = row.getCell(13).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(0) , true);
                        }
                        else if(rs.getInt(5)!=0)
                        {
                            row.getCell(10).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(10).addParagraph();
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(rs.getInt(6)) , true);
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            row.getCell(11).removeParagraph(0);paragraph7 = row.getCell(11).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(rs.getInt(5)) , false);
                            row.getCell(6).removeParagraph(0);paragraph7 = row.getCell(6).addParagraph();
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, rs.getString(4) , false);
                            row.getCell(13).removeParagraph(0);paragraph7 = row.getCell(13).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(0) , true);
                        }
                        else if(rs.getInt(5)==0){
                            row.getCell(10).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(10).addParagraph();
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(0) , true);
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            row.getCell(12).removeParagraph(0);paragraph7 = row.getCell(12).addParagraph();
                            setRun(paragraph7.createRun() , "Times New Roman" , 10,row.getCell(1).getText() , false);
                             row.getCell(13).removeParagraph(0);paragraph7 = row.getCell(13).addParagraph();
                            paragraph7.setAlignment(ParagraphAlignment.CENTER);
                            setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(rs.getInt(6)) , true);
                        }
                      break;
                    }                    
                 }
            }
            }catch(Exception f){System.out.println(f);}
             int sum=0,sum1=0,sum2=0;
        for(int i=2;i<table.getNumberOfRows();i++)
            {XWPFTableRow row = table.getRow(i);
               for(int j=0;j<numCells;j++)
                {
                    if(j==0 && i<table.getNumberOfRows()-1) {row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
                         setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(i-1) , false);}
                    if(j==5 && i<table.getNumberOfRows()-1) sum=sum+Integer.parseInt(row.getCell(j).getText());
                    else if(j==5 && i<table.getNumberOfRows()){
                        row.getCell(1).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(1).addParagraph();
                     setRun(paragraph7.createRun() , "Times New Roman" , 10, "TOTAL" , true);
                     row.getCell(j).removeParagraph(0); paragraph7 = row.getCell(j).addParagraph();
                     paragraph7.setAlignment(ParagraphAlignment.CENTER);
                     row.getCell(j).getCTTc().addNewTcPr().addNewShd().setFill("fde9d9");
                     setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(sum) , true);}
                   if(j==10 && i<table.getNumberOfRows()-1) sum1=sum1+Integer.parseInt(row.getCell(j).getText());
                   else if(j==10 && i<table.getNumberOfRows()){
                     row.getCell(6).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(6).addParagraph();
                     setRun(paragraph7.createRun() , "Times New Roman" , 10, "TOTAL" , true); 
                     row.getCell(j).removeParagraph(0); paragraph7 = row.getCell(j).addParagraph();
                     paragraph7.setAlignment(ParagraphAlignment.CENTER);
                     row.getCell(j).getCTTc().addNewTcPr().addNewShd().setFill("fde9d9");
                     setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(sum1) , true);
                   }
                   if(j==13 && i<table.getNumberOfRows()-1) sum2=sum2+Integer.parseInt(row.getCell(j).getText());
                   else if(j==13 && i<table.getNumberOfRows()){
                     row.getCell(j).removeParagraph(0);XWPFParagraph paragraph7 = row.getCell(j).addParagraph();
                     paragraph7.setAlignment(ParagraphAlignment.CENTER);
                     row.getCell(j).getCTTc().addNewTcPr().addNewShd().setFill("fde9d9");
                     setRun(paragraph7.createRun() , "Times New Roman" , 10, String.valueOf(sum2) , true);
                   }
                }
            }
       }  
      FileOutputStream out;
        try {
            out = new FileOutputStream( new File("Documente\\"+nume[0]+" "+nume[1]+" "+nume[2]+".docx"));
            document.write(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
    try {
     if (Desktop.isDesktopSupported()) {
       Desktop.getDesktop().open(new File("Documente\\"+nume[0]+" "+nume[1]+" "+nume[2]+".docx"));
     }
   } catch (IOException ioe) {
     ioe.printStackTrace();
   }
    }
    private void Sterge(String s)
    {
        try{   Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
              con= DriverManager.getConnection("jdbc:ucanaccess://BDEchivUniv.accdb");
            
    }catch ( SQLException f ){System.out.println(f);} catch (ClassNotFoundException ex) {
            Logger.getLogger(EchivFostStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            System.out.println("DROP TABLE "+s);
            stmt=con.createStatement();
            String sql="DROP TABLE ["+s+"]";
            stmt.executeUpdate(sql);
        }catch(Exception f){System.out.println(f);}
    }          
}

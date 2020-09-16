
package echivuniv;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Erori extends Frame {
   JFrame f;
   private static String s;
   private static int ok;
    Erori(String s,int ok) {
    this.s=s;
    this.ok=ok;
    if(ok==0)
    f=new JFrame("Avertizare");
    else f=new JFrame("Mesaj");
    Label lbl=new Label(s);
    lbl.setBounds(60,10,400,50);
    Button b1=new Button("OK");
    b1.setBounds(200,60,100,25);
    lbl.setFont(new Font("Verdana",Font.PLAIN,18));
    b1.setFont(new Font("Verdana",Font.PLAIN,18));
    ImageIcon icon = new ImageIcon("warning.png");
    JLabel limg=new JLabel(icon);
    limg.setBounds(10,10,50,50);
    f.add(lbl);
    f.add(b1);
    if(ok==0){
    f.add(limg);
    }
    f.setBounds(800,400,500,150);
    f.setLayout(null);
    f.setVisible(true);
          b1.addActionListener(new ActionListener(){ 
    public void actionPerformed (ActionEvent e){ 
       f.dispose();
    }
     });
    }
}

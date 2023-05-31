//공지

package frogM;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
public class Tab5 extends JPanel {
	
	String beforeText[] = {"","","",""};
	JButton btn1,btn2;
	newPanel pa,pa1, pa2, pa3, pa4,plus1,minus1,plus2,plus3,plus4,minus2,minus3,minus4;
	JTextArea tf1, tf2, tf3, tf4;
	ImageIcon icon,icon1;
	newPanel [] post = new newPanel[4];  
	JTextArea [] tf = new JTextArea[4];  
	newPanel [] plus = new newPanel[4]; 
	newPanel [] minus = new newPanel[4];  
	
	
	FrogMgr mgr = new FrogMgr();
	public Tab5() {		    
	
		//beforetext 불러와		

		Vector<noticeBean> vlist1 = mgr.select_notice();
			
		beforeText[0] = vlist1.get(0).getContent();
		beforeText[1] = vlist1.get(1).getContent();
		beforeText[2] = vlist1.get(2).getContent();
		beforeText[3] = vlist1.get(3).getContent();
			
		pa = new newPanel("back1.png",3,4, 1228,603); // 칠판

		for(int i = 0; i<4; i++) {
			post[i] =  new newPanel("post"+(i+1)+".png",0,0,0,0);	
			tf[i]= new JTextArea(beforeText[i]) {
				@Override 
	            public void setBorder(Border border) {  }  }; 
			}
		
		post[0].setBounds(90,90,286,273);
		post[1].setBounds(340,190,286,273);
		post[2].setBounds(590,110,286,273);
		post[3].setBounds(840,170,286,273);
		tf[0].setBounds(25,45,200,150);
		tf[1].setBounds(25,45,200,150);
		tf[2].setBounds(40,65,200,150);
		tf[3].setBounds(40,65,200,150);
		tf[0].setBackground(new Color(255,234,116));
		tf[1].setBackground(new Color(255,153,176));
		tf[2].setBackground(new Color(122,218,243));
		tf[3].setBackground(new Color(163,235,131));
		tf[0].setFont(new Font("UhBee MiMi", 0, vlist1.get(0).getFontsize()));
		tf[1].setFont(new Font("Nanum MaGoCe", 0, vlist1.get(1).getFontsize()));
		tf[2].setFont(new Font("Kyobo Handwriting 2021 sjy", 0, vlist1.get(2).getFontsize()));
		tf[3].setFont(new Font("Nanum NaNeunIGyeoNaenDa", 0, vlist1.get(3).getFontsize()));
		plus[0] = new newPanel("plus1.png",180,10,30,30);
		plus[1] = new newPanel("plus1.png",165,10,30,30);
		plus[2] = new newPanel("plus1.png",180,20,30,30);
		plus[3] = new newPanel("plus1.png",180,10,30,30);
		minus[0] = new newPanel("minus1.png",215,10,30,30);
		minus[1] = new newPanel("minus1.png",200,10,30,30);
		minus[2] = new newPanel("minus1.png",215,20,30,30);
		minus[3] = new newPanel("minus1.png",215,10,30,30);
		
		for(int i = 0; i<4; i++) {	post[i].add(plus[i]);}
		for(int i = 0; i<4; i++) {	post[i].add(minus[i]);}
		for(int i = 0; i<4; i++) {	post[i].add(tf[i]);}
		for(int i = 0; i<4; i++) {	pa.add(post[i]);}
		for(int i = 0; i<4; i++) {	plus[i].setVisible(false);}
		for(int i = 0; i<4; i++) {	minus[i].setVisible(false);}
		for(int i = 0; i<4; i++) {	
			post[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Object obj = e.getSource();
					for(int i = 0; i<4; i++) {	
						if(obj == post[i]) {
							plus[i].setVisible(true);
							minus[i].setVisible(true);							
						}
					}		
				}
					
				@Override
				public void mouseExited(MouseEvent e) {
					for(int i = 0; i<4; i++) {
						if (plus[i].isVisible()) {
							plus[i].setVisible(false);
							minus[i].setVisible(false);
							if (tf[i].getText() != beforeText[i]) mgr.updateNotice(i+1,tf[i].getText(), tf[i].getFont().getSize());							
						}
				}}
			} );
			tf[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					Object obj = e.getSource();
					for(int i = 0; i<4; i++) {	
						if(obj == tf[i]) {
							plus[i].setVisible(true);
							minus[i].setVisible(true);							
						}
				}}
				@Override
				public void mouseExited(MouseEvent e) {
					for(int i = 0; i<4; i++) {	
					plus[i].setVisible(false);
					minus[i].setVisible(false);
				}}
			});	
		}
		
	
		
	
		plus[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 tf[0].setFont(new Font("UhBee MiMi", 0, tf[0].getFont().getSize() +1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[0].setVisible(true);
				minus[0].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[0].setVisible(false);
				minus[0].setVisible(false);
			}
		});
		plus[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tf[1].setFont(new Font("Nanum MaGoCe", 0, tf[1].getFont().getSize() +1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[1].setVisible(true);
				minus[1].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[1].setVisible(false);
				minus[1].setVisible(false);
			}
		});
		plus[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tf[2].setFont(new Font("Kyobo Handwriting 2021 sjy", 0, tf[2].getFont().getSize() +1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[2].setVisible(true);
				minus[2].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[2].setVisible(false);
				minus[2].setVisible(false);
			}
		});
		plus[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tf[3].setFont(new Font("Nanum NaNeunIGyeoNaenDa", 0, tf[3].getFont().getSize() +1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[3].setVisible(true);
				minus[3].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[3].setVisible(false);
				minus[3].setVisible(false);
			}
		});
		minus[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tf[0].getFont().getSize() == 10) return; 
				tf[0].setFont(new Font("UhBee MiMi", 0, tf[0].getFont().getSize() -1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[0].setVisible(true);
				minus[0].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[0].setVisible(false);
				minus[0].setVisible(false);
			}
		});
		minus[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tf[1].getFont().getSize() == 10) return; 
				tf[1].setFont(new Font("Nanum MaGoCe", 0, tf[1].getFont().getSize() -1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[1].setVisible(true);
				minus[1].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[1].setVisible(false);
				minus[1].setVisible(false);
			}
		});
		minus[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tf[2].getFont().getSize() == 10) return; 
				tf[2].setFont(new Font("Kyobo Handwriting 2021 sjy", 0, tf[2].getFont().getSize() -1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[2].setVisible(true);
				minus[2].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[2].setVisible(false);
				minus[2].setVisible(false);
			}
		});
		minus[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tf[3].getFont().getSize() == 10) return; 
				tf[3].setFont(new Font("Nanum NaNeunIGyeoNaenDa", 0, tf[3].getFont().getSize() -1 ));
			}
			public void mouseEntered(MouseEvent e) {
				plus[3].setVisible(true);
				minus[3].setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				plus[3].setVisible(false);
				minus[3].setVisible(false);
			}
		});

		for(int i = 0; i<4; i++) {tf[i].setLineWrap(true);}
	
		add(pa); 
		
		setLayout(null);		
		setBackground(new Color(255,255,255,0));
		setOpaque(false);
		revalidate();
		repaint();
		setVisible(false);
		  
	}
}




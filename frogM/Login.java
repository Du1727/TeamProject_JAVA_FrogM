package frogM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Vector;

import javax.swing.ImageIcon;

public class Login extends UI implements ActionListener, MouseListener, Runnable{
	LocalDate now = LocalDate.now();
	int olderTab = 0, selectYear = now.getYear(), selectMonth = now.getMonthValue(), arrowInput = 0, bgdummy = 0;
	boolean denyLeft, denyRight;
	String command = "", text = "", description[] = {"일정을 보는 곳이야.","","","", ""};
	newLabel talk;
	newPanel btn,  bubble, arrowLeft, arrowRight, close;
	newPanelOp frog ,p1, p2, p3,loading, profileFace;
	Frame f1,f2;
	newTextField tf1;
	newPasswordField tf2;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	FrogMgr mgr = new FrogMgr();
	Tab1 tab1 = new Tab1(); //일정 
	Tab2 tab2 = new Tab2(); //관리
	Tab3 tab3 = new Tab3(); //직원
	Tab4 tab4 = new Tab4(); //현황	
	Tab5 tab5 = new Tab5(); //공지	
	newPanel [] tabs = new newPanel[5];
	newPanelOp [] profile = new newPanelOp[3];
	Vector<MemberBean> vlist;
	MemberBean bean;
	
	public Login() {
		f1 = newFrame(d.width/2-190, d.height, 1300, 800, new Color(0, 0, 0, 0));
		talk = new newLabel("", 148, 0, 153, 75, 15, Color.black, "CookieRunOTF Regular",0);
		frog = new newPanelOp("frog.png", 15,0, 118, 121);
		btn = new newPanel("btn.png", 250, 410, 100, 47);	
		p1 = new newPanelOp("login.png",0,100,380,700);
		p2 = new newPanelOp("loading.png",0,100,380,700);
		p3 = new newPanelOp("main.png",0,100,380,700);
		loading = new newPanelOp("loading.gif",40,350,300,171);
		bubble = new newPanel("bubble.png", 148,0, 153, 80);	
		close = new newPanel("exit.png", 1205,70, 25, 26);	
		arrowLeft = new newPanel("arrow0.png", 680,490, 70, 70);
		arrowRight = new newPanel("arrow1.png", 755,490, 70, 70);
		profile[0] = new newPanelOp("man.png",60,50,270,270);
		profile[1] = new newPanelOp("woman.png",60,50,270,270);
		profile[2] = new newPanelOp("manager.png",60,50,270,270);
		tf1 = new newTextField(55, 395, 180, 30, 15, "CookieRunOTF Regular");
		tf2 = new newPasswordField(55, 440, 180, 30, 15, "CookieRunOTF Regular");
		tf2.setEchoChar('*');
		
		for(int i = 0; i < 5; i++) {
			if(i == 0)	tabs[i] = new newPanel("tab" + i + "_true.png",1229,20+(i*65),100,67);
			else	tabs[i] = new newPanel("tab" + i + "_false.png",1229,20+(i*65),100,67);
			tabs[i].addMouseListener(this);
			p3.add(tabs[i]);
		}
		
		frog.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				f1.setLocation(e.getLocationOnScreen().x -75 ,e.getLocationOnScreen().y -75);	
			}
		});
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				f1.dispose();
			}
		});
		tab1.setBounds(0, 0, 1235, 610);
		tab2.setBounds(0, 0, 1235, 610);
		tab3.setBounds(5, 0, 1225, 610);
		tab4.setBounds(0, 0, 1235, 610);
		tab5.setBounds(0, 0, 1235, 610);
		setcursor(f1, "mouseicon0.png");
		setClickcursor(frog,"mouseicon1.png");
		setClickcursor(arrowLeft,"mouseicon1.png");
		setClickcursor(arrowRight,"mouseicon1.png");
		setClickcursor(btn,"mouseicon1.png");
		setClickcursor(close,"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(0),"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(1),"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(2),"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(3),"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(4),"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(5),"mouseicon1.png");
		setClickcursor(tab2.dayPicture.get(6),"mouseicon1.png");
		setClickcursor(tab2.durationBtn,"mouseicon1.png");
		setClickcursor(tab4.calBtn,"mouseicon1.png");
		setLabelcursor(tab2.deleteBtn,"mouseicon1.png");
		setBtncursor(tab2.btn1,"mouseicon1.png");
		setBtncursor(tab2.btn2,"mouseicon1.png");
		setBtncursor(tab2.btn3,"mouseicon1.png");

		for(int i = 0; i < 5; i++) {setClickcursor(tabs[i],"mouseicon1.png");}
		
		tab1.setVisible(true);
		bubble.setVisible(false);
		p1.add(btn);
		tf1.setText("");
		tf2.setText("");
		p1.add(tf1);
		p1.add(tf2);		
		p2.add(loading);
		p3.add(tab1);
		p3.add(tab2);
		p3.add(tab3);
		p3.add(tab4);
		p3.add(tab5);
		f1.add(talk);
		f1.add(bubble);
		f1.add(frog);
		f1.add(p1);
		tab1.p2.add(arrowLeft);
		tab1.p2.add(arrowRight);
		arrowLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				command("arrow:1");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				command("arrow:0");
			}
			@Override
			public void mouseClicked(MouseEvent e) {				
				now = now.minusMonths(1);					
				tab1.createTable(now.getYear(),now.getMonthValue(), bean.getId());
				tab1.p.repaint();
			}
		});
		arrowRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				command("arrow:2");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				command("arrow:0");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				now = now.plusMonths(1);					
				tab1.createTable(now.getYear(),now.getMonthValue(), bean.getId());
				tab1.p.repaint();
				//f1.repaint();
			}
		});
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		btn.addMouseListener(this);
		revalidate();		
		init(f1);	
		command("breath:1");
	}
	
	public void checkLogin() {
		if(tf1.getText().length() == 0) {
			command("talking:아이디를 입력해!");
			tf1.requestFocus();
		} else if (tf2.getText().length() == 0) {
			command("talking:비밀번호를 입력해!");
			tf2.requestFocus();
		} else {
			if(mgr.loginChk(tf1.getText().trim(),tf2.getText().trim())) {
				command("loading:1");
			} else {
				command("talking:잘못된 정보야!");					
			}
		}		
	}	
	
	
	public void init(Frame frame) {
		
		int fx = frame.getX();
		delay(1);
		frame.setVisible(true);
		for (int i = 0; i < 50; i++) { 
			frame.setLocation(fx, frame.getY()-1);
			delay(15);
		}
		delay(300);
		for (int i = 0; i < 25; i++) {
			frame.setLocation(fx, frame.getY()+2);
			delay(10);
		}
		delay(100);
		for (int i = 0; i < 31; i++) {
			frame.setLocation(fx, frame.getY()-(i*2));
			delay(10);
		}
		frame.setSize(1300,frame.getHeight());
		
	}
	
	public static void delay(long delay) {
		try {
			Thread.sleep(delay);
		} catch (Exception e) {}
	}
	
	public void command(String cmd) {
		command = cmd.split(":")[0];
		text = cmd.split(":")[1];
		new Thread(this).start();
	}
	@Override
	public void run() {	
		
		switch (command) {
		case "breath":
				p3.setVisible(false);
				for (int i = 1; i < 7; i++) {
					frog.setLocation(15, i);
					delay(100);
					if (p3.isVisible()) {
						frog.setLocation(frog.getX(),0);
						return;
					}
				}
				for (int i = 6; i > 0; i--) {
					frog.setLocation(15, i);
					delay(100);
					if (p3.isVisible()) {
						frog.setLocation(frog.getX(),0);
						return;
					}
				}				
				f1.repaint();
				command("breath:1");				
				break;
		case "breath2":			
			for (int i = 1; i < 70; i++) {
				frog.setLocation(15, i/10);
				if(tab2.arrowP.isVisible()) {
					tab2.arrow.setLocation(0, i/10);
				}
				if(tab3.dummy.isVisible()) {
					if(bgdummy == 10 && !tab3.profileInfo.isVisible()) bgdummy = 0;
					if (bgdummy < 9) {
						bgdummy++;
						tab3.dummy.setBackground(new Color(0,0,0,bgdummy*11));
						tab3.repaint();						
					} else if (bgdummy == 9) {
						bgdummy = 10;
						if (!tab3.profileInfo.isVisible()) tab3.profileInfo.setVisible(true);						
					}
				}
				if(tab2.command.isVisible()) {
					command("talking:" + tab2.command.getText());
					tab2.command.setVisible(false);
				}
				f1.repaint();
				delay(10);
			}
			for (int i = 60; i > 0; i--) {
				frog.setLocation(15, i/10);
				if(tab2.arrowP.isVisible()) {
					tab2.arrow.setLocation(0, i/10);
					tab2.repaint();
				}
				if(tab3.dummy.isVisible()) {
					if(bgdummy == 10 && !tab3.profileInfo.isVisible()) bgdummy = 0;
					if (bgdummy < 9) {
						bgdummy++;
						tab3.dummy.setBackground(new Color(0,0,0,bgdummy*10));
						tab3.repaint();						
					} else if (bgdummy == 9) {
						bgdummy = 10;
						if (!tab3.profileInfo.isVisible()) tab3.profileInfo.setVisible(true);						
					}
				}
				f1.repaint();
				delay(10);
			}				
			command("breath2:1");				
			break;
		case "talking":
			String dummy = text;
			bubble.setVisible(true);
			for (int i = 0 ; i <= dummy.length(); i ++) {
				talk.setText(dummy.substring(0, i));
				delay(15);
			}
			break;
		case "loading":
			int select = 0;
			f1.setAlwaysOnTop(false);
			vlist = mgr.memberSelectall();
			for (int i = 0; i < vlist.size(); i++) {
				bean = vlist.get(i);
				if (bean.getId().equals(tf1.getText())) break;					
			}
			tab1.loadData(bean.getName(), bean.getGrade(), bean.getPosition());
			command("talking:환영해 " + bean.getName() + "!");
			if(bean.getGrade().equals("점장") || bean.getGrade().equals("매니저")) select = 2;
			else if(bean.getGender().equals("여자")) select = 1;			
			p2.add(profile[select]);
			f1.add(p2);				
			delay(100);
			f1.remove(p1);
			f1.repaint();
			for(int i = 0; i < 135; i++) {
				profile[select].setSize(270,i*2);
				delay(5);
			}
			tab1.createTable(selectYear,selectMonth, bean.getId());
			delay(1500);
			bubble.setVisible(false);
			talk.setText("");
			File file = new File("./frogM/image/" + bean.getId() + ".jpg");
			if (file.isFile()) {				
				tab1.p.add(new newPanel("pictureDummy.png", 80,50,230,230));
				tab1.p.add(profileFace = new newPanelOp(bean.getId() + ".jpg", 80, 50, 230, 230));
			} else tab1.p.add(profile[select]);
			tab1.p.repaint();
			p3.setVisible(true);
			p3.repaint();
			f1.repaint();
			f1.add(p3);		
			delay(100);
			f1.remove(p2);
			f1.repaint();
			int fy = f1.getY();
			for(int i = 0; i < 46; i++) {
				f1.setLocation(f1.getX()-10,fy);
				p3.setSize(p3.getWidth()+20,p3.getHeight());		
				p3.repaint();
				f1.repaint();
				delay(3);
			}				
			delay(10);
			f1.add(close);
			command("breath2:1");				
			tab1.stats[0].setText(bean.getName());
			tab1.stats[1].setText("직책 : " + bean.getGrade());
			tab1.stats[2].setText("업무 : " + bean.getPosition());
			for(int i = 255; i > 0; i -= 2) {
				tab1.stats[0].setForeground(new Color(i,i,i));	
				tab1.stats[1].setForeground(new Color(i,i,i));	
				tab1.stats[2].setForeground(new Color(i,i,i));
				delay(3);
				tab1.p.repaint();
				f1.repaint();
			}
			break;
		case "opentab":
			int input = Integer.parseInt(text);
			if(input == olderTab) return;
			switch(olderTab) {
			case 0:
				tab1.setVisible(false);
				break;
			case 1:
				tab2.setVisible(false);
				break;
			case 2:
				tab3.setVisible(false);
				break;
			case 3:
				tab4.setVisible(false);
				break;
			case 4:
				tab5.setVisible(false);
				break;
			}
			switch(input) {
			case 0:					
				tab1.setVisible(true);
				tab1.createTable(now.getYear(),now.getMonthValue(), bean.getId());
				File file2 = new File("./frogM/image/" + bean.getId() + ".jpg");
				if (file2.isFile()) profileFace.setImage(bean.getId() + ".jpg");				
				tab1.repaint();
				break;
			case 1:
				tab2.setVisible(true);				
				tab2.repaint();
				break;
			case 2:
				tab3.setVisible(true);
				tab3.repaint();
				break;
			case 3:
				tab4.setVisible(true);
				tab4.repaint();
				break;
			case 4:
				tab5.setVisible(true);
				tab5.repaint();
				break;
			}
			tabs[olderTab].setImage("tab" + olderTab + "_false.png");
			tabs[input].setImage("tab" + input + "_true.png");
			olderTab = input;
			
			//p3.repaint();
			//p3.revalidate();				
			//command("talking:" + description[Integer.parseInt(text)]);			
			break;
		case "arrow":
				arrowInput = Integer.parseInt(text);
				if(arrowInput == 1) {
					if(!denyLeft) {
						denyLeft = true;
						for (int k = 0; k < 7; k++) {
							arrowLeft.setLocation(680-k,490);
							tab1.repaint();
							delay(30);
						}
						for (int k = 6; k > 0; k--) {
							arrowLeft.setLocation(680-k,490);
							tab1.repaint();
							delay(30);
						}			
						delay(1000);
						denyLeft = false;
						if(arrowInput == 1) { command("arrow:1"); }			
					}
				} else if (arrowInput == 2) {
					if(!denyRight) {
						denyRight = true;
						for (int k = 0; k < 7; k++) {
							arrowRight.setLocation(755+k,490);
							tab1.repaint();
							delay(30);
						}
						for (int k = 6; k > 0; k--) {
							arrowRight.setLocation(755+k,490);
							tab1.repaint();
							delay(30);
						}
						delay(1000);
						denyRight = false;
						if(arrowInput == 2) { command("arrow:2"); }		
					}			
				}
				
				break;
		}
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == tf1 || obj == tf2) checkLogin();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == btn) {
			checkLogin();
		} else {
			for(int i = 0; i < 10; i++) {
				if (obj == tabs[i]) {
					command("opentab:" + (i > 4 ? i - 5 : i));
					return;
				}
			}			
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public static void main(String[] args) {
		Login login = new Login();
	}
}
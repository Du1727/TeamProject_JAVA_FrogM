//일정

package frogM;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JPanel;

public class Tab1 extends JPanel {
	
    int monthSet[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String userName, userGrade, userPos;
    String [] daysStats = new String[42];
    newPanel [] daysP = new newPanel[42];
    newLabel [] daysL = new newLabel[42];
    newLabel [] daysS = new newLabel[42];
    newPanel [] daysI = new newPanel[42];
    newLabel [] wekString = new newLabel[7];
    newLabel yearLabel, monthLabel;
	newPanel p,p2,wek, line;
	newPanelOp check, cal;
	newLabel [] stats = new newLabel[3];
	Calendar now = Calendar.getInstance();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	FrogMgr mgr = new FrogMgr();
	
	public Tab1() {			
		
		p = new newPanel("",0,0,380,610);
		p2 = new newPanel("",381,20,1230,610);
		wek = new newPanel("",0,0,901,100);
		cal = new newPanelOp("Calendar.png",28,0,150,150);
		line = new newPanel("line.png",381,0,5,800);
		stats[0] = new newLabel("", 0, 305, 380, 65, 50, Color.white,"CookieRunOTF Regular",0);
		stats[1] = new newLabel("", 0, 420, 380, 45, 30, Color.white,"CookieRunOTF Regular",0);
		stats[2] = new newLabel("", 0, 460, 380, 45, 30, Color.white,"CookieRunOTF Regular",0);
		yearLabel = new newLabel("", 50 ,20, 150, 30, 20, Color.white, "CookieRunOTF Regular");
		monthLabel = new newLabel("", 5 ,45, 100, 50, 45, Color.white, "CookieRunOTF Black",0);
		wekString[0] = new newLabel("일", 17, 80, 50, 50, 40, new Color(255,87,89), "netmarble Bold",0);
		wekString[1] = new newLabel("월", 17, 80, 50, 50, 40, new Color(50,50,50), "netmarble Bold",0);
		wekString[2] = new newLabel("화", 17, 80, 50, 50, 40, new Color(50,50,50), "netmarble Bold",0);
		wekString[3] = new newLabel("수", 17, 80, 50, 50, 40, new Color(50,50,50), "netmarble Bold",0);
		wekString[4] = new newLabel("목", 17, 80, 50, 50, 40, new Color(50,50,50), "netmarble Bold",0);
		wekString[5] = new newLabel("금", 17, 80, 50, 50, 40, new Color(50,50,50), "netmarble Bold",0);
		wekString[6] = new newLabel("토", 17, 80, 50, 50, 40, new Color(51,152,252), "netmarble Bold",0);
		for(int i = 0; i < 7; i ++) { p2.add(wekString[i]); }

		int offsetY = 145;
		for(int i = 0; i < 42; i++) {
			daysP[i] = new newPanel(null,137 + ((i%7)*100),offsetY,80,85);
			daysI[i] = new newPanel("star.png",1,3,50,49);
			if(i<7) {
				wekString[i].setLocation(daysP[i].getX(),80);
			}			
			if((i+1)%7 == 0) { 
				offsetY += 70;
				daysL[i] = new newLabel("", 0, 5, 50, 50, 30, new Color(51,152,252), "CookieRunOTF Regular",0);
				daysS[i] = new newLabel("", 0, 30, 55, 50, 14, new Color(51,152,252), "CookieRunOTF Regular",0);
			} else if ((i%7 == 0)) {
				daysL[i] = new newLabel("", 0, 5, 50, 50, 30, new Color(255,87,89), "CookieRunOTF Regular",0);	
				daysS[i] = new newLabel("", 0, 30, 55, 50, 14, new Color(255,87,89), "CookieRunOTF Regular",0);			
			} else {	
				daysL[i] = new newLabel("", 0, 5, 50, 50, 30, new Color(50,50,50), "CookieRunOTF Regular",0);	
				daysS[i] = new newLabel("", 0, 30, 55, 50, 14, new Color(50,50,50), "CookieRunOTF Regular",0);					
			}

			daysP[i].add(daysL[i]);
			daysP[i].add(daysS[i]);
			daysP[i].add(daysI[i]);
			p2.add(daysP[i]);
		}
		//System.out.println(df.format(now.getTime()).toString());		

		add(line);
		cal.add(yearLabel);
		cal.add(monthLabel);
		p2.add(cal);
		for(int i = 0; i < 3; i++) {p.add(stats[i]);}
		
		add(p);
		add(p2);
		setLayout(null);
		setBackground(new Color(255,255,255,0));
		setOpaque(false);
		revalidate();
		repaint();
		setVisible(false); //이거 남겨놔주세요!		
		
	}

	public void loadData(String userName,String userGrade,String userPos) {
		this.userName = userName;
		this.userGrade = userGrade;
		this.userPos = userPos;	
	}
	
	public void createTable(int y, int m, String id) {

		int week=0, day=0, i=0; 
		yearLabel.setText(y + "");
		monthLabel.setText(m + "월");
		if(check != null) p2.remove(check);
        if(y%4 == 0 && y%100 != 0 || y%400 == 0) monthSet[1] = 29;
        else monthSet[1] = 28;
	
        day = (y-1)*365 + (y-1)/4 - (y-1)/100 + (y-1)/400;
        for(i=0;i<m-1;i++) {
         day += monthSet[i];  
        }
        week=day%7; // 구하고자 하는 달의 시작일(1일)의 요일을 구함.

        for(i=0;i<42;i++) {
        	daysL[i].setText("");
        	daysS[i].setText("");
        	daysStats[i] = "";
        	daysI[i].setVisible(false);
        	daysP[i].setVisible(false);	        
			daysP[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// newPanel 객체 -> 발생되는 주체
					// MouseEvent -> 주체를 들고 있는데 object 타입이기 때문에 형변환 해줘야한다
					newPanel temp =  (newPanel) e.getSource();
					if(temp.isVisible()) {
						if(check != null) p2.remove(check);
						check = new newPanelOp("check.gif",temp.x-50,temp.y-20,145,113);
						p2.add(check);
						p2.repaint();
						repaint();
					}								
					for(int m = 0; m < 42; m++) {
						if (e.getSource() == daysP[m] && daysStats[m] != "") {					
							stats[0].setText(daysStats[m].split("/")[0]);
							stats[1].setText(daysStats[m].split("/")[1]);
							stats[2].setText(daysStats[m].split("/")[2]);
							p.repaint();
							repaint();
							return;
						}
					}
					stats[0].setText(userName);
					stats[1].setText("직책 : " + userGrade);
					stats[2].setText("업무 : " + userPos);
				}
			});	
        }
        
		Vector<Bean_Calender> calenderV = mgr.select_calender(id);
		
        for(i=1;i<=monthSet[m-1];i++) {
    
	        daysL[week+1].setText("" + i);
			
			String format = y + (m < 10 ? "0" + m :m + "") + (i < 10 ? "0" + i : i + "");
			for (int j = 0; j < calenderV.size(); j++) {				
				Bean_Calender calender_bean = calenderV.get(j);
				if(calender_bean.getWork_dt().equals(format)) {
					daysS[week+1].setText(calender_bean.getTitle());
					daysI[week+1].setVisible(true);
					daysStats[week+1] = "[" + calender_bean.getTitle() + "]/업무 : " + calender_bean.getPosition() + "/시간 : "
					+ calender_bean.getStartTime().substring(0,2) + ":" + calender_bean.getStartTime().substring(2,4)  + " ~ "
					+ calender_bean.getEndTime().substring(0, 2) + ":" + calender_bean.getEndTime().substring(2, 4);
				}			
			}	
			daysP[week+1].setVisible(true);			
	        week++; 
        }
    	repaint();
	}

}

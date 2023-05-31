//관리

package frogM;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Tab2 extends JPanel implements ActionListener, ItemListener {

	String loadData = "";
	Frame diarySelect = null;
	LocalDate now = LocalDate.now();
	int orderY = 60, orderYMax = 60;
	String backup[];
	String dwtmp = "월";
	FrogMgr mgr;
	newPanel panel, arrowP, arrow, durationBtn;
	JPanel p1,p1_1, p2, p3, p4;
	JComboBox<String> cb1, cb2, cb3, cb4, cb5;
	newTextField ntf1;
	newLabel deleteBtn, duration, command;
	JTextField tf1, to_tf1, to_tf2;
	JTextField time_tf1, time_tf2, time_tf3, time_tf4, time_tf5, time_tf6, time_tf7, time_tf8;
	JTextField emp_tf1, emp_tf2;
	JTextField tmptf;

	
	JButton btn1, btn2, btn3;

	String dw[] = { "월", "화", "수", "목", "금", "토", "일" };
	String saveYoil[], savePos[], saveTitle[];
	
	// 벡터 배열 선언
	ArrayList<newPanel> dayPicture = new ArrayList<>();

	
//	ArrayList<JTextField> barData_tf = new ArrayList<>();
	
	JTextField code_p3; 
	ArrayList<JComboBox> p4CbList = new ArrayList<>();
	ArrayList<JComboBox> p4_IdList = new ArrayList<>();
	Vector<Bean_StoreSchedule> p1_V_positionList;
	Vector<Bean_StoreSchedule> barDataList;
	
	int YPosi_p4 = -20;

	String date1 = monday(now).substring(0,4) + monday(now).substring(5,7) + monday(now).substring(8,10);
	String date2 = sunday(now).substring(0,4) + sunday(now).substring(5,7) + sunday(now).substring(8,10);
	
	int YPosi_people = 50;

	// ###

	public Tab2() {

		mgr = new FrogMgr();

		p1 = new newPanel(null, 15, 35, 900, 555);
		p1.setBorder(new LineBorder(Color.BLACK));
		p1_1 = new newPanel(null, 15, 60, 900, 50);
		
		p2 = new newPanel(null, 920, 35, 300, 200);
		p2.setBorder(new LineBorder(Color.BLACK));

		p3 = new newPanel(null, 920, 240, 300, 350);
		p3.setBorder(new LineBorder(Color.BLACK));

		duration = new newLabel(monday(now) + " ~ " + sunday(now), 500,7, 500, 40, 17, Color.black,"netmarble Bold");		
		durationBtn = new newPanel("diaryicon.png", 760, 7, 47, 38);
		command =  new newLabel("",-100,-100,0,0,1,Color.white,"");
		command.setVisible(false);
		p1.add(duration);
		p1.add(durationBtn);
		
		deleteBtn = new newLabel("(-) 삭제", 0,0, 100, 30, 15, Color.red,"CookieRunOTF Regular");
		deleteBtn.setVisible(false);
		deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				mgr.deleteStoreSchedule(code_p3.getText());
				mgr.deleteWorkHistory(code_p3.getText());
				cb3.setSelectedIndex(0);
				cb4.setSelectedIndex(0);
				cb5.setSelectedIndex(0);
				cb3.setEnabled(false);
				cb4.setEnabled(false);
				cb5.setEnabled(false);
				p4.removeAll();
				retrieve();
				repaint();
			}
		});
		durationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (diarySelect == null || !diarySelect.isDisplayable()) {
					diarySelect = newWeekDiary(e.getXOnScreen(),e.getYOnScreen(),now.getYear(),now.getMonthValue(),now.getDayOfMonth());
				}
			}
		});
		p1.add(deleteBtn);
		
		arrowP = new newPanel(null, 0,0,17,30);
		arrow = new newPanel("arrow.png", 0,0,17,17);
		arrowP.add(arrow);
		arrowP.setVisible(false);
		p1.add(arrowP);
		
		// P2 근무시간대 추가하기 패널
		p2.add(new newLabel("근무시간대 추가하기", 5, 0, 200, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(new newLabel("   요  일 :", 5, 35, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(new newLabel("   포지션 :", 5, 60, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(new newLabel("   근무명 :", 5, 85, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(new newLabel("근무시간 :", 5, 110, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(new newLabel("     T.O :", 5, 135, 65, 20, 13, Color.BLACK, "netmarble Medium"));

		cb1 = new JComboBox<String>();

		cb1.setBounds(70, 35, 100, 20);
		for (int i = 0; i < dw.length; i++) {
			cb1.addItem(dw[i]);
		}
		cb1.addItemListener(this);		
		
		p2.add(cb1);

		cb2 = new JComboBox<String>();
		cb2.setBounds(70, 60, 100, 20);
		// for (int i = 0; i < group1.length; i++) { cb2.add(group1[i]); }
		cb2.addItemListener(this);
		cb2.setEditable(true);
		p2.add(cb2);

		// 등록btn
		btn1 = new JButton("등록");
		btn1.setBounds(180, 60, 60, 20);
		btn1.addActionListener(this);
		p2.add(btn1);

		// ntf1 = new newTextField(70,85,100,20,13,"");
		tf1 = new JTextField(); // 근무명
		time_tf1 = new JTextField(); // 근무시간 From
		time_tf2 = new JTextField(); // 근무시간 From
		time_tf3 = new JTextField(); // 근무시간 To
		time_tf4 = new JTextField(); // 근무시간 To
		to_tf1 = new JTextField(); // T.O

		tf1.setBounds(70, 85, 200, 20);
		time_tf1.setBounds(70, 110, 30, 20);
		time_tf2.setBounds(110, 110, 30, 20);
		time_tf3.setBounds(170, 110, 30, 20);
		time_tf4.setBounds(210, 110, 30, 20);

		time_tf1.setHorizontalAlignment(JTextField.CENTER);
		time_tf2.setHorizontalAlignment(JTextField.CENTER);
		time_tf3.setHorizontalAlignment(JTextField.CENTER);
		time_tf4.setHorizontalAlignment(JTextField.CENTER);

		to_tf1.setBounds(70, 135, 50, 20);
		to_tf1.setHorizontalAlignment(JTextField.CENTER);

		p2.add(tf1);
		p2.add(time_tf1);
		p2.add(new newLabel(":", 103, 110, 10, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(time_tf2);
		p2.add(new newLabel("~", 150, 110, 10, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(time_tf3);
		p2.add(new newLabel(":", 203, 110, 10, 20, 13, Color.BLACK, "netmarble Medium"));
		p2.add(time_tf4);
		p2.add(to_tf1);
		p2.add(new newLabel("명", 130, 135, 20, 20, 13, Color.BLACK, "netmarble Medium"));

		btn2 = new JButton("추가하기");
		btn2.setBounds(110, 170, 97, 23);
		btn2.addActionListener(this);
		p2.add(btn2);

		// P3 근무자 배치하기 패널
		p3.add(new newLabel("근무자 정보", 5, 0, 200, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(new newLabel("   요  일 :", 5, 35, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(new newLabel("   포지션 :", 5, 60, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(new newLabel("   근무명 :", 5, 85, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(new newLabel("근무시간 :", 5, 110, 65, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(new newLabel("     T.O :", 5, 135, 65, 20, 13, Color.BLACK, "netmarble Medium"));

		cb3 = new JComboBox<String>(); // 요일
		cb3.setBounds(70, 35, 100, 20);
		for (int i = 0; i < dw.length; i++) {
			cb3.addItem(dw[i]);
		}

		cb3.addItemListener(this);
		p3.add(cb3);
		
		
		code_p3 = new JTextField();
		code_p3.setBounds(190, 35, 50, 20);
		p3.add(code_p3);

		cb4 = new JComboBox<String>(); // 포지션
		cb4.setBounds(70, 60, 100, 20);
		// 안에 항목 추가하는거 따로 빼놈
		cb4.addItemListener(this);
		p3.add(cb4);

		cb5 = new JComboBox<String>(); // 근무명
		cb5.setBounds(70, 85, 180, 20);
//		for (int i = 0; i < group1.length; i++) {	cb5.add(group1[i]); 	}
		cb5.addItemListener(this);
		p3.add(cb5);

		// ntf1 = new newTextField(70,85,100,20,13,"");
		time_tf5 = new JTextField(); // 근무시간 From
		time_tf6 = new JTextField(); // 근무시간 From
		time_tf7 = new JTextField(); // 근무시간 To
		time_tf8 = new JTextField(); // 근무시간 To
		to_tf2 = new JTextField(); // T.O

		time_tf5.setBounds(70, 110, 30, 20);
		time_tf6.setBounds(110, 110, 30, 20);
		time_tf7.setBounds(170, 110, 30, 20);
		time_tf8.setBounds(210, 110, 30, 20);

		time_tf5.setHorizontalAlignment(JTextField.CENTER);
		time_tf6.setHorizontalAlignment(JTextField.CENTER);
		time_tf7.setHorizontalAlignment(JTextField.CENTER);
		time_tf8.setHorizontalAlignment(JTextField.CENTER);

		to_tf2.setBounds(70, 135, 50, 20);
		to_tf2.setHorizontalAlignment(JTextField.CENTER);

		p3.add(time_tf5);
		p3.add(new newLabel(":", 103, 110, 10, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(time_tf6);
		p3.add(new newLabel("~", 150, 110, 10, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(time_tf7);
		p3.add(new newLabel(":", 203, 110, 10, 20, 13, Color.BLACK, "netmarble Medium"));
		p3.add(time_tf8);
		p3.add(to_tf2);
		p3.add(new newLabel("명", 130, 135, 20, 20, 13, Color.BLACK, "netmarble Medium"));

		// P4 근무자명단 패널 ( P3 근무자 배치하기 패널 안에 있음)
		p4 = new newPanel(null, 925, 405, 290, 180);
		p4.setBorder(new LineBorder(Color.BLACK));

		p4.add(new newLabel("직원 명단", 5, 0, 200, 20, 13, Color.BLACK, "netmarble Medium"));

		btn3 = new JButton("저장하기");
		btn3.setBounds(110, 320, 97, 23);
		btn3.addActionListener(this);
		p3.add(btn3);


		cb1.setFont(new Font("netmarble Medium", Font.PLAIN, 12));
		cb2.setFont(new Font("netmarble Medium", Font.PLAIN, 12));
		cb3.setFont(new Font("netmarble Medium", Font.PLAIN, 12));
		cb4.setFont(new Font("netmarble Medium", Font.PLAIN, 12));
		cb5.setFont(new Font("netmarble Medium", Font.PLAIN, 12));
		btn1.setFont(new Font("netmarble Bold", Font.PLAIN, 12));
		btn2.setFont(new Font("netmarble Bold", Font.PLAIN, 12));
		btn3.setFont(new Font("netmarble Bold", Font.PLAIN, 12));
		
		add(p1);
		add(p2);
		add(p3);
		add(p4);

		setLayout(null);
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		revalidate();
		repaint();
		setVisible(false);

		dayPicture.add(new newPanel("daySelect0.png", 5, 5, 40, 40));
		dayPicture.add(new newPanel("day1.png", 50, 5, 40, 40));
		dayPicture.add(new newPanel("day2.png", 95, 5, 40, 40));
		dayPicture.add(new newPanel("day3.png", 140, 5, 40, 40));
		dayPicture.add(new newPanel("day4.png", 185, 5, 40, 40));
		dayPicture.add(new newPanel("day5.png", 230, 5, 40, 40));
		dayPicture.add(new newPanel("day6.png", 275, 5, 40, 40));
		
		p1.add(dayPicture.get(0));
		p1.add(dayPicture.get(1));
		p1.add(dayPicture.get(2));
		p1.add(dayPicture.get(3));
		p1.add(dayPicture.get(4));
		p1.add(dayPicture.get(5));
		p1.add(dayPicture.get(6));

		for(int i = 0; i < 7; i++) {
			dayPicture.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for(int j = 0; j < 7; j++) {
						if(e.getSource() == dayPicture.get(j)) {
							dayPicture.get(j).setImage("daySelect" + j + ".png");
							dwtmp = dw[j];
							cb1.setSelectedItem(dwtmp);
							retrieve();			
						} else dayPicture.get(j).setImage("day" + j + ".png");
					}
					repaint();
				}
			});			
		}
		
		
		/* 콤보박스 초기 세팅 */

		setPositionCombobox(cb2);
		setPositionCombobox(cb4);

		/*** JCombobox에 커서를 두면, 안에 텍스트 모두 지워지는 소스 ***/
		tmptf = (JTextField) cb2.getEditor().getEditorComponent();
		tmptf.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				tmptf.setText("");
			}
		});
		code_p3.setVisible(false);
		retrieve();
		p1.repaint();
		repaint();
	}

	public void retrieve() {

		
		// 기타 선언
		int count = 0;
		int YPosi_bar = -70;
		int peopleCount = 0;
		int YPosi_PositionGroup = -75;
		newPanel bar[], barDummy[], barGage[];
				
		// 요일에 해당하는 포지션 리스트를 가져온다. > 벡터에 저장
		p1_V_positionList     =  mgr.select_p1_position(date1, date2, dwtmp);
		barDataList              =  mgr.select_p1_barData(date1, date2, dwtmp);
		
		
		p1_1.removeAll();
		p1_1.setSize(900,50);
		p1_1.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (orderYMax < -29) return;
				if (e.getWheelRotation() > 0) {
					orderY -= 20;					
					if(orderY < orderYMax) orderY = orderYMax;
				} else {
					orderY += 20;
					if(orderY > 61) orderY = 60;
				}
				p1_1.setLocation(15, orderY);
				repaint();				
			}
		});

		arrowP.setVisible(false);
		deleteBtn.setVisible(false);
		bar = new newPanel[barDataList.size()];
		delay(1);
		barDummy = new newPanel[barDataList.size()];
		delay(1);
		barGage = new newPanel[barDataList.size()];	
		delay(1);
		saveYoil = new String[barDataList.size()];
		savePos = new String[barDataList.size()];
		saveTitle = new String[barDataList.size()];
		
		// 포지션을 하나씩 출력한다. 
		for (int i = 0; i < p1_V_positionList.size(); i++) {				
			
			Bean_StoreSchedule positonV_bean = p1_V_positionList.get(i);

			// 기타 위치조정 
			YPosi_PositionGroup += 70;
			YPosi_bar += 70;
			p1_1.setSize(p1_1.getWidth(),YPosi_bar + 50);
			// 포지션명을 가져와서, 화면에 붙여줌
			p1_1.add(new newLabel(" [" + positonV_bean.getPosition() + "] ", 5, YPosi_PositionGroup, 300, 40, 25, Color.BLACK, "netmarble Medium"));
			

			//  바를 만들어 준다. ( 헤드라인 포지션과 StoreBean의 포지션이 같으면 출력)
			for (int j = 0; j < barDataList.size(); j++) {
				

				Bean_StoreSchedule bean = barDataList.get(j);
				
				// StoreSchecule 정보를 하나씩 가져와서.. 
				if (positonV_bean.getPosition().equals(bean.getPosition())) {

					Vector<Bean_WorkHistory> VectorTmp = barDataList.get(j).getBean();
					
					YPosi_bar += 50;
					YPosi_PositionGroup += 50;
					p1_1.setSize(p1_1.getWidth(),YPosi_bar + 50);

					String stratTime = bean.getStartTime().substring(0, 2) + ":" + bean.getStartTime().substring(2, 4);
					String endTime = bean.getEndTime().substring(0, 2) + ":" + bean.getEndTime().substring(2, 4);
					double a = Integer.parseInt(bean.getStartTime().substring(0,2))*60;
					double b = Integer.parseInt(bean.getStartTime().substring(2,4));					
					double startPoint = Math.round(740 * (a+b)/1440);
					a = Integer.parseInt(bean.getEndTime().substring(0,2))*60;
					b = Integer.parseInt(bean.getEndTime().substring(2,4));				
					double endPoint = Math.round(740 * (a+b)/1440) - startPoint ;
					p1_1.add(new newLabel(bean.getTitle() , 0, (YPosi_bar - 4), 100, 23, 15, Color.BLACK, "netmarble Medium",0));
					p1_1.add(new newLabel(stratTime + "~" + endTime, 0, (YPosi_bar + 13), 100, 23, 12, Color.BLACK, "netmarble Medium",0));
				
					//  Bar생성
					bar[count] = new newPanel(null,110,YPosi_bar,850,25);
					
					for(int d = 0; d < Integer.parseInt(bean.getMemberTo()); d ++) {
						if (VectorTmp.size() <= d) bar[count].add(new newPanel("off.png",(5 + (int)startPoint) + ((int) endPoint)/2 - 13*Integer.parseInt(bean.getMemberTo())/2 + (13*d),5,13,13));
						else bar[count].add(new newPanel("on.png",(5 + (int)startPoint) + ((int) endPoint)/2 - 13*Integer.parseInt(bean.getMemberTo())/2 + (13*d),5,13,13));
					}
										
					delay(1);					
					bar[count].add(barGage[count] = new newPanel("bar1.png",5 + (int) startPoint ,5, (int) endPoint,13));
					delay(1);
					bar[count].add(barDummy[count] = new newPanel("bar0.png",0, 0,850,23));
					
					saveYoil[count] = dwtmp;
					savePos[count] = bean.getPosition();
					saveTitle[count] = bean.getTitle();
					p1_1.add(bar[count]);
					bar[count].addMouseListener(new MouseAdapter() {						
						@Override
						public void mouseClicked(MouseEvent e) {
								for(int k = 0; k < barDataList.size(); k++) {
										if (bar[k] == e.getSource()) {
											cb3.setEnabled(true);
											cb4.setEnabled(true);
											cb5.setEnabled(true);
											cb3.setSelectedItem(saveYoil[k]);
											cb4.setSelectedItem(savePos[k]);
											cb5.setSelectedItem(saveTitle[k]);
											arrowP.setLocation(7 + bar[k].x + barGage[k].x + barGage[k].getWidth()/2 ,bar[k].y+34);
											deleteBtn.setLocation(bar[k].x + 700 , bar[k].y+28);
											arrowP.setVisible(true);
											deleteBtn.setVisible(true);
											return;
										}
								}							
						}
					});
					count++;
					Bean_WorkHistory bean2 = new Bean_WorkHistory();					
				}
			}
		}
		orderYMax = p1_1.getHeight() - 520;
		p1_1.repaint();
		p1.add(p1_1);
		p1.repaint();
		repaint();
	}
	
//	DBFrogMgr mgr = new DBFrogMgr();
//	Vector<SelectBean> slistBeans = mgr.dw(cb1.getSelectedItem());
//	for(int i =0;i<slistBeans.size();i++) {
//		slistBeans.get(i);
//	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		Object obj = e.getSource();

		cb3.setEnabled(true);
		cb4.setEnabled(true);
		cb5.setEnabled(true);
		if (obj == cb1) { // p2 요일
			JComboBox cbObj = (JComboBox) e.getSource();
			Object selected = cbObj.getSelectedItem();

			ArrayList<String> group1 = new ArrayList<>();
			Vector<Bean_StoreSchedule> vlist1 = mgr.getPosition();

			for (int i = 0; i < vlist1.size(); i++) {
				Bean_StoreSchedule bean = vlist1.get(i);
				group1.add(bean.getPosition());
			}

			cb2.removeAllItems();
			for (int i = 0; i < group1.size(); i++) {
				cb2.addItem(group1.get(i));
			}
			repaint();

		} else if (obj == cb2) { // p2 포지션
			JComboBox cbObj = (JComboBox) e.getSource();
			Object selected = cbObj.getSelectedItem();

		} else if (obj == cb3) { // p3 요일
			cb4.removeAllItems();
			JComboBox cbObj = (JComboBox) e.getSource();
			Object selected = cbObj.getSelectedItem();

			ArrayList<String> group2 = new ArrayList<>();
			Vector<Bean_StoreSchedule> vlist2 = mgr.getPosition();

			for (int i = 0; i < vlist2.size(); i++) {
				Bean_StoreSchedule bean = vlist2.get(i);
				group2.add(bean.getPosition());
			}

			for (int i = 0; i < group2.size(); i++) {
				cb4.addItem(group2.get(i));
			}
			repaint();

		} else if (obj == cb4) { // p3 포지션
			cb5.removeAllItems();
			JComboBox cbObj = (JComboBox) e.getSource();
			Object selected = cbObj.getSelectedItem();

			if (cb3.getSelectedItem() != null && cb4.getSelectedItem() != null) {
				ArrayList<String> group3 = new ArrayList<>();
				Vector<Bean_StoreSchedule> vlist3 = mgr.getSelect2(cb3.getSelectedItem().toString(),
						cb4.getSelectedItem().toString());

				for (int i = 0; i < vlist3.size(); i++) {
					Bean_StoreSchedule bean = vlist3.get(i);
					group3.add(bean.getTitle());
				}

				for (int i = 0; i < group3.size(); i++) {
					cb5.addItem(group3.get(i));
				}
				repaint();
			}

			// ###
		} else if (obj == cb5) { // p3 근무명

			JComboBox cbObj = (JComboBox) e.getSource();
			Object selected = cbObj.getSelectedItem();
			Bean_StoreSchedule bean;

			if (cb3.getSelectedItem() != null && cb4.getSelectedItem() != null && cb5.getSelectedItem() != null) {

				// 바 정보를 조회하여, 근무시간 & TO를 뿌려준다.
				bean = mgr.select_p4_barData(date1, date2, cb3.getSelectedItem().toString(),
						cb4.getSelectedItem().toString(), cb5.getSelectedItem().toString());

				if (bean.getStartTime() != null && bean.getEndTime() != null) {
					time_tf5.setText(bean.getStartTime().substring(0, 2));
					time_tf6.setText(bean.getStartTime().substring(2, 4));
					time_tf7.setText(bean.getEndTime().substring(0, 2));
					time_tf8.setText(bean.getEndTime().substring(2, 4));
					to_tf2.setText(bean.getMemberTo());

					time_tf5.setEditable(false);
					time_tf6.setEditable(false);
					time_tf7.setEditable(false);
					time_tf8.setEditable(false);
					to_tf2.setEditable(false);

					
					code_p3.setText(bean.getCode());
					
					p4.removeAll();
					YPosi_p4 = -20;

					Vector<MemberBean> vlist = mgr.select_p4_member(bean.getCode());
					Vector<MemberBean> vlist2 = mgr.select_p4_allmember();
					
					
					
					
					// 콤보박스에 넣을 멤버 데이터
					String[] allmember_arr = new String[vlist2.size()+1];
					String[] allmemberId_arr = new String[vlist2.size()+1];
					
					p4CbList.clear();
					p4_IdList.clear();
					
					for (int i = 0; i < Integer.parseInt(bean.getMemberTo()); i++) {
						String name = "";
						String name2 = "직원을 선택하세요.";
						
						allmember_arr[0] = name2;
						allmemberId_arr[0] = "NoID";
						for (int j = 0; j < vlist2.size(); j++) {
							allmember_arr[j+1] = vlist2.get(j).getName() + "(" + vlist2.get(j).getGrade() + ")";
							allmemberId_arr[j+1] = vlist2.get(j).getId();							
							
						}
						if ( i < vlist.size() ) {
							name = vlist.get(i).getName() + "(" + vlist.get(i).getGrade() + ")";
							createPanel_p4_cb(allmember_arr, allmemberId_arr, name);
							
						}
						else {
							createPanel_p4_cb(allmember_arr, allmemberId_arr, name2);
						}
						validate();
						repaint();
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();

		if (obj == btn1) {

			mgr.insertPosition(tmptf.getText());

			setPositionCombobox(cb2);
			setPositionCombobox(cb4);

		} else if (obj == btn2) {
			
			if(tf1.getText().length() < 1) {
				command.setText("근무명을 입력해!");
				command.setVisible(true);
				tf1.requestFocus();
				return;
			}			
			if (!isNumber(time_tf1.getText()) || !isNumber(time_tf2.getText()) || !isNumber(time_tf3.getText()) || !isNumber(time_tf4.getText())) {
				command.setText("숫자를 입력해야해!");
				command.setVisible(true);
				return;
			}
			if(Integer.parseInt(time_tf1.getText()) > 24 && Integer.parseInt(time_tf1.getText()) < 0) {
				command.setText("시간이 잘못됐어!");
				command.setVisible(true);
				time_tf1.requestFocus();
				return;
			}
			if(Integer.parseInt(time_tf2.getText()) > 59 && Integer.parseInt(time_tf2.getText()) < 0) {
				command.setText("시간이 잘못됐어!");
				command.setVisible(true);
				time_tf2.requestFocus();
				return;
			}
			if(Integer.parseInt(time_tf3.getText()) > 24 && Integer.parseInt(time_tf3.getText()) < 0) {
				command.setText("시간이 잘못됐어!");
				command.setVisible(true);
				time_tf3.requestFocus();
				return;
			}
			if(Integer.parseInt(time_tf4.getText()) > 59 && Integer.parseInt(time_tf4.getText()) < 0) {
				command.setText("시간이 잘못됐어!");
				command.setVisible(true);
				time_tf4.requestFocus();
				return;
			}			
			if(!isNumber(to_tf1.getText())) {
				to_tf1.requestFocus();
				command.setText("숫자를 입력해야해!");
				command.setVisible(true);
				return;
			}
			
			// 저장하기
			Bean_StoreSchedule bean = new Bean_StoreSchedule();
			bean.setWork_dt(nowDay(date1));
			bean.setDw(cb1.getSelectedItem().toString());
			bean.setPosition(cb2.getSelectedItem().toString()); // 콤보박스 나타나는거 해결 후 수정
			bean.setTitle(tf1.getText());
			bean.setStartTime(time_tf1.getText() + time_tf2.getText());
			bean.setEndTime(time_tf3.getText() + time_tf4.getText());
			bean.setMemberTo(to_tf1.getText());
			mgr.insertStoreSchedule(bean);			
			retrieve();		
		} else if (obj == btn3) {
//			Vector<MemberBean> vlist = mgr.select_p4_member(code_p3.getText());
//			for (int i = 0; i < vlist.size(); i++) {
//				MemberBean bean = vlist.get(i);
//				mgr.insertWorkHistory(code_p3.getText(), bean.getId());
//			}
			
			
			mgr.deleteWorkHistory(code_p3.getText());
			// p4에서 아이디를 가져와서 
			for (int i = 0; i < p4CbList.size(); i++) {
				String tmpID = p4_IdList.get(i).getSelectedItem().toString();
				if(!tmpID.equals("NoID")) mgr.insertWorkHistory(code_p3.getText(), tmpID);				
			}			
			retrieve();		
		}
	}


	public void createPanel_p4_cb(String[] nameAndGrade, String[] id, Object obj) {
		
		YPosi_p4 += 25;
		JComboBox<String> emp_cb1 = new JComboBox(nameAndGrade);
		JComboBox<String> emp_cb2 = new JComboBox(id);
		p4.add(new newLabel("이름(직급) : ", 5, YPosi_p4, 75, 20, 13, Color.BLACK, "netmarble Medium"));
		emp_cb1.setBounds(80, YPosi_p4, 150, 20);
		emp_cb2.setBounds(235, YPosi_p4, 50, 20);
		emp_cb1.setFont(new Font("netmarble Medium", Font.PLAIN, 12));
		// emp_cb1.setHorizontalAlignment(JTextField.CENTER);
		emp_cb1.setSelectedItem(obj);
		emp_cb2.setSelectedIndex(emp_cb1.getSelectedIndex());
		emp_cb1.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				JComboBox tmp = (JComboBox)e.getSource();
				emp_cb2.setSelectedIndex(tmp.getSelectedIndex());
				
				
			}
		});
		
		
		
		emp_cb2.setVisible(false);
		p4CbList.add(emp_cb1);
		p4_IdList.add(emp_cb2);
		p4.add(emp_cb1);
		p4.add(emp_cb2);
		
		p4.repaint();

	}

	public void setPositionCombobox(JComboBox cb) {

		ArrayList<String> group = new ArrayList<>();
		Vector<Bean_StoreSchedule> vlist_postion = mgr.getPosition();

		for (int i = 0; i < vlist_postion.size(); i++) {
			Bean_StoreSchedule bean = vlist_postion.get(i);
			group.add(bean.getPosition());
		}

		cb.removeAllItems();
		for (int i = 0; i < group.size(); i++) {
			cb.addItem(group.get(i));
		}
		repaint();

	}

	public void createPanel_p4(String nameAndGrade) {
		YPosi_p4 += 25;
		emp_tf1 = new JTextField();
		p4.add(new newLabel("이름(직급) : ", 5, YPosi_p4, 70, 20, 13, Color.BLACK, "netmarble Medium"));
		emp_tf1.setBounds(75, YPosi_p4, 180, 20);
		emp_tf1.setText(nameAndGrade);
		emp_tf1.setHorizontalAlignment(JTextField.CENTER);
		p4.add(emp_tf1);
		p4.repaint();
	}

	
	public static void delay(long delay) {
		try {
			Thread.sleep(delay);
		} catch (Exception e) {}
	}    

	public Frame newWeekDiary( int x, int z, int Y, int  M, int D) {
		
		now = LocalDate.of(Y, M, D); 
		int offset = 90;
		String str[] = {"일","월","화","수","목","금","토"};
		
		Frame frame;
		ImageIcon img = new ImageIcon("./frogM/image/diaryicon.png");
		newLabel date, left, right;
		newLabel [] days = new newLabel[42];
		newLabel [] weeks = new newLabel[7];
		newPanel ok;

		frame = new Frame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();				
			}
		});
		frame.setLayout(null);
		frame.setSize(300,300);
		frame.setLocation(x,z);
		frame.setResizable(false);
		frame.setIconImage(img.getImage());
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		left = new newLabel("<", 30, 30, 30, 30, 15, new Color(0,0,0),"netmarble Light",0);
		date = new newLabel(Y + "." + (M < 10 ? "0" + M :M + ""), 30, 30, 240, 30, 18, new Color(0,0,0),"netmarble Bold",0);
		right = new newLabel(">", 240, 30, 30, 30, 15, new Color(0,0,0),"netmarble Light",0);
		ok = new newPanel("ok.png", 220,247, 45, 25);	
		ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				date1 = monday(now).replace("-", "");
				date2 = sunday(now).replace("-", "");				
				duration.setText(monday(now) + " ~ " + sunday(now));
				dwtmp = "월";		
				dayPicture.get(0).setImage("daySelect0.png");
				for(int j = 1; j < 7; j++) {
					dayPicture.get(j).setImage("day" + j + ".png");
				}
				repaint();
				retrieve();
				frame.dispose();				
			}
		});
		setcursor(frame, "mouseicon0.png");
		setClickcursor(ok,"mouseicon1.png");
		setLabelcursor(left,"mouseicon1.png");
		setLabelcursor(right,"mouseicon1.png");
		frame.add(ok);
		frame.add(left);
		frame.add(right);
		frame.add(date);		
		for(int i = 0; i < 42; i++) {			
			if((i+1)%7 == 0) { 
				days[i] = new newLabel("", 30 + ((i%7)*35), offset, 30, 30, 15, new Color(51,152,252),"netmarble Medium",0);
				offset += 30;
			} else if ((i%7 == 0)) {
				days[i] = new newLabel("", 30 +  ((i%7)*35), offset, 30, 30, 15, new Color(255,87,97),"netmarble Medium",0);	
			} else {
				days[i] = new newLabel("", 30 +  ((i%7)*35), offset, 30, 30, 15, new Color(50, 50, 50),"netmarble Medium",0);
			}
			if(i < 7) {
				if(i == 0)weeks[i] = new newLabel(str[i], days[i].getX(), 60, 30, 30, 15, new Color(255,87,97) ,"netmarble Medium",0);
				else if (i == 6) weeks[i] = new newLabel(str[i], days[i].getX(), 60, 30, 30, 15, new Color(51,152,252) ,"netmarble Medium",0);				
				else weeks[i] = new newLabel(str[i], days[i].getX(), 60, 30, 30, 15, new Color(50,50,50) ,"netmarble Medium",0);
				frame.add(weeks[i]);
			}
			days[i].setBackground(new Color(183,240,177));
			days[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {					
					for(int n = 0; n < 42; n++) {
						if(days[n].getText() != "") {							
							if (e.getSource() == days[n]) now = LocalDate.of(now.getYear(), now.getMonth(), Integer.parseInt(days[n].getText()));							
						} 
						days[n].setOpaque(false);
						frame.repaint();					
					}
					for(int n = 0; n < 42; n++) {
						if(days[n].getText() != "") {
							LocalDate ld = LocalDate.parse(now.getYear() + "-" + (now.getMonthValue() < 10 ? "0"							
						    + now.getMonthValue() : now.getMonthValue()) + "-" 
							+ (days[n].getText().length() == 1 ? "0" + days[n].getText() : days[n].getText()));
							if (LocalDate.parse(monday(now)).isBefore(ld.plusDays(1)) && LocalDate.parse(sunday(now)).isAfter(ld.minusDays(1)) ) {
								days[n].setOpaque(true);								
							}
						}		  
					}
					frame.repaint();				
				}
			});
			frame.add(days[i]);
		}
		createDiary(frame, days, now.getYear(), now.getMonthValue());
		for(int n = 0; n < 42; n++) {
			if(days[n].getText() != "") {
				LocalDate ld = LocalDate.parse(now.getYear() + "-" + (now.getMonthValue() < 10 ? "0"							
			    + now.getMonthValue() : now.getMonthValue()) + "-" 
				+ (days[n].getText().length() == 1 ? "0" + days[n].getText() : days[n].getText()));
				if (LocalDate.parse(monday(now)).isBefore(ld.plusDays(1)) && LocalDate.parse(sunday(now)).isAfter(ld.minusDays(1)) ) {
					days[n].setOpaque(true);								
				}
			}		  
		}
		frame.repaint();
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				now = now.minusMonths(1);
				date.setText(now.getYear() + "." + (now.getMonthValue() < 10 ? "0" + now.getMonthValue() :now.getMonthValue() + ""));
				frame.setTitle("");
				createDiary(frame, days, now.getYear(), now.getMonthValue());
			}
		});	
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				now = now.plusMonths(1);
				date.setText(now.getYear() + "." + (now.getMonthValue() < 10 ? "0" + now.getMonthValue() :now.getMonthValue() + ""));
				frame.setTitle("");
				createDiary(frame, days, now.getYear(), now.getMonthValue());
			}
		});	
		return frame;
	}
	public void createDiary(Frame frame, newLabel days[], int Y, int M) {
		
		for(int i = 0; i < 42; i++) 	days[i].setOpaque(false);
		
		int day = 0, week=0;
		int monthSet[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		
        if(Y%4 == 0 && Y%100 != 0 || Y%400 == 0) monthSet[1] = 29;
        else monthSet[1] = 28;
	
        day = (Y-1)*365 + (Y-1)/4 - (Y-1)/100 + (Y-1)/400;
        for(int i = 0; i < M-1 ; i++) {
        	day += monthSet[i];  
        }
        week=day%7; // 구하고자 하는 달의 시작일(1일)의 요일을 구함.
        
        for(int i = 0; i < 42; i++) { days[i].setText(""); }
        for(int i=1;i<=monthSet[M-1];i++) {            
	        days[week+1].setText("" + i);	
	        week++; 
        }
	}
	
	public String monday(LocalDate now) {
		String[] week = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
		LocalDate result = now;
		for (int i = 0; i < week.length; i++) {
			if (now.getDayOfWeek().toString().equals(week[i])) {
				result = now.minusDays(i);
			}
		}
		return DateTimeFormatter.ofPattern("YYYY-MM-dd").format(result);
	}

	public String sunday(LocalDate now) {
		String[] week = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
		LocalDate result = now;
		for (int i = 0; i < week.length; i++) {			
			if (now.getDayOfWeek().toString() == week[i]) {
				result = now.minusDays(i);
				result = result.plusDays(6);
			}
		}
		return DateTimeFormatter.ofPattern("YYYY-MM-dd").format(result);
	}
	public String nowDay(String inputDate) {
		
		LocalDate result = LocalDate.parse(inputDate.substring(0,4) + "-" + inputDate.substring(4,6) + "-" + inputDate.substring(6,8));
	
		for (int i = 0; i < dw.length; i++) {			
			if (dw[i].equals(dwtmp)) {
				result = result.plusDays(i);
				break;			
			}
		}		
		return DateTimeFormatter.ofPattern("YYYYMMdd").format(result);
	}
	
	public static boolean isNumber(String number) {

		boolean flag = true;
		if (number == null || "".equals(number)) {
			return false;
		}

		int size = number.length();
		int st_no = 0;

		// 45(-)음수여부 확인, 음수이면 시작위치를 1부터 시작
		if (number.charAt(0) == 45) {
			st_no = 1;
		}

		// 48(0)~57(9)가 아니면 false
		for (int i = st_no; i < size; ++i) {
			if (!(48 <= ((int) number.charAt(i)) && 57 >= ((int) number.charAt(i)))) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	public void setcursor(Frame jc, String img){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("./frogM/image/" + img);
		Point point=new Point(5,5);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "");
		jc.setCursor(cursor); 
	}
	public void setClickcursor(JPanel jp, String img){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("./frogM/image/" + img);
		Point point=new Point(5,5);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "");
		jp.setCursor(cursor); 
	}
	public void setLabelcursor(JLabel jl, String img){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage=tk.getImage("./frogM/image/" + img);
		Point point=new Point(5,5);
		Cursor cursor=tk.createCustomCursor(cursorimage, point, "");
		jl.setCursor(cursor); 
	}
}

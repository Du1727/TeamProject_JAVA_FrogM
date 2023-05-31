//직원

package frogM;

import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Tab3 extends JPanel implements MouseListener {
	static DBConnectionMgr pool;
	LocalDate now = LocalDate.now();
	int data_size = 0, max_scroll = 1230, order_x = 5;
	int data_size2 = 13;
	Frame fr = new Frame();

	JPanel dummy;
	newPanel main;
	newPanel[] profile;
	newPanel plusProfile;
	newPanel profileInfo;
	newPanel plusImage;
	newPanel imageDummy;
	Vector<MemberBean> vlist;
	FrogMgr mgr = new FrogMgr();
	ImageIcon icon;
	newLabel[] textL = new newLabel[data_size2];
	newTextField[] textF = new newTextField[8];
	newPanel[] imageBtn = new newPanel[3];
	newPanel[] calBtn = new newPanel[4];
	newLabel imagePlace;
	File imgfile;
	Choice ch;
	JComboBox<String> cbGrade, cbPosition, cbGender, cbday;
	Frame diarySelect = null;
	Vector<Integer> vlist_info;

	String str[] = { "btn0.png", "btn1.png", "btn2.png" };
	FileDialog read, save;
	Image img;
	MyCanvas canvas;
	String filename, id;
	String dirfile, selectdate;
	File file;
	int selectNum;
	String orgFilePath;
	String outFilePath = "./frogM/image/";

	public Tab3() {
		vlist = mgr.memberSelectall();
		data_size = vlist.size();
		profile = new newPanel[data_size];
		profileInfo = new newPanel("profile2.png", 360, 26, 515, 564);
		dummy = new JPanel();
		dummy.setBackground(new Color(0,0,0,0));
		dummy.setBounds(0, 0, 1320, 610);
		dummy.setVisible(false);
		main = new newPanel(null, 5, 0, (data_size < 3 ? max_scroll : max_scroll + ((max_scroll - 4) * 320)), 610);
		imagePlace = new newLabel(null, 142, 15, 230, 232, 0, null, null);
		imageDummy = new newPanel("pictureDummy.png", 142, 15, 230, 232);

		for (int i = 0; i < data_size; i++) {
			MemberBean bean = vlist.get(i);
			profile[i] = new newPanel("profile.png", 50 + (i * 320), 50, 300, 500);
			profile[i].add(new newPanel("pictureDummy.png", 35, 12, 270, 270));
			delay(1);
			profile[i].add(new newPanel(bean.getId() + ".jpg", 35, 12, 230, 230));
			if (bean.getGender() == null)
				profile[i].add(new newPanel(null, 245, 238, 38, 45));
			else if (bean.getGender().equals("여자"))
				profile[i].add(new newPanel("woman_icon.png", 245, 238, 38, 45));
			else if (bean.getGender().equals("남자"))
				profile[i].add(new newPanel("man_icon.png", 245, 238, 38, 45));
			profile[i].add(new newLabel(bean.getName(), 0, 300, 300, 70, 40, Color.black, "CookieRunOTF Regular", 0));
			profile[i].add(new newLabel(bean.getPosition(), 0, 350, 300, 70, 20, Color.black, "CookieRunOTF Regular", 0));
			profile[i].add(new newLabel(bean.getPhone(), 0, 400, 300, 70, 20, Color.black, "CookieRunOTF Regular", 0));
			profile[i].setVisible(true);
			profile[i].addMouseListener(this);
			main.add(profile[i]);
			main.repaint();
		}
		plusProfile = new newPanel("profile.png", 50 + (data_size * 320), 50, 300, 500);
		plusImage = new newPanel("plusProfile.png", 35, 120, 250, 270);
		plusProfile.add(plusImage);

		plusImage.addMouseListener(this);

		main.add(plusProfile);
//		main.add(page);
		main.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (data_size < 3 || dummy.isVisible())
					return;
				if (e.getWheelRotation() > 0) {
					if (order_x > -115 - ((data_size - 3) * 320))
						order_x -= 20;
					else
						order_x = -115 - ((data_size - 3) * 320);
				} else {
					if (order_x < 5)
						order_x += 20;
					else
						order_x = 5;
				}
				main.setLocation(order_x, main.getY());
			}

		});

		dummy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX() < 335 || e.getX() > 880) {
					dummy.setVisible(false);
					profileInfo.setVisible(false);
					repaint();
				}
			}
		});

		imageDummy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (read == null) {
					read = new FileDialog(fr, "파일불러오기", FileDialog.LOAD);
				}
				read.setDirectory("C:");
				read.setVisible(true);

				try {
					filename = read.getFile();
					orgFilePath = read.getDirectory() + filename;
//					imagePlace = new newPanel(orgFilePath, 0, 330 - 300, 200, 300);
					ImageIcon icon = new ImageIcon(orgFilePath);
					imagePlace.setIcon(icon);
					profileInfo.repaint();
					repaint();
				} catch (Exception e1) {

				}
			}
		});

		FrogMgr mgr = new FrogMgr();
		String[] dataList = { "ID", "PW", "이름", "전화번호", "직급", "담당", "생일", "입사일", "퇴사일", "주간 근무일수", "보건증 만료일", "성별" };
//		main = new newPanel("", 1230, 0, 600, 800);
		int textL_x = 20;
		int short_w = 30;
		int sizeW = 0;
		int sizeH = 0;

		int y1 = 270;
		int y2 = 310;
		int y3 = 350;
		int y4 = 390;
		int y5 = 430;
		int y6 = 470;

		int plusX = 20;
		// 라벨 추가
		textL[0] = new newLabel(dataList[0], 20+plusX, y1, short_w, 20, 15, Color.black, "netmarble Medium");// id
		textL[1] = new newLabel(dataList[1], 160+plusX, y1, short_w, 20, 15, Color.black, "netmarble Medium");// pw
		textL[2] = new newLabel(dataList[2], 310+plusX, y1, short_w, 20, 15, Color.black, "netmarble Medium");// name
		textL[3] = new newLabel(dataList[3], 20+plusX, y3, 70, 20, 15, Color.black, "netmarble Medium");// 전화번호
		textL[4] = new newLabel(dataList[4], 20+plusX, y2, short_w, 20, 15, Color.black, "netmarble Medium");// 직급
		textL[5] = new newLabel(dataList[5], 160+plusX, y2, short_w, 20, 15, Color.black, "netmarble Medium");// 담당
		textL[6] = new newLabel(dataList[6], 240+plusX, y3, short_w, 20, 15, Color.black, "netmarble Medium");// 생일

		textL[7] = new newLabel(dataList[7], 20+plusX, y4, 70, 20, 15, Color.black, "netmarble Medium");// 입사일
		textL[8] = new newLabel(dataList[8], 235+plusX, y4, 50, 20, 15, Color.black, "netmarble Medium");// 퇴사일
		textL[9] = new newLabel(dataList[9], 20+plusX, y5, 100, 20, 15, Color.black, "netmarble Medium");// 주간 근무일수
		textL[10] = new newLabel(dataList[10], 210+plusX, y5, 100, 20, 15, Color.black, "netmarble Medium");// 보건증 만료일
		textL[11] = new newLabel(dataList[11], 310+plusX, y2, short_w, 20, 15, Color.black, "netmarble Medium");// 성별

		for (int i = 0; i < dataList.length; i++) {
			textL[i].setHorizontalAlignment(JLabel.CENTER);

			profileInfo.add(textL[i]);
		}
		textL[0].setHorizontalAlignment(JLabel.LEFT);
		textL[3].setHorizontalAlignment(JLabel.LEFT);
		textL[4].setHorizontalAlignment(JLabel.LEFT);
		textL[7].setHorizontalAlignment(JLabel.LEFT);
		textL[9].setHorizontalAlignment(JLabel.LEFT);

		/// 텍스트 필드 추가
		int textF_x = 360;
		textF[0] = new newTextField(50+plusX, y1, 100, 20, 12, "netmarble Medium");// id
		textF[1] = new newTextField(200+plusX, y1, 100, 20, 12, "netmarble Medium");// pw
		textF[2] = new newTextField(350+plusX, y1, 100, 20, 12, "netmarble Medium");// 이름
		textF[3] = new newTextField(100+plusX, y3, 100, 20, 10, "netmarble Medium");// 전화번호
		textF[4] = new newTextField(330+plusX, y3, 100, 20, 12, "netmarble Medium");// 생일
		textF[5] = new newTextField(100+plusX, y4, 100, 20, 12, "netmarble Medium");// 입사일
		textF[6] = new newTextField(330+plusX, y4, 100, 20, 12, "netmarble Medium");// 퇴사일
		textF[7] = new newTextField(330+plusX, y5, 100, 20, 12, "netmarble Medium");// 보건증 만료일
		for (int i = 0; i < textF.length; i++) {
			profileInfo.add(textF[i]);

		}
		
		calBtn[0] = new newPanel("diaryiconmin.png", 460, y3, 20, 19);
		calBtn[1] = new newPanel("diaryiconmin.png", 230, y4, 20, 19);
		calBtn[2] = new newPanel("diaryiconmin.png", 460, y4, 20, 19);
		calBtn[3] = new newPanel("diaryiconmin.png", 460, y5, 20, 19);
		for (int i = 0; i < calBtn.length; i++) {
			calBtn[i].addMouseListener(this);
			profileInfo.add(calBtn[i]);
		}
		
		
		// 성별 콤보박스
		cbGender = new JComboBox<String>();
		cbGender.setBounds(350+plusX, y2, 100, 20);
		String[] gen = { "남자", "여자" };
		for (int i = 0; i < gen.length; i++) {
			cbGender.addItem(gen[i]);
		}

		// 근무일수 콤보박스
		cbday = new JComboBox<String>();
		cbday.setBounds(140+plusX, y5, 40, 20);
		String[] day = { "1", "2", "3", "4", "5", "6", "7" };
		for (int i = 0; i < day.length; i++) {
			cbday.addItem(day[i]);
		}

		// 포지션 콤보 박스
		cbPosition = new JComboBox<String>();
		cbPosition.setBounds(200+plusX, y2, 100, 20);
		ArrayList<String> group1 = new ArrayList<>();
		Vector<Bean_StoreSchedule> vlist1 = mgr.getPosition();

		for (int i = 0; i < vlist1.size(); i++) {
			Bean_StoreSchedule bean = vlist1.get(i);
			group1.add(bean.getPosition());
		}

		for (int i = 0; i < group1.size(); i++) {
			cbPosition.addItem(group1.get(i));
		}
		repaint();

		// 그룹 콤보박스
		ArrayList<String> group2 = new ArrayList<>();
		Vector<MemberBean> vlist2 = mgr.getGrade();
		cbGrade = new JComboBox<String>();
		cbGrade.setBounds(55+plusX, y2, 100, 20);
		for (int i = 0; i < vlist2.size(); i++) {
			MemberBean bean = vlist2.get(i);
			group2.add(bean.getGrade());
		}

		for (int i = 0; i < group2.size(); i++) {
			cbGrade.addItem(group2.get(i));
		}
		repaint();
		cbGrade.addItem("");
		cbPosition.addItem("");
		cbGender.addItem("");
		cbday.addItem("");
		cbday.setFont(new Font("netmarble Medium", Font.PLAIN, 15));
		cbGender.setFont(new Font("netmarble Medium", Font.PLAIN, 15));
		cbGrade.setFont(new Font("netmarble Medium", Font.PLAIN, 15));
		cbPosition.setFont(new Font("netmarble Medium", Font.PLAIN, 15));
		profileInfo.add(cbGrade);
		profileInfo.add(cbPosition);
		profileInfo.add(cbGender);
		profileInfo.add(cbday);
		// 버튼 생성
		//
		
		int imageBtn_y = 510;
		int imageBtn_x = 220;

		for (int i = 0; i < str.length; i++) {
			imageBtn[i] = new newPanel(str[i], imageBtn_x + (i * 90), imageBtn_y, 87, 48);
			profileInfo.add(imageBtn[i]);
			imageBtn[i].addMouseListener(this);

		}
		//0번 220 , 510 1번  310, 510 3번 400 510
		
		
		
		profileInfo.add(imageDummy);
		delay(1);
		profileInfo.add(imagePlace);
		profileInfo.setVisible(false);

		add(profileInfo);
		delay(1);
		add(dummy);
		delay(1);
		add(main);
		setLayout(null);
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		revalidate();
		repaint();
		setVisible(false); // 이거 남겨놔주세요!
	}

	public void openLoad(String id) { // 프로필 선택시에 출력되는 창
		FrogMgr mgr = new FrogMgr();
		MemberBean bean = new MemberBean();
		bean = mgr.memberSelectone(id);
		textF[0].setText(bean.getId());
		textF[1].setText(bean.getPw());
		textF[2].setText(bean.getName());
		textF[3].setText(bean.getPhone());
		if (bean.getBirthday() == null) {
			textF[4].setText("");
		} else {
			textF[4].setText(bean.getBirthday().split(" ")[0]);
		}
		if (bean.getJoin_dt() == null) {
			textF[5].setText("");
		} else {
			textF[5].setText(bean.getJoin_dt().split(" ")[0]);
		}
		if (bean.getRetire_dt() == null) {
			textF[6].setText("");
		} else {
			textF[6].setText(bean.getRetire_dt().split(" ")[0]);
		}
		
		if (bean.getHealth() == null) {
			textF[7].setText("");
		} else {
			textF[7].setText(bean.getHealth().split(" ")[0]);
		}
		;
		if (bean.getHope_workday() == null) {
			cbday.setSelectedItem("");
		} else {
			cbday.setSelectedItem(bean.getHope_workday());
		}
		if (bean.getGender() == null) {
			cbGender.setSelectedItem("");
		} else {
			cbGender.setSelectedItem(bean.getGender());
		}
		if (bean.getGrade() == null) {
			cbGrade.setSelectedItem("");
		} else {
			cbGrade.setSelectedItem(bean.getGrade());
		}
		if (bean.getPosition() == null) {
			cbPosition.setSelectedItem("");
		} else {
			cbPosition.setSelectedItem(bean.getPosition());
		}
		repaint();
		revalidate();

	}

	public static void delay(long delay) {
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mgr = new FrogMgr();
		Object obj = e.getSource();
		String id = textF[0].getText();
		String pw = textF[1].getText();
		String name = textF[2].getText();
		String phone = textF[3].getText();
		String birthday = textF[4].getText().replaceAll("-", "");
		String join_dt = textF[5].getText().replaceAll("-", "");
		String retire_dt = textF[6].getText().replaceAll("-", "");
		String hope_workday = cbday.getSelectedItem().toString();
		String health = textF[7].getText().replaceAll("-", "");
		String gender = cbGender.getSelectedItem().toString();
		for (int i = 0; i < profile.length; i++) {
			if (obj == profile[i]) {
				MemberBean bean = vlist.get(i);
				openLoad(bean.getId());
				imageBtn[0].setBounds(310,510,87,48);
				imageBtn[0].setVisible(true);
				imageBtn[1].setVisible(false);				
				imageBtn[2].setBounds(400,510,87,48);
				imageBtn[2].setVisible(true);
				icon = new ImageIcon(outFilePath + bean.getId() + ".jpg");
				imagePlace.setIcon(icon);
				
				dummy.setBackground(new Color(0, 0, 0, 0));
				dummy.setVisible(true);
			}
		}
		if (obj == plusImage) {// 프로필 생성 창 띄우기
			imagePlace.setIcon(null);
			imageBtn[0].setVisible(false);;
			imageBtn[1].setBounds(400,510,87,48);				
			imageBtn[1].setVisible(true);				
			imageBtn[2].setVisible(false);
			
			for (int i = 0; i < textF.length; i++) {
				textF[i].setText("");
			}
			dummy.setBackground(new Color(0, 0, 0, 0));
			dummy.setVisible(true);
			repaint();
		} else if (obj == imageBtn[1]) { // 새 데이터 추가
			mgr.saveData(id, pw, name, phone, cbGrade.getSelectedItem().toString(),
					cbPosition.getSelectedItem().toString(), birthday, join_dt, retire_dt, hope_workday, health,
					gender);
			if (orgFilePath != null) {
				copyFileByStream(orgFilePath, outFilePath + id + ".jpg");
			}
			refresh();
			revalidate();
			repaint();
		} else if (obj == imageBtn[0]) { // 기존 데이터 수정

			mgr.updateMember(id, pw, name, phone, cbGrade.getSelectedItem().toString(),
					cbPosition.getSelectedItem().toString(), birthday, join_dt, retire_dt, hope_workday, health,
					gender);
			if (orgFilePath != null) {
				copyFileByStream(orgFilePath, outFilePath + id + ".jpg");
			}
			refresh();
			revalidate();
			repaint();
		} else if (obj == imageBtn[2]) {// 데이터 삭제
			mgr.deleteMember(id);
			imagePlace.setIcon(null);
			file = new File(id+".jpg");
			if( file.exists() ){
	    		if(file.delete()){
	    			System.out.println("파일삭제 성공");
	    		}else{
	    			System.out.println("파일삭제 실패");
	    		}
	    	}else{
	    		System.out.println("파일이 존재하지 않습니다.");
	    	}

			dummy.setVisible(false);
			refresh();
			repaint();
		}
		//67810
		for (int i = 0; i < calBtn.length; i++) {
			if(obj == calBtn[0]) {
				if (diarySelect == null || !diarySelect.isDisplayable()) {
					diarySelect = newDiary(e.getXOnScreen(),e.getYOnScreen(),now.getYear(),now.getMonthValue(),now.getDayOfMonth());
					selectNum = 4;
				}
			}else if(obj == calBtn[1]) {
				if (diarySelect == null || !diarySelect.isDisplayable()) {
					diarySelect = newDiary(e.getXOnScreen(),e.getYOnScreen(),now.getYear(),now.getMonthValue(),now.getDayOfMonth());
					selectNum = 5;
				}
			}else if(obj == calBtn[2]) {
				if (diarySelect == null || !diarySelect.isDisplayable()) {
					diarySelect = newDiary(e.getXOnScreen(),e.getYOnScreen(),now.getYear(),now.getMonthValue(),now.getDayOfMonth());
					selectNum = 6;
				}
			} else if(obj == calBtn[3]) {
				if (diarySelect == null || !diarySelect.isDisplayable()) {
					diarySelect = newDiary(e.getXOnScreen(),e.getYOnScreen(),now.getYear(),now.getMonthValue(),now.getDayOfMonth());
					selectNum = 7;
				}
			}  
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	class MyCanvas extends Canvas {

		@Override
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, this);
		}

	}

	public static void copyFileByStream(String sourcePath, String targetPath) {
		File source = new File(sourcePath);// sourcePath의 파일명을 가진 객체 생성
		File target = new File(targetPath);
		if (!source.exists()) {// 지정된 파일명을 가진 객체가 없을때
			return;// 브레이크 역할, 소스가 없을 경우 패스하는 기능
		}
		if (!target.getParentFile().exists()) {
			target.getAbsoluteFile().mkdirs();
			// target.getAbsoluteFile().mkdir();
		}
		try {
			InputStream is = new FileInputStream(source);
			OutputStream os = new FileOutputStream(target);
			int temp = 0;
			byte[] data = new byte[1024];
			while ((temp = is.read(data)) != -1) {
				os.write(data, 0, temp);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refresh() {
		order_x = 5;
		dummy.setVisible(false);
		profileInfo.setVisible(false);
		main.removeAll();
		removeAll();
		vlist = mgr.memberSelectall();
		data_size = vlist.size();
		profile = new newPanel[data_size];
		dummy = new newPanel(null, 0, 0, 1320, 610);
		dummy.setBackground(new Color(0, 0, 0, 0));
		dummy.setVisible(false);
		main = new newPanel(null, 5, 0, (data_size < 3 ? max_scroll : max_scroll + ((max_scroll - 4) * 320)), 610);
		imagePlace = new newLabel(null, 142, 15, 230, 232, 0, null, null);
		imageDummy = new newPanel("pictureDummy.png", 142, 15, 230, 232);

		for (int i = 0; i < data_size; i++) {
			MemberBean bean = vlist.get(i);
			profile[i] = new newPanel("profile.png", 50 + (i * 320), 50, 300, 500);
			profile[i].add(new newPanel("pictureDummy.png", 35, 12, 270, 270));
			delay(1);
			profile[i].add(new newPanel(bean.getId() + ".jpg", 35, 12, 230, 230));
			if (bean.getGender().equals("여"))
				profile[i].add(new newPanel("woman_icon.png", 245, 238, 38, 45));
			else if (bean.getGender().equals("남"))
				profile[i].add(new newPanel("man_icon.png", 245, 238, 38, 45));
			profile[i].add(new newLabel(bean.getName(), 0, 300, 300, 70, 40, Color.black, "CookieRunOTF Regular", 0));
			profile[i].add(new newLabel(bean.getPosition(), 0, 350, 300, 70, 20, Color.black, "CookieRunOTF Regular", 0));
			profile[i].add(new newLabel(bean.getPhone(), 0, 400, 300, 70, 20, Color.black, "CookieRunOTF Regular", 0));
			profile[i].setVisible(true);
			profile[i].addMouseListener(this);
			main.add(profile[i]);
		}
		plusProfile = new newPanel("profile.png", 50 + (data_size * 320), 50, 300, 500);
		plusImage = new newPanel("plusProfile.png", 35, 120, 250, 270);
		plusProfile.add(plusImage);

		plusImage.addMouseListener(this);

		main.add(plusProfile);
//		main.add(page);
		main.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (data_size < 3 || dummy.isVisible())
					return;
				if (e.getWheelRotation() > 0) {
					if (order_x > -115 - ((data_size - 3) * 320))
						order_x -= 20;
					else
						order_x = -115 - ((data_size - 3) * 320);
				} else {
					if (order_x < 5)
						order_x += 20;
					else
						order_x = 5;
				}
				main.setLocation(order_x, main.getY());
			}

		});
		add(profileInfo);
		delay(1);
		dummy = new JPanel();
		dummy.setBackground(new Color(0,0,0,0));
		dummy.setBounds(0, 0, 1320, 610);
		dummy.setVisible(false);
		dummy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX() < 335 || e.getX() > 880) {
					dummy.setVisible(false);
					profileInfo.setVisible(false);
					repaint();
				}
			}
		});
		add(dummy);
		delay(1);
		add(main);
		repaint();
	}
public Frame newDiary( int x, int z, int Y, int  M, int D) {
		
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
		frame.setTitle(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(now));
		frame.setVisible(true);
		left = new newLabel("<", 30, 30, 30, 30, 15, new Color(0,0,0),"netmarble Light",0);
		date = new newLabel(Y + "." + (M < 10 ? "0" + M :M + ""), 30, 30, 240, 30, 18, new Color(0,0,0),"netmarble Bold",0);
		right = new newLabel(">", 240, 30, 30, 30, 15, new Color(0,0,0),"netmarble Light",0);
		ok = new newPanel("ok.png", 220,247, 45, 25);		
		setcursor(frame, "mouseicon0.png");
		setClickcursor(ok, "mouseicon1.png");
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
						if (e.getSource() == days[n] && days[n].getText() != "") {
							days[n].setOpaque(true);
							now = LocalDate.of(now.getYear(), now.getMonth(), Integer.parseInt(days[n].getText())); 
							frame.setTitle(DateTimeFormatter.ofPattern("YYYY-MM-dd").format(now));
						}
						else if (days[n].isOpaque()) days[n].setOpaque(false);
						frame.repaint();					
					}					
				}
			});
			frame.add(days[i]);
		}
		createDiary(frame, days, now.getYear(), now.getMonthValue());		
		for(int n = 0; n < 42; n++) 
			if(days[n].getText() != "")
				if(Integer.parseInt(days[n].getText()) == D)
					days[n].setOpaque(true);							
		
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
		ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectdate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
				textF[selectNum].setText(selectdate);
				frame.dispose();		
			}
		});
		frame.repaint();
		return frame;
	}


	public void setcursor(Frame jc, String img) {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("./frogM/image/" + img);
		Point point = new Point(5, 5);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "");
		jc.setCursor(cursor);
	}

	public void setClickcursor(JPanel jp, String img) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image cursorimage = tk.getImage("./frogM/image/" + img);
		Point point = new Point(5, 5);
		Cursor cursor = tk.createCustomCursor(cursorimage, point, "");
		jp.setCursor(cursor);
	}

	public void createDiary(Frame frame, newLabel days[], int Y, int M) {

		for (int i = 0; i < 42; i++)
			days[i].setOpaque(false);

		int day = 0, week = 0;
		int monthSet[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (Y % 4 == 0 && Y % 100 != 0 || Y % 400 == 0)
			monthSet[1] = 29;
		else
			monthSet[1] = 28;

		day = (Y - 1) * 365 + (Y - 1) / 4 - (Y - 1) / 100 + (Y - 1) / 400;
		for (int i = 0; i < M - 1; i++) {
			day += monthSet[i];
		}
		week = day % 7; // 구하고자 하는 달의 시작일(1일)의 요일을 구함.

		for (int i = 0; i < 42; i++) {
			days[i].setText("");
		}
		for (int i = 1; i <= monthSet[M - 1]; i++) {
			days[week + 1].setText("" + i);
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
		return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(result);
	}

	public String sunday(LocalDate now) {
		String[] week = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
		LocalDate result = now;

		for (int i = 0; i < week.length; i++) {
			if (now.getDayOfWeek().toString().equals(week[i])) {
				result = now.minusDays(i);
				
				
				result = result.plusDays(6);

			}
		}
		return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(result);
	}
	
	
}

//현황

package frogM;

import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import frogM.MemberBean;

public class Tab4 extends JPanel implements MouseListener, Runnable {

	static DBConnectionMgr pool;
	newLabel[] lab;
	String str[] = { "이름", "직급", "성별", "근무일수", "희망", "실제", "실제근무시간", "월", "화", "수", "목", "금", "토", "일", "주 계", "월 계",
			"예상 월급", "보건증 만료일" };
	String columnNames[] = { "이름", "직급", "성별", "희망", "실제", "월", "화", "수", "목", "금", "토", "일", "주 계", "월 계", "전화번호",
			"보건증 만료일" };
	newLabel text_monday, text_sunday, wave, duration;
	JPanel p1, p2, p3;
	Scrollbar bar;
	newPanel calBtn;
	String monday, sunday, firstDate, lastDate, date1, date2;
	JTable jt;
	String[][] rowData;
	LocalDate now = LocalDate.now();
	FrogMgr mgr = new FrogMgr();
	Vector<MemberBean> vlist_member;
	Vector<Bean_StoreSchedule> vlist_day, vlist_month;
	Frame diarySelect = null;
	int x1 = 120;
	int x2 = 60;
	int x3 = 40;
	int fontsize = 20;

	public Tab4() {
		// 날짜 생성 및선택
		// 부분-----------------------------------------------------------------------------------------------------------------

		p1 = new newPanel(null, 30, 150, 1170, 440);
		p2 = new newPanel(null, 30, 200, 1170, 390);
		int data_size = str.length;

		lab = new newLabel[data_size];

		lab[0] = new newLabel("이름", 0, 0, x1, 50, fontsize, Color.black, "");
		lab[1] = new newLabel("직급", lab[0].getX() + lab[0].getWidth(), 0, x1, 50, fontsize, Color.black,
				"netmarble Medium");
		lab[2] = new newLabel("성별", lab[1].getX() + lab[1].getWidth(), 0, x1, 50, fontsize, Color.black,
				"netmarble Medium");
		lab[3] = new newLabel("근무일수", lab[2].getX() + lab[2].getWidth(), 0, x1, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[4] = new newLabel("희망", lab[2].getX() + lab[2].getWidth(), 25, x2, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[5] = new newLabel("실제", lab[4].getX() + lab[4].getWidth(), 25, x2, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[6] = new newLabel("실제근무시간", lab[5].getX() + lab[5].getWidth(), 0, 400, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[7] = new newLabel("월", lab[5].getX() + lab[5].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[8] = new newLabel("화", lab[7].getX() + lab[7].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[9] = new newLabel("수", lab[8].getX() + lab[8].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[10] = new newLabel("목", lab[9].getX() + lab[9].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[11] = new newLabel("금", lab[10].getX() + lab[10].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[12] = new newLabel("토", lab[11].getX() + lab[11].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[13] = new newLabel("일", lab[12].getX() + lab[12].getWidth(), 25, x3, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[14] = new newLabel("주 계", lab[13].getX() + lab[13].getWidth(), 25, x2, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[15] = new newLabel("월 계", lab[14].getX() + lab[14].getWidth(), 25, x2, 25, fontsize, Color.black,
				"netmarble Medium");
		lab[16] = new newLabel("전화번호", lab[15].getX() + lab[15].getWidth(), 0, x1, 50, fontsize, Color.black,
				"netmarble Medium");
		lab[17] = new newLabel("보건증 만료일", lab[16].getX() + lab[16].getWidth(), 0, 170, 50, fontsize, Color.black,
				"netmarble Medium");

//		p1.setBorder(new LineBorder(Color.black, 1));

		for (int i = 0; i < lab.length; i++) {
			lab[i].setHorizontalAlignment(JLabel.CENTER);
			lab[i].setBorder(new LineBorder(Color.black, 1));
			p1.add(lab[i]);
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		monday = monday(now);
		sunday = sunday(now);
		firstDate = month_first(now);
		lastDate = month_last(now);

		duration = new newLabel(monday + "  ~  " + sunday, 0, 50, 1230, 50, 30, Color.black, "netmarble Medium");
//		text_monday = new newLabel(monday, 20, 50, 160, 50, 30, Color.black, "netmarble Medium");
//		text_sunday = new newLabel(sunday, 320, 50, 160, 50, 30, Color.black, "netmarble Medium");
//		wave = new newLabel("~", 220, 25, 20, 50, 30, Color.black, "netmarble Medium");
		calBtn = new newPanel("diaryicon.png", 850, 55, 40, 38);
		duration.setHorizontalAlignment(JLabel.CENTER);
//		add(text_sunday);
//		add(text_monday);
//		add(wave);
		add(duration);
		add(calBtn);
		add(p1);
		add(p2);
		createTable(monday, sunday);
		validate();

		setLayout(null);
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		revalidate();
		repaint();
		setVisible(false); // 이거 남겨놔주세요

		calBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (diarySelect == null || !diarySelect.isDisplayable()) {
					diarySelect = newWeekDiary(e.getXOnScreen(), e.getYOnScreen(), now.getYear(), now.getMonthValue(),
							now.getDayOfMonth());
				}
			}
		});
	}

	public void createTable(String monday, String sunday) {
		vlist_member = mgr.workMemberData(monday, sunday);
		String[] week = { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" };
		String[] id = new String[vlist_member.size()];
		String[] name = new String[vlist_member.size()];
		for (int i = 0; i < vlist_member.size(); i++) {
			MemberBean bean_member = vlist_member.get(i);
		}
		HashSet<String> hashSet = new HashSet<>(Arrays.asList(id));
		// HashSet을 배열로 변환
		String[] resultArr = hashSet.toArray(new String[0]);

		rowData = new String[vlist_member.size()][16];
		int count = 0;
		for (int i = 0; i < vlist_member.size(); i++) {
			MemberBean bean_member = vlist_member.get(i);
			vlist_day = mgr.workData(bean_member.getId(), monday, sunday);
			vlist_month = mgr.workMonthData(bean_member.getId(), firstDate, lastDate);
			if (!Arrays.asList(name).contains(bean_member.getName())) {
				rowData[count][0] = bean_member.getName(); // 이름 칼럼
				rowData[count][1] = bean_member.getGrade();// 직급
				rowData[count][2] = bean_member.getGender();// 성별
				rowData[count][3] = bean_member.getHope_workday();
				rowData[count][14] = bean_member.getPhone();
				if (bean_member.getHealth() == null) {
					rowData[count][15] = bean_member.getHealth();
				} else {
					rowData[count][15] = bean_member.getHealth().split(" ")[0];
				}
				for (int j = 0; j < vlist_member.size(); j++) {
					rowData[count][4] = Integer.toString(vlist_day.size());
					rowData[count][12] = Integer.toString(vlist_day.size());
					
					rowData[count][13] = Integer.toString(vlist_month.size());
					for (int k = 0; k < vlist_day.size(); k++) {
						Bean_StoreSchedule bean_day = vlist_day.get(k);
						for (int j2 = 0; j2 < week.length; j2++) {
							LocalDate date = LocalDate.parse(bean_day.getWork_dt().substring(0, 4) + "-"
									+ bean_day.getWork_dt().substring(5, 7) + "-" + bean_day.getWork_dt().substring(8, 10));
							if (date.getDayOfWeek().toString().equals(week[j2])) {
								rowData[count][j2 + 5] = "출";
							}
						}
					}
				}
				count++;
			}else {
				count--;
				rowData[count][0] = bean_member.getName(); // 이름 칼럼
				rowData[count][1] = bean_member.getGrade();// 직급
				rowData[count][2] = bean_member.getGender();// 성별
				rowData[count][3] = bean_member.getHope_workday();
				rowData[count][14] = bean_member.getPhone();				
				if (bean_member.getHealth() == null) {
					rowData[count][15] = bean_member.getHealth();
				} else {
					rowData[count][15] = bean_member.getHealth().split(" ")[0];
				}
				for (int j = 0; j < vlist_member.size(); j++) {
					rowData[count][4] = Integer.toString(vlist_day.size());
					rowData[count][12] = Integer.toString(vlist_day.size());
					
					rowData[count][13] = Integer.toString(vlist_month.size());
					for (int k = 0; k < vlist_day.size(); k++) {
						Bean_StoreSchedule bean_day = vlist_day.get(k);
						for (int j2 = 0; j2 < week.length; j2++) {
							LocalDate date = LocalDate.parse(bean_day.getWork_dt().substring(0, 4) + "-"
									+ bean_day.getWork_dt().substring(5, 7) + "-" + bean_day.getWork_dt().substring(8, 10));
							if (date.getDayOfWeek().toString().equals(week[j2])) {
								rowData[count][j2 + 5] = "출";
							}
						}
					}
				}
				count++;
			}
			name[i] = bean_member.getName();
			
		}
		JTable jt = new JTable(rowData, columnNames);
		int y = 50;
		int j = 0;

		JScrollPane jscroll;
		jscroll = new JScrollPane(jt);
		
		jt.getColumnModel().getColumn(0).setMaxWidth(x1);
		jt.getColumnModel().getColumn(0).setMinWidth(x1);
		jt.getColumnModel().getColumn(0).setWidth(x1);
		jt.getColumnModel().getColumn(1).setMaxWidth(x1);
		jt.getColumnModel().getColumn(1).setMinWidth(x1);
		jt.getColumnModel().getColumn(1).setWidth(x1);
		jt.getColumnModel().getColumn(2).setMaxWidth(x1);
		jt.getColumnModel().getColumn(2).setMinWidth(x1);
		jt.getColumnModel().getColumn(2).setWidth(x1);
		jt.getColumnModel().getColumn(3).setMaxWidth(x2);
		jt.getColumnModel().getColumn(3).setMinWidth(x2);
		jt.getColumnModel().getColumn(3).setWidth(x2);
		jt.getColumnModel().getColumn(4).setMaxWidth(x2);
		jt.getColumnModel().getColumn(4).setMinWidth(x2);
		jt.getColumnModel().getColumn(4).setWidth(x2);
		jt.getColumnModel().getColumn(5).setMaxWidth(x3);
		jt.getColumnModel().getColumn(5).setMinWidth(x3);
		jt.getColumnModel().getColumn(5).setWidth(x3);
		jt.getColumnModel().getColumn(6).setMaxWidth(x3);
		jt.getColumnModel().getColumn(6).setMinWidth(x3);
		jt.getColumnModel().getColumn(6).setWidth(x3);
		jt.getColumnModel().getColumn(7).setMaxWidth(x3);
		jt.getColumnModel().getColumn(7).setMinWidth(x3);
		jt.getColumnModel().getColumn(7).setWidth(x3);
		jt.getColumnModel().getColumn(8).setMaxWidth(x3);
		jt.getColumnModel().getColumn(8).setMinWidth(x3);
		jt.getColumnModel().getColumn(8).setWidth(x3);
		jt.getColumnModel().getColumn(9).setMaxWidth(x3);
		jt.getColumnModel().getColumn(9).setMinWidth(x3);
		jt.getColumnModel().getColumn(9).setWidth(x3);
		jt.getColumnModel().getColumn(10).setMaxWidth(x3);
		jt.getColumnModel().getColumn(10).setMinWidth(x3);
		jt.getColumnModel().getColumn(10).setWidth(x3);
		jt.getColumnModel().getColumn(11).setMaxWidth(x3);
		jt.getColumnModel().getColumn(11).setMinWidth(x3);
		jt.getColumnModel().getColumn(11).setWidth(x3);
		jt.getColumnModel().getColumn(12).setMaxWidth(x2);
		jt.getColumnModel().getColumn(12).setMinWidth(x2);
		jt.getColumnModel().getColumn(12).setWidth(x2);
		jt.getColumnModel().getColumn(13).setMaxWidth(x2);
		jt.getColumnModel().getColumn(13).setMinWidth(x2);
		jt.getColumnModel().getColumn(13).setWidth(x2);
		jt.getColumnModel().getColumn(14).setMaxWidth(x1);
		jt.getColumnModel().getColumn(14).setMinWidth(x1);
		jt.getColumnModel().getColumn(14).setWidth(x1);
		jt.getColumnModel().getColumn(15).setMaxWidth(170);
		jt.getColumnModel().getColumn(15).setMinWidth(170);
		jt.getColumnModel().getColumn(15).setWidth(170);
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴

	


//		      전체 열에 지정
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(celAlignCenter);
		}
		
		jt.setRowHeight(20);
		jt.setFont(new Font("netmarble Medium", Font.PLAIN, 13));
		jt.setBounds(0, 0, 1170, 390);
		jt.getTableHeader().setReorderingAllowed(false);
		
		p2.add(jt);
		
		setVisible(true);
		repaint();
		validate();

	}

	// 현재 날짜
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

	public String month_first(LocalDate now) {
		LocalDate result = now.withDayOfMonth(1);
		return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(result);
	}

	public String month_last(LocalDate now) {
		LocalDate result = now.withDayOfMonth(now.lengthOfMonth());
		return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(result);
	}

	public Frame newWeekDiary(int x, int z, int Y, int M, int D) {

		now = LocalDate.of(Y, M, D);
		int offset = 90;
		String str[] = { "일", "월", "화", "수", "목", "금", "토" };

		Frame frame;
		ImageIcon img = new ImageIcon("./frogM/image/diaryicon.png");
		newLabel date, left, right;
		newLabel[] days = new newLabel[42];
		newLabel[] weeks = new newLabel[7];
		newPanel ok;

		frame = new Frame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
		frame.setLayout(null);
		frame.setSize(300, 300);
		frame.setLocation(x, z);
		frame.setResizable(false);
		frame.setIconImage(img.getImage());
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.setTitle(monday(now) + "~" + sunday(now));
		left = new newLabel("<", 30, 30, 30, 30, 15, new Color(0, 0, 0), "netmarble Light", 0);
		date = new newLabel(Y + "." + (M < 10 ? "0" + M : M + ""), 30, 30, 240, 30, 18, new Color(0, 0, 0),
				"netmarble Bold", 0);
		right = new newLabel(">", 240, 30, 30, 30, 15, new Color(0, 0, 0), "netmarble Light", 0);
		ok = new newPanel("ok.png", 220, 247, 45, 25);
		setcursor(frame, "mouseicon0.png");
		setClickcursor(ok, "mouseicon1.png");
		frame.add(ok);
		frame.add(left);
		frame.add(right);
		frame.add(date);
		for (int i = 0; i < 42; i++) {
			if ((i + 1) % 7 == 0) {
				days[i] = new newLabel("", 30 + ((i % 7) * 35), offset, 30, 30, 15, new Color(51, 152, 252),
						"netmarble Medium", 0);
				offset += 30;
			} else if ((i % 7 == 0)) {
				days[i] = new newLabel("", 30 + ((i % 7) * 35), offset, 30, 30, 15, new Color(255, 87, 97),
						"netmarble Medium", 0);
			} else {
				days[i] = new newLabel("", 30 + ((i % 7) * 35), offset, 30, 30, 15, new Color(50, 50, 50),
						"netmarble Medium", 0);
			}
			if (i < 7) {
				if (i == 0)
					weeks[i] = new newLabel(str[i], days[i].getX(), 60, 30, 30, 15, new Color(255, 87, 97),
							"netmarble Medium", 0);
				else if (i == 6)
					weeks[i] = new newLabel(str[i], days[i].getX(), 60, 30, 30, 15, new Color(51, 152, 252),
							"netmarble Medium", 0);
				else
					weeks[i] = new newLabel(str[i], days[i].getX(), 60, 30, 30, 15, new Color(50, 50, 50),
							"netmarble Medium", 0);
				frame.add(weeks[i]);
			}
			days[i].setBackground(new Color(183, 240, 177));
			days[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (int n = 0; n < 42; n++) {
						if (days[n].getText() != "") {
							if (e.getSource() == days[n])
								now = LocalDate.of(now.getYear(), now.getMonth(), Integer.parseInt(days[n].getText()));
						}
						days[n].setOpaque(false);
						frame.repaint();
					}
					for (int n = 0; n < 42; n++) {
						if (days[n].getText() != "") {
							LocalDate ld = LocalDate.parse(now.getYear() + "-"
									+ (now.getMonthValue() < 10 ? "0" + now.getMonthValue() : now.getMonthValue()) + "-"
									+ (days[n].getText().length() == 1 ? "0" + days[n].getText() : days[n].getText()));
							if (LocalDate.parse(monday(now)).isBefore(ld.plusDays(1))
									&& LocalDate.parse(sunday(now)).isAfter(ld.minusDays(1))) {
								days[n].setOpaque(true);
							}
						}
					}
					if (((newLabel) e.getSource()).getText() != "") {
						frame.setTitle(monday(now) + "~" + sunday(now));
					}
					frame.repaint();
				}
			});
			frame.add(days[i]);
		}
		createDiary(frame, days, now.getYear(), now.getMonthValue());
		for (int n = 0; n < 42; n++) {
			if (days[n].getText() != "") {
				LocalDate ld = LocalDate.parse(now.getYear() + "-"
						+ (now.getMonthValue() < 10 ? "0" + now.getMonthValue() : now.getMonthValue()) + "-"
						+ (days[n].getText().length() == 1 ? "0" + days[n].getText() : days[n].getText()));
				if (LocalDate.parse(monday(now)).isBefore(ld.plusDays(1))
						&& LocalDate.parse(sunday(now)).isAfter(ld.minusDays(1))) {
					days[n].setOpaque(true);
				}
			}
		}
		frame.repaint();
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				now = now.minusMonths(1);
				date.setText(now.getYear() + "."
						+ (now.getMonthValue() < 10 ? "0" + now.getMonthValue() : now.getMonthValue() + ""));
				frame.setTitle("");
				createDiary(frame, days, now.getYear(), now.getMonthValue());
			}
		});
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				now = now.plusMonths(1);
				date.setText(now.getYear() + "."
						+ (now.getMonthValue() < 10 ? "0" + now.getMonthValue() : now.getMonthValue() + ""));
				frame.setTitle("");
				createDiary(frame, days, now.getYear(), now.getMonthValue());
			}
		});
		ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				date1 = monday(now).replace("-", "");
				date2 = sunday(now).replace("-", "");
				duration.setText(monday(now) + "  ~  " + sunday(now));
				monday = monday(now);
				sunday = sunday(now);
				firstDate = month_first(now);
				lastDate = month_last(now);
				p2.removeAll();
				createTable(monday, sunday);
				frame.dispose();
			}
		});
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

	@Override
	public void run() {

		revalidate();
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

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

	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {

	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
}
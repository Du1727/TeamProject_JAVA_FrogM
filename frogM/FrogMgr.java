package frogM;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import frogM.MemberBean;

public class FrogMgr {

	static DBConnectionMgr pool;
	MemberBean bean;

	public FrogMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// ------------------------------------------------------------------------로그인하기
	public boolean loginChk(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "select COUNT(ID) from FROG.member " + "where id = ? and pw = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt(1) == 1) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}		
		return flag;

	}

	// ---------------------------------------------------------------멤버데이터 가져오기
	public Vector<MemberBean> memberSelectall() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MemberBean> vlist = new Vector<MemberBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM FROG.MEMBER ORDER BY ID"; // 쿼리문 삽입 공간
			pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
			rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
			while (rs.next()) {				
				MemberBean bean = new MemberBean();
				bean.setId(rs.getString("id"));
				bean.setPw(rs.getString("pw"));
				bean.setName(rs.getString("name"));
				bean.setPhone(rs.getString("phone"));
				bean.setGrade(rs.getString("Grade"));
				bean.setPosition(rs.getString("position"));
				bean.setBirthday(rs.getString("birthday"));
				bean.setJoin_dt(rs.getString("join_dt"));
				bean.setRetire_dt(rs.getString("retire_dt"));
				bean.setHope_workday(rs.getString("hope_workday"));
				bean.setHealth(rs.getString("health"));
				bean.setGender(rs.getString("gender"));
				vlist.addElement(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	public MemberBean memberSelectone(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberBean bean = new MemberBean();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM FROG.MEMBER where id = ?"; // 쿼리문 삽입 공간
			pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
			if (rs.next()) {
				bean.setId(rs.getString("id"));
				bean.setPw(rs.getString("pw"));
				bean.setName(rs.getString("name"));
				bean.setPhone(rs.getString("phone"));
				bean.setGrade(rs.getString("grade"));
				bean.setPosition(rs.getString("position"));
				bean.setBirthday(rs.getString("birthday"));
				bean.setJoin_dt(rs.getString("join_dt"));
				bean.setRetire_dt(rs.getString("retire_dt"));
				bean.setHope_workday(rs.getString("hope_workday"));
				bean.setHealth(rs.getString("health"));
				bean.setGender(rs.getString("gender"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);

		}
		return bean;
	}

	// ---------------------------------------------------------------멤버데이터 가져오기
	public Vector<MemberBean> workScheduleSelectall() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MemberBean> vlist = new Vector<MemberBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM FROG."; // 쿼리문 삽입 공간
			pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
			rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
			while (rs.next()) {
				MemberBean bean = new MemberBean();
				bean.setId(rs.getString("id"));
				bean.setPw(rs.getString("pw"));
				bean.setName(rs.getString("name"));
				bean.setPhone(rs.getString("phone"));
				bean.setGrade(rs.getString("Grade"));
				bean.setPosition(rs.getString("position"));
				bean.setBirthday(rs.getString("birthday"));
				bean.setJoin_dt(rs.getString("join_dt"));
				bean.setRetire_dt(rs.getString("retire_dt"));
				bean.setHope_workday(rs.getString("hope_workday"));
				bean.setHealth(rs.getString("health"));
				bean.setPhone(rs.getString("photo"));
				bean.setGender(rs.getString("gender"));
				vlist.addElement(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}

	// ----------------------------------------------------member 데이터 추가
	// 저장하기
	public boolean saveData(String id, String pw, String name, String phone, String Grade, String position,
			String birthday, String join_dt, String retire_dt, String hope_workday, String health, String gender) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = dateFormat.format(Calendar.getInstance().getTime());
		try {
			con = pool.getConnection();
			sql = " INSERT INTO FROG.MEMBER"
					+ " (ID ,PW ,NAME ,PHONE ,Grade ,POSITION ,BIRTHDAY ,JOIN_DT,RETIRE_DT ,HOPE_WORKDAY ,HEALTH, GENDER) VALUES"
					+ " (? , ? , ? , ? , ? ,?,to_date( ?,'YYYYMMDD') , to_date( ?,'YYYYMMDD') , to_date( ?,'YYYYMMDD') , ? , to_date( ?,'YYYYMMDD'),?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.setString(5, Grade);
			pstmt.setString(6, position);
			pstmt.setString(7, birthday);
			pstmt.setString(8, join_dt);
			pstmt.setString(9, retire_dt);
			pstmt.setString(10, hope_workday);
			pstmt.setString(11, health);
			pstmt.setString(12, gender);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}

	// ------------------------------------------------------------------------멤버 기본
	// 데이터 가져오기 한개만

	// ------------------------------------------------member 수정
	public boolean updateMember(String id, String pw, String name, String phone, String Grade, String position,
			String birthday, String join_dt, String retire_dt, String hope_workday, String health, String gender) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = " update FROG.member set  pw = ?,name = ?, "
					+ "phone = ?,Grade = ?, position = ? ,birthday = ?, "
					+ "join_dt = ?,retire_dt = ?,hope_workday = ?, health = ?, gender = ? where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			pstmt.setString(4, Grade);
			pstmt.setString(5, position);
			pstmt.setString(6, birthday);
			pstmt.setString(7, join_dt);
			pstmt.setString(8, retire_dt);
			pstmt.setString(9, hope_workday);
			pstmt.setString(10, health);
			pstmt.setString(11, gender);
			pstmt.setString(12, id);

			pstmt.executeUpdate();
			// insert, update, delete는 실행을 하면 적용된 레코드 개수가 리턴된다.
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}

	// -----------------------------------------------------------------member 삭제
	public boolean deleteMember(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;

		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from FROG.member where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;

	}
	public void insertImg(File file, String userName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "insert INTO FROG.IMGLIST (ID , NAME, IMG) values(?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			InputStream is = new FileInputStream(file);
			pstmt.setString(1, userName);
			pstmt.setString(2, file.getName());			
			pstmt.setBinaryStream(3, is);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	
//	public void insertImg(File file) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		String sql = null;
//		try {
//			con = pool.getConnection();
//			sql = "insert tblImageLoad values(null, ?, ?)";
//			pstmt = con.prepareStatement(sql);
//			InputStream is = new FileInputStream(file);
//			pstmt.setString(1, file.getName());
//			pstmt.setBinaryStream(2, is);
//			pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			pool.freeConnection(con, pstmt);
//		}
//	}
	
	public File selectImg(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			con = pool.getConnection();
			sql = "select NAME, IMG from FROG.IMGLIST where ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				file =  new File("imgDBUpload/"+rs.getString("NAME"));
				fos = new FileOutputStream(file);
				is = rs.getBinaryStream("IMG");
				int i;
				while((i=is.read())!=-1) {
					fos.write(i);
				}
			}
			if(fos!=null) fos.close();
			if(is!=null) is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return file;
	}
	//--------------------------------------------------------일하는 사람의 정보 가져오기
	
		public Vector<MemberBean> workMemberData(String monday, String sunday) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<MemberBean> vlist = new Vector<MemberBean>();
			try {
				con = pool.getConnection();
				sql = "select m.id, m.NAME, m.GRADE, m.HOPE_WORKDAY, m.HEALTH, s.WORK_DT , m.phone , m.gender "
						+ "from frog.work_history h, frog.member m, frog.STORE_SCHEDULE s " 
						+ "where h.id = m.id "
						+ "AND h.STORE_SCHEDULE_CODE  = s.CODE "
						+ "and s.work_dt between to_date(? ,'yyyy-mm-dd') and to_date(? ,'yyyy-mm-dd')"; // 쿼리문 삽입 공간
				pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
				pstmt.setString(1, monday);
				pstmt.setString(2, sunday);
				rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
				while (rs.next()) {
					MemberBean bean = new MemberBean();
					bean.setId(rs.getString("id"));
					bean.setName(rs.getString("name"));
					bean.setGrade(rs.getString("grade"));
					bean.setHope_workday(rs.getString("hope_workday"));
					bean.setHealth(rs.getString("health"));				
					bean.setPhone(rs.getString("phone"));				
					bean.setGender(rs.getString("gender"));				
					vlist.addElement(bean);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		
		//-----------------------일하는 날짜 가져오기
		
		
		public Vector<Bean_StoreSchedule> workData(String id, String monday, String sunday) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<Bean_StoreSchedule> vlist = new Vector<Bean_StoreSchedule>();
			try {
				con = pool.getConnection();
				sql = "select s.WORK_DT "
						+ "from frog.work_history h, frog.member m, frog.STORE_SCHEDULE s " 
						+ "where h.id = m.id "
						+ "AND h.STORE_SCHEDULE_CODE  = s.CODE "
						+ "and s.work_dt between to_date(? ,'yyyy-mm-dd') and to_date(? ,'yyyy-mm-dd')"
						+ "and h.id =  ? "; 
				pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
				pstmt.setString(1, monday);
				pstmt.setString(2, sunday);
				pstmt.setString(3, id);
				rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
				while (rs.next()) {
					Bean_StoreSchedule bean = new Bean_StoreSchedule();
					bean.setWork_dt(rs.getString("work_dt"));
					
					vlist.addElement(bean);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		public Vector<Bean_StoreSchedule> workMonthData(String id, String monday, String sunday) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<Bean_StoreSchedule> vlist = new Vector<Bean_StoreSchedule>();
			try {
				con = pool.getConnection();
				sql = "select s.WORK_DT "
						+ "from frog.work_history h, frog.member m, frog.STORE_SCHEDULE s " 
						+ "where h.id = m.id "
						+ "AND h.STORE_SCHEDULE_CODE  = s.CODE "
						+ "and s.work_dt between to_date(? ,'yyyy-mm-dd') and to_date(? ,'yyyy-mm-dd')"
						+ "and h.id =  ? "; 
				pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
				pstmt.setString(1, monday);
				pstmt.setString(2, sunday);
				pstmt.setString(3, id);
				rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
				while (rs.next()) {
					Bean_StoreSchedule bean = new Bean_StoreSchedule();
					bean.setWork_dt(rs.getString("work_dt"));
					
					vlist.addElement(bean);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		public void insertPosition(String str) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				con = pool.getConnection();
				sql = "INSERT INTO FROG.FROG_CONFIG (CODE, GBN, VALUE1) "
					+ " VALUES (FROG.SEQ_FROG_CONFIG.NEXTVAL, 'Position', ?)";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, str);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
		}
		
		public void insertStoreSchedule(Bean_StoreSchedule bean) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				con = pool.getConnection();
				sql = "INSERT INTO STORE_SCHEDULE (CODE,  WORK_DT, YOIL, POSITION, TITLE ,       START_TIME , END_TIME , MEMBER_TO )"
						+ " VALUES (SEQ_STORE_SCHEDULE.NEXTVAL, TO_DATE(?,'YYYYMMDD') , ? , ? , ?, ?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bean.getWork_dt());
				pstmt.setString(2, bean.getDw());
				pstmt.setString(3, bean.getPosition());
				pstmt.setString(4, bean.getTitle());
				pstmt.setString(5, bean.getStartTime());
				pstmt.setString(6, bean.getEndTime());
				pstmt.setString(7, bean.getMemberTo());
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
		}
		
		

		public void insertWorkHistory(Bean_StoreSchedule bean) {
			Connection con = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				con = pool.getConnection();
				sql = "INSERT WORK_HISTORY () VALUES(SEQ_WORK_HISTORY.NEXTVAL, ? , ? , ? , ?        , ? , ? , ? , ? , ? )";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bean.getDw());
				pstmt.setString(2, bean.getPosition());
				pstmt.setString(3, bean.getTitle());
				pstmt.setString(4, bean.getStartTime());
				pstmt.setString(5, bean.getEndTime());
				pstmt.setString(6, bean.getMemberTo());
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
		}
		
		
		
		
		public Vector<Bean_StoreSchedule> getPosition() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<Bean_StoreSchedule> vlist = new Vector<Bean_StoreSchedule>();
			try {
				con = pool.getConnection();
				sql = " SELECT VALUE1 FROM FROG.FROG_CONFIG WHERE GBN LIKE 'Position' ";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Bean_StoreSchedule bean = new Bean_StoreSchedule();
					bean.setPosition(rs.getString("VALUE1"));
					vlist.addElement(bean);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		
		

		
		public Vector<Bean_StoreSchedule> getSelect2(String a, String b) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<Bean_StoreSchedule> vlist = new Vector<Bean_StoreSchedule>();
			try {
				con = pool.getConnection();
				sql = "    SELECT DISTINCT TITLE"
						+ " FROM FROG.STORE_SCHEDULE"
						+ " WHERE YOIL LIKE ? AND POSITION LIKE ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, a);
				pstmt.setString(2, b);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Bean_StoreSchedule bean = new Bean_StoreSchedule();
					bean.setTitle((rs.getString("TITLE")));
					vlist.addElement(bean);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		
		

		
		public Vector<Bean_StoreSchedule> select_p1_position(String date1, String date2, String YOIL) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<Bean_StoreSchedule> vlist = new Vector<Bean_StoreSchedule>();
			try {
				con = pool.getConnection();
				sql = "    SELECT DISTINCT POSITION"
						+ " FROM FROG.STORE_SCHEDULE"
						+ " WHERE WORK_DT BETWEEN TO_DATE(?,'YYYYMMDD') AND TO_DATE(?,'YYYYMMDD')"
						+ "     AND YOIL LIKE ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, date1);
				pstmt.setString(2, date2);
				pstmt.setString(3, YOIL);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Bean_StoreSchedule bean = new Bean_StoreSchedule();
					
					bean.setPosition(rs.getString(1));
					
					vlist.addElement(bean);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		public Vector<MemberBean> getGrade() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<MemberBean> vlist = new Vector<MemberBean>();
			try {
				con = pool.getConnection();
				sql = " SELECT GRADE  FROM FROG.MEMBER m "
						+ "GROUP BY GRADE ";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MemberBean bean = new MemberBean();
					bean.setGrade(rs.getString("grade"));
					vlist.addElement(bean);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}

		public Vector<Bean_StoreSchedule> select_p1_barData(String date1, String date2, String YOIL) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null, rs2 = null;
			String sql, sql2 = null;
			Vector<Bean_StoreSchedule> vlist = new Vector<Bean_StoreSchedule>();
			Vector<Bean_WorkHistory> vlist2   = new Vector<Bean_WorkHistory>();
			try {
				con = pool.getConnection();
				sql = "       SELECT CODE, TO_CHAR(WORK_DT,'YYYYMMDD') AS WORK_DT , YOIL, POSITION, TITLE, START_TIME, END_TIME , MEMBER_TO "
						+ "	FROM   STORE_SCHEDULE "
						+ "    WHERE TO_CHAR(WORK_DT,'YYYYMMDD') BETWEEN ? AND  ? "
						+ "        AND YOIL LIKE ? "
						+ "        ORDER BY POSITION, START_TIME " ;
						
			
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, date1);
				pstmt.setString(2, date2);
				pstmt.setString(3, YOIL);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Bean_StoreSchedule bean = new Bean_StoreSchedule();
					
					bean.setCode(rs.getString("CODE"));
					bean.setWork_dt(rs.getString("WORK_DT"));
					bean.setDw(rs.getString("YOIL"));
					bean.setPosition((rs.getString("POSITION")));
					bean.setTitle(rs.getString("TITLE"));
					bean.setStartTime(rs.getString("START_TIME"));
					bean.setEndTime(rs.getString("END_TIME"));
					bean.setMemberTo(rs.getString("MEMBER_TO"));
					
					
					sql2 = "      SELECT H.ID, M.NAME, M.GRADE "
							+ "       FROM STORE_SCHEDULE S, WORK_HISTORY H, MEMBER M "
							+ "     WHERE S.CODE = H.STORE_SCHEDULE_CODE "
							+ "         AND H.ID = M.ID  "
							+ "         AND H.STORE_SCHEDULE_CODE = ? ";
					
//				System.out.println("### " + rs.getString("CODE"));
					pstmt = con.prepareStatement(sql2);
					pstmt.setString(1, rs.getString("CODE"));
					rs2 = pstmt.executeQuery();
					
					
					vlist.add(bean);
					vlist2.clear();
					while(rs2.next()) {
						Bean_WorkHistory bean2 = new Bean_WorkHistory();
						
						
//						System.out.println(">>" + rs2.getString(1));
						
						bean2.setId(rs2.getString(1));
						bean2.setName(rs2.getString(2));
						bean2.setGrade(rs2.getString(3));
						vlist.get(vlist.size()-1).getBean().add(bean2);
//						vlist2.addElement(bean2);
						
					}
//					System.out.println(">>" + vlist2.size());
//					bean.setBean(vlist2);
					
//					vlist.add(bean);
//					System.out.println(vlist.get(vlist.size()-1).getBean().size());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		
		
		
		public Bean_StoreSchedule select_p4_barData(String date1, String date2, String YOIL, String position, String title) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Bean_StoreSchedule bean = new Bean_StoreSchedule();
			try {
				con = pool.getConnection();
				sql = "       SELECT CODE, TO_CHAR(WORK_DT,'YYYYMMDD') AS WORK_DT , YOIL, POSITION, TITLE, START_TIME, END_TIME , MEMBER_TO "
						+ "	FROM   STORE_SCHEDULE "
						+ "    WHERE TO_CHAR(WORK_DT,'YYYYMMDD') BETWEEN ? AND  ? "
						+ "        AND YOIL LIKE ? "
						+ "        AND POSITION LIKE ? "
						+ "        AND TITLE LIKE ? ";
					
						
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, date1);
				pstmt.setString(2, date2);
				pstmt.setString(3, YOIL);
				pstmt.setString(4, position);
				pstmt.setString(5, title);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					bean.setCode(rs.getString("CODE"));
					bean.setWork_dt(rs.getString("WORK_DT"));
					bean.setDw(rs.getString("YOIL"));
					bean.setPosition((rs.getString("POSITION")));
					bean.setTitle(rs.getString("TITLE"));
					bean.setStartTime(rs.getString("START_TIME"));
					bean.setEndTime(rs.getString("END_TIME"));
					bean.setMemberTo(rs.getString("MEMBER_TO"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return bean;
		}
		
		

		public Vector<MemberBean> select_p4_member(String code) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<MemberBean> vlist = new Vector<MemberBean>();
			try {
				con = pool.getConnection();
				sql = "         SELECT  H.ID, M.NAME, M.GRADE "
						+ "      FROM WORK_HISTORY H, MEMBER M "
						+ "      WHERE H.ID = M.ID"
						+ "        AND H.STORE_SCHEDULE_CODE = ? " ;
						
					
						
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, code);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MemberBean bean = new MemberBean();
					bean.setId(rs.getString("ID"));
					bean.setName(rs.getString("NAME"));
					bean.setGrade(rs.getString("GRADE"));
					vlist.addElement(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		
		
		

		public Vector<MemberBean> select_p4_allmember() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<MemberBean> vlist = new Vector<MemberBean>();
			try {
				con = pool.getConnection();
				sql = " SELECT ID, NAME, GRADE FROM MEMBER " ;
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MemberBean bean = new MemberBean();
					bean.setId(rs.getString("ID"));
					bean.setName(rs.getString("NAME"));
					bean.setGrade(rs.getString("GRADE"));
					vlist.addElement(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}
		
//		public void insert_work_history(String ) {
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			String sql = null;
//			Vector<MemberBean> vlist = new Vector<MemberBean>();
//			try {
//				con = pool.getConnection();
//				sql = " SELECT ID, NAME, GRADE FROM MEMBER " ;
//				pstmt = con.prepareStatement(sql);
//				rs = pstmt.executeQuery();
//				
//				while(rs.next()) {
//					MemberBean bean = new MemberBean();
//					bean.setId(rs.getString("ID"));
//					bean.setName(rs.getString("NAME"));
//					bean.setGrade(rs.getString("GRADE"));
//					vlist.addElement(bean);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				pool.freeConnection(con, pstmt, rs);
//			}
//			return vlist;
//		}	
	
	public Vector<noticeBean> select_notice() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<noticeBean> vlist = new Vector<noticeBean>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM FROG.NOTICE"; // 쿼리문 삽입 공간
			pstmt = con.prepareStatement(sql);// 쿼리문에 넣을 명령문을 가져옴
			rs = pstmt.executeQuery();// 실행한 결과를 에 저장한다.
			while (rs.next()) {
				noticeBean bean = new noticeBean();
				bean.setCode(rs.getInt("CODE"));
				bean.setContent(rs.getString("CONTENT"));
				bean.setFontsize(rs.getInt("FONTSIZE"));
				vlist.addElement(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}
	
	public boolean updateNotice(int code ,String content, int fontsize) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = " update FROG.NOTICE set  content = ?,fontsize = ? "
					+ "where code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, fontsize);
			pstmt.setInt(3, code);

			pstmt.executeUpdate();
			// insert, update, delete는 실행을 하면 적용된 레코드 개수가 리턴된다.
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;
	}
	
	public Vector<Bean_Calender> select_calender(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<Bean_Calender> vlist = new Vector<Bean_Calender>();
		try {
			con = pool.getConnection();
			sql = "    SELECT TO_CHAR(S.WORK_DT,'YYYYMMDD') AS WORK_DT , S.POSITION , S.TITLE ,S.START_TIME ,S.END_TIME"
					+ " FROM WORK_HISTORY H, STORE_SCHEDULE S "
					+ " WHERE H.STORE_SCHEDULE_CODE = S.CODE "
					+ "  AND H.ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Bean_Calender bean = new Bean_Calender();
				bean.setWork_dt(rs.getString("WORK_DT"));
				bean.setPosition(rs.getString("POSITION"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStartTime(rs.getString("START_TIME"));
				bean.setEndTime(rs.getString("END_TIME"));
				vlist.addElement(bean);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return vlist;
	}


	public void deleteWorkHistory(String barCode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM WORK_HISTORY WHERE STORE_SCHEDULE_CODE = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, barCode);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	public void insertWorkHistory(String barCode, String id) {		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "INSERT INTO WORK_HISTORY VALUES(SEQ_WORK_HISTORY.NEXTVAL, ? , ? )";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, barCode);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	public void deleteStoreSchedule(String barCode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "DELETE FROM STORE_SCHEDULE WHERE CODE = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, barCode);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
}
	
	
	
/*
 * Classpath 설정 jdk or jre -> 기본클래스가 필요 현재 실행중인 class jvm인식 외부 클래스들 -> .jar 형식으로
 * 압축
 */

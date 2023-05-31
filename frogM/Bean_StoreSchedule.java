package frogM;

import java.util.Vector;

public class Bean_StoreSchedule {

	
	private String code;
	private String work_dt;
	private String dw;
	private String position;
	private String title;
	private String startTime;
	private String endTime;
	private String memberTo;
	private Vector<Bean_WorkHistory> bean = new Vector<>();
	private int rowCount;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWork_dt() {
		return work_dt;
	}
	public void setWork_dt(String work_dt) {
		this.work_dt = work_dt;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMemberTo() {
		return memberTo;
	}
	public void setMemberTo(String memberTo) {
		this.memberTo = memberTo;
	}
	public Vector<Bean_WorkHistory> getBean() {
		return bean;
	}
	public void setBean(Vector<Bean_WorkHistory> bean) {
		this.bean = bean;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
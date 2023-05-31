package frogM;

public class Work_ScheduleBean {
	
	private String schd_code;	
		private String week_date;
		private String schd_name;
		private String emp_posi;
		
		private String start_time;
		private String end_time;
		
		
		
		public String getweek_date() {
			return week_date;
		}
		public void setweek_date(String work_date) {
			this.week_date = work_date;
		}
		public String getschd_code() {
			return schd_code;
		}
		public void setschd_code(String schd_code) {
			this.schd_code = schd_code;
		}
		public String getschd_name() {
			return schd_name;
		}
		public void setschd_name(String schd_name) {
			this.schd_name = schd_name;
		}
		

		
		public String getemp_posi() {
			return emp_posi;
		}
		public void setemp_posi(String emp_posi) {
			this.emp_posi = emp_posi;
		}

		public String getstart_time() {
			return start_time;
		}
		public void setstart_time(String start_time) {
			this.start_time = start_time;
		}
		public String getend_time() {
			return end_time;
		}
		public void setend_time(String end_time) {
			this.end_time = end_time;
		}


	

}

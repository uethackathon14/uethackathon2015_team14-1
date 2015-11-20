package com.mtafriends.tutor4u.model;


public class ListviewTutor {
	int avatarTutor;
	String txtNameTutor;
	String txtSubjectTutor;
	String txtLevelTutor;
	public ListviewTutor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ListviewTutor(int avatarTutor, String txtNameTutor,
			String txtSubjectTutor, String txtLevelTutor) {
		super();
		this.avatarTutor = avatarTutor;
		this.txtNameTutor = txtNameTutor;
		this.txtSubjectTutor = txtSubjectTutor;
		this.txtLevelTutor = txtLevelTutor;
	}

	public int getAvatarTutor() {
		return avatarTutor;
	}
	public void setAvatarTutor(int avatarTutor) {
		this.avatarTutor = avatarTutor;
	}
	public String getTxtNameTutor() {
		return txtNameTutor;
	}
	public void setTxtNameTutor(String txtNameTutor) {
		this.txtNameTutor = txtNameTutor;
	}
	public String getTxtSubjectTutor() {
		return txtSubjectTutor;
	}
	public void setTxtSubjectTutor(String txtSubjectTutor) {
		this.txtSubjectTutor = txtSubjectTutor;
	}
	public String getTxtLevelTutor() {
		return txtLevelTutor;
	}
	public void setTxtLevelTutor(String txtLevelTutor) {
		this.txtLevelTutor = txtLevelTutor;
	}
	

}

package com.mtafriends.tutor4u.model;

public class GroupStudy {
	private String username;
	private String name_subject;
	private Double latitude;
	private Double longitude;
	private int limitMem;
	private int countNow;//Số thành viên hiện tại đã đăng ký học tại nhóm
	
	public int getLimitMem() {
		return limitMem;
	}

	public void setLimitMem(int limitMem) {
		this.limitMem = limitMem;
	}

	public int getCountNow() {
		return countNow;
	}

	public void setCountNow(int countNow) {
		this.countNow = countNow;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName_subject() {
		return name_subject;
	}

	public void setName_subject(String name_subject) {
		this.name_subject = name_subject;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}

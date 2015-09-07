package com.sgen.DTO;

import java.io.Serializable;

public class DeviceDTO implements Serializable{
	
	private String deviceCode;
	private int deviceStatus; 
	private String otp;
	// 0 for normal-mode , 1 for BangBum-mode , 2 for TRIP mode
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public int getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}

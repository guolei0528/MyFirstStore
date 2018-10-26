package com.project.backMng.admin.transitRecord.EntryMng.model;

import java.io.Serializable;

public class EntryBean implements Serializable{

	private String recordno;
	
	private String cardnetwork;
	
	private String cardid;
	
	private String parkid;
	
	private String areaid;
	
	private String entrystation;
	
	private String entrylane;
	
	private String entrytime;
	
	private String entryoperator;
	
	private String entryshift;
	
	private String autovlicense;
	
	private String mvlicense;
	
	private String imagepath;
	
	private String vehiclekind;
	private String etctermcode;
	private String etcobuid;
	private String etctac;
	private String programstarttime	;
	private String vehicleclass;
	private String entrydate;
	private String park_name;
	private String area_name;
	private int car_color;
	
	
	public String getPark_name() {
		return park_name;
	}

	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getVehicleclass() {
		return vehicleclass;
	}

	public void setVehicleclass(String vehicleclass) {
		this.vehicleclass = vehicleclass;
	}

	public String getVehiclekind() {
		return vehiclekind;
	}

	public void setVehiclekind(String vehiclekind) {
		this.vehiclekind = vehiclekind;
	}

	public String getEtctermcode() {
		return etctermcode;
	}

	public void setEtctermcode(String etctermcode) {
		this.etctermcode = etctermcode;
	}

	public String getEtcobuid() {
		return etcobuid;
	}

	public void setEtcobuid(String etcobuid) {
		this.etcobuid = etcobuid;
	}

	public String getEtctac() {
		return etctac;
	}

	public void setEtctac(String etctac) {
		this.etctac = etctac;
	}

	public String getProgramstarttime() {
		return programstarttime;
	}

	public void setProgramstarttime(String programstarttime) {
		this.programstarttime = programstarttime;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public void setrecordno(String recordno){
		this.recordno=recordno;
	}
	
	public String getrecordno(){
		return recordno;
	}
	
	public void setcardnetwork(String cardnetwork){
		this.cardnetwork=cardnetwork;
	}
	
	public String getcardnetwork(){
		return cardnetwork;
	}
	
	public void setcardid(String cardid){
		this.cardid=cardid;
	}
	
	public String getcardid(){
		return cardid;
	}
	
	public void setparkid(String parkid){
		this.parkid=parkid;
	}
	
	public String getparkid(){
		return parkid;
	}
	
	public void setareaid(String areaid){
		this.areaid=areaid;
	}
	
	public String getareaid(){
		return areaid;
	}
	
	public void setentrystation(String entrystation){
		this.entrystation=entrystation;
	}
	
	public String getentrystation(){
		return entrystation;
	}
	
	public void setentrylane(String entrylane){
		this.entrylane=entrylane;
	}
	
	public String getentrylane(){
		return entrylane;
	}
	
	public void setentrytime(String entrytime){
		this.entrytime=entrytime;
	}
	
	public String getentrytime(){
		return entrytime;
	}
	
	public void setentryoperator(String entryoperator){
		this.entryoperator=entryoperator;
	}
	
	public String getentryoperator(){
		return entryoperator;
	}
	
	public void setentryshift(String entryshift){
		this.entryshift=entryshift;
	}
	
	public String getentryshift(){
		return entryshift;
	}
	
	public void setautovlicense(String autovlicense){
		this.autovlicense=autovlicense;
	}
	
	public String getautovlicense(){
		return autovlicense;
	}
	
	public void setmvlicense(String mvlicense){
		this.mvlicense=mvlicense;
	}
	
	public String getmvlicense(){
		return mvlicense;
	}

	public int getCar_color() {
		return car_color;
	}

	public void setCar_color(int car_color) {
		this.car_color = car_color;
	}
	
	
	
}

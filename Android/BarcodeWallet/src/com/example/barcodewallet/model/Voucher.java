package com.example.barcodewallet.model;

public class Voucher {
	int id;
	String name;
	String barcodeID;
	String barcodeType;
	
	public Voucher(){
	}
	
	public Voucher(String name, String barcodeID, String barcodeType){
		this.name = name;
		this.barcodeID = barcodeID;
		this.barcodeType=barcodeType;
	}
	
	public Voucher(int id, String name, String barcodeID, String barcodeType){
		
	}
	
	//setters
	
	public void setId(int id){
		this.id =id;
	}
	
	public void setName (String name){
		this.name = name;
	}
	
	public void setBarcodeID(String barcodeID){
		this.barcodeID = barcodeID;
	}
	
	public void setBarcodeType(String barcodeType){
		this.barcodeType = barcodeType;
	}
	
	//getters
	public long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getBarcodeID(){
		return this.barcodeID;
	}
	
	public String getBarcodeType(){
		return this.barcodeType;
	}
}

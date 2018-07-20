package com.javaProject.internalDB.model;

import java.lang.reflect.Field;

public class Person {
 
    String firstName;
    
    String lastName;
    
    String emailId;
	
	String gender;
	
	String image;
	
	String cardType;
	
	String price;
	
	String flag1;
	
	String flag2;
	
	String city;
    
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getFlag1() {
		return flag1;
	}
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}
	public String getFlag2() {
		return flag2;
	}
	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public boolean isAnyFieldNull() throws IllegalAccessException {
	    for (Field f : getClass().getDeclaredFields()) {
	        if (f.get(this).equals(""))
	            return true;
	    }
	    return false;            
	}
	@Override
	public String toString() {
		return firstName + "," + lastName + ","+ emailId + "," + gender + "," + cardType + "," + price + "," + flag1 + "," + flag2 + "," + city;
	}
}
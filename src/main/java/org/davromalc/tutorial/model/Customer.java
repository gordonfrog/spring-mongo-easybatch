package org.davromalc.tutorial.model;

import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class Customer {

    @Id
    public String id;

    public Customer() {
		super();
		
	}
    public Customer(String id, String firstName, String lastName, String title, String location, String country,
			String phonenumber, String middleName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.location = location;
		this.country = country;
		this.phonenumber = phonenumber;
		this.middleName = middleName;
	}
    public Customer(String firstName, String lastName, String title, String location, String country,
			String phonenumber, String middleName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.location = location;
		this.country = country;
		this.phonenumber = phonenumber;
		this.middleName = middleName;
	}
    
	public String firstName;
    public String lastName;
    public String title;
    public String location;
    public String country;
    public String phonenumber;
    public String middleName;

}

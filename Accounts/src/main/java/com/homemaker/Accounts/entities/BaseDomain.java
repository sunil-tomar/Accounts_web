package com.homemaker.Accounts.entities;

import java.io.Serializable;

public class BaseDomain<IDType> implements Serializable {
	
	public BaseDomain() {
        //System.out.println("BaseEntity.BaseEntity()");
	}
	
    public IDType getId() {
        return null;
    }
}

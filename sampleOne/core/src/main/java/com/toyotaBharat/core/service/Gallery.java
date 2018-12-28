package com.toyotaBharat.core.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.sling.api.resource.LoginException;

public interface Gallery {
	
	public List<String> getImage(String path)throws SQLException, LoginException ;

}

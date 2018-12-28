package com.toyotaBharat.core.util;

public class AcmeUtils {
	
	public String getCarModelName(String url)
    {
        return url.replaceFirst(".*/([^/?]+).*", "$1");        
    }
}

/***
 * This interface helps in implementing the best practices of AEM as there
 * should be no direct reference of object using new keyword made in the
 * classes. Hence this interface is referred using the @Refrence annotation like
 * ResourceResolverFactory is referred in classes.
 * 
 * Example implementation can be seen in InsertDealerInformation under
 * com.toyotaBharat.core.servlets package
 */
package com.toyotaBharat.core.service;

public interface AcmeUtil {

	public String getNodeName(String url);

	public String getRelPath(String path);

}

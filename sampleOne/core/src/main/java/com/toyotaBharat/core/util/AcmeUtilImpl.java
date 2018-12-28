/***
 * This class implements the interface that will be referred using @Refrence annotation
 * As per the http://bitbucket.cybage.com/projects/CYB2961/repos/aem_toyotabharat/pull-requests/30/overview
 * pull request AcmeUtils will be integrated here in this code and will be used as per the best practices. 
 */
package com.toyotaBharat.core.util;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.toyotaBharat.core.service.AcmeUtil;

@Service
@Component
public class AcmeUtilImpl implements AcmeUtil {

	@Override
	public String getNodeName(String url) {
		return url.replaceFirst(".*/([^/?]+).*", "$1");
	}

	@Override
	public String getRelPath(String path) {
		return path.substring(1);
	}

}

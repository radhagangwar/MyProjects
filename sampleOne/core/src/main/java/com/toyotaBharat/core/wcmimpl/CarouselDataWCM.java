package com.toyotaBharat.core.wcmimpl;

import com.adobe.cq.sightly.WCMUse;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.*;
import org.apache.sling.api.resource.*;
import org.apache.sling.commons.json.JSONArray;


public class CarouselDataWCM extends WCMUse {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private Map<String, String> heroCarouselLinkDataMap = new HashMap<String, String>();
	
	private ResourceResolver resourceResolver;
	
    
	@Override
	public void activate() throws Exception {
		populateHeroCarouselLinkData();
		
	}
	
	public void populateHeroCarouselLinkData(){
		String heroCarouselPagePath;
		String currentPagePath;
		
		heroCarouselPagePath = get("pagePath", String.class);		
		resourceResolver = getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		
		currentPagePath = heroCarouselPagePath + "/jcr:content/par/homepageherocarousel";
		
		Resource currentPageResource = resourceResolver.getResource(currentPagePath);
		if(currentPageResource==null){
			log.info("No Resource Found.");
			return;
		}
		Node currentPageNode = currentPageResource.adaptTo(Node.class);
		
		try {
			log.info("In try Block");			
			
            Property property = null;
 
            if (currentPageNode.hasProperty("carousellinks")) {
            	
                property = currentPageNode.getProperty("carousellinks");
                
            }
 
            if (property != null) {
                JSONObject allHeroCarouselLink = null, singleHeroCarouselLink = null;
                Value[] values = null;
                
                if (property.isMultiple()) {
                    values = property.getValues();
                } else {
                    values = new Value[1];
                    values[0] = property.getValue();
                }
 
                for (Value val : values) {
                	allHeroCarouselLink = new JSONObject(val.getString());
                	
                    if (allHeroCarouselLink.has("carousellinksone")) {
                        JSONArray heroCarouselLinks = (JSONArray) allHeroCarouselLink.get("carousellinksone");
                       
                        if (heroCarouselLinks != null) {
                            for (int index = 0, length = heroCarouselLinks.length(); index < length; index++) {
                            		singleHeroCarouselLink = (JSONObject) heroCarouselLinks.get(index);                            		
                            		heroCarouselLinkDataMap.put((String)singleHeroCarouselLink.get("imagelink"), (String)singleHeroCarouselLink.get("linkpath"));
                            		log.info("Values"+singleHeroCarouselLink.get("linkpath")+"keys"+singleHeroCarouselLink.get("imagelink"));
                            }
                        } 
                    }
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
            log.info("An Excepetion Occured" + e.getMessage());
        }
	}
	
	public Map<String, String> getHeroCarouselLinkData() {
		return heroCarouselLinkDataMap;
	}

}
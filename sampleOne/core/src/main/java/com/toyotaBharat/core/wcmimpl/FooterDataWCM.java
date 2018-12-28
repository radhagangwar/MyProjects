package com.toyotaBharat.core.wcmimpl;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.toyotaBharat.core.models.FooterModel;

public class FooterDataWCM extends WCMUse {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ResourceResolver resourceResolver;	
	private FooterModel footobj = new FooterModel();
	private List<FooterModel> footerDataList = new ArrayList<>();

	@Override
	public void activate() throws Exception {
		setFooterData();		
	}
	
	public void setFooterData(){
		resourceResolver = getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		
		//Getting & Setting Follow Toyota Data
		Resource res = resourceResolver.getResource("/content/toyota-bharat/en/FooterAuthorPage/jcr:content/content/footer/textinfo1");
		ValueMap tempProp = res.adaptTo(ValueMap.class);
        footobj.setFollowToyotaText(tempProp.get("text", (String) null));
        
        //Getting & Setting CopyRight Data
        res = resourceResolver.getResource("/content/toyota-bharat/en/FooterAuthorPage/jcr:content/content/footer/textinfo");
        tempProp = res.adaptTo(ValueMap.class);
        footobj.setCopyRightText(tempProp.get("text", (String) null));
        
        //Getting & Setting Social Links Data
        res = resourceResolver.getResource("/content/toyota-bharat/en/FooterAuthorPage/jcr:content/content/footer/socialLinks");
        tempProp = res.adaptTo(ValueMap.class);
		
		//Getting & Setting Twitter Data
        footobj.setTwitterLink(tempProp.get("socialhref", (String) null));
        footobj.setTwitterIconPath(tempProp.get("imgpath", (String) null));
        footobj.setTwitterAltText(tempProp.get("altname", (String) null));
        
        //Getting & Setting Facebook Data
        footobj.setFacebookLink(tempProp.get("socialhref1", (String) null));
        footobj.setFacebookIconPath(tempProp.get("imgpath1", (String) null));
        footobj.setFacebookAltText(tempProp.get("altname1", (String) null));
        
        //Getting & Setting Youtube Data
        footobj.setYoutubeLink(tempProp.get("socialhref2", (String) null));
        footobj.setYoutubeIconPath(tempProp.get("imgpath2", (String) null));
        footobj.setYoutubeAltText(tempProp.get("altname2", (String) null));
        
        //Getting & Setting Footer Other Links Data
        res = resourceResolver.getResource("/content/toyota-bharat/en/FooterAuthorPage/jcr:content/content/footer/footOtherLinks");
		tempProp = res.adaptTo(ValueMap.class);
		
		//Getting & Setting Contact Us Data
		footobj.setContactUsText(tempProp.get("linkname", (String) null));
		footobj.setContactUsPath(tempProp.get("pagepath", (String) null));
		
		//Getting & Setting Announcement Data
		footobj.setAnnouncementText(tempProp.get("linkname1", (String) null));
		footobj.setAnnouncementPath(tempProp.get("pagepath1", (String) null));
		
		//Getting & Setting Legal Notice Data
		footobj.setLegalNoticeText(tempProp.get("linkname2", (String) null));
		footobj.setLegalNoticePath(tempProp.get("pagepath2", (String) null));
				
		//Getting & Setting Help Data;
		footobj.setHelpText(tempProp.get("linkname3", (String) null));
		footobj.setHelpPath(tempProp.get("pagepath3", (String) null));
		
		//Getting & Setting Site Map Data
		footobj.setSiteMapText(tempProp.get("linkname4", (String) null));
		footobj.setSiteMapPath(tempProp.get("pagepath4", (String) null));        
        
		footerDataList.add(footobj);
	}
	
	public List<FooterModel> getFooterData(){
		return footerDataList;
	}

}

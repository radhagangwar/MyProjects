package com.aem.community.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class FirstSlingModel {

    @Inject
    private SlingSettingsService slingSettingsService;

    @Inject
    protected String resourceType;

    @Inject
    private String headingtext;

    @Inject
    private String descriptiontext;

    public String getHeadingtext(){
      return headingtext;
    }

    public String getDescriptiontext(){
        return descriptiontext;
    }




}

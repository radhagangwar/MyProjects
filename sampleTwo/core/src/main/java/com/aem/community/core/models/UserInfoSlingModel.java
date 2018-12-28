package com.aem.community.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class UserInfoSlingModel {

    @Inject
    private String firstName;

    private String formattedName;

    @PostConstruct
    private void initModel(){
        this.formattedName = Character.toUpperCase(this.firstName.charAt(0)) + this.firstName.substring(1);
    }

    public String getFormattedName() {
        return formattedName;
    }
}

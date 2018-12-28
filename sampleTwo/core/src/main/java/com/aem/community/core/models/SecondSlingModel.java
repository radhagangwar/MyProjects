package com.aem.community.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

@Model(adaptables = SlingHttpServletRequest.class)
public class SecondSlingModel {

    // Injects currentPage using ScriptVariable annotation
    @ScriptVariable(name="currentPage")
    Page page;

    public String getPagePath() {
        return  page.getPath();
    }
}

package com.aem.community.core.services.impl;

import com.aem.community.core.services.ConfigurationService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service({ConfigurationServiceImpl.class})
@Component(immediate=true, metatype=true, label="Example Configuration Service")
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Property(unbounded= PropertyUnbounded.ARRAY, label="Multi String", cardinality=2, description="Example for Multi field config")
    private static final String MULTI_FIELD = "multifield";

    @Property(unbounded=PropertyUnbounded.DEFAULT, label="Simple String", description="Example for Simple text field config")
    private static final String SIMPLE_FIELD = "simplefield";

    @Property(name = "recipientsEmailIDs", cardinality = Integer.MAX_VALUE, unbounded = PropertyUnbounded.ARRAY, label = "Recipient Email ID(s)", value = {"contentapprover@domain.com"}, description = "Provide the recipients email ids")
    private static final String EMAILID_FIELD = "emailid";

    private String[] multiString;
    private String simpleString;
    private String emailId;

    @Override
    public String[] getMultiString() {
        return this.multiString;
    }

    @Override
    public String getSimpleString() {
        return this.simpleString;
    }

    @Override
    public String getEmailID() {
        return this.emailId;
    }


    @Activate
    protected void activate(Map<String, Object> properties)
    {
        LOG.info("[*** AEM ConfigurationService]: activating configuration service");
        readProperties(properties);
    }

    private void readProperties(Map<String, Object> properties) {
        LOG.info("All Property Values::::: " + properties.toString());

        this.multiString = PropertiesUtil.toStringArray(properties.get("multifield"));
        LOG.info("Mutli String Size: " + this.multiString.length);

        this.simpleString = PropertiesUtil.toString(properties.get("simplefield"), "default");
        LOG.info("Simple String: " + this.simpleString);

        this.emailId = PropertiesUtil.toString(properties.get("emailId"), "contentapprover@domain.com");
        LOG.info("Email ID:::::" + this.emailId);
    }
}

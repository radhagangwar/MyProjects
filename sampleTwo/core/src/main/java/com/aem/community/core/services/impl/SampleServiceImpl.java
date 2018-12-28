package com.aem.community.core.services.impl;

import com.aem.community.core.services.SampleService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(label = "Sample Service Impl", description = "This is a Sample Service Implementation", metatype = true, immediate = true)

@Properties({@Property(label="SampleVendor", name="service.vendor", value="SampleVendor",propertyPrivate=true )})

@Service(SampleService.class)
public class SampleServiceImpl implements SampleService {

    private static Logger Log = LoggerFactory.getLogger(SampleServiceImpl.class);

    private static final boolean DEFAULT_ENABLED = false;
    private boolean enabled = DEFAULT_ENABLED;

    ResourceResolverFactory resourceResolverFactory;

    @Property(label = "Service Enable/Disable", description = "Enables/Disables the service without nullifying service reference objects. This enable/disabling must be implemented in all public methods of this service.", boolValue = DEFAULT_ENABLED)
    public static final String PROP_ENABLED = "prop.enabled";

    @Override
    public String helloWorld() {
        if (!this.enabled) {
            return "Service has been disabled";
        }
        return "Hello World!";
    }

    @Override
    public String getName(String path) {
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(ResourceResolverFactory.SUBSERVICE, "systemUser");
            Log.info("ParamMap :::::::::" + paramMap);
            ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
            Resource resource = resourceResolver.resolve(path);
            Log.info("resource :::::::::" + resource);

            if (resource == null) {
                return null;
            }
            return resource.getName();
        } catch (Exception e) {
            Log.error("Error unable to continue updating {}", e);
        }


        return path;
    }
}

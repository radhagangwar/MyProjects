package com.aem.community.core.services.impl;

import com.aem.community.core.services.FirstService;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component(immediate = true, metatype = true, label = "First Service Impl", description = "This is my First Sample Service")
@Service(value = FirstService.class)
public class FirstServiceImpl implements FirstService {
    private static Logger Log = LoggerFactory.getLogger(FirstServiceImpl.class);

    @Property(value = "http://companyservices/myservice?wsdl")
    static final String SERVICE_ENDPOINT_URL = "service.endpoint.url";

    private String serviceEndpointUrl;

    @Activate
    public void activate(final Map<String, Object> props) {
        Log.info("Calling Activate Method");
        this.serviceEndpointUrl = (String)props.get(SERVICE_ENDPOINT_URL);
        Log.info("ServiceEndpointUrl:" + this.serviceEndpointUrl);
    }

    @Override
    public String getData() {
        return "Calling Service from:" + this.serviceEndpointUrl;
    }
}



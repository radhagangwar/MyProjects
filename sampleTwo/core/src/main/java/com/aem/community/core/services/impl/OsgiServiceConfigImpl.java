package com.aem.community.core.services.impl;

import com.aem.community.core.services.OsgiServiceConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component (service = OsgiServiceConfigImpl.class, immediate=true, configurationPolicy= ConfigurationPolicy.REQUIRE)
@Designate(ocd = OsgiServiceConfig.class)
public class OsgiServiceConfigImpl {
    private static Logger LOG = LoggerFactory.getLogger(OsgiServiceConfigImpl.class);

    private OsgiServiceConfig config;

    private String username;
    private String password;
    private String[] environment;
    private int validity;
    private String memberOf;

    @Activate
    public void activate(final OsgiServiceConfig serviceConfig){
        LOG.info("ClassName: OsgiServiceConfigImpl - Inside Activate Method");
        this.config = serviceConfig;

        LOG.info("Value Of Config is :::::" + config);
        username = serviceConfig.getUserName();
        password = serviceConfig.getPassword();
        environment = serviceConfig.getEnvironments();
        validity = serviceConfig.getValidity();
        memberOf = serviceConfig.getMemberOf();

        LOG.info("Value Of UserName is :::::" + username);
        LOG.info("Value Of Password is :::::" + password);
        LOG.info("Value Of Environment is :::::" + environment);
        LOG.info("Value Of Validity is :::::" + validity);
        LOG.info("Value Of MemberOf is :::::" + memberOf);
    }

    public String getSimpleValue() {
        return "hello " + config.configValue();
    }

}


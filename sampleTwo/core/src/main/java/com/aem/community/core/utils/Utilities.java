package com.aem.community.core.utils;

import com.adobe.cq.sightly.WCMUsePojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;

public class Utilities extends WCMUsePojo {
    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    @Override
    public void activate() throws Exception {
        log.info("Inside Activate Method");
        Node currentNode = getResource().adaptTo(Node.class);
        log.info("currentNode :::::::::::" + currentNode);

        if(currentNode.hasProperty("jcr:title")){
            log.info("This is our first page");
        }
    }
}



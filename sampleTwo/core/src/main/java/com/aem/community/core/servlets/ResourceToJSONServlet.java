package com.aem.community.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceToJSONServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(ResourceToJSONServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");


    }
}

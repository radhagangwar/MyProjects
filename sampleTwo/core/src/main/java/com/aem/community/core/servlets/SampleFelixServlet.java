package com.aem.community.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;

import com.aem.community.core.services.SampleFelixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SlingServlet(
        methods = "GET",
        metatype = true,
        resourceTypes = {"services/powerproxy"},
        selectors = {"groups"})

public class SampleFelixServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 2598426539166789515L;

    private static final Logger log = LoggerFactory.getLogger(SampleFelixServlet.class);

   @Override
    protected void doGet(final SlingHttpServletRequest request,
                         final SlingHttpServletResponse response) throws ServletException, IOException {

        try
        {
            log.info("---> THIS IS THE GET METHOD OF slingSevletApp/components/page/slingTemplate");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>This value was returned by an AEM Sling Servlet bound by using a Sling ResourceTypes property</h1>");
            out.println("</html></body>");

        }
        catch(Exception e)
        {
            log.info(e.getMessage(),e);
        }
    }
}
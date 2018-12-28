package com.aem.community.core.servlets;

import com.aem.community.core.services.FirstService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(paths = "/services/firstservlet", methods = "GET")
public class FirstServlet extends SlingAllMethodsServlet{

    @Reference
    FirstService firstService;

    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException,IOException {
        response.getWriter().write(firstService.getData());
    }
}

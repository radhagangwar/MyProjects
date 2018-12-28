package com.aem.community.core.servlets;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import javax.jcr.Repository;
import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(
        paths={"/bin/customservlet"}
)
@Properties({
        @Property(name="service.pid", value="com.day.servlets.SampleServlet",propertyPrivate=false),
        @Property(name="service.description",value="SampleDescription", propertyPrivate=false),
        @Property(name="service.vendor",value="SampleVendor", propertyPrivate=false)
})
public class SampleServletByPath extends SlingAllMethodsServlet {

    @Reference
    private Repository repository;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        String keys[] = repository.getDescriptorKeys();
        JSONObject jsonobject = new JSONObject();
        for(int i=0;i<keys.length;i++){
            try{
                jsonobject.put(keys[i], repository.getDescriptor(keys[i]));
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse resp) throws ServletException, IOException {
        //Write your custom code here
    }
}

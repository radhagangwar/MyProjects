<%@include file="/libs/fd/af/components/guidesglobal.jsp"%>
<%@include file="/libs/foundation/global.jsp"%>
<%@page
	import="com.day.cq.wcm.foundation.forms.FormsHelper,
            org.apache.sling.api.resource.ResourceUtil,
            com.toyotaBharat.core.models.ModelClass,
			com.toyotaBharat.core.service.HandleModelForm,
            org.apache.sling.api.resource.ValueMap"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0"%>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0"%>

<cq:defineObjects/>
<sling:defineObjects/>
<% 
    String modelName = request.getParameter("modelName");
	String[] modelTypes = request.getParameterValues("modelTypes");
	
    HandleModelForm hmf = sling.getService(HandleModelForm.class);
    hmf.handleModelFormData(modelName,modelTypes);
%>
<%
    response.sendRedirect("www.google.com");
%>
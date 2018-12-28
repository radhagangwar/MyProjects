package com.toyotaBharat.core.serviceImpl;


import javax.jcr.Session;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.*;
import javax.jcr.Node;
import com.toyotaBharat.core.models.CarModel;
import com.toyotaBharat.core.service.InsertModelData;

@Component
@Service
public class InsertModelDataImpl implements InsertModelData{

	/** Default log. */
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	String parentPathToModel = "etc/toyota/models";
	String modelPath;
	
	/*Method to Store the Model and it's Fuel type into Jcr*/
	@Override
	public void insertModelData(CarModel model,String[] modelTypes, ResourceResolver rr) {
		try {		
			Session sessionModel = rr.adaptTo(Session.class);
			Node root = sessionModel.getRootNode();			
			Node pathToModel = root.getNode(parentPathToModel);
			Node modelNode = pathToModel.addNode(model.getModelName(), "sling:Folder");
			modelNode.setProperty("description", "Model Node");
			sessionModel.save();
			modelPath =  parentPathToModel + "/" + model.getModelName();
			Node pathToModelType = root.getNode(modelPath);			
			if (modelTypes != null) {
				for (int i = 0; i < modelTypes.length; i++) {
					Node type = pathToModelType.addNode(modelTypes[i], "sling:OrderedFolder");
					sessionModel.save();
				}
			}
			sessionModel.logout();
		} catch (Exception e) {
			log.error("Catch block of InsertModelDataImpl" + e.getMessage());
		}
	}
}

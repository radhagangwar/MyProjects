package com.aem.community.core;

import com.adobe.cq.sightly.WCMUsePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;

public class HeroTextComponent extends WCMUsePojo {

    private static Logger Log = LoggerFactory.getLogger(HeroTextComponent.class);

    /** The hero text bean. */
    private HeroTextBean heroTextBean = null;

    @Override
    public void activate() throws Exception {
        Node currentNode = getResource().adaptTo(Node.class);
        Log.info("CurrentNode: " + currentNode);

        heroTextBean = new HeroTextBean();

        if(currentNode.hasProperty("jcr:Heading")){
            heroTextBean.setHeadingText(currentNode.getProperty("./jcr:Heading").getString());
        }
        if(currentNode.hasProperty("jcr:description")){
            heroTextBean.setDescription(currentNode.getProperty("./jcr:description").getString());
        }
    }

    public HeroTextBean getHeroTextBean() {
        return this.heroTextBean;
    }
}

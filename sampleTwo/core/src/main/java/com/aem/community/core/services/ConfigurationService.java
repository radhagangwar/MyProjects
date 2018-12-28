package com.aem.community.core.services;

public interface ConfigurationService {
    public abstract String[] getMultiString();

    public abstract String getSimpleString();

    public String getEmailID();
}

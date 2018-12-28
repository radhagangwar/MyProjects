package com.aem.community.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = "RGAEM User Account Configuration", description = "Configure the user Details")
public @interface OsgiServiceConfig {
    @AttributeDefinition(name = "user.name", description = "User Name")
    String getUserName() default "";

    @AttributeDefinition(name = "user.password", description = "Password of the User Account", type = AttributeType.PASSWORD)
    String getPassword() default "";

    @AttributeDefinition(name = "user.environments", description = "Define the all environment where this user will be available")
    String[] getEnvironments() default {};

    @AttributeDefinition(name = "user.validity", description = "Validity of the User Account", defaultValue = "10", required = true, type = AttributeType.INTEGER, min ="10")
    int getValidity() default 10;

    @AttributeDefinition(name = "memberof.name", description = "Member of the group", options = {
            @Option(label = "Approval", value = "approve"),
            @Option(label = "Editor", value = "editor"),
            @Option(label = "Administrator", value = "admin")})
    String getMemberOf() default "";

    @AttributeDefinition(name = "Config Value", description = "Configuration value")
    String configValue();

    @AttributeDefinition(name = "MultipleValues", description = "Multi Configuration values")
    String[] getStringValues();

    @AttributeDefinition(name = "NumberValue", description = "Number values", type=AttributeType.INTEGER)
    int getNumberValue();
}

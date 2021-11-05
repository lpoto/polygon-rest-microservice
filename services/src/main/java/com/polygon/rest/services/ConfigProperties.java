package com.polygon.rest.services;

import javax.enterprise.context.ApplicationScoped;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;

/**
 * ConfigProperties class
 *
 * @author Luka Potočnik
 * @since 1.0.0
 */
@ApplicationScoped
@ConfigBundle("rest-config")
public class ConfigProperties {
	private String authConnectUrl;

	public void setAuthConnectUrl(String authConnectUrl) {
		this.authConnectUrl = authConnectUrl;
	}

	public String getAuthConnectUrl() {
		return authConnectUrl;
	}
}

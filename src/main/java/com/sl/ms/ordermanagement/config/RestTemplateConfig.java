package com.sl.ms.ordermanagement.config;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestTemplateConfig {
	
	RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate() {
		initializeRestTemplate(false);
		ObjectMapper mapper=new ObjectMapper();
		//mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		List<HttpMessageConverter<?>> messageConverters=new ArrayList<>();
		MappingJackson2HttpMessageConverter converters=new MappingJackson2HttpMessageConverter();
		converters.setObjectMapper(mapper);
		converters.setPrettyPrint(true);
		messageConverters.add(converters);
		this.restTemplate.setMessageConverters(messageConverters);
		return this.restTemplate;
	}


	private void initializeRestTemplate(boolean secured) {
		if (secured) {
			SSLContext sslContext;
			try {
				sslContext=SSLContexts.custom().loadTrustMaterial(getKeyStore(),getTrustStrategy()).build();
				SSLConnectionSocketFactory socketFactory=new SSLConnectionSocketFactory(sslContext);
				HttpClient httpClient=HttpClients.custom().setSSLSocketFactory(socketFactory).build();
				HttpComponentsClientHttpRequestFactory requestFactory=new HttpComponentsClientHttpRequestFactory();
				requestFactory.setHttpClient(httpClient);
				this.restTemplate=new RestTemplate(requestFactory);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else {
			this.restTemplate=new RestTemplate();
		}
	}


	private TrustStrategy getTrustStrategy() {
		return new TrustAllStrategy();
	}


	private KeyStore getKeyStore() {
		return null;
	}
}
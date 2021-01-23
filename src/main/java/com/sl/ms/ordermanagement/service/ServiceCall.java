package com.sl.ms.ordermanagement.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableHystrix
@Service
public class ServiceCall {

	@Autowired
	RestTemplate restTemplate;

	@Value("${rest.url}")
	private String url;

	@Value("${rest.token}")
	private String token;

	@HystrixCommand(fallbackMethod = "defaultError")
	public Object callInventoryMgmt(int productid) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		//try {
			ResponseEntity<Map> ent = restTemplate.exchange(url + "/{productid}", HttpMethod.GET, entity, Map.class,
					productid);
			return new JSONObject(ent.getBody());
		/*} catch (Exception e) {
			return e;
		}*/
	}

	public Object defaultError(int productid) {
		String err = "Looks like service unavailable. Please try later.";
		return err;
	}

}

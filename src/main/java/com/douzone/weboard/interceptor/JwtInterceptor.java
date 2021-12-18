package com.douzone.weboard.interceptor;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.weboard.vo.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {
	@Value("${authServer.url}")
	private String authServerUrl;
	@Autowired
	private ObjectMapper objectMapper;
	private static final String HEADER_AUTH = "Authorization";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		final String token = request.getHeader(HEADER_AUTH);
		HttpHeaders headers = new HttpHeaders();

		headers.set(HEADER_AUTH, token);
		HttpEntity requestEntity = new HttpEntity(headers);

		if (token != null) {
			try {
				ResponseEntity<String> responseEntity = restTemplate.exchange(authServerUrl + "/validate", HttpMethod.GET,
						requestEntity, String.class);
				User authUser = objectMapper.readValue(responseEntity.getBody(), User.class);
				request.setAttribute("authUser", authUser);
				return true;
			}catch (RestClientException e) {
				System.out.println(e);
				response.setStatus(401);
				return false;
			}

		}
		
		response.setStatus(401);
		return false;
	}
}

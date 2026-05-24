package com.picpaydesafio.services;

import com.picpaydesafio.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("rawtypes")
    public boolean authorizeTransaction(User sender, BigDecimal value) {
        String url = "https://util.devi.tools/api/v2/authorize";

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String status = (String) response.getBody().get("status");
                return "success".equalsIgnoreCase(status);
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
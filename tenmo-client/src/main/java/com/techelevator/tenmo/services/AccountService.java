package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private static String API_BASE_URL = "http://localhost:8080/account/";
    final private RestTemplate restTemplate = new RestTemplate();
    final private AuthenticatedUser currentUser;

    public AccountService(String url, AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
        API_BASE_URL = url;
    }
//Send token using the exchange in order to pull the balance from the server.
    public BigDecimal getBalance() {
        BigDecimal balance = new BigDecimal(0);
        try {
            balance = restTemplate.exchange(API_BASE_URL + "balance/" + currentUser.getUser().getId(),
                    HttpMethod.GET, makeAccountAuthEntity(), BigDecimal.class).getBody();
            System.out.println("Your current account balance is: " + balance);
        } catch (RestClientException e) {
            System.out.println("Could not get balance"); //Getting the exception pat
        }
        return balance;
    }
<<<<<<< HEAD
//Making HttpEntity and using token info.
=======

>>>>>>> eeb0562fcace851ff03b4b6e659dbe7be0f22851
    private HttpEntity<Void> makeAccountAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }

}

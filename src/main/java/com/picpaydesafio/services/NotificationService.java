package com.picpaydesafio.services;

import com.picpaydesafio.domain.User;
import com.picpaydesafio.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        // URL mockada do desafio para simular o envio de notificação
        String url = "https://util.devi.tools/api/v1/notify";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, notificationRequest, String.class);

            if (!(response.getStatusCode() == HttpStatus.OK)) {
                System.out.println("Erro ao enviar notificação");
                throw new Exception("Serviço de notificação está fora do ar.");
            }
        } catch (Exception e) {
            // Em cenários reais, se a notificação falhar, geralmente não desfazemos a transferência.
            // Mas para o desafio, vamos apenas printar o erro ou lançar a exceção dependendo do rigor do teste.
            System.out.println("Falha na comunicação com o serviço de notificação: " + e.getMessage());
        }
    }
}
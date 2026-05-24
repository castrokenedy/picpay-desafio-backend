package com.picpaydesafio.services;

import com.picpaydesafio.domain.Transaction;
import com.picpaydesafio.domain.User;
import com.picpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
public class TransactionService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private TransactionRepository repository;

    @Transactional
    public Transaction createTransaction(TransactionDTO transactionDto) throws Exception {
        User sender = this.userService.findUserById(transactionDto.senderId());
        User receiver = this.userService.findUserById(transactionDto.receiverId());

        this.userService.validateTransaction(sender, transactionDto.value());
        // Consulta o serviço autorizador externo
        boolean isAuthorized = this.authService.authorizeTransaction(sender, transactionDto.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada pelo serviço externo.");
        }

        sender.setBalance(sender.getBalance().subtract(transactionDto.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.value()));

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDto.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        this.repository.save(newTransaction);
        this.notificationService.sendNotification(receiver, "Dinheiro recebido com sucesso!");    
        return newTransaction;
    }
}
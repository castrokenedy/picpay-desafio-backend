package com.picpaydesafio.services;

import com.picpaydesafio.domain.User;
import com.picpaydesafio.domain.UserType;
import com.picpaydesafio.dtos.UserDTO;
import com.picpaydesafio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, java.math.BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuários do tipo Lojista não estão autorizados a realizar transferências.");
        }

        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente.");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public User createUser(UserDTO data) {
        User newUser = new User();
        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setDocument(data.document());
        newUser.setBalance(data.balance());
        newUser.setEmail(data.email());
        newUser.setPassword(data.password());
        newUser.setUserType(data.userType());
        
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
package br.edu.ifba.jogomemoria.user.service;

import br.edu.ifba.jogomemoria.infrastructure.exception.ObjectNotFoundException;
import br.edu.ifba.jogomemoria.infrastructure.exception.ValidationException;
import br.edu.ifba.jogomemoria.infrastructure.suport.StringUtil;
import br.edu.ifba.jogomemoria.user.model.User;
import br.edu.ifba.jogomemoria.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    public User findById (Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()){
            throw new ObjectNotFoundException("Usuário não encontrado!");
        }

        return user.get();
    }

    public User findByUsername (String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ObjectNotFoundException("Usuário não encontrado!");
        }

        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save (User user) {

        if (user == null) {
            throw new ValidationException("Usuário nulo!");
        }

        if (validate(user)) {
            userRepository.save(encodePassword(user));
        }

        return user;
    }

    public void delete (User user) {

        if (user == null) {
            throw new ValidationException("Usuário nulo!");
        }

        if (userRepository.findById(user.getId()) == null){
            throw new ObjectNotFoundException("Usuário não existe!");
        }

        userRepository.delete(user);
    }

    private User encodePassword (User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmPassword(encoder.encode(user.getConfirmPassword()));
        return user;
    }

    private boolean validate (User user) {

        StringUtil strUtil = new StringUtil();

        if (strUtil.isNullOrEmpty(user.getName())){
            throw new ValidationException("Nome inválido!");
        }

        if (user.getName().length() > 100) {
            throw new ValidationException("Nome muito longo! Máximo 100 caracteres!");
        }

        if (strUtil.isNullOrEmpty(user.getUsername())){
            throw new ValidationException("Usuário inválido");
        }

        if (user.getUsername().length() > 25) {
            throw new ValidationException("Usuário muito longo! Máximo 25 caracteres!");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ValidationException("Usuário já cadastrado!");
        }

        if (strUtil.isNullOrEmpty(user.getPassword())) {
            throw new ValidationException("Senha inválida!");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new ValidationException("As senhas não conferem!");
        }

        if (user.getPassword().length() < 8) {
            throw new ValidationException("Senha muito curta! Mínimo 8 caracteres!");
        }

        if (user.getPassword().length() > 50) {
            throw new ValidationException("Senha muito longa! Máximo 50 caracteres!");
        }

        return true;
    }
}
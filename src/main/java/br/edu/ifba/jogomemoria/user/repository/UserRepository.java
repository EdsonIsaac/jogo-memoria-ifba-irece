package br.edu.ifba.jogomemoria.user.repository;

import br.edu.ifba.jogomemoria.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername (String username);

    @Override
    @Query("FROM User WHERE username <> 'admin'")
    List<User> findAll();
}
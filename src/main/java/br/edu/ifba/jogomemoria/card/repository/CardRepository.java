package br.edu.ifba.jogomemoria.card.repository;

import br.edu.ifba.jogomemoria.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {}
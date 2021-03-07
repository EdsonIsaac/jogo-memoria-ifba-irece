package br.edu.ifba.jogomemoria.card.model;

import br.edu.ifba.jogomemoria.infrastructure.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_card")
public class Card extends AbstractEntity {

    @Column(columnDefinition = "text")
    private String cardOne;

    @Column(columnDefinition = "text")
    private String cardTwo;

    public String getCardOne() {
        return cardOne;
    }

    public void setCardOne(String cardOne) {
        this.cardOne = cardOne;
    }

    public String getCardTwo() {
        return cardTwo;
    }

    public void setCardTwo(String cardTwo) {
        this.cardTwo = cardTwo;
    }

    @Override
    public String toString() {
        return
            "Card{" +
                "cardOne='" + cardOne + '\'' +
                ", cardTwo='" + cardTwo + '\'' +
            '}'
        ;
    }
}
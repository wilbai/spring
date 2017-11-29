package com.wil.pojo;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wil on 2017/11/28.
 */
@Entity
@Table(name = "t_card")
public class Card {

    @Id
    @GenericGenerator(name = "fk", strategy = "foreign",
            parameters = @org.hibernate.annotations.Parameter(name = "property", value = "person"))
    @GeneratedValue(generator = "fk")
    private Integer id;
    @Column(name = "card_name")
    private String cardName;
    @OneToOne(mappedBy = "card")
    @PrimaryKeyJoinColumn
    private Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

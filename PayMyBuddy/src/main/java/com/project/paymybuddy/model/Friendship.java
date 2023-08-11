package com.project.paymybuddy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Entity
public class Friendship {

    //lien entre user et user

    @ManyToOne
    @JoinColumn(name = "user_origin_id")
    public User userOriginId;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    public User friendId;
    @Id
    private Long id;

}

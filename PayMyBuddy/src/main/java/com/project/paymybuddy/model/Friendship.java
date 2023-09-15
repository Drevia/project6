package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(FriendshipPk.class)
public class Friendship {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_origin_id")
    public AppUser appUserOriginId;

    @Id
    @ManyToOne
    @JoinColumn(name = "friend_id")
    public AppUser userFriendId;




}

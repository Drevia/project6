package com.project.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Entity
public class Friendship {

    //lien entre user et user

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(
                    name = "appUserOriginId",
                    column = @Column(name = "user_origin_id")
            ),
            @AttributeOverride(
                    name = "userFriendId",
                    column = @Column(name = "friend_id")
            )
    })
    private FriendshipPk id;




}

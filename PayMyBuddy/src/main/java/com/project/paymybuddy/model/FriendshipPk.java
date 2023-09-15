package com.project.paymybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipPk implements Serializable {

    public AppUser appUserOriginId;

    public AppUser userFriendId;


}

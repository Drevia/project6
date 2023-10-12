package com.project.paymybuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipPk implements Serializable {

    public AppUser appUserOriginId;

    public AppUser userFriendId;


}

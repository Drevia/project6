package com.project.paymybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendshipPk implements Serializable {

    public Integer appUserOriginId;

    public Integer userFriendId;

    public FriendshipPk() {
    }

    public FriendshipPk(Integer userOriginId, Integer friendId) {
        this.appUserOriginId = userOriginId;
        this.userFriendId = friendId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipPk that = (FriendshipPk) o;
        return Objects.equals(appUserOriginId, that.appUserOriginId) &&
                Objects.equals(userFriendId, that.userFriendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserOriginId, userFriendId);
    }
}

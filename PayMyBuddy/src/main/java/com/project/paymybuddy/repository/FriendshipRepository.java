package com.project.paymybuddy.repository;

import com.project.paymybuddy.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    List<Friendship> findAllByAppUserOriginId_Id(Integer id);


}

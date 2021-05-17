package com.heptagon.userdetailstask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heptagon.userdetailstask.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

}

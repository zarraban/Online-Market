package com.example.Online_Market.repository.user;

import com.example.Online_Market.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {
}

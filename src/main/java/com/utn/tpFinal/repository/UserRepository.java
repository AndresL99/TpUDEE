package com.utn.tpFinal.repository;

import com.utn.tpFinal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByUserNameAndAndPassword(String userName,String password);
    User findByUserName(String userName);

}

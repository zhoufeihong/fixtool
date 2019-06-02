package com.za.console.reponsitory;

import com.za.console.entity.UserPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserReponsitory extends JpaRepository<UserPO, Long> {
    Page<UserPO> findAll(Specification<UserPO> specification, Pageable pageable);
    UserPO findByUserName(String userName);
}

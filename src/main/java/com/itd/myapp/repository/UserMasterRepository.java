package com.itd.myapp.repository;
import com.itd.myapp.domain.UserMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

}

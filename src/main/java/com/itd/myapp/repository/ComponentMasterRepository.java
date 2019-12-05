package com.itd.myapp.repository;
import com.itd.myapp.domain.ComponentMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ComponentMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComponentMasterRepository extends JpaRepository<ComponentMaster, Long> {

}

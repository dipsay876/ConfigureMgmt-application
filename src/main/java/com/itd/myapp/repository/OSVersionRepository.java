package com.itd.myapp.repository;
import com.itd.myapp.domain.OSVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OSVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OSVersionRepository extends JpaRepository<OSVersion, Long> {

}

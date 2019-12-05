package com.itd.myapp.repository;
import com.itd.myapp.domain.ConfigurationFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConfigurationFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigurationFileRepository extends JpaRepository<ConfigurationFile, Long> {

}

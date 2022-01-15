package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.schemiq.model.ElementActivityModel;
import project.schemiq.model.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementActivityRepository extends JpaRepository<ElementActivityModel, Long> {

    @Query("SELECT ea FROM ElementActivity ea where ea.user = :userID ORDER BY ea.id ASC")
    Optional<List<ElementActivityModel>> findByUserID(UserModel userID);
}

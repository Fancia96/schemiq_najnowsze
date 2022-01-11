package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends JpaRepository<ElementModel, Long> {

    @Query(" SELECT e FROM element e " +
            "where e.boardModel = :boardModel ")
    List<ElementModel> findBoardElementsByBoardID(BoardModel boardModel);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM element_message_model where element_model_id = :elementID ", nativeQuery = true)
    void deleteElementMessages(Long elementID);
}

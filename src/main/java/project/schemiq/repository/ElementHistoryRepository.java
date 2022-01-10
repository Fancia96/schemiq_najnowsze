package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementHistoryModel;

import java.util.List;

@Repository
public interface ElementHistoryRepository extends JpaRepository<ElementHistoryModel, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM element_history_model where element_model_id = :elementID ", nativeQuery = true)
    void deleteElementHistory(Long elementID);

//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM user_board_model where board_model_id = :boardID and board_model_id = :boardID", nativeQuery = true)
//    void deleteUserFromABoard(Long boardID, Long userID);


//    @Transactional
//    @Query(value = "SELECT user_id FROM user_board_model where board_model_id = :boardID ", nativeQuery = true)
//    List<Long> findBoardUsers(Long boardID);

}

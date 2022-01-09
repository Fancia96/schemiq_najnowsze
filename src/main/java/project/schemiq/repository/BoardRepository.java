package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.schemiq.model.BoardModel;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_board_model where board_model_id = :boardID ", nativeQuery = true)
    void deleteBoardUsers(Long boardID);

//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM user_board_model where board_model_id = :boardID and board_model_id = :boardID", nativeQuery = true)
//    void deleteUserFromABoard(Long boardID, Long userID);


    @Transactional
    @Query(value = "SELECT user_id FROM user_board_model where board_model_id = :boardID ", nativeQuery = true)
    List<Long> findBoardUsers(Long boardID);

}

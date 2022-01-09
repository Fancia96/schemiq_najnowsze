package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementModel;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_board_model where board_model_id = :boardID ", nativeQuery = true)
    void deleteBoardUsers(Long boardID);

}

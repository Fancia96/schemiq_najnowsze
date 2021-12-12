package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends JpaRepository<ElementModel, Long> {

    @Query(" SELECT e FROM element e " +
            "where e.boardModel = :boardModel ")
    List<ElementModel> findBoardElementsByBoardID(BoardModel boardModel);
}

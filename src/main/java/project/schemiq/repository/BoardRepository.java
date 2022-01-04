package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementModel;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel, Long> {

    List<BoardModel> findBoardModelBy(Long ID);

}

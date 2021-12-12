package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.schemiq.model.BoardModel;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel, Long> {

}

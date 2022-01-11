package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.schemiq.model.ElementHistoryModel;
import project.schemiq.model.ElementMessageModel;

@Repository
public interface ElementMessageRepository extends JpaRepository<ElementMessageModel, Long> {


}

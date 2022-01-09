package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.schemiq.model.BoardModel;
import project.schemiq.model.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Query(" SELECT u FROM user u " +
            "where u.id = :userID ")
    Optional<UserModel> findUserById(Long userID);

    @Query(" SELECT u FROM user u " +
            "where u.name = :name ")
    Optional<UserModel> findUserByName(String name);

    @Query(" SELECT u FROM user u " +
            "where u.email = :email ")
    Optional<UserModel> findUserByEmail(String email);

    @Query(" SELECT u FROM user u " +
            "where u.boardModel = :board ")
    List<UserModel> findUsersByBoard(BoardModel board);


}

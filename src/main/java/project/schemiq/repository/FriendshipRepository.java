package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.schemiq.model.FriendshipModel;
import project.schemiq.model.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipModel, Long> {

    @Query(" SELECT f FROM FriendshipModel f ")
    Optional<List<FriendshipModel>> findAllFriendships();

    @Query(" SELECT f FROM FriendshipModel f " +
            "where f.personOne = :person")
    Optional<List<FriendshipModel>> findFriendsForID(UserModel person);

    @Query(" SELECT f FROM FriendshipModel f " +
            "where f.personOne = :personOne and f.personTwo = :personTwo")
    Optional<FriendshipModel> findFriendshipBetweenPeople(UserModel personOne, UserModel personTwo);

    @Query(" SELECT f FROM FriendshipModel f " +
            "where (f.personTwo = :person) or (f.personTwo = :person)")
    Optional<List<FriendshipModel>> findAllFreindshipsThisPersonHasWithEveryoneAndBack(UserModel person);

}

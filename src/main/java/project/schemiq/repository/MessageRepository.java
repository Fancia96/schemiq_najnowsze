package project.schemiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.schemiq.model.MessageModel;
import project.schemiq.model.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {

    //zalicz wszystkie wiadomosci pomiedzy osobami

    //find in conversation word like bblkabla

    @Query(" SELECT msg FROM MessageModel msg " +
            "where (msg.personFrom = :personFrom and msg.personTo = :personTo) or " +
            "(msg.personTo = :personFrom and msg.personFrom = :personTo)")
    Optional<List<MessageModel>> findAllMessagesBetweenPeople(UserModel personFrom, UserModel personTo);

    @Query(" SELECT msg.id FROM MessageModel msg " +
            "where (msg.personFrom = :personFrom and msg.personTo = :personTo) or " +
            "(msg.personTo = :personFrom and msg.personFrom = :personTo)")
    Optional<List<Long>> findAllMessagesIDsBetweenPeople(UserModel personFrom, UserModel personTo);

    //tylko do wyswietlenia ladnie dla
    @Query(" SELECT msg.personFrom.name, msg.msg, msg.date FROM MessageModel msg " +
            "where (msg.personFrom = :personFrom and msg.personTo = :personTo) or " +
            "(msg.personTo = :personFrom and msg.personFrom = :personTo) order by msg.date ")
    Optional<List<Object>> findAllMessagesBetweenPeopleOrderByTime(UserModel personFrom, UserModel personTo);

    @Query(" SELECT msg FROM MessageModel msg " +
            "where (msg.personFrom = :person) or (msg.personTo = :person)")
    Optional<List<MessageModel>> findAllMessagesThisPersonSentAndReceived(UserModel person);

    @Query(" SELECT msg FROM MessageModel msg " +
            "where ((msg.personFrom = :personFrom and msg.personTo = :personTo) or " +
            "(msg.personTo = :personFrom and msg.personFrom = :personTo)) and UPPER(msg.msg) LIKE %:msg% ")
    Optional<List<MessageModel>> findMessagesContainATextBetweenIDs(UserModel personFrom, UserModel personTo, String msg);

}

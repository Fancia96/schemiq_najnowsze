//package project.schemiq.test;
//
//
//import org.hibernate.ObjectNotFoundException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.springframework.util.Assert;
//import project.schemiq.SchemiqApplication;
//import project.schemiq.Service.FriendshipService;
//import project.schemiq.Service.MessageService;
//import project.schemiq.Service.UserService;
//import project.schemiq.model.MessageModel;
//import project.schemiq.model.UserModel;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class MessageServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private FriendshipService friendshipService;
//
//    @Autowired
//    private MessageService messageService;
//
//    @Test
//    public void shouldSendMessage(){
//        messageService.deleteAll();
//        userService.deleteAll();
//        friendshipService.deleteAll();
//        createMessage();
//
//        messageService.sendMessageBetweenIDs(
//                1l, 2l, new Message("abc"));
//
//        Assert.assertNotNull(messageService.getMessageByID(1l));
//
//
//    }
//    @Test
//    public void shouldNotSendMessage(){
//        messageService.deleteAll();
//        personService.deleteAll();
//        friendshipService.deleteAll();
//        createMessage();
//
//        try {
//        messageService.sendMessageBetweenIDs(
//                1l, 2l, new Message("abc"));
//            Assert.assertTrue(false);
//        }
//        catch(
//                ObjectNotFoundException obj) {
//            Assert.assertEquals(obj.getEntityName(),
//                    SchemiqApplication.personNotFound);
//        }
//
//    }
//
//    @Test
//    public void shouldDeleteMessage(){
//        messageService.deleteAll();
//        personService.deleteAll();
//        friendshipService.deleteAll();
//        createMessage();
//
//        messageService.sendMessageBetweenIDs(
//                1l, 2l, new Message("abc"));
//
//        try {
//            messageService.deleteMessageByID(1L);
//            messageService.getMessageByID(1l);
//            Assert.assertTrue(false);
//        }
//        catch(
//                ObjectNotFoundException obj) {
//            Assert.assertEquals(obj.getEntityName(),
//                    SchemiqApplication.messageNotFound);
//        }
//
//    }
//
//    @Test
//    public void shouldNotDeleteMessage(){
//        messageService.deleteAll();
//        userService.deleteAll();
//        friendshipService.deleteAll();
//        createMessage();
//
//        messageService.sendMessageBetweenIDs(
//                1l, 2l, new MessageModel("abc"));
//
//        userService.deletePersonByID(1l);
//
//        try {
//            messageService.deleteAllMessagesBetweenIDs(1l, 2l);
//            Assert.assertTrue(false);
//        }
//        catch(
//    ObjectNotFoundException obj) {
//        Assert.assertEquals(obj.getEntityName(),
//                SchemiqApplication.userNotFound);
//    }
//
//    }
//
//    private void createMessage(){
//
//        UserModel userOne = new UserModel("Tom");
//        UserModel userTwo = new UserModel("TomTwo");
//
//        personService.createPerson(userOne);
//        personService.createPerson(userTwo);
//
//        friendshipService.addFriendshipBetweenIDs(userOne.getID(), userTwo.getID());
//    }
//
}

<template>
  <div>
    <div class="row">
      <div class="col-3">
        <b-card>
          <b-card-header>Friends list</b-card-header>
          <b-list-group>
            <b-list-group-item class="d-flex align-items-center" v-for="friend in friends" :key="friend.id" button @click="openFriend(friend)">
              <b-avatar variant="primary" class="mr-3" :text="(friend.name || '').charAt(0).toUpperCase()"></b-avatar>
              <span>{{friend.name}}</span>
            </b-list-group-item>
          </b-list-group>
        </b-card>
      </div>
      <div class="col-9">
        <div class="text-end">
          <b-btn type="primary" @click="openAddOptionFriend()"><b-icon-plus></b-icon-plus> Add friend</b-btn>
        </div>
      </div>
    </div>
    <b-modal ref="friendModal" hide-footer :title="edit.friend.name">
      <b-list-group ref="chatbox" style="max-height: 50vh; overflow-y: auto;">
        <b-list-group-item v-for="(message, idx) in messages" :key="idx">
          <div class="d-flex align-items-center justify-content-between">
            <small class="text-muted">{{message[0]}}</small>
            <small class="text-muted">{{message[2]}}</small>
          </div>
          <div style="overflow: hidden; text-wrap: normal; word-break: break-all">{{message[1]}}</div>
        </b-list-group-item>
      </b-list-group>
      <b-form @submit="sendMessage(edit.friend.id)">
        <div class="row">
          <div class="col-10">
            <b-form-group>
              <b-form-input
                  v-model="edit.friend.message"
                  type="text"
                  minlength="1"
                  maxlength="100"
                  placeholder="Enter message"
                  required
              ></b-form-input>
            </b-form-group>
          </div>
          <div class="col-2">
              <b-btn variant="outline-primary" type="submit">
                <b-icon-arrow-right-circle />
              </b-btn>
          </div>
        </div>
<h3>
</h3>
      </b-form>
      <div class="d-flex justify-content-center mt-3">
        <b-button v-if="edit.friend.id" variant="danger" style="margin-right: 1em;" @click="deleteFriend(edit.friend)">
          <b-icon-trash /> Delete friend
        </b-button>


      </div>
    </b-modal>

    <b-modal ref="friendAddOptionModal" hide-footer title="Choose option to add a friend">
      <div class="d-flex justify-content-evenly align-items-center">
        <b-btn type="primary" @click="openAddFriendByID()"><b-icon-plus></b-icon-plus> Add friend by ID</b-btn>
        <b-btn type="primary" @click="openAddFriendBySearch()"><b-icon-plus></b-icon-plus> Search friends</b-btn>
      </div>
    </b-modal>

    <b-modal ref="friendAddBySearchModal" hide-footer title="Searching for friends">
      <b-form @submit="addFriend(addFriendByID)">

        <b-form-group
            id="input-group-1"
            label="Or search for them by name to invite"
            label-for="input-1"
            description=""
        >
          <b-form-input
              id="input-1"
              v-model="addFriendByID"
              type="text"
              placeholder="Pick a user"
              required
          ></b-form-input>
        </b-form-group>

        <div class="d-flex justify-content-center">
          <b-btn type="primary" @click="openAddFriendBySearch()"><b-icon-plus></b-icon-plus> Search friends</b-btn>
        </div>

        <div class="d-flex justify-content-center mt-3">
          <b-button type="submit" variant="primary" ><b-icon-check /> Add to friends</b-button>
        </div>
      </b-form>
    </b-modal>

    <b-modal ref="friendAddByIDModal" hide-footer title="Adding new friend by ID">
      <b-form @submit="addFriend(addFriendByID)">
        <b-form-group
            id="input-group-1"
            label="Put ID of other user:"
            label-for="input-1"
            description="you can find it in your profile"
        >
          <b-form-input
              id="input-1"
              v-model="addFriendByID"
              type="text"
              placeholder="Enter user ID"
              required
          ></b-form-input>
        </b-form-group>
        <div class="d-flex justify-content-center mt-3">
          <b-button type="submit" variant="primary" ><b-icon-check /> Add to friends</b-button>
        </div>
      </b-form>
    </b-modal>
    <search-users
        ref="searchFriendsPanel"
        title="Find friends"
        :exclude="friends"
        v-on:selected="onSearchCompleted"
    />
  </div>
</template>
<style scoped>

</style>
<script>
import SearchUsers from "@/components/search_users";
export default {
  name: "friends",
  components: {SearchUsers},
  data() {
    return {
      addFriendByID: 0,
      friends: [],
      messages: [],
      edit: {
        friend: {
          id: 0,
          message: ''
        },
      },
      email: ''
    }
  },
  methods: {
    openFriend(friend) {
      this.edit.friend = {...friend};
      fetch(`http://localhost:8081/getAllMessagesBetweenIDs/${this.$root.user.id}/${friend.id}`)
          .then( response =>  response.json())
          .then( messages => {
            this.messages = messages;
            this.$refs.friendModal.show();
            this.$root.$once('bv::modal::shown', () => {
              this.$refs.chatbox.scrollTop = this.$refs.chatbox.scrollHeight;
            })
          })
          .catch(exception => console.error(exception));
    },
    closeFriend() {
      this.$refs.friendModal.hide();
    },
    deleteFriend(friend) {
      fetch(`http://localhost:8081/deleteFriendshipBetweenIDs/${this.$root.user.id}/${friend.id}`, {
        method: 'DELETE',
      }).then(() => {
        this.friends.splice(this.friends.findIndex(f => f.id === friend.id), 1);
        this.closeFriend();
      })
    },
    openAddOptionFriend() {
      this.$refs.friendAddOptionModal.show();
    },
    closeAddOptionFriend() {
      this.$refs.friendAddOptionModal.hide();
    },
    openAddFriendByID() {
      this.$refs.friendAddByIDModal.show();
    },
    closeAddFriendByID() {
      this.$refs.friendAddByIDModal.hide();
    },
    openAddFriendBySearch() {
      this.$refs.searchFriendsPanel.open();
    },
    closeAddFriendBySearch() {
      this.$refs.friendAddBySearchModal.hide();
    },
    sendMessage() {

      fetch(`http://localhost:8081/sendMessageBetweenIDs/${this.$root.user.id}/${this.edit.friend.id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({msg: this.edit.friend.message})
      })
        .then(r => r.json())
        .then(msg => {
          this.messages.push([msg.personFrom.name, msg.msg, msg.date]);
          this.edit.friend.message = '';
        })
        .catch(e => console.error(e))
    },

    addFriend(addFriendByID){
      //alert(addFriendByID);

      fetch(`http://localhost:8081/addFriendshipBetweenIDs/${this.$root.user.id}/${addFriendByID}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        //body: JSON.stringify({msg: this.edit.friend.message})
      }).then( friendship => {
        if(friendship){



          fetch(`http://localhost:8081/findFriendsForID/${this.$root.user.id}`)
              .then( response =>  response.json())
              .then( friends => {
                this.friends = friends;
              })
              .catch(exception => console.error(exception));


          this.closeAddFriendByID();
          this.closeAddOptionFriend();
          //alert('Friend added')

        }
        else{
          alert('User is not existing')
        }

      });

    },
    onSearchCompleted(user) {
      this.addFriend(user.id);
    }
  },
  mounted(){
    fetch(`http://localhost:8081/findFriendsForID/${this.$root.user.id}`)
        .then( response =>  response.json())
        .then( friends => {
          this.friends = friends;
        })
        .catch(exception => console.error(exception));

  }
}
</script>

<style scoped>

</style>
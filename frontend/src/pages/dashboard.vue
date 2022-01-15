<template>
  <div style="height: 100%; width: 100%;">
    <b-button variant="primary" style="position: absolute; bottom: 0; right: 0;" @click="addRandomTimeData">Add time data</b-button>
    <b-alert v-if="!!timeTracker.element" show class="text-left" >
      <div class="row align-items-center">
        <div class="col" style="text-align: left;">
          <h4 class="alert-heading">
            Currently working on
          </h4>
          "{{ timeTracker.element.name }}"
        </div>
        <div class="col-auto">
          <div class="h2 mb-0">
            {{timer.hours}}:{{timer.minutes}}:{{timer.seconds}}
          </div>
        </div>
        <div class="col-auto">
          <b-button variant="danger" @click="stopTimeTracker()">
            <b-icon-stop-circle></b-icon-stop-circle>
          </b-button>
        </div>
      </div>
    </b-alert>
    <div v-else  style="width: 100%; height: 100px;"></div>
    <div id="boards" class="mt-5 py-3">
      <div class="board d-flex justify-content-center align-items-center">
        <b-btn type="primary" @click="openBoard(template.board)"><b-icon-plus></b-icon-plus> Add board</b-btn>
      </div>
      <b-card v-for="board in boards" :key="`${board.id}-${board.boardName}`" class="board mx-2">
        <b-card-title>
          <b-link @click="openBoard(board)">{{board.boardName || 'Untitled'}}</b-link>
        </b-card-title>
        <b-list-group>
          <b-list-group-item
              v-for="element in board.elementModelList"
              :key="`element-${element.id}`"
              href="#"
              @click="openElement(element, board)"
              class="my-2"
          >
            <div class="text-start">
              {{element.name}}
              <hr class="mb-0 mt-1"/>
              <small :class="{
               'text-danger': element.elementStatus === 'FAILED',
              'text-warning': element.elementStatus === 'IN_PROGRESS',
              'text-muted': element.elementStatus === 'CLOSED',
              'text-success': element.elementStatus === 'NEW',
              }">{{element.elementStatus}}</small>
              <hr class="mt-0 mb-1"/>
              <small class="text-muted">{{element.description}}</small>
              <hr class="mt-0 mb-1"/>
              <small class="text-muted" v-if="element.userChangeModel">
                Last edit:
                <span>{{element.userChangeModel ? element.userChangeModel.name : ''}}</span>
              </small>
            </div>
          </b-list-group-item>
          <b-list-group-item @click="addElement(board)" href="#">
            <b-icon-plus></b-icon-plus> Add element
          </b-list-group-item>
        </b-list-group>
      </b-card>
    </div>
    <b-modal ref="elementModal" hide-footer :title="edit.element.name">
      <b-form @submit="saveElement(edit.element)">
        <b-form-group
            label="Element name:"
            label-for="element-name"
        >
          <b-form-input
              id="element-name"
              v-model="edit.element.name"
              type="text"
              placeholder="Enter element name"
              maxlength="30"
              required
          ></b-form-input>
        </b-form-group>
        <b-form-group
            label="Element description:"
            label-for="element-description"
        >
          <b-form-textarea
              id="element-description"
              v-model="edit.element.description"
              placeholder="Enter element description"
              required
          ></b-form-textarea>
        </b-form-group>
        <div class="row">
          <div class="col">
            <b-form-group
                label="Element status:"
                label-for="element-status"
            >
              <b-form-select
                  id="element-status"
                  v-model="edit.element.elementStatus"
                  :value="edit.element.elementStatus"
                  required
              >
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="CLOSED">CLOSED</option>
                <option value="FAILED">FAILED</option>
              </b-form-select>
            </b-form-group>
          </div>
          <div v-if="edit.element.id" class="col-auto">
            <b-button v-if="!timeTracker.element || edit.element.id !== timeTracker.element.id" variant="info" class="mt-3" @click="startTimeTracker(edit.element, edit.element.board)">Track time</b-button>
            <b-button v-else variant="danger" class="mt-3" @click="stopTimeTracker()">Stop track time</b-button>
          </div>
        </div>
        <div v-if="edit.element.id" class="d-flex justify-content-center align-items-center">
          <div>
            <b-tabs content-class="mt-3">
              <b-tab title="Comments" active>
                <b-list-group ref="chatbox" style="max-height: 50vh; overflow-y: auto;">
                  <b-list-group-item v-for="(elementMessage, idx) in edit.element.elementMessageModel" :key="idx">
                    <div class="d-flex align-items-center justify-content-between" style="overflow: hidden;">
                      <div>
                        <small class="text-muted" style="overflow: hidden; word-break: break-all; text-overflow: ellipsis;">Name: {{elementMessage.userMessageModel}}</small><br/>
                        <small class="text-muted">Date: {{elementMessage.date}}</small>
                      </div>
                      <b-button v-if="$root.user.name === elementMessage.userMessageModel" variant="danger" @click="deleteMessage(elementMessage)"> <b-icon-trash></b-icon-trash></b-button>
                    </div>
                    <div style="overflow: hidden; word-break: break-all; text-overflow: ellipsis;">{{elementMessage.msg}}</div>
                  </b-list-group-item>
                </b-list-group>
                <b-form @submit="sendMessage(edit.element.id)">
                  <div class="row">
                    <div class="col-10">
                      <b-form-group>
                        <b-form-input
                            v-model="edit.message"
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
                </b-form>
              </b-tab>
              <b-tab title="History">
                <b-list-group ref="chatbox" style="max-height: 50vh; overflow-y: auto;">
                  <b-list-group-item v-for="(elementHistory, idx) in edit.element.elementHistoryModel" :key="idx">
                    <div class="d-flex align-items-center justify-content-between" style="overflow: hidden;">
                      <small class="text-muted" style="overflow: hidden; word-break: break-all; text-overflow: ellipsis;">Name: {{elementHistory.name}}</small>
                      <small class="text-muted">Element status: {{elementHistory.elementStatus}}</small>
                    </div>
                    <div class="text-muted" style="overflow: hidden; word-break: break-all; text-overflow: ellipsis;">Description: {{elementHistory.description}}</div>
                    <div class="text-muted">Change time: {{elementHistory.addChangeTime}}</div>
                    <div class="text-muted">Change user: {{elementHistory.userChangeModel}}</div>
                  </b-list-group-item>
                </b-list-group>
              </b-tab>
              <b-tab title="Time tracking">
                <div>Total group work time: {{ (Math.round(100 * edit.element.activity.reduce((c, a) => a.time + c, 0) / 3600) / 100).toFixed(2) }} hours</div>
                <div>Your time: {{ (Math.round(100 * edit.element.activity.filter(a => a.user.id === $root.user.id).reduce((c, a) => a.time + c, 0) / 3600) / 100).toFixed(2) }} hours</div>
                <b-list-group ref="chatbox" style="max-height: 50vh; overflow-y: auto;">
                  <b-list-group-item v-for="activity in edit.element.activity" :key="activity.id">
                    <div class="small text-muted" style="overflow: hidden; word-break: break-all; text-overflow: ellipsis;">Name: {{activity.user.name}}</div>
                    <div class="small text-muted">Started at: {{(new Date(activity.startedAt)).toISOString()}}</div>
                    <div class="small text-muted">Time: {{(Math.round(100 * activity.time / 60) / 100).toFixed(2)}} minutes</div>
                  </b-list-group-item>
                </b-list-group>
              </b-tab>
            </b-tabs>
          </div>
        </div>

        <div class="d-flex justify-content-center mt-3">
          <b-button v-if="edit.element.id" variant="danger" style="margin-right: 1em;" @click="deleteElement(edit.element)">
            <b-icon-trash /> Delete
          </b-button>
          <b-button type="submit" variant="primary" ><b-icon-check /> Save</b-button>
        </div>
      </b-form>
    </b-modal>
    <b-modal ref="boardModal" hide-footer :title="edit.board.boardName">
        <b-form @submit="saveBoard(edit.board)">
          <b-form-group
              label="Board name:"
              label-for="board-name"
          >
            <b-form-input
                id="board-name"
                v-model="edit.board.boardName"
                type="text"
                placeholder="Enter board name"
                required
            ></b-form-input>
          </b-form-group>

          <div>
          <b-form-group
              label="People assigned to board"
              label-for="board-people"
          >
            <div>
                <b-list-group>
                  <b-list-group-item class="d-flex align-items-center justify-content-between" v-for="user in boardUsers" :key="user.id">
                    <b-avatar variant="primary" class="mr-3" :text="(user.name || '').charAt(0).toUpperCase()"></b-avatar>
                    <span>{{user.name}}</span>
                    <b-button variant="danger" :disabled="$root.user.id === user.id" @click="deleteUserFromBoard(edit.board.id, user.id)" ><b-icon-trash /></b-button>
                  </b-list-group-item>
                </b-list-group>
            </div>
            <div class="d-flex justify-content-center mt-3">
              <b-button  variant="primary" @click="openAddOptionFriend()" ><b-icon-plus /> Add more people</b-button>
            </div>
          </b-form-group>
          </div>


          <div class="d-flex justify-content-center mt-3">
            <b-button v-if="edit.board.id" variant="danger" style="margin-right: 1em;" @click="deleteBoard(edit.board)">
              <b-icon-trash /> Delete
            </b-button>
            <b-button type="submit" variant="primary"><b-icon-check /> Save</b-button>
          </div>
        </b-form>
    </b-modal>

    <b-modal ref="friendAddOptionModal" hide-footer title="Choose option to add a friend">
      <div class="d-flex justify-content-evenly align-items-center">
        <b-btn type="primary" @click="openAddUserToBoardByID()"><b-icon-plus></b-icon-plus> Add friend by ID</b-btn>
        <b-btn type="primary" @click="openAddUserToBoardBySearch()"><b-icon-plus></b-icon-plus> Search friends</b-btn>
      </div>
    </b-modal>

    <b-modal ref="userAddToBoardByIDModal" hide-footer title="Adding new user by ID to the board">
      <b-form @submit="addUserToABoard(addThisUserIDToBoard, edit.board.id)">
        <b-form-group
            id="input-group-1"
            label="Put ID of other user:"
            label-for="input-1"
            description="Ask your friend for his ID!"
        >
          <b-form-input
              id="input-1"
              v-model="addThisUserIDToBoard"
              type="text"
              placeholder="Enter user ID"
              required
          ></b-form-input>
        </b-form-group>
        <div class="d-flex justify-content-center mt-3">
          <b-button type="submit" variant="primary" ><b-icon-check /> Add this user to the board</b-button>
        </div>
      </b-form>
    </b-modal>

    <search-users
        ref="userAddToBoardBySearchModal"
        title="Find friends"
        :exclude="boardUsers"
        v-on:selected="onSearchCompleted"
    />

  </div>
</template>
<style scoped>
  #boards {
    width: 100%;
    max-width: 100%;
    height: calc(100% - 100px);
    display: flex;
    flex-wrap: nowrap;
    flex-direction: row;
    overflow-x: auto;
    overflow-y: auto;
  }
  .board {
    min-width: 25%;
    width: 25%;
    max-height: 100%;
    overflow-y: auto;
  }
</style>
<script>
import SearchUsers from '@/components/search_users'
export default {
  name: "dashboard",
  components: {SearchUsers},
  computed: {
    timer() {
      if (!this.timeTracker.element) {
        return {
          hours: '00',
          minutes: '00',
          seconds: '00'
        }
      }
      return {
        hours: (Math.floor(this.timeTracker.timeElapsed / 3600) % 60).toString().padStart(2, '0'),
        minutes: (Math.floor(this.timeTracker.timeElapsed / 60) % 60).toString().padStart(2, '0'),
        seconds: (this.timeTracker.timeElapsed % 60).toString().padStart(2, '0'),
      }
    }
  },
  data() {
    return {
      addThisUserIDToBoard: 0,
      boardUsers: [],
      boards: [],
      selectedBoard: {},
      template: {
        element: {
          id: 0,
          name: '',
          status: 'NEW',
          description: '',
          board: 0,
          userChangeModel: {},
          elementHistoryModel: [],
          elementMessageModel: [],
          activity: []
        },
        board: {
          id: 0,
          boardName: '',
          elementModelList: []
        }
      },
      edit: {
        board: {},
        element: {},
        message: ''
      },
      timeTracker: {
        board: null,
        element: null,
        startedAt: null,
        eventLoop: null,
        timeElapsed: 0
      }
    }
  },
  beforeDestroy() {
    if (this.timeTracker.eventLoop) {
      clearInterval(this.timeTracker.eventLoop);
      this.timeTracker.eventLoop = null;
    }
  },
  methods: {
    async addRandomTimeData() {
        let random = (min, max) => { // min and max included
          return Math.floor(Math.random() * (max - min + 1) + min)
        };

        let dates = [];
        let maxTimePerDay = 3600 * 7;
        for (let i = 0; i < 5; i++) {
          dates.push({
            startedAt: new Date(2022, 0, 10 + i, 8, 0, 0),
            totalTime: 0
          })
        }
        for (let j = 0; j < dates.length; j++) {
          let date = dates[j];
          while (date.totalTime < maxTimePerDay) {
            let board = this.boards[Math.floor(Math.random() * this.boards.length)];
            if (board) {
              let element = board.elementModelList[Math.floor(Math.random() * board.elementModelList.length)];
              if (element) {
                let time = random(600, maxTimePerDay / 2);
                console.log({time: time, startedAt: date.startedAt.toISOString()});
                let resp = await fetch(`http://localhost:8081/trackElementTime/${element.id}/${this.$root.user.id}`, {
                  method: 'POST',
                  headers: {
                    'Content-Type': 'application/json'
                  },
                  body: JSON.stringify({time: time, startedAt: date.startedAt.toISOString()})
                });

                let json = await resp.json();
                element.activity.push(json);
                date.startedAt.setTime(date.startedAt.getTime() + (time * 1000));
                date.totalTime += time;
              }
            }
          }

        }
      console.log(dates);

    },
    startTimeTracker(element, boardId, date) {
      let callback = () => {
        this.timeTracker.board = boardId;
        this.timeTracker.element = element;
        this.timeTracker.startedAt = date || new Date();
        localStorage.setItem('time_tracker', JSON.stringify({
          element: element.id,
          board: boardId,
          startedAt: this.timeTracker.startedAt.toISOString()
        }))

        this.timeTracker.eventLoop = setInterval(() => {
          this.timeTracker.timeElapsed = Math.round(((new Date()).getTime() - this.timeTracker.startedAt.getTime()) / 1000)
        }, 1000)
      };

      if (this.timeTracker.element) {
        this.stopTimeTracker(callback);
      } else {
        callback();
      }
    },
    resetTimeTracker() {
      this.timeTracker.element = null;
      this.timeTracker.startedAt = null;
      this.timeTracker.board = null;
      this.timeTracker.timeElapsed = 0;
      if (this.timeTracker.eventLoop) {
        clearInterval(this.timeTracker.eventLoop);
        this.timeTracker.eventLoop = null;
      }
      localStorage.removeItem('time_tracker');
    },
    stopTimeTracker(cb) {
      let elId = this.timeTracker.element.id;
      let boardId = this.timeTracker.board;
      let time = this.timeTracker.timeElapsed;
      let startedAt = this.timeTracker.startedAt;

      fetch(`http://localhost:8081/trackElementTime/${elId}/${this.$root.user.id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({time, startedAt})
      })
          .then(r => r.json())
          .then(resp => {
            let board = this.boards.find(b => b.id === boardId);
            if (board) {
              let element = board.elementModelList.find(e => e.id === elId);
              if (element) {
                element.activity.push(resp);
              }
            }
            this.resetTimeTracker();
            cb && cb();
          })
          .catch(() => {
            this.resetTimeTracker();
            cb && cb();
          })
    },
    sendMessage(elementId) {

      fetch(`http://localhost:8081/sendMessageUnderElement/${elementId}/${this.$root.user.id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({msg: this.edit.message})
      })
          .then(r => r.json())
          .then(msg => {
            this.edit.element.elementMessageModel.push(msg);
            this.edit.message = '';
          })
          .catch(e => console.error(e))
    },
    deleteMessage(message) {
      fetch(`http://localhost:8081/deleteElementMessageByID/${message.id}`, {
        method: 'DELETE',
      }).then(() => {
        this.edit.element.elementMessageModel.splice(this.edit.element.elementMessageModel.findIndex((m) => m.id === message.id), 1);
      })
    },
    openBoard(board) {
      this.edit.board = {...board};

     this.findUsersOfABoard(board.id);

      this.$refs.boardModal.show();
    },
    deleteElement(element) {
      let board = this.boards.find(b => element.board === b.id);
      fetch(`http://localhost:8081/deleteElement/${element.id}`, {
        method: 'DELETE',
      }).then(() => {
        if (element.id === this.timeTracker.element.id) {
          this.resetTimeTracker();
        }
        board.elementModelList.splice(board.elementModelList.findIndex((e) => e.id === element.id), 1);
        this.closeElement();
      })
    },
    openElement(element, board) {
      this.edit.element = {...element, board: board.id};
      this.$refs.elementModal.show();
    },
    closeElement() {
      this.$refs.elementModal.hide();
    },
    closeBoard() {
      this.$refs.boardModal.hide();
    },
    openAddUserToBoardBySearch() {
      this.$refs.userAddToBoardBySearchModal.open();
    },
    openAddUserToBoardByID() {
      this.$refs.userAddToBoardByIDModal.show();
    },
    closeAddUserToBoardByID() {
      this.$refs.userAddToBoardByIDModal.hide();
    },
    openAddOptionFriend() {
      this.$refs.friendAddOptionModal.show();
    },
    closeAddOptionFriend() {
      this.$refs.friendAddOptionModal.hide();
    },
    deleteUserFromBoard(boardID, userID) {

      if (!confirm('Are you sure you want to delete this user?')) {
        return;
      }

      if(userID == this.$root.user.id){
        alert('Can\'t delete yourself!');
        return;
      }
      // delete user

      fetch(`http://localhost:8081/deleteUserFromBoard/${boardID}/${userID}`, {
        method: 'DELETE',
      })
          .then(r => r.text())
          .then((response) => {
            if (response && response.length > 0) {
              try {
                response = JSON.parse(response)
                if (response.error) {
                  alert('Can\'t delete user!');
                  return;
                }
              } catch (e) {console.error(e)}
        }
        this.boardUsers.splice(this.boardUsers.findIndex(bu => bu.id === userID), 1);
      })
    },
    addElement(board) {
      this.openElement(this.template.element, board)
    },
    saveElement(element) {
      let board = this.boards.find(b => element.board === b.id);
      fetch(element.id ? `http://localhost:8081/updateElement/${element.id}/${this.$root.user.id}` : `http://localhost:8081/createElement/${board.id}/${this.$root.user.id}`, {
        method: element.id ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          description: element.description,
          name: element.name,
          elementStatus: element.elementStatus
        })
      })
          .then( response =>  response.json())
          .then( elementJson => {
            if (!element.id) {
              board.elementModelList.push(elementJson);
            } else {
              let idx = board.elementModelList.findIndex(e => e.id === element.id);
              this.$set(board.elementModelList, idx, elementJson);
              if (this.timeTracker.element.id === elementJson.id) {
                this.timeTracker.element = elementJson;
              }
            }
            this.closeElement();
          })
    },
    deleteBoard(board) {
      fetch(`http://localhost:8081/deleteBoard/${board.id}`, {
        method: 'DELETE',
      }).then(() => {
        if (this.timeTracker.board === board.id) {
          this.resetTimeTracker();
        }
        this.boards.splice(this.boards.findIndex((b) => b.id === board.id), 1);
        this.closeBoard();
      })

    },
    saveBoard(board) {
      fetch(board.id ? `http://localhost:8081/updateBoard/${board.id}` : `http://localhost:8081/createBoardWithUserID/${this.$root.user.id}`, {
        method: board.id ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({boardName: board.boardName})
      })
          .then( response =>  response.json())
          .then( boardJson => {
            if (!board.id) {
              this.boards.push(boardJson);
            } else {
              this.$set(this.boards, this.boards.findIndex(b => board.id === b.id), boardJson);
            }
            this.closeBoard();
          })
    },
    addUserToABoard(userID, boardID) {
      //alert(userID+ " -- " +boardID)
      fetch(`http://localhost:8081/addUserToABoard/${userID}/${boardID}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        //body: JSON.stringify({boardName: board.boardName})
      })
          .then( response =>  response.json())
          .then( userJson => {
            // if (!board.id) {
            //   this.boards.push(boardJson);
            // } else {
            //   this.$set(this.boards, this.boards.findIndex(b => board.id === b.id), boardJson);
            // }
            this.boardUsers.push(userJson);

            this.closeAddUserToBoardByID();
            this.closeAddOptionFriend();
          })
    },
    findUsersOfABoard(boardID) {
      //alert(" -- " +boardID)
      fetch(`http://localhost:8081/findUsersOfABoard/${boardID}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
        //body: JSON.stringify({boardName: board.boardName})
      })
          .then( response =>  response.json())
          .then( usersJson => {
            // if (!board.id) {
            //   this.boards.push(boardJson);
            // } else {
            //   this.$set(this.boards, this.boards.findIndex(b => board.id === b.id), boardJson);
            // }
            this.boardUsers = usersJson;
          })
    },
    onSearchCompleted(user) {
      this.addUserToABoard(user.id, this.edit.board.id);
    }
    // findUserById(userID) {
    //
    //   fetch(`http://localhost:8081/findUserByID/${userID}`)
    //       .then( response =>  response.json())
    //       .then( userFromServerJson => {
    //         this.form=userFromServerJson;
    //       }).catch(exception => console.error(exception));
    //
    // }
  },
  mounted(){
    fetch(`http://localhost:8081/findBoardsByUserID/${this.$root.user.id}`)
        .then( response =>  response.json())
        .then( boardsFromServerJson => {
          this.boards = boardsFromServerJson;
          try {
            let timeTracker = JSON.parse(localStorage.getItem('time_tracker'));
            if (timeTracker) {
              let boardIdx = this.boards.findIndex(b => timeTracker.board === b.id);
              if (boardIdx > -1) {
                let elementIdx = this.boards[boardIdx].elementModelList.findIndex(e => timeTracker.element === e.id);
                if (elementIdx > -1) {
                  this.startTimeTracker(
                      this.boards[boardIdx].elementModelList[elementIdx],
                      this.boards[boardIdx].id,
                      new Date(timeTracker.startedAt)
                  );
                }
              }
            }
          } catch (e) {
            //localStorage.removeItem('time_tracker');
          }
        })
        .catch(exception => console.error(exception));
  }
}
</script>

<style scoped>

</style>
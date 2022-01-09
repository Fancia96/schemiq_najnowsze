<template>
  <div>
    <div id="boards" class="my-5 py-3">
      <div class="board d-flex justify-content-center align-items-center">
        <b-btn type="primary" @click="openBoard(template.board)"><b-icon-plus></b-icon-plus> Add board</b-btn>
      </div>
      <b-card v-for="board in boards" :key="`${board.id}-${board.boardName}`" class="board">
        <b-card-title>
          <b-link @click="openBoard(board)">{{board.boardName || 'Untitled'}}</b-link>
        </b-card-title>
        <b-list-group>
          <b-list-group-item
              v-for="element in board.elementModelList"
              :key="`element-${element.id}`"
              href="#"
              @click="openElement(element, board)"
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

<!--          <b-list-group>-->
<!--            <b-list-group-item-->
<!--                v-for="friend in board.elementModelList"-->
<!--                :key="`friend-${element.id}`"-->
<!--                href="#"-->
<!--                @click="openElement(element, board)"-->
<!--            >-->
<!--              <div class="text-start">-->
<!--                {{element.name}}-->
<!--                <hr class="mb-0 mt-1"/>-->
<!--                <small :class="{-->
<!--               'text-danger': element.elementStatus === 'FAILED',-->
<!--              'text-warning': element.elementStatus === 'IN_PROGRESS',-->
<!--              'text-muted': element.elementStatus === 'CLOSED',-->
<!--              'text-success': element.elementStatus === 'NEW',-->
<!--              }">{{element.elementStatus}}</small>-->
<!--                <hr class="mt-0 mb-1"/>-->
<!--                <small class="text-muted">{{element.description}}</small>-->
<!--              </div>-->
<!--            </b-list-group-item>-->
<!--&lt;!&ndash;            <b-list-group-item @click="addElement(board)" href="#">&ndash;&gt;-->
<!--&lt;!&ndash;              <b-icon-plus></b-icon-plus> Add element&ndash;&gt;-->
<!--&lt;!&ndash;            </b-list-group-item>&ndash;&gt;-->
<!--          </b-list-group>-->


          <div class="d-flex justify-content-center mt-3">
            <b-button v-if="edit.board.id" variant="danger" style="margin-right: 1em;" @click="deleteBoard(edit.board)">
              <b-icon-trash /> Delete
            </b-button>
            <b-button type="submit" variant="primary"><b-icon-check /> Save</b-button>
          </div>
        </b-form>
    </b-modal>
  </div>
</template>
<style scoped>
  #boards {
    width: 100%;
    overflow-x: scroll;
    display: flex;
    flex-wrap: nowrap;
  }
  .board {
    width: 25%;
  }
</style>
<script>
export default {
  name: "dashboard",
  data() {
    return {
      boards: [],
      selectedBoard: {},
      template: {
        element: {
          id: 0,
          name: '',
          status: 'NEW',
          description: '',
          board: 0
        },
        board: {
          id: 0,
          boardName: '',
          elementModelList: []
        }
      },
      edit: {
        board: {},
        element: {}
      }
    }
  },
  methods: {
    openBoard(board) {
      this.edit.board = {...board};
      this.$refs.boardModal.show();
    },
    deleteElement(element) {
      let board = this.boards.find(b => element.board === b.id);
      fetch(`http://localhost:8081/deleteElement/${element.id}`, {
        method: 'DELETE',
      }).then(() => {
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
    addElement(board) {
      this.openElement(this.template.element, board)
    },
    saveElement(element) {
      let board = this.boards.find(b => element.board === b.id);
      fetch(element.id ? `http://localhost:8081/updateElement/${element.id}` : `http://localhost:8081/createElement/${board.id}`, {
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
            }
            this.closeElement();
          })
    },
    deleteBoard(board) {
      fetch(`http://localhost:8081/deleteBoard/${board.id}`, {
        method: 'DELETE',
      }).then(() => {
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
    }
  },
  mounted(){
    fetch(`http://localhost:8081/findBoardsByUserID/${this.$root.user.id}`)
        .then( response =>  response.json())
        .then( boardsFromServerJson => {
          this.boards = boardsFromServerJson;
          console.log(boardsFromServerJson)
        })
        .catch(exception => console.error(exception));
  }
}
</script>

<style scoped>

</style>
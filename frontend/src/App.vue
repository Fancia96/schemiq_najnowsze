<template>
  <div id="app">

    <div id="header"  >
<!--    <b-navbar toggleable="lg" type="dark" >-->
<!--      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>-->

<!--      <b-collapse id="nav-collapse" is-nav >-->
<!--&lt;!&ndash;        <b-navbar-nav>&ndash;&gt;-->
<!--&lt;!&ndash;          <b-nav-item href="#">Link</b-nav-item>&ndash;&gt;-->
<!--&lt;!&ndash;          <b-nav-item href="#" disabled>Disabled</b-nav-item>&ndash;&gt;-->
<!--&lt;!&ndash;        </b-navbar-nav>&ndash;&gt;-->

<!--        &lt;!&ndash; Right aligned nav items &ndash;&gt;-->
<!--        <b-navbar-nav class="ml-auto">-->
<!--&lt;!&ndash;          <b-nav-form>&ndash;&gt;-->
<!--&lt;!&ndash;            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>&ndash;&gt;-->
<!--&lt;!&ndash;            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>&ndash;&gt;-->
<!--&lt;!&ndash;          </b-nav-form>&ndash;&gt;-->

<!--          <b-nav-item-dropdown text="Language" right>-->
<!--            <b-dropdown-item href="#">EN</b-dropdown-item>-->
<!--            <b-dropdown-item href="#">PL</b-dropdown-item>-->
<!--          </b-nav-item-dropdown>-->

<!--          <b-nav-item-dropdown right>-->
<!--            &lt;!&ndash; Using 'button-content' slot &ndash;&gt;-->
<!--            <template #button-content>-->
<!--              <em>User : {{user.name}}</em>-->
<!--            </template>-->
<!--            <b-dropdown-item href="#">Profile</b-dropdown-item>-->
<!--            <b-dropdown-item v-if="user" href="#">Log out</b-dropdown-item>-->
<!--            <b-dropdown-item v-else href="#">Log in</b-dropdown-item>-->
<!--          </b-nav-item-dropdown>-->



<!--        </b-navbar-nav>-->
<!--      </b-collapse>-->
<!--    </b-navbar>-->

      <img id="schemiq_logo" alt="Schemiq logo" src="./assets/schemiq.png" style="float: right;">
      </div>



<!--      <b-navbar-brand href="#">-->

<!--      </b-navbar-brand>-->


<!--    <b-table :items= "elements" :fields="['board_name']">-->
<!--      <template #table-caption>This is a table caption.</template>-->
<!--    </b-table>-->

    <!--<b-table :fields="['board_name']" :items="boards"></b-table>
    -->

    <div id="container">



    <div id="left_panel" >
      <b-container fluid>
        <b-row >
          <b-col md="4" class="py-4">
            <b-button id="button-1" variant="outline-success">Live chat</b-button>
          </b-col>
        </b-row>
        <b-row>
          <b-col md="4" class="py-4">
            <b-button id="button-2" variant="outline-success">Html chat</b-button>
          </b-col>
        </b-row>
        <b-row>
          <b-col md="4" class="py-4">
            <b-button ref="button-3" variant="outline-success">Alternative chat</b-button>
          </b-col>
        </b-row>

        <!-- Tooltip title specified via prop title -->
        <b-tooltip target="button-1" title="Online!"></b-tooltip>

        <!-- HTML title specified via default slot -->
        <b-tooltip target="button-2" placement="bottom">
          Hello <strong>World!</strong>
        </b-tooltip>

        <!-- Tooltip for an element identified by ref -->
        <b-tooltip :target="() => $refs['button-3']" title="Alternative!"></b-tooltip>
      </b-container>
    </div>

      <div id="tabs">

        <b-tabs content-class="mt-3" id="tab_class">
          <b-tab title="Boards" active>

            <div id="boards">

              <p>I'm the first tab here</p>

<!--              name="LeaveType" @change="onChange($event)" class="form-control" v-model="key">-->


             <b-form-select v-model="selected" class="mb-3" @change="getElements(selected)" >
                <b-form-select-option v-for="board in boards" :key="board.id" :value="board.id" >
                   {{board.boardName}}
                </b-form-select-option>

              </b-form-select>


<!--              <select class="form-control" @click="getElements(board.id)">-->
<!--                <option value="" selected disabled> Choose</option>-->
<!--                <option v-for="board in boards" :value="board.name" :key="board.id">{{board.boardName }}</option>-->
<!--              </select>-->


<!--              <select @click="changeItem(board.id)">-->
<!--                <option v-for="board in boards" :key="board.id">{{board.boardName}}</option>-->
<!--              </select>-->




<!--              <ol>-->
<!--                <li v-for="board in boards" :key="board.id">-->
<!--                  &lt;!&ndash;        v-on:click&ndash;&gt;-->
<!--                  <a href="#" @click="getElements(board.id)"> {{board.boardName}} </a>-->
<!--                </li>-->
<!--              </ol>-->


              <b-table :fields="['name', 'description', 'element_status']" :items="elements"></b-table>
            </div>

          </b-tab>
          <b-tab title="Friends">
            <div id="friends">
              <p>I'm the second tab</p>

              <p>Using stand-alone:<p/>
              <div class="mb-4">
                <b-avatar></b-avatar>
                <b-avatar variant="primary" text="BV"></b-avatar>
                <b-avatar variant="info" src="https://placekitten.com/300/300"></b-avatar>
                <b-avatar variant="success" icon="people-fill"></b-avatar>
              </div>
              <p>Using in components (list group) example:<p/>
              <b-list-group style="max-width: 300px;">
                <b-list-group-item class="d-flex align-items-center">
                  <b-avatar class="mr-3"></b-avatar>
                  <span class="mr-auto">J. Circlehead</span>
                  <b-badge>5</b-badge>
                </b-list-group-item>
                <b-list-group-item class="d-flex align-items-center">
                  <b-avatar variant="primary" text="BV" class="mr-3"></b-avatar>
                  <span class="mr-auto">BootstrapVue</span>
                  <b-badge>12</b-badge>
                </b-list-group-item>
                <b-list-group-item class="d-flex align-items-center">
                  <b-avatar variant="info" src="https://placekitten.com/300/300" class="mr-3"></b-avatar>
                  <span class="mr-auto">Super Kitty</span>
                  <b-badge>9</b-badge>
                </b-list-group-item>
                <b-list-group-item class="d-flex align-items-center">
                  <b-avatar variant="success" icon="people-fill" class="mr-3"></b-avatar>
                  <span class="mr-auto">ACME group</span>
                  <b-badge>7</b-badge>
                </b-list-group-item>
              </b-list-group>

            </div>

          </b-tab>
          <b-tab title="Disabled" disabled><p>I'm a disabled tab!</p></b-tab>
        </b-tabs>
      </div>

    </div>

    <div id="footer">
<!--    <button>USTAWIENIA</button>-->
    <b-link href="#"><b-img id="schemiq_one" src="./assets/settings.svg" fluid alt="settings" style="float: right"></b-img></b-link>

<!--    <div v-if="boards.length"> blabla </div><div v-else> log in </div>-->
    </div>

  </div>
</template>

<script>

export default {
  name: 'App',



  data() {
    return {
      // mainProps: {
      //   center: true,
      //   fluidGrow: true,
      //   blank: true,
      //   blankColor: '#bbb',
      //   width: 600,
      //   height: 400,
      //   class: 'my-5'
      // }
      boards: [{id: 1,board_name: "zadamie 1"},{id: 2, board_name: "zadanie 2"}],

      // elements: [{name: "zadamie 1"},{name: "zadanie 2"}],
      elements: [],

      user: {id: 1,name: "wanda"},

      selected: null


    }
  },
  methods: {
      getElements(boardId) {
        fetch(`http://localhost:8081/findBoardElementsByBoardID/${boardId}`)
            .then( response =>  response.json())
            .then( elementsFromServerJson => { this.elements = elementsFromServerJson;}).catch(exception => console.error(exception));
      },

      changeItem(boardId) {
        fetch(`http://localhost:8081/findBoardElementsByBoardID/${boardId}`)
            .then( response =>  response.json())
            .then( elementsFromServerJson => { this.elements = elementsFromServerJson;}).catch(exception => console.error(exception));
      }

   },
  mounted(){
    fetch(`http://localhost:8081/getAllExistingBoards`)
        .then( response =>  response.json())
        .then( boardsFromServerJson => { this.boards = boardsFromServerJson;}).catch(exception => console.error(exception));
  }
}

</script>

<style>
/*#app {*/
/*  font-family: Avenir, Helvetica, Arial, sans-serif;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*  text-align: center;*/
/*  color: #2c3e50;*/
/*  margin-top: 60px;*/
/*  background-color: lightgray;*/
/*}*/


#schemiq_logo, #schemiq_one{
  width: 100%;
  height: auto;
  max-width: 300px;

  position: relative;
  float: top;
}

#schemiq_one{

  max-width: 70px;
}

#container {
  display: inline-flex;
  position: relative;
  top: 100px;
}

#tabs{
  background-color: crimson;


  height: 100vh;
  width: 100vw;

  /*width: 90%;*/

}

#friends,boards{
  height: 100vh;
  width: 100vw;
}

#left_panel{
  visibility: visible;

 /*float: left;*/
 /* position: absolute;*/
  top: 100px;
  left: 0px;

  background-color: rgb(102,102,102);

  /*width: 10%;*/
}

#footer{
  background-color: rgb(160,160,160);

  display: block;
  position:absolute;

  bottom: 0;
  width:100%;


}

#header{
  display: block;
  position:absolute;
  top: 0;
  width:100%;

  height: 100px;

  background-color: rgb(102,102,102);
}

#tab_class{
  background-color: #d488a1;
}

#tabs{
  color: black;
}


#tabs,b-tab{
  background-color: forestgreen;

}

#tabs,b-tabs{
  background-color: #f2cfc3;
}

</style>

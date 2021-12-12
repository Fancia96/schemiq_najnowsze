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
      <b-jumbotron header="BootstrapVue" lead="Bootstrap v4 Components for Vue.js 2">
        <p>For more information visit website</p>
        <b-button variant="primary" href="#">More Info</b-button>
      </b-jumbotron>
    </div>

      <div id="tabs">

        <b-tabs content-class="mt-3" id="tab_class">
          <b-tab title="Boards" active>

            <div id="boards">

              <p>I'm the first tab here</p>

              <ol>
                <li v-for="board in boards" :key="board.id">
                  <!--        v-on:click-->
                  <a href="#" @click="getElements(board.id)"> {{board.boardName}} </a>
                </li>
              </ol>


              <b-table :fields="['name', 'description', 'element_status']" :items="elements"></b-table>
            </div>

          </b-tab>
          <b-tab title="Friends">
            <div id="friends">
              <p>I'm the second tab</p>


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

      user: {id: 1,name: "wanda"}


    }
  },
  methods: {
      getElements(boardId) {
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

  /*position: relative;*/
  top: 100px;
  left: 0px;
  /*width: 90%;*/

}

#left_panel{
 /*float: left;*/
 /* position: absolute;*/
  top: 100px;
  left: 0px;
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
  background-color: aqua;
}



#tabs,b-tab{
  background-color: forestgreen;
}

</style>

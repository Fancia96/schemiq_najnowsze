<template>
  <div id="app">
    <div class="d-flex">
      <div style="min-width: 240px; width: 240px; height: 100vh">
        <div class="bg-light text-dark" style="position: relative; width: 100%; height: 100%;">
          <b-link>
            <b-img src="./assets/schemiq.png"  fluid class="p-3"/>
          </b-link>

          <b-list-group>
            <b-list-group-item :to="'/dashboard'" :disabled="!$root.user"><b-icon-folder></b-icon-folder> Boards</b-list-group-item>
            <b-list-group-item :to="'/friends'" :disabled="!$root.user"><b-icon-diamond></b-icon-diamond> Friends</b-list-group-item>
            <b-list-group-item :to="'/activity'" :disabled="!$root.user" ><b-icon-bar-chart-steps></b-icon-bar-chart-steps> Activity</b-list-group-item>
            <b-list-group-item :to="'/settings'" :disabled="true" ><b-icon-nut></b-icon-nut> Settings</b-list-group-item>
          </b-list-group>
        </div>
      </div>
      <div class="flex-grow-1" style="height:100vh;">
        <b-navbar toggleable="lg" type="light" variant="light" class="py-4">
          <b-container fluid>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav class=" justify-content-between">
              <b-navbar-nav align="left">
                <b-nav-item href="#">Link</b-nav-item>
                <b-nav-item href="#" disabled>Disabled</b-nav-item>
                <b-nav-item-dropdown right>
                  <!-- Using 'button-content' slot -->
                  <template #button-content>
                    <em v-if="$root.user">{{$root.user.name}}</em>
                    <em v-else>User</em>
                  </template>
                  <b-dropdown-item :disabled=!$root.user :to="'/profile_settings'" href="#">Profile</b-dropdown-item>

                  <b-dropdown-item v-if="$root.user" href="#" @click="SignOut">Sign Out</b-dropdown-item>
                  <b-dropdown-item v-else href="#" @click="SignIn">Sign In</b-dropdown-item>
                </b-nav-item-dropdown>
              </b-navbar-nav>
<!--              <b-navbar-nav class="ml-auto" align="right">-->
<!--                <b-nav-form>-->
<!--                  <b-input-group prepend="🔎">-->
<!--                    <b-form-input placeholder="Search"></b-form-input>-->
<!--                  </b-input-group>-->
<!--                </b-nav-form>-->
<!--              </b-navbar-nav>-->
            </b-collapse>
          </b-container>
        </b-navbar>
        <b-container fluid style="height: calc(100vh - 150px)">
          <router-view></router-view>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  methods: {
    goBack() {
      window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/')
    },
    SignOut() {
      this.$root.user = null;
      localStorage.removeItem('user');
      this.$router.replace('/login');
    },
    SignIn() {
      this.$router.replace('/login');
    }
  },
  mounted() {

    if (!this.$root.user) {
      if(this.$router.currentRoute.fullPath !== '/login'){
        this.$router.replace('/login');
      }
      //go to login/register
    } else if (this.$router.currentRoute.fullPath === '/' || this.$router.currentRoute.fullPath === '/login') {

      this.$router.replace('/dashboard');


    }
  }
}

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: lightgray;
}
</style>

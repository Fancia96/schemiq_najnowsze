<template>
  <div>
    <h2>Creating an account</h2>
    <h5>Your personal ID : {{$root.user.id}}</h5>

    <b-form @submit="onSubmit">

      <b-form-group
          id="input-group-1"
          label="Name:"
          label-for="input-1"
      >
        <b-form-input
            id="input-1"
            v-model="form.name"
            type="text"
            placeholder="Enter name for account"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group
          id="input-group-2"
          label="First Name:"
          label-for="input-2"
      >
        <b-form-input
            id="input-2"
            v-model="form.firstName"
            type="text"
            placeholder="Enter first name"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group
          id="input-group-3"
          label="Last Name:"
          label-for="input-3"
      >
        <b-form-input
            id="input-3"
            v-model="form.lastName"
            type="text"
            placeholder="Enter last name"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group
          id="input-group-4"
          label="Email address:"
          label-for="input-4"
          description="We'll never share your email with anyone else."
      >
        <b-form-input
            id="input-4"
            v-model="form.email"
            type="email"
            placeholder="Enter email"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-5" label="Your password:" label-for="input-5" description="Click on password you want to see it.">
        <b-form-input v-on:focus="type='text' " v-on:blur="type='password' "
            id="input-5"
            v-model="form.password"
            placeholder="Enter password"
            :type="type"

            required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Save changes</b-button>

        <b-list-group-item @click="deleteUser()" href="#">
          <b-icon-></b-icon-> Delete account
        </b-list-group-item>

    </b-form>

<!--    <b-button v-b-modal.modal-footer-sm >I want to delete my account</b-button>-->

<!--    <b-modal id="modal-footer-sm" title="BootstrapVue" button-size="sm">-->
<!--      <p class="my-2">Are you sure you want to delete your account?</p>-->
<!--      <b-form-group id="input-group-5" label="Your password:" label-for="input-5">-->
<!--        <b-form-input-->
<!--            id="input-5"-->
<!--            v-model="form.password"-->
<!--            placeholder="Enter password"-->
<!--            type="password"-->
<!--            required-->
<!--        ></b-form-input>-->
<!--      </b-form-group>-->
<!--    </b-modal>-->



  </div>
</template>

<script>
export default {
  name: 'profile_settings',
  data() {
    return {
      form: {
        name: '',
        firstName: '',
        lastName: '',
        email: '',
        password: ''

      },
      type: 'password'
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      //alert(JSON.stringify(this.form))

      fetch(`http://localhost:8081/editUser`, {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.form) // body data type must match "Content-Type" header
      }).then( response =>  response.json())
          .then( user => {
            localStorage.setItem('user', JSON.stringify(user))
            this.$root.user = user;
            this.form = user;
          })
          .catch(exception => console.error(exception));


    },
    deleteUser() {
      fetch(`http://localhost:8081/deleteUserByID/${this.$root.user.id}`, {
        method: 'DELETE'
      }).then(() => {
          localStorage.removeItem('user')
          this.$root.user = null;
          this.$router.replace('/login');

      }).catch(exception => console.error(exception));

    }
  },
  mounted() {
      fetch(`http://localhost:8081/findUserByID/${this.$root.user.id}`)
          .then( response =>  response.json())
          .then( userFromServerJson => {
            this.form=userFromServerJson;
          })
          //.catch(exception => console.error(exception));
  }
}
</script>
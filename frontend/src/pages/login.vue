<template>
  <div>

    <h2>Logging in</h2>
    <b-form @submit="onSubmit">
      <b-form-group
          id="input-group-1"
          label="Email address:"
          label-for="input-1"
          description="We'll never share your email with anyone else."
      >
        <b-form-input
            id="input-1"
            v-model="form.email"
            type="email"
            placeholder="Enter email"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Your password:" label-for="input-2">
        <b-form-input
            id="input-2"
            v-model="form.password"
            placeholder="Enter password"
            type="password"
            required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>

    </b-form>

    <b-link :to="'/create_account'">
      I want to create account
    </b-link>

  </div>
</template>

<script>
export default {
  name: 'logjn',
  data() {
    return {
      form: {
        email: '',
        password: ''

      }
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      //alert(JSON.stringify(this.form))

      fetch(`http://localhost:8081/findUserByEmailAndPassword`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.form) // body data type must match "Content-Type" header
      }).then( response =>  response.json())
          .then( user => {
            if(user.error){
              alert(user.error);
              return;
            }

            localStorage.setItem('user', JSON.stringify(user))
            this.$root.user = user;

            if (this.$root.user) {
              this.$router.replace('/dashboard');
            }
          })
          .catch(exception => console.error(exception));


    }
  }
}
</script>
<template>
  <div>
      <h2>Creating an account</h2>
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
          label="Name:"
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
          label="Name:"
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

      <b-form-group id="input-group-5" label="Your password:" label-for="input-5">
        <b-form-input
            id="input-5"
            v-model="form.password"
            placeholder="Enter password"
            type="password"
            required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Create account</b-button>

    </b-form>

  </div>
</template>

<script>
export default {
  name: 'create_account',
  data() {
    return {
      form: {
        name: '',
        firstName: '',
        lastName: '',
        email: '',
        password: ''

      }
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      //alert(JSON.stringify(this.form))

      fetch(`http://localhost:8081/createUser`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.form) // body data type must match "Content-Type" header
      }).then( response =>  response.json())
          .then( user => {
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
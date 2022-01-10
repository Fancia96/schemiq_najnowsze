<template>
 <b-modal ref="searchFriendsModal" :title="title || 'Search users'">
  <b-form-group>
    <b-form-input
      v-model="query"
      type="text"
      placeholder="Search users..."
    >
    </b-form-input>
  </b-form-group>
   <b-list-group style="max-height: 50vh; overflow-y: auto;">
     <b-list-group-item v-for="user in filteredUsers" :key="user.id" button @click="selectUser(user)">
       {{user.name}}
     </b-list-group-item>
   </b-list-group>
 </b-modal>
</template>

<script>
export default {
  name: "search_users",
  props: {
    exclude: Array,
    title: String
  },
  computed: {
    filteredUsers() {
      let q = this.query.trim().toLowerCase();
      if (!q.length) {
        return [];
      }
      q = q.normalize('NFKD').split(' ').map(el => `(?=.*${el})`).join('')
      q = new RegExp(`^(${q}.*)$`, 'gi');

      return this.nonExcludedUsers.filter(t => t.search.match(q)).slice();
    },
    excludeIds() {
      return this.exclude.map(u => u.id);
    },
    nonExcludedUsers() {
        return this.users.filter(u => this.excludeIds.indexOf(u.id) === -1).slice();
    }
  },
  data() {
    return {
      query: '',
      users: []
    }
  },
  methods: {
    open() {
      this.$refs.searchFriendsModal.show();
    },
    selectUser(user) {
      if (confirm('Are you sure you want to add this user?')) {
        this.$emit('selected', {id: user.id, name: user.name});
        this.query = '';
        this.$refs.searchFriendsModal.hide();
      }
    }
  },
  mounted() {
    fetch('http://localhost:8081/getAllExistingUsers')
      .then(r => r.json())
      .then(users => {
          users.forEach(u => {
            this.users.push({id: u.id, name: u.name, search: u.name.normalize('NFKD').toLowerCase()})
          })
      })
      .catch(e => console.error(e));
  }
}
</script>

<style scoped>

</style>
<template>
  <div class="edit-profile">
    <h2>Редагувати профіль</h2>
    <form @submit.prevent="updateUser" class="edit-form">
      <div class="form-group">
        <label for="firstName">Ім'я</label>
        <input type="text" id="firstName" v-model="user.firstName" required />
      </div>
      <div class="form-group">
        <label for="lastName">Прізвище</label>
        <input type="text" id="lastName" v-model="user.lastName" required />
      </div>
      <div class="form-group">
        <label for="email">Електронна пошта</label>
        <input type="email" id="email" v-model="user.email" required />
      </div>
      <button type="submit" class="btn btn-primary">Зберегти зміни</button>
      <router-link to="/profile" class="btn btn-secondary">Скасувати</router-link>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        email: '',
      },
    };
  },
  mounted() {
    this.fetchUserData();
  },
  methods: {
    async fetchUserData() {
      try {
        const response = await axios.get('/api/users/me'); // Вказати правильний ендпоінт
        this.user = response.data;
      } catch (error) {
        console.error('Помилка при отриманні даних користувача:', error);
      }
    },
    async updateUser() {
      try {
        const response = await axios.put('/api/users/me', this.user); // Вказати правильний ендпоінт
        if (response.status === 200) {
          this.$router.push('/user-profile');
        }
      } catch (error) {
        console.error('Помилка при оновленні даних користувача:', error);
      }
    },
  },
};
</script>

<style scoped>
.edit-profile {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.edit-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 15px;
}

label {
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  transition: border-color 0.3s;
}

input:focus {
  border-color: #007bff;
}

.btn {
  margin-top: 10px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #f0f0f0;
  color: #333;
}

.btn-secondary:hover {
  background-color: #e0e0e0;
}
</style>

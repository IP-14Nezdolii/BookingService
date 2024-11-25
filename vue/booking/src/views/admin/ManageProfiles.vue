<template>
  <div class="manage-profiles">
    <h1>Управління профілями</h1>
    <div v-if="profiles.length > 0">
      <ul class="profile-list">
        <li v-for="profile in profiles" :key="profile.id" class="profile-item">
          <p><strong>Ім'я:</strong> {{ profile.name }}</p>
          <p><strong>Електронна пошта:</strong> {{ profile.email }}</p>
          <p><strong>Роль:</strong> {{ profile.role }}</p>
          <button @click="changeRole(profile.id, 'user')" class="btn">Призначити користувачем</button>
          <button @click="changeRole(profile.id, 'admin')" class="btn">Призначити адміністратором</button>
          <button @click="deleteProfile(profile.id)" class="btn delete">Видалити профіль</button>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Немає профілів для управління.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ManageProfiles',
  data() {
    return {
      profiles: [], // Ініціалізація масиву профілів
    };
  },
  created() {
    this.fetchProfiles(); // Завантаження профілів при створенні компонента
  },
  methods: {
    async fetchProfiles() {
      try {
        const response = await axios.get('/api/profiles'); // Запит до бекенду для отримання профілів
        this.profiles = response.data; // Оновлення масиву профілів
      } catch (error) {
        console.error('Не вдалося отримати профілі:', error);
      }
    },
    async changeRole(id, role) {
      try {
        await axios.put(`/api/profiles/${id}/role`, { role }); // Запит на зміну ролі профілю
        this.fetchProfiles(); // Оновлення списку профілів
      } catch (error) {
        console.error('Не вдалося змінити роль:', error);
      }
    },
    async deleteProfile(id) {
      try {
        await axios.delete(`/api/profiles/${id}`); // Запит на видалення профілю
        this.fetchProfiles(); // Оновлення списку профілів
      } catch (error) {
        console.error('Не вдалося видалити профіль:', error);
      }
    },
  },
};
</script>

<style scoped>
.manage-profiles {
  text-align: center;
  padding: 20px;
}

.profile-list {
  list-style: none;
  padding: 0;
}

.profile-item {
  border: 1px solid #ccc;
  padding: 15px;
  margin: 10px 0;
  border-radius: 8px;
}

.btn {
  margin: 5px;
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: black;
  background-color: #4c8bf5;
}

.delete {
  background-color: #dc3545;
}

</style>

<template>
  <div class="favorites">
    <h2>Обране</h2>
    <div v-if="favorites.length > 0" class="favorites-list">
      <ul>
        <li v-for="(favorite, index) in favorites" :key="index" class="favorite-item">
          <div class="favorite-details">
            <p><strong>Апартамент:</strong> {{ favorite.propertyName }}</p>
            <div class="favorite-actions">
              <router-link :to="`/apartment/${favorite.id}`" class="btn btn-secondary">
                Переглянути
              </router-link>
              <button @click="removeFavorite(favorite.id)" class="btn btn-remove">
                Видалити з обраного
              </button>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Ви ще не додали жодного апартаменту в обране.</p>
    </div>
    <router-link to="/user-profile" class="back-link">
      Назад до профілю
    </router-link>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      favorites: [],
    };
  },
  mounted() {
    this.fetchFavorites();
  },
  methods: {
    async fetchFavorites() {
      try {
        const response = await axios.get('/api/favorites'); // Вказати правильний ендпоінт
        this.favorites = response.data;
      } catch (error) {
        console.error('Помилка при отриманні обраних апартаментів:', error);
      }
    },
    async removeFavorite(id) {
      try {
        await axios.delete(`/api/favorites/${id}`); // Вказати правильний ендпоінт
        this.favorites = this.favorites.filter(favorite => favorite.id !== id);
      } catch (error) {
        console.error('Помилка при видаленні з обраного:', error);
      }
    },
  },
};
</script>


<style scoped>
.favorites {
  max-width: 800px;
  margin: 40px auto;
  text-align: center;
  font-family: Arial, sans-serif;
}

h2 {
  color: #333;
  margin-bottom: 20px;
}

.favorites-list {
  margin-bottom: 20px;
}

ul {
  list-style-type: none;
  padding: 0;
}

.favorite-item {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin: 10px 0;
  padding: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.favorite-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.favorite-actions {
  display: flex;
  gap: 10px; /* Відстань між кнопками */
}

.btn {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #0056b3;
}

.btn-remove {
  background-color: #dc3545;
}

.btn-remove:hover {
  background-color: #c82333;
}
.back-link {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px; /* Внутрішній відступ */
  background-color: darkgray; /* Фоновий колір */
  color: white; /* Колір тексту */
  border-radius: 50px; /* Закруглені кути */
  text-decoration: none; /* Відключити підкреслення */
  transition: background-color 0.3s, transform 0.2s; /* Анімації */
}

.back-link:hover {
  background-color: grey; /* Темніший колір при наведенні */
  transform: translateY(-2px); /* Легке підняття */
  color: white;
}
</style>

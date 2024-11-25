<template>
  <div class="manage-properties">
    <h2>Управління Апартаментами</h2>
    <div v-if="properties.length > 0">
      <ul class="property-list">
        <li v-for="property in properties" :key="property.id" class="property-item">
          <h3>{{ property.title }}</h3>
          <p><strong>Опис:</strong> {{ property.description }}</p>
          <p><strong>Ціна:</strong> {{ property.price }} грн/доба</p>
          <button @click="deleteProperty(property.id)" class="btn delete">Видалити</button>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Немає доступних апартаментів.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ManageProperties',
  data() {
    return {
      properties: [], // Масив для зберігання оголошень
    };
  },
  created() {
    this.fetchProperties(); // Завантажити оголошення при створенні компонента
  },
  methods: {
    async fetchProperties() {
      try {
        const response = await axios.get('/api/apartments'); // Отримати оголошення з бекенду
        this.properties = response.data; // Оновити масив оголошень
      } catch (error) {
        console.error('Не вдалося отримати оголошення:', error);
      }
    },
    async deleteProperty(id) {
      try {
        await axios.delete(`/api/apartments/${id}`); // Видалення оголошення
        this.fetchProperties(); // Оновити список оголошень
      } catch (error) {
        console.error('Не вдалося видалити оголошення:', error);
      }
    },
  },
};
</script>

<style scoped>
.manage-properties {
  text-align: center;
  padding: 20px;
}

.property-list {
  list-style: none;
  padding: 0;
}

.property-item {
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

<template>
  <div class="property-card">
    <img :src="property.image" alt="Property Image" class="property-image" />
    <div class="property-info">
      <h3 class="property-name">{{ property.name }}</h3>
      <p class="property-description">{{ property.description }}</p>
      <p class="price">{{ property.price }} ₴ за ніч</p>
      <router-link :to="`/property-details/${property.id}`" class="btn btn-primary">Деталі</router-link>
    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Імпортуємо axios для роботи з запитами

export default {
  name: 'PropertyCard',
  props: {
    property: {
      type: Object,
      required: true,
    },
  },
  async created() {
    await this.fetchPropertyDetails(); // Викликаємо метод для отримання даних про квартиру
  },
  methods: {
    async fetchPropertyDetails() {
      try {
        const response = await axios.get(`/api/properties/${this.property.id}`); // Запит до бекенду для отримання даних
        this.property = response.data; // Оновлюємо дані про квартиру
      } catch (error) {
        console.error('Помилка при отриманні даних про квартиру:', error); // Обробка помилок
      }
    },
  },
};
</script>

<style scoped>
.property-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  transition: box-shadow 0.3s;
}

.property-card:hover {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.property-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.property-info {
  padding: 10px;
}

.property-name {
  font-size: 1.5rem;
  margin: 0 0 5px;
}

.property-description {
  color: #666;
  margin: 0 0 10px;
}

.price {
  font-weight: bold;
  color: #28a745;
  margin: 0 0 10px;
}

.btn {
  display: inline-block;
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  text-align: center;
  border: none;
  border-radius: 5px;
  text-decoration: none;
}

.btn:hover {
  background-color: #0056b3;
}
</style>
<template>
  <div class="home">
    <SearchForm @search="performSearch" />
    <h2>Доступні Апартаменти</h2>
    <div class="properties">
      <PropertyCard
          v-for="property in properties"
          :key="property.id"
          :property="property"
      />
    </div>
  </div>
</template>

<script>
import SearchForm from '../components/SearchForm.vue';
import PropertyCard from '../components/PropertyCard.vue';
import axios from 'axios';

export default {
  name: 'Home',
  components: {
    SearchForm,
    PropertyCard,
  },
  data() {
    return {
      properties: [], // Порожній масив для зберігання квартир
    };
  },
  created() {
    this.fetchProperties();
  },
  methods: {
    async fetchProperties() {
      try {
        const response = await axios.get('/api/apartments'); // квартири з бекенду
        this.properties = response.data;
      } catch (error) {
        console.error('Не вдалося отримати квартири:', error);
      }
    },
    async performSearch(criteria) {
      try {
        const response = await axios.get('http://localhost:8080/api/apartments/search', { params: criteria });
        this.properties = response.data; // Оновлює список квартир за результатами пошуку
      } catch (error) {
        console.error('Помилка пошуку:', error);
      }
    },
  },
};
</script>

<style scoped>
.home {
  padding: 40px;
  background-color: #f4f4f4; /* Легкий фон для контрасту */
  min-height: 100vh; /* Заповнити всю висоту вікна */
}

.properties {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center; /* Центрування карток */
}

h2 {
  text-align: center; /* Центрування заголовка */
  margin-bottom: 30px; /* Відступ під заголовком */
  font-size: 2rem; /* Збільшений розмір тексту */
  color: #333; /* Темний колір тексту */
}

.property-card {
  width: 300px; /* Ширина картки */
  border-radius: 10px; /* Округлені кути */
  overflow: hidden; /* Сховати вміст, що виходить за межі */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Тінь картки */
  background-color: white; /* Білий фон для картки */
  transition: transform 0.3s; /* Анімація при наведенні */
}

.property-card:hover {
  transform: translateY(-5px); /* Піднімання картки при наведенні */
}

.property-card img {
  width: 100%; /* Займає всю ширину картки */
  height: auto; /* Автоматична висота */
}

.property-card .property-details {
  padding: 15px; /* Внутрішні відступи */
}

.property-card h3 {
  margin: 0; /* Видалення відступів */
  font-size: 1.5rem; /* Розмір заголовка */
}

.property-card p {
  color: #666; /* Сірий колір опису */
}

.property-card .price {
  font-weight: bold; /* Жирний текст для ціни */
  color: #007bff; /* Синій колір для ціни */
}
</style>

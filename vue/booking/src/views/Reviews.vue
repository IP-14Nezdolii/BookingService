<template>
  <div class="reviews">
    <h1>Відгуки про {{ property.name }}</h1>

    <div class="review-list">
      <div v-for="review in reviews" :key="review.id" class="review-card">
        <div class="card-header">
          <h3>{{ review.userName }}</h3>
          <p class="rating">Оцінка: <strong>{{ review.rating }} ⭐</strong></p>
        </div>
        <p class="comment">{{ review.comment }}</p>
      </div>
    </div>

    <router-link :to="`/properties/${property.id}`" class="back-link">
      <span>Назад до деталей</span>
    </router-link>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Reviews',
  data() {
    return {
      property: {
        id: this.$route.params.propertyId, // Витягуємо ID з маршруту
        name: '', // Пізніше заповнимо
      },
      reviews: [],
    };
  },
  mounted() {
    this.fetchPropertyData();
    this.fetchReviews();
  },
  methods: {
    async fetchPropertyData() {
      try {
        const response = await axios.get(`/api/properties/${this.property.id}`); // Вказати правильний ендпоінт
        this.property.name = response.data.name; // Припустимо, що ви отримаєте назву
      } catch (error) {
        console.error('Помилка при отриманні даних апартаменту:', error);
      }
    },
    async fetchReviews() {
      try {
        const response = await axios.get(`/api/reviews/property/${this.property.id}`); // Вказати правильний ендпоінт
        this.reviews = response.data;
      } catch (error) {
        console.error('Помилка при отриманні відгуків:', error);
      }
    },
  },
};
</script>

<style scoped>
.reviews {
  padding: 40px;
  text-align: center;
  background-color: #f7f7f7;
}

h1 {
  font-size: 2.5em;
  color: #333;
  margin-bottom: 30px;
}

.review-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.review-card {
  background: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h3 {
  font-size: 1.5em;
  color: #555;
}

.rating {
  font-size: 1.2em;
  color: #f5a623;
}

.comment {
  margin-top: 10px;
  font-size: 1.1em;
  color: #666;
  line-height: 1.6;
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

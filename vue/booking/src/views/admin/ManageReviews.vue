<template>
  <div class="manage-reviews">
    <h3>Управління Відгуками</h3>

    <div v-if="reviews.length > 0">
      <ul class="reviews-list">
        <li v-for="review in reviews" :key="review.id" class="review-item">
          <strong>Оцінка:</strong> {{ review.rating }}<br />
          <strong>Відгук:</strong> {{ review.review }}<br />
          <button @click="deleteReview(review.id)" class="btn delete">Видалити</button>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Ще немає відгуків.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ManageReviews',
  data() {
    return {
      reviews: [], // Масив для зберігання відгуків
    };
  },
  created() {
    this.fetchReviews(); // Завантажити відгуки при створенні компонента
  },
  methods: {
    async fetchReviews() {
      try {
        const response = await axios.get('/api/reviews'); // Отримати відгуки з бекенду
        this.reviews = response.data; // Оновити масив відгуків
      } catch (error) {
        console.error('Не вдалося отримати відгуки:', error);
      }
    },
    async deleteReview(id) {
      try {
        await axios.delete(`/api/reviews/${id}`); // Видалення відгуку за ID
        this.fetchReviews(); // Оновити список відгуків
      } catch (error) {
        console.error('Не вдалося видалити відгук:', error);
      }
    },
  },
};
</script>

<style scoped>
.manage-reviews {
  text-align: center;
  padding: 20px;
}

.reviews-list {
  list-style: none;
  padding: 0;
}

.review-item {
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
  color: white;
  background-color: #4c8bf5;
}

.delete {
  background-color: #dc3545;
}
</style>

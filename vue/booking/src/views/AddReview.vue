<template>
  <div class="review-section">
    <h3>Оцінка та відгуки</h3>
    <form @submit.prevent="submitReview" class="review-form">
      <div class="form-group">
        <label for="rating">Оцінка (1-5):</label>
        <input type="number" v-model="rating" min="1" max="5" required class="rating-input" />
      </div>
      <div class="form-group">
        <label for="review">Відгук:</label>
        <textarea v-model="review" required class="review-textarea" placeholder="Напишіть ваш відгук"></textarea>
      </div>
      <button type="submit" class="btn-submit">Додати відгук</button>
    </form>

    <div v-if="reviews.length" class="reviews-list">
      <h4>Відгуки:</h4>
      <ul>
        <li v-for="(rev, index) in reviews" :key="index" class="review-item">
          <strong>Оцінка:</strong> {{ rev.rating }}<br />
          <strong>Відгук:</strong> {{ rev.review }}
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Ще немає відгуків. Станьте першим, хто залишить відгук!</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rating: 1,
      review: '',
      reviews: [],
    };
  },
  methods: {
    async submitReview() {
      const reviewData = {
        rating: this.rating,
        review: this.review,
      };

      try {
        // Відправляємо відгук на сервер
        await axios.post('/api/reviews', reviewData);
        this.reviews.push(reviewData); // Додаємо новий відгук
        this.resetForm(); // Очищуємо форму
      } catch (error) {
        console.error('Помилка при відправці відгуку:', error);
      }
    },
    resetForm() {
      this.rating = 1;
      this.review = '';
    },
  },
};
</script>
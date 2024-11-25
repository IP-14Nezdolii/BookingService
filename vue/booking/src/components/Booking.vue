<template>
  <div class="booking-page">
    <h1 class="booking-title">Бронювання квартири</h1>
    <form @submit.prevent="submitBooking" class="booking-form">
      <div class="form-group">
        <label for="guests">Кількість осіб</label>
        <input type="number" v-model="bookingData.guests" min="1" required />
      </div>
      <div class="form-group">
        <label for="checkin">Дата заїзду</label>
        <input type="date" v-model="bookingData.checkin" required />
      </div>
      <div class="form-group">
        <label for="checkout">Дата виїзду</label>
        <input type="date" v-model="bookingData.checkout" required />
      </div>
      <div id="card-element" class="card-element"></div>
      <button type="submit" class="btn btn-primary" :disabled="loading">
        {{ loading ? 'Завантаження...' : 'Оплатити' }}
      </button>
    </form>
    <p v-if="message" class="message">{{ message }}</p>
  </div>
</template>

<script>
import { loadStripe } from '@stripe/stripe-js';
import axios from 'axios';

export default {
  name: 'Booking',
  data() {
    return {
      bookingData: {
        propertyId: this.$route.params.id,
        guests: 1,
        checkin: '',
        checkout: '',
      },
      stripe: null,
      cardElement: null,
      loading: false,
      message: '',
    };
  },
  async created() {
    this.stripe = await loadStripe('your-publishable-key-here'); // Ваш API ключ
    this.setupCardElement();
  },
  methods: {
    setupCardElement() {
      const elements = this.stripe.elements();
      this.cardElement = elements.create('card');
      this.cardElement.mount('#card-element');
    },
    async submitBooking() {
      this.loading = true;
      try {
        const { data: order } = await axios.post('/api/book', this.bookingData);
        const { paymentIntent, error } = await this.stripe.confirmCardPayment(order.clientSecret, {
          payment_method: {
            card: this.cardElement,
          },
        });

        if (error) {
          console.error('Помилка оплати:', error);
          this.message = 'Помилка оплати. Спробуйте ще раз.';
          this.loading = false;
          return;
        }

        this.message = 'Оплата успішна! Ваше бронювання підтверджено.';
        this.loading = false;
      } catch (error) {
        console.error('Помилка при бронюванні:', error);
        this.message = 'Не вдалося завершити бронювання. Спробуйте ще раз.';
        this.loading = false;
      }
    },
  },
};
</script>


<style scoped>
.booking-page {
  padding: 40px;
  background: linear-gradient(135deg, #f5f7fa, #c3cfe2); /* Градієнтний фон */
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  max-width: 600px;
  margin: 20px auto;
}

.booking-title {
  font-size: 2em;
  color: #343a40;
  text-align: center;
  margin-bottom: 20px;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.booking-form {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px; /* Відступ між формами */
}

label {
  font-weight: bold;
  margin-bottom: 5px;
  display: block; /* Відображення блоку */
}

input {
  width: 100%;
  padding: 12px;
  border: 2px solid #ccc;
  border-radius: 8px;
  transition: border-color 0.3s ease;
}

input:focus {
  border-color: #007bff; /* Колір рамки при фокусі */
}

.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 100%; /* Займати всю ширину */
}

.btn-primary {
  background-color: #4c8bf5;
  color: white;
}

.btn-primary:hover {
  background-color: #1d68e1; /* Темніший колір при наведенні */
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
}
.card-element {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

</style>

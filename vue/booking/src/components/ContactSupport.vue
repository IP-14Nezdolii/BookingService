<template>
  <div class="contact-support">
    <h2>Зв’язатися з технічною підтримкою</h2>
    <form @submit.prevent="submitSupportRequest">
      <div class="form-group">
        <label for="name">Ваше ім'я:</label>
        <input type="text" v-model="name" required placeholder="Введіть ваше ім'я" />
      </div>
      <div class="form-group">
        <label for="email">Електронна пошта:</label>
        <input type="email" v-model="email" required placeholder="Введіть вашу електронну пошту" />
      </div>
      <div class="form-group">
        <label for="message">Повідомлення:</label>
        <textarea v-model="message" required placeholder="Опишіть вашу проблему"></textarea>
      </div>
      <button type="submit" class="submit-button">Відправити запит</button>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      name: '',
      email: '',
      message: '',
      successMessage: '',
      errorMessage: '',
    };
  },
  methods: {
    async submitSupportRequest() {
      const supportData = {
        name: this.name,
        email: this.email,
        message: this.message,
      };

      try {
        await axios.post('/api/support', supportData);
        this.successMessage = 'Ваш запит успішно відправлено!';
        this.resetForm();
      } catch (error) {
        this.errorMessage = 'Не вдалося відправити запит. Спробуйте ще раз.';
      }
    },
    resetForm() {
      this.name = '';
      this.email = '';
      this.message = '';
      this.successMessage = '';
      this.errorMessage = '';
    },
  },
};
</script>

<style scoped>
.contact-support {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 26px;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  font-size: 16px;
  color: #666;
}

input, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
  margin-top: 8px;
  transition: all 0.3s ease;
}

input:focus, textarea:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

textarea {
  height: 120px;
  resize: none;
}

.submit-button {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #0056b3;
}

.success {
  color: #28a745;
  font-size: 16px;
  margin-top: 20px;
}

.error {
  color: #dc3545;
  font-size: 16px;
  margin-top: 20px;
}
</style>

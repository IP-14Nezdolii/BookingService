<template>
  <div class="register">
    <h2>Реєстрація</h2>
    <form @submit.prevent="registerUser">
      <div class="form-group">
        <label for="firstName">Ім'я:</label>
        <input type="text" v-model="firstName" required />
      </div>
      <div class="form-group">
        <label for="lastName">Прізвище:</label>
        <input type="text" v-model="lastName" required />
      </div>
      <div class="form-group">
        <label for="email">Електронна пошта:</label>
        <input type="email" v-model="email" required />
      </div>
      <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" v-model="password" required />
      </div>
      <div class="form-group">
        <button type="submit">Зареєструватися</button>
      </div>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>

    <div class="social-login">
      <button class="social-button google-button" @click="loginWithGoogle">
        <img src="https://upload.wikimedia.org/wikipedia/commons/c/c1/Google_%22G%22_logo.svg" alt="Google" />
      </button>
      <button class="social-button facebook-button" @click="loginWithFacebook">
        <img src="https://upload.wikimedia.org/wikipedia/commons/5/51/Facebook_f_logo_%282019%29.svg" alt="Facebook" />
      </button>
    </div>

    <p>Вже є акаунт? <router-link to="/login">Увійти</router-link></p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async registerUser() {
      try {
        const response = await axios.post('/api/register', {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password
        });

        // Перенаправлення на профіль користувача або іншу сторінку
        this.$router.push('/user-profile');
      } catch (error) {
        this.errorMessage = 'Не вдалося зареєструватися. Спробуйте ще раз.';
      }
    },
    loginWithGoogle() {
      window.location.href = '/oauth2/authorization/google';
    },
    loginWithFacebook() {
      window.location.href = '/oauth2/authorization/facebook';
    }
  }
};
</script>

<style scoped>
.register {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

button {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

.social-login {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}

.social-button {
  width: 40px; /* Розмір кнопки */
  height: 40px; /* Розмір кнопки */
  border: none;
  background: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 5px; /* Відступ між кнопками */
  cursor: pointer;
}

.google-button img {
  width: 100%; /* Відповідний розмір логотипу */
  height: auto;
}

.facebook-button img {
  width: 100%; /* Відповідний розмір логотипу */
  height: auto;
}

.error {
  color: red;
}
</style>

<style scoped>
.register {
  max-width: 400px;
  margin: auto;
  padding: 30px; /* Збільшено відступи */
  border: 1px solid #ddd; /* Світліша межа */
  border-radius: 10px; /* Округлені кути */
  background-color: #ffffff; /* Білий фон */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Тінь для підняття */
}

h2 {
  text-align: center; /* Центрування заголовка */
  margin-bottom: 20px; /* Відступ під заголовком */
  color: #333; /* Темний колір заголовка */
}

.form-group {
  margin-bottom: 20px; /* Відступи між групами */
}

label {
  display: block; /* Блоковий елемент для відображення етикеток */
  margin-bottom: 5px; /* Відступ під етикеткою */
  color: #555; /* Сірий колір для етикеток */
}

input {
  width: 100%; /* Ширина на всю ширину контейнера */
  padding: 10px; /* Внутрішні відступи */
  border: 1px solid #ccc; /* Світла межа */
  border-radius: 5px; /* Округлені кути */
  transition: border-color 0.3s; /* Анімація зміни кольору межі */
}

input:focus {
  border-color: #007bff; /* Синя межа при фокусуванні */
  outline: none; /* Прибрати стандартне обведення */
}

button {
  width: 100%; /* Ширина кнопки на всю ширину контейнера */
  padding: 10px; /* Внутрішні відступи */
  background-color: #007bff; /* Синій фон для кнопки */
  color: white; /* Білий текст на кнопці */
  border: none; /* Без межі */
  border-radius: 5px; /* Округлені кути */
  cursor: pointer; /* Зміна курсора при наведенні */
  transition: background-color 0.3s; /* Анімація зміни кольору фону */
}

button:hover {
  background-color: #0056b3; /* Темніший синій при наведенні */
}

.error {
  color: red; /* Червоний колір для повідомлень про помилки */
  text-align: center; /* Центрування тексту */
}
</style>

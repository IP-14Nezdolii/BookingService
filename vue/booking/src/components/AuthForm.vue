<template>
  <div class="auth-form">
    <h2>Авторизація</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="email">Електронна пошта:</label>
        <input
            v-model="email"
            type="email"
            required
        />
      </div>
      <div class="form-group">
        <label for="password">Пароль:</label>
        <input
            v-model="password"
            type="password"
            required
        />
      </div>
      <button type="submit">Увійти</button>
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

    <p>Не маєте акаунта? <router-link to="/register">Зареєструйтесь!</router-link></p>
    <p><router-link to="/admin/login">Увійти як адміністратор</router-link></p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async submitForm() {
      try {
        // Логіка авторизації
        const response = await axios.post('/api/login', {
          email: this.email,
          password: this.password
        });

        // Перенаправлення на профіль користувача або іншу сторінку
        this.$router.push('/user-profile');
      } catch (error) {
        this.errorMessage = 'Не вдалося увійти. Перевірте електронну пошту та пароль.';
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
.auth-form {
  max-width: 400px;
  margin: auto;
}

.form-group {
  margin-bottom: 15px;
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
  margin-top: 10px;
}
</style>


<style scoped>
.auth-form {
  max-width: 400px;
  margin: auto;
  padding: 30px; /* Збільшено відступи для комфорту */
  border: 1px solid #ddd; /* Світла межа */
  border-radius: 10px; /* Округлені кути */
  background-color: #ffffff; /* Білий фон */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); /* Тінь для об'єму */
}

h2 {
  text-align: center; /* Центрування заголовка */
  margin-bottom: 20px; /* Відступ під заголовком */
  color: #333; /* Темний колір заголовка */
}

input {
  width: 100%; /* Ширина на всю ширину контейнера */
  padding: 12px; /* Внутрішні відступи */
  margin-bottom: 15px; /* Відступ між полями */
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
  padding: 12px; /* Внутрішні відступи */
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

.toggle-link {
  text-align: center; /* Центрування тексту */
  color: #007bff; /* Синій колір для посилання */
  cursor: pointer; /* Зміна курсора при наведенні */
}

.toggle-link:hover {
  text-decoration: underline; /* Підкреслення при наведенні */
}
</style>

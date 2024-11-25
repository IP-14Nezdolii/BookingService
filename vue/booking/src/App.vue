<template>
  <div id="app">
    <!-- Навігаційне меню або шапка сайту -->
    <header>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
      <img src="@/img.png" alt="Логотип" class="logo" />
      <nav class="navbar navbar-expand-lg navbar-light menu">
        <!-- Додаємо кнопку для випадаючого меню -->
        <button
            class="navbar-toggler"
            type="button"
            @click="toggleMenu"
            aria-controls="navbarNav"
            aria-expanded="isMenuOpen"
            aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div :class="{'collapse': !isMenuOpen, 'navbar-collapse': true}" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link to="/" class="nav-link">Головна</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/user-profile" class="nav-link">Профіль користувача</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/login" class="nav-link">Увійти</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/register" class="nav-link">Реєстрація</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/add-apartment" class="nav-link">Додати квартиру</router-link>
            </li>
          </ul>
        </div>
      </nav>
    </header>

    <!-- Головний вміст сторінок -->
    <main>
      <!-- Підключайте компоненти для сторінок тут через <router-view> -->
      <router-view></router-view>
    </main>
  </div>
  <!-- Футер з контактами -->
  <footer class="bg-primary text-white text-center mt-5">
    <div class="container p-3">
      <div class="row">
        <div class="col-lg-6 col-md-12 mb-1">
          <h5 class="text-uppercase">Контакти</h5>
          <p>
            <strong>Телефон:</strong> +38 (067) 123-45-67<br />
            <strong>Електронна пошта:</strong>
            <a href="mailto:support@example.com" class="text-white">support@example.com</a>
          </p>
        </div>

        <div class="col-lg-6 col-md-12 mb-1">
          <h5 class="text-uppercase">Соціальні мережі</h5>
          <div>
            <a href="#" class="text-white me-3">
              <i class="fab fa-facebook-f"></i>
            </a>
            <a href="#" class="text-white me-3">
              <i class="fab fa-instagram"></i>
            </a>
            <a href="#" class="text-white">
              <i class="fab fa-twitter"></i>
            </a>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
import { defineComponent } from 'vue';
import Home from './views/Home.vue';
import PropertyDetails from './views/PropertyDetails.vue';
import UserProfile from './views/UserProfile.vue';
import BookingConfirmation from './components/BookingConfirmation.vue';
import Login from './components/AuthForm.vue';
import Register from './components/Register.vue';
import AddApartment from './components/AddApartment.vue';
import AdminProfile from './views/admin/AdminProfile.vue';
import ManageListings from './views/admin/ManageProperties.vue';
import ManageReviews from './views/admin/ManageReviews.vue';
import ManageComplaints from './views/admin/ManageComplaints.vue';
import AdminLogin from './views/admin/AdminLogin.vue';
import Reviews from './views/Reviews.vue';
import AddReview from "./views/AddReview.vue";
import ContactSupport from "./components/ContactSupport.vue";
import ManageProperties from "./views/admin/ManageProperties.vue";

export default defineComponent({
  name: 'App',
  data() {
    return {
      isMenuOpen: false,
    };
  },
  components: {
    Home,
    PropertyDetails,
    UserProfile,
    BookingConfirmation,
    Login,
    Register,
    AddApartment,
    AdminProfile,
    ManageListings,
    ManageReviews,
    ManageComplaints,
    AdminLogin,
    Reviews,
    AddReview,
    ContactSupport,
    ManageProperties,
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
  },
  mounted() {
    this.$router.push('/'); // Перенаправляємо на головну сторінку
  },
});
</script>

<style>
.logo {
  height: 50px; /* Задаємо висоту логотипа */
  width: auto;  /* Ширина підлаштовується автоматично */
}

.fab {
  font-size: 24px; /* Розмір іконок */
  transition: color 0.3s; /* Анімація зміни кольору */
}

.fab:hover {
  color: grey; /* Змінюємо колір при наведенні */
}

footer {
  position: relative;
  bottom: 0;
  width: 100%;
}
</style>

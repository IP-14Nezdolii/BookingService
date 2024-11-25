<template>
  <div class="user-profile">
    <h2>Профіль користувача</h2>
    <div class="profile-container">
      <div class="user-info">
        <div class="user-card">
          <h3>{{ user.firstName }} {{ user.lastName }}</h3>
          <p>
            <strong>Електронна пошта:</strong> {{ user.email }}
          </p>
          <button @click="editUser" class="btn btn-edit">Редагувати</button>
        </div>
        <div class="action-links">
          <router-link to="/add-apartment" class="btn btn-primary action-btn">
            Додати апартамент
          </router-link>
          <router-link to="/favorites" class="btn btn-primary action-btn">
            Переглянути обране
          </router-link>
          <p>Виникла проблема?</p>
          <router-link to="/contact-support" class="btn btn-support action-btn">
            Зв’язатися з технічною підтримкою
          </router-link>
        </div>
      </div>

      <div class="booking-history">
        <h3>Історія бронювань</h3>
        <div v-if="bookings.length > 0">
          <ul class="booking-list">
            <li v-for="(booking, index) in bookings" :key="index" class="booking-item">
              <div class="booking-details">
                <p><strong>Апартамент:</strong> {{ booking.propertyName }}</p>
                <p><strong>Дата заїзду:</strong> {{ booking.checkin }}</p>
                <p><strong>Дата виїзду:</strong> {{ booking.checkout }}</p>
              </div>
              <router-link :to="`/add-review/${booking.id}`" class="btn btn-secondary">
                Додати відгук
              </router-link>
            </li>
          </ul>
        </div>
        <div v-else>
          <p>Ви ще не зробили жодного бронювання.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {},
      bookings: [],
    };
  },
  mounted() {
    this.fetchUserData();
    this.fetchUserBookings();
  },
  methods: {
    async fetchUserData() {
      try {
        const response = await axios.get('/api/users/me'); // Вказати правильний ендпоінт
        this.user = response.data;
      } catch (error) {
        console.error('Помилка при отриманні даних користувача:', error);
      }
    },
    async fetchUserBookings() {
      try {
        const response = await axios.get('/api/bookings/user'); // Вказати правильний ендпоінт
        this.bookings = response.data;
      } catch (error) {
        console.error('Помилка при отриманні історії бронювань:', error);
      }
    },
    editUser() {
      this.$router.push('/edit-profile');
    },
  },
};
</script>

<style scoped>
.user-profile {
  max-width: 900px;
  margin: 40px auto;
  text-align: center;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f0f4f8;
  border-radius: 15px;
  padding: 20px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
}

h2 {
  color: #2c3e50;
  margin-bottom: 20px;
}

.profile-container {
  display: flex;
  justify-content: space-between;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.user-info {
  flex: 1;
  margin-right: 20px;
}

.user-card {
  background-color: #e6f7ff;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.user-card:hover {
  transform: scale(1.02);
}

.booking-history {
  flex: 1;
  padding-left: 20px;
  border-left: 1px solid #ccc;
}

.btn {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
  transition: background-color 0.3s, transform 0.2s;
}

.btn:hover {
  background-color: #0056b3;
}

.btn-edit {
  margin-left: 10px;
  background-color: #28a745;
}

.btn-support {
  background-color: #17a2b8;
}

.btn-secondary {
  background-color: #6c757d;
}

h3 {
  margin-top: 20px;
  color: #34495e;
}

.booking-list {
  list-style-type: none;
  padding: 0;
}

.booking-item {
  background-color: #f9f9f9;
  border-radius: 5px;
  margin: 10px 0;
  padding: 15px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.booking-details {
  flex: 1;
}

.booking-item:hover {
  background-color: #f0f8ff;
}
.action-links {
  margin-top: 20px; /* Відступ зверху для групи кнопок */
}

.action-btn {
  margin: 10px 0; /* Відстань між кнопками */
  padding: 12px 24px; /* Більше відступу всередині кнопок */
  font-size: 16px; /* Збільшений розмір шрифту для кращої читабельності */
}

</style>




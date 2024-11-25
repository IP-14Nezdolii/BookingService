<template>
  <div class="search-container">
    <button class="toggle-search" @click="toggleSearchBox">
      {{ isSearchBoxOpen ? 'Сховати пошук' : 'Розширений пошук' }}
    </button>

    <div class="search-form" v-if="isSearchBoxOpen || !isMobile">
      <h2>Пошук апартаментів</h2>
      <form @submit.prevent="submitSearch">
        <div class="form-row">
          <div class="form-group">
            <label for="location">Місто</label>
            <select id="location" v-model="location" required>
              <option value="" disabled selected>Оберіть місто</option>
              <option value="Kyiv">Київ</option>
              <option value="Lviv">Львів</option>
              <option value="Odesa">Одеса</option>
              <option value="Kharkiv">Харків</option>
            </select>
          </div>

          <div class="form-group">
            <label for="checkin">Дата заїзду</label>
            <input type="date" id="checkin" v-model="checkin" required />
          </div>

          <div class="form-group">
            <label for="checkout">Дата виїзду</label>
            <input type="date" id="checkout" v-model="checkout" required />
          </div>

          <div class="form-group">
            <label for="guests">Кількість осіб</label>
            <select v-model="guests" id="guests" required>
              <option value="1">1 особа</option>
              <option value="2">2 особи</option>
              <option value="3">3 особи</option>
              <option value="4">4 особи</option>
              <option value="5">5 осіб</option>
              <option value="6">6 осіб</option>
            </select>
          </div>

          <div class="form-group">
            <label for="propertyType">Тип житла</label>
            <select v-model="propertyType">
              <option value="">Всі типи</option>
              <option value="apartment">Квартира</option>
              <option value="house">Будинок</option>
              <option value="hotel">Готель</option>
            </select>
          </div>

          <div class="form-group">
            <label for="rating">Рейтинг</label>
            <select v-model="rating">
              <option value="">Всі рейтинги</option>
              <option value="1">1 Зірка</option>
              <option value="2">2 Зірки</option>
              <option value="3">3 Зірки</option>
              <option value="4">4 Зірки</option>
              <option value="5">5 Зірок</option>
            </select>
          </div>
        </div>
        <button type="submit" class="search-button">Знайти</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'SearchForm',
  data() {
    return {
      isSearchBoxOpen: false,
      location: '',
      checkin: '',
      checkout: '',
      guests: 1,
      propertyType: '',
      rating: '',
      isMobile: false,
    };
  },
  mounted() {
    this.isMobile = window.innerWidth < 600;
    window.addEventListener('resize', this.checkMobile);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.checkMobile);
  },
  methods: {
    checkMobile() {
      this.isMobile = window.innerWidth < 600;
      if (!this.isMobile) {
        this.isSearchBoxOpen = true;
      }
    },
    async submitSearch() {
      const searchCriteria = {
        location: this.location,
        checkin: this.checkin,
        checkout: this.checkout,
        guests: this.guests,
        propertyType: this.propertyType,
        rating: this.rating,
      };

      try {
        const response = await axios.post('/api/search', searchCriteria);
        console.log('Результати пошуку:', response.data);
        // Додайте логіку для обробки результатів пошуку
      } catch (error) {
        console.error('Помилка при пошуку:', error);
      }
    },
    toggleSearchBox() {
      this.isSearchBoxOpen = !this.isSearchBoxOpen;
    },
  },
};
</script>

<style scoped>
.search-container {
  max-width: 900px;
  margin: auto;
  padding: 10px;
}

.toggle-search {
  display: block;
  margin: 5px auto; /* Зменшено верхній відступ */
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.search-form {
  max-width: 900px; /* Збільшена ширина */
  margin: auto;
  padding: 10px; /* Збільшене поле для відступів */
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Додано тінь */
}

h2 {
  text-align: center;
  margin-bottom: 10px;
}

.form-row {
  display: flex; /* Використання флексбоксу для розташування полів в один ряд */
  justify-content: space-between; /* Вирівнювання полів */
  margin-bottom: 10px;
}

.form-group {
  flex: 1; /* Поля займають однакову ширину */
  margin-right: 5px; /* Відступ між полями */
}

.form-group:last-child {
  margin-right: 0; /* Немає відступу для останнього поля */
}

.form-group label {
  display: block;
  margin-bottom: 3px;
  font-weight: bold; /* Текст жирним шрифтом */
}

input, select {
  width: 100%; /* Збільшено ширину */
  padding: 6px; /* Збільшено внутрішнє відступ */
  border: 1px solid #ccc;
  border-radius: 5px; /* Краї з округленими кутами */
}

input:focus, select:focus {
  border-color: #007bff; /* Зміна кольору рамки при фокусі */
  outline: none; /* Видалення стандартного обведення */
}

.search-button {
  width: 100%; /* Кнопка займає всю ширину */
  padding: 8px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer; /* Курсор вказує на можливість натискання */
  transition: background-color 0.3s; /* Анімація переходу */
}

.search-button:hover {
  background-color: #0056b3; /* Темніший колір при наведенні */
}
@media (min-width: 600px) {
  .form-group {
    flex: 1 1 48%;
  }

  .toggle-search {
    display: none; /* Сховати кнопку на екранах більших ніж 600px */
  }

  .search-form {
    display: block; /* Показати бокс пошуку на більших екранах */
  }
}

.form-group label {
  display: block;
  margin-bottom: 3px;
  font-weight: bold;
}

input,
select {
  width: 100%;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input:focus,
select:focus {
  border-color: #007bff;
  outline: none;
}

.search-button {
  width: 100%;
  padding: 8px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-button:hover {
  background-color: #0056b3;
}
</style>

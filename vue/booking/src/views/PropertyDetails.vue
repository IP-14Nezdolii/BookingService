<template>
  <div class="property-details">
    <button @click="addToFavorites" class="btn btn-favorite" :disabled="isFavorite">
      <span v-if="isFavorite">❤ Вже в обраному</span>
      <span v-else>❤</span>
    </button>
    <h1 class="property-title">{{ property.name }}</h1>
    <div class="property-content">
      <div class="info">
        <p class="property-description">{{ property.description }}</p>
        <p class="price">{{ property.price }} ₴ за ніч</p>
        <p class="property-phone">Контактний номер телефону: {{ property.phone }}</p>

        <router-link :to="`/properties/${property.id}/reviews`" class="btn btn-secondary">
          <span>Переглянути відгуки</span>
        </router-link>
        <router-link :to="`/properties/${property.id}/book`" class="btn btn-primary">
          Забронювати цю квартиру
        </router-link>

        <router-link to="/" class="back-link">
          <span>Назад до списку</span>
        </router-link>
      </div>

      <div class="property-image-container">
        <img :src="property.image" alt="Property Image" class="property-image" />
      </div>
    </div>

    <h3>Місцезнаходження</h3>
    <div id="map" class="map-container">
      <iframe
          :src="getMapEmbedUrl(property.address)"
          width="600"
          height="450"
          style="border:0;"
          allowfullscreen
          loading="lazy"
          referrerpolicy="no-referrer-when-downgrade">
      </iframe>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PropertyDetails',
  data() {
    return {
      property: {},
      isFavorite: false,
    };
  },
  created() {
    this.fetchPropertyDetails(); // Отримуємо деталі квартири при створенні компонента
  },
  methods: {
    async fetchPropertyDetails() {
      const propertyId = this.$route.params.id; // Отримуємо ID з маршруту
      try {
        const response = await axios.get(`/api/apartments/${propertyId}`); // Запит до бекенду
        this.property = response.data; // Зберігаємо дані про квартиру
        this.checkIfFavorite(); // Перевіряємо, чи квартира в обраному
      } catch (error) {
        console.error('Не вдалося отримати деталі квартири:', error);
      }
    },
    async addToFavorites() {
      try {
        await axios.post('/api/favorites', { propertyId: this.property.id }); // Додаємо квартиру до обраного
        this.isFavorite = true;
        alert('Додано до обраного!');
      } catch (error) {
        console.error('Не вдалося додати до обраного:', error);
      }
    },
    async checkIfFavorite() {
      try {
        const response = await axios.get(`/api/favorites/check/${this.property.id}`); // Перевіряємо, чи в обраному
        this.isFavorite = response.data.isFavorite; // Встановлюємо статус
      } catch (error) {
        console.error('Не вдалося перевірити обране:', error);
      }
    },
    getMapEmbedUrl(address) {
      const formattedAddress = encodeURIComponent(address);
      return `https://www.google.com/maps/embed/v1/place?key=YOUR_API_KEY&q=${formattedAddress}`;
    },
  },
};
</script>

<style scoped>
.property-details {
  padding: 40px;
  background: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  max-width: 1200px;
  margin: 20px auto;
}

.property-title {
  font-size: 2.5em;
  color: #343a40;
  text-transform: uppercase;
  margin-bottom: 20px;
  text-align: center;
}

.property-content {
  display: flex;
  gap: 30px;
}

.info {
  flex: 1;
  max-width: 600px; /* Максимальна ширина для інформації про квартиру */
}

.property-image-container {
  flex: 1;
  display: flex;
  justify-content: flex-end; /* Вирівнюємо зображення вправо */
}

.property-image {
  width: 100%;
  height: auto;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.property-description {
  font-size: 1.1em;
  line-height: 1.6;
  margin: 15px 0;
  color: #555;
}

.property-content {
  position: relative; /* Встановлюємо відносне позиціонування для контейнера */
}

.price {
  position: absolute;
  top: -20px; /* Піднімаємо ще вище */
  right: 0;
  font-size: 1.5em;
  color: #fff;
  font-weight: bold;
  margin: 0; /* Прибираємо додатковий відступ */
  padding: 5px 10px; /* Можна додати трохи відступу всередині */
  background-color: #007bff; /* Бажано додати фон для ціни, щоб вона не зливалась */
  border-radius: 8px; /* Закруглені краї для фону */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Додаємо тінь */
}


.property-phone {
  font-size: 1.1em;
  color: #007bff;
  margin: 10px 0;
}

.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
  font-weight: bold;
  display: block;
  margin-bottom: 10px; /* Відступ між кнопками */
}

.btn-favorite {
  background-color: #6c757d;
  color: white;
}

.btn-favorite:hover {
  background-color: #ff4757;
  transform: scale(1.05);
}

.btn-primary {
  background-color: #007bff;
  color: white;
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px; /* Внутрішній відступ */

}

.btn-primary:hover {
  background-color: #0056b3;
  transform: scale(1.05);
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px; /* Внутрішній відступ */
}

.btn-secondary:hover {
  background-color: #0056b3;
  transform: scale(1.05);
}

.map-container {
  width: 100%;
  height: 500px;
  margin-top: 20px;
  border-radius: 12px;
  overflow: hidden;
  background-color: #e9ecef;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2em;
  color: #6c757d;
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

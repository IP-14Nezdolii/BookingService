<template>
  <div class="add-apartment">
    <h2>Додати Апартамент</h2>
    <form @submit.prevent="addApartment">
      <div class="form-row">
        <div class="form-group">
          <label for="name">Назва:</label>
          <input type="text" v-model="name" required placeholder="Введіть назву апартаменту" />
        </div>
        <div class="form-group">
          <label for="price">Ціна:</label>
          <input type="number" v-model="price" required placeholder="Введіть ціну" />
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label for="description">Опис:</label>
          <textarea v-model="description" required placeholder="Напишіть опис апартаменту"></textarea>
        </div>
        <div class="form-group">
          <label for="street">Вулиця:</label>
          <input type="text" v-model="street" required placeholder="Введіть назву вулиці" />
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label for="phone">Контактний номер телефону:</label>
          <input type="tel" v-model="phone" required placeholder="Введіть контактний номер телефону" />
        </div>
        <div class="form-group">
          <label for="images">Зображення:</label>
          <input type="file" @change="handleFileUpload" multiple accept="image/*" />
          <div class="image-preview" v-if="images.length">
            <h4>Попередній перегляд:</h4>
            <img v-for="(img, index) in images" :key="index" :src="img" class="image-preview-item" />
          </div>
        </div>
      </div>
      <button type="submit" class="submit-button">Додати Апартамент</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
    </form>

    <div id="map" class="map" v-if="map"></div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      name: '',
      description: '',
      price: '',
      street: '',
      phone: '',
      images: [],
      errorMessage: '',
      successMessage: '',
      map: null,
    };
  },
  methods: {
    handleFileUpload(event) {
      const files = event.target.files;
      this.images = [];
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.images.push(e.target.result);
        };
        reader.readAsDataURL(files[i]);
      }
    },
    async addApartment() {
      const formData = new FormData();
      formData.append('name', this.name);
      formData.append('description', this.description);
      formData.append('price', this.price);
      formData.append('street', this.street);
      formData.append('phone', this.phone);
      this.images.forEach((image) => {
        formData.append('images', image);
      });

      try {
        const response = await axios.post('/api/apartments', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
        this.$emit('apartment-added', response.data);
        this.successMessage = 'Апартамент успішно додано!';
        this.resetForm();
        this.initMap(); // ініціалізуємо карту після додавання
      } catch (error) {
        this.errorMessage = 'Не вдалося додати апартамент. Спробуйте ще раз.';
      }
    },
    resetForm() {
      this.name = '';
      this.description = '';
      this.price = '';
      this.street = '';
      this.phone = '';
      this.images = [];
      this.errorMessage = '';
      this.successMessage = '';
      this.map = null; // очищаємо карту
    },
    initMap() {
      const geocoder = new google.maps.Geocoder();
      geocoder.geocode({ address: this.street }, (results, status) => {
        if (status === 'OK') {
          const location = results[0].geometry.location;
          this.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15,
            center: location,
          });
          new google.maps.Marker({
            position: location,
            map: this.map,
          });
        } else {
          console.error('Geocode was not successful for the following reason: ' + status);
        }
      });
    },
  },
};
</script>

<style scoped>
.add-apartment {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: auto;
}

h2 {
  text-align: center;
  color: #343a40;
}

.form-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-bottom: 15px;
}

.form-group {
  flex: 1 1 calc(50% - 10px);
  margin-right: 10px;
}

.form-group:last-child {
  margin-right: 0;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type='text'],
input[type='number'],
input[type='tel'],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ced4da;
  border-radius: 5px;
}

textarea {
  resize: none;
  height: 100px;
}

.submit-button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  text-align: center;
}

.success {
  color: green;
  text-align: center;
}

.image-preview {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.image-preview-item {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin: 5px;
}
</style>

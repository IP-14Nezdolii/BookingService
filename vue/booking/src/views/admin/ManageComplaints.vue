<template>
  <div class="manage-complaints">
    <h2>Управління Скаргами</h2>

    <div v-if="complaints.length > 0">
      <ul class="complaints-list">
        <li v-for="complaint in complaints" :key="complaint.id" class="complaint-item">
          <strong>Ім'я:</strong> {{ complaint.name }}<br />
          <strong>Електронна пошта:</strong> {{ complaint.email }}<br />
          <strong>Скарга:</strong> {{ complaint.message }}<br />
          <strong>Статус:</strong> <span :class="{'resolved': complaint.resolved}">{{ complaint.resolved ? 'Вирішена' : 'Не вирішена' }}</span><br />
          <div>
            <textarea v-model="response[complaint.id]" placeholder="Напишіть відповідь"></textarea>
            <button @click="submitResponse(complaint.id)" class="btn">Надіслати відповідь</button>
          </div>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Немає скарг для управління.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ManageComplaints',
  data() {
    return {
      complaints: [], // Масив для зберігання скарг
      response: {}, // Об'єкт для зберігання відповідей на скарги
    };
  },
  created() {
    this.fetchComplaints(); // Завантажити скарги при створенні компонента
  },
  methods: {
    async fetchComplaints() {
      try {
        const response = await axios.get('/api/complaints'); // Отримати скарги з бекенду
        this.complaints = response.data; // Оновити масив скарг
      } catch (error) {
        console.error('Не вдалося отримати скарги:', error);
      }
    },
    async submitResponse(id) {
      const responseData = {
        complaintId: id,
        response: this.response[id],
      };

      try {
        await axios.post('/api/complaints/response', responseData); // Надіслати відповідь на скаргу
        this.updateComplaintStatus(id); // Позначити скаргу як вирішену
        this.response[id] = ''; // Очистити поле відповіді
      } catch (error) {
        console.error('Не вдалося надіслати відповідь:', error);
      }
    },
    async updateComplaintStatus(id) {
      try {
        await axios.patch(`/api/complaints/${id}/resolve`); // Надіслати запит для оновлення статусу
        const complaint = this.complaints.find(c => c.id === id);
        if (complaint) {
          complaint.resolved = true; // Оновити статус у локальному масиві
        }
      } catch (error) {
        console.error('Не вдалося оновити статус скарги:', error);
      }
    },
  },
};
</script>

<style scoped>
.manage-complaints {
  text-align: center;
  padding: 20px;
}

.complaints-list {
  list-style: none;
  padding: 0;
}

.complaint-item {
  border: 1px solid #ccc;
  padding: 15px;
  margin: 10px 0;
  border-radius: 8px;
}

.resolved {
  color: green;
  font-weight: bold;
}

.btn {
  margin: 5px;
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: white;
  background-color: #4c8bf5;
}
</style>

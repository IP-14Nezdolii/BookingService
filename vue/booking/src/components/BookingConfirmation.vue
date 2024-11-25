<template>
  <div class="booking-confirmation">
    <h1>Підтвердження бронювання</h1>
    <p>Дякуємо за бронювання! Ваше бронювання підтверджено.</p>
    <router-link to="/">Повернутися на головну</router-link>
  </div>
</template>

<script>
export default {
  name: 'BookingConfirmation',
};
</script>

<style scoped>
.booking-confirmation {
  padding: 20px;
  text-align: center;
}
</style>

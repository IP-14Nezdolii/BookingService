<template>
  <div class="admin-profile">
    <h1>Адміністративна панель</h1>
    <router-link to="/admin/manage-listings" class="btn">Управління оголошеннями</router-link>
    <router-link to="/admin/manage-reviews" class="btn">Управління відгуками</router-link>
    <router-link to="/admin/manage-complaints" class="btn">Управління скаргами</router-link>
    <router-link to="/admin/manage-profiles" class="btn">Управління профілями</router-link>
  </div>
</template>

<script>
export default {
  name: 'AdminProfile',
};
</script>

<style scoped>
.admin-profile {
  text-align: center;
  padding: 20px;
}

.btn {
  display: block;
  margin: 10px auto;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  text-decoration: none;
  text-align: center;
  width: 200px;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #0056b3;
}
</style>



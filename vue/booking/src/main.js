import {createApp} from 'vue';
import {createRouter, createWebHistory} from 'vue-router';
import App from './App.vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap';
import './assets/main.css';
import './assets/admin.css';


import HomePage from './views/Home.vue';
import PropertyDetails from './views/PropertyDetails.vue';
import UserProfile from './views/UserProfile.vue';
import BookingConfirmation from './components/BookingConfirmation.vue';
import AuthForm from './components/AuthForm.vue';
import Register from './components/Register.vue';
import AddApartment from './components/AddApartment.vue';
import AdminProfile from './views/admin/AdminProfile.vue';
import ManageProperties from './views/admin/ManageProperties.vue';
import ManageReviews from './views/admin/ManageReviews.vue';
import ManageComplaints from './views/admin/ManageComplaints.vue';
import AdminLogin from './views/admin/AdminLogin.vue';
import Reviews from './views/Reviews.vue';
import AddReview from "./views/AddReview.vue";
import ContactSupport from "./components/ContactSupport.vue";
import Booking from './components/Booking.vue';
import Favorites from './views/Favorites.vue';
import ManageProfiles from './views/admin/ManageProfiles.vue';
import EditProfile from "./views/EditProfile.vue";

const routes = [
    {path: '/admin/login', component: AdminLogin},
    {path: '/admin-profile', component: AdminProfile},
    {path: '/admin/manage-listings', component: ManageProperties},
    {path: '/admin/manage-reviews', component: ManageReviews},
    {path: '/admin/manage-complaints', component: ManageComplaints},
    {path: '/', component: HomePage},
    {path: '/property-details/:id', component: PropertyDetails},
    {path: '/user-profile', component: UserProfile},
    {path: '/booking-confirmation', component: BookingConfirmation},
    {path: '/login', component: AuthForm},
    {path: '/register', component: Register},
    {path: '/edit-profile', component: EditProfile},
    {path: '/add-apartment', component: AddApartment},
    {
        path: '/properties/:id/reviews',
        name: 'Reviews',
        component: Reviews,
    },
    {path: '/add-review', component: AddReview},
    {path: '/contact-support', component: ContactSupport},
    {
        path: '/properties/:id/book',
        name: 'Booking',
        component: Booking,
    },
    {
        path: '/favorites',
        name: 'Favorites',
        component: Favorites
    },
    {
        path: '/admin/manage-profiles',
        name: 'ManageProfiles',
        component: ManageProfiles,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

const app = createApp(App);
app.use(router);
app.mount('#app');

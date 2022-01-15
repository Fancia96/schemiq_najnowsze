import Dashboard from "@/pages/dashboard";
import CreateAccount from "@/pages/create_account";
import ProfileSettings from "@/pages/profile_settings";
import Settings from "@/pages/settings";
import Login from "@/pages/login";
import Friends from "@/pages/friends";
import Activity from "@/pages/activity";

const routes = [
    { path: '/dashboard', component: Dashboard },
    { path: '/create_account', component: CreateAccount },
    { path: '/profile_settings', component: ProfileSettings },
    { path: '/settings', component: Settings },
    { path: '/login', component: Login},
    { path: '/friends', component: Friends},
    { path: '/activity', component: Activity},
]

export default routes;
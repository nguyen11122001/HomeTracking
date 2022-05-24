package com.example.hometracking

sealed class NavigationItem(var route : String, var icon : Int, var title : String){
    object  Home: NavigationItem("Home", R.drawable.ic_baseline_home_24,"Home")
    object  History: NavigationItem("History", R.drawable.ic_baseline_insert_drive_file_24,"History")
    object  Notification: NavigationItem("Notification", R.drawable.ic_baseline_notifications_24,"Notification")
    object  Account: NavigationItem("Account", R.drawable.ic_baseline_person_24,"Tip")
}

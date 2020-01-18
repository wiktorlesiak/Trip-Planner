# Trip-Planner

## GPS based app for cycling

The app calculates the lost calories over the distance, and the amount of calorie that will be burned during the exercise and shows the nutrition’s that are needed to provide the body with enough energy to get through the distance that was set as goal.

The database holds information like users age, weight, height, username, password, starting point and destination. Database will collect data from GPS: distance, time. This will allow for daily recommended daily calorie intake, and calories that will be burned during the trip.

### Splash
The first activity that you notice when entering the app will be the splash screen. Splash screen is a graphical control element consisting of a window with an image (logo). It creates more professional look to the app. Splash screens typically serve to enhance the look and feel of an application or web site, hence they are often visually appealing.
<img src="https://i.imgur.com/4QjQIww.png">

### Sign Up
The second activity that we notice after running the app will be Sign Up screen called MainAcitivity this activity uses Firebase Authenticated for signing up options.
<img src="https://i.imgur.com/Q9pJ0Iu.png">

### Login
This activity prompts user to enter email and password they given when creating their account.
<img src="https://i.imgur.com/rAlR5hC.png">

### Profile
In User profile you can change email, change password, reset password, remove user and sign out. Layout of this activity is based on CoordinatiorLayout.
<img src="https://i.imgur.com/3ttitMi.png">

### Home
Home activity is the first activity that displays after you create account. It displays user data from firebase database that was given during registration. In this activity you can select the amount to exercise you do weekly and based this information and given details during registration user will be able to discover how much calorie intake they need daily.
<img src="https://i.imgur.com/uygM74W.png">

Using the Drawer Layout, we can access more options leaving more space on the screen.
<img src="https://i.imgur.com/bETteVQ.png">

### Map Activity
Map Activity shows user location and allows user to select two points on the map and calculate distance between them. To connect to Google Maps API, we had to create API key 
When we created the key, we had to provide the key into the values/google_maps_api.xml file so that the application could display Google Maps API. We needed app permission to use location finding. User location is marked by pink marker.
<img src="https://i.imgur.com/CgQNXWu.png">

To use the distance between two location we needed to acquire point a “ORIGIN” and point b “DESTINATION”. Code below displays how the markers are created
<img src="https://i.imgur.com/EeXS7xZ.png">

### Summary Activity
Summary activity take data from both Firebase Database and Home Activity. In this activity user can select speed they would like to bicycle which is calculated into METS e.g. 16-19 km/h is equivalent to 3 METS. Users also enter the duration which is speed that they think they can cycle the route in minutes. 
<img src="https://i.imgur.com/ARW4y92.png">
### Calories Burned
In this activity we use formula mentioned in section 4.2 based on the given data user can calculate how much calories he has burned and how many more he can take during the day.
All the main code is located below.
<img src="https://i.imgur.com/FYi4mtk.png">



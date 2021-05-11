# Proximity_task
This projects includes MVVM architecture with hilt as DI and android navigation.
Main Activity is the base and single activity inside the app.
AqiListFragment shows list of city with respective Aqi valur. List is getting updated from AqiViewModel(method - fetchAqiData) which gets the data from AqiRepository class.
AqiRepository consists web socket server implemetation which supplies data to viewModel.
CityWiseAqiFragment is the second fragment which will get open after pressing on any city from AqiListFragment.
CityWiseAqiFragment implemets graph chart of live aqi data of selecterd city.
Apk file is included. 

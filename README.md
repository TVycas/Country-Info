# Country-Info

An Android app for searching and displaying information about countries.

The users of this app can search for a country that they want to know
more about in a list of countries. If the users press on one of the
countries, a new window will appear with additional information about
that country as well as a Google map, showing where the country is.

The app uses [Retrofit](https://square.github.io/retrofit/) client
together with [Gson](https://github.com/google/gson) to make calls to
two different RESTful API services -
[REST countries](https://restcountries.eu/) and
[Nominatim](https://nominatim.openstreetmap.org/) - to gather
information about various countries. It keeps this information using a
Room database and uses LiveData objects to display it to the user.
Additionally, the app uses Hilt for dependency injection and was built
following the MVVM architecture pattern.

## Made with:
* Retrofit
* Google Maps API
* Room Database
* Hilt Dependency Injection
* LiveData Objects
* Recycler Views
* Model-View-ViewModel Architecture Pattern


### Screenshots

  <p align="left">
    <img src="../assets/ListView.jpg" alt="search" width="200" style="padding-left: 10px"/>
    <img src="../assets/search.jpg" alt="search" width="200" style="padding-left: 10px"/>
    <img src="../assets/UKInfo.jpg" alt="search" width="200" style="padding-left: 10px"/>
    <img src="../assets/AndorraInfo.jpg" alt="search" width="200" style="padding-left: 10px"/>
  </p>

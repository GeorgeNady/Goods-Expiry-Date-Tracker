# Goods Expiry Date Tracker

## IDE information
### this project was built with
* Android studio / IDE version: [ Android Studio Arctic Fox | 2020.3.1 | Patch 2 ]
* Android Build Gradle tool version: 7.0.2 _with a distribution Url_ : `https\://services.gradle.org/distributions/gradle-7.0.2-bin.zip`
* kotlin stable version: `1.5.20` 

## dependencies used

* Coroutine
* RxJava -- never used in this project
* Room database -- as a ORM for SQLite for handing local database
* Retrofit -- as a network layer for handling Remote Data Source
* Dagger & Dagger Hilt -- as a dependency injection for inject database and nome other staff into
  specific components/Activities/ Fragments
* ViewModel -- as an architecture components that uses to implement MVVM architecture pattern for
  handling application lifecycle
* Navigation Component -- for handling transaction between Fragments
* Gson -- as json converter factory
* Code Scanner -- for scanning [ BarCode, QrCode ]
* SDP -- for handling responsive ui diminutions

## How To Get Started

* first: I have been used `https://mockapi.io` for createing a simulation for the api 
* second: you can find below the postman collection to the mockup apis that used to simulate the Goods
  data in some store repository you can:
  ```
  https://www.getpostman.com/collections/29fc332f2bf204aad384
  ```


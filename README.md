# Goods Expiry Date Tracker

## IDE information
### this project was built with
* Android studio / IDE version: `Android Studio Arctic Fox | 2020.3.1 | Patch 2`
* Android Build Gradle tool version: 7.0.2 _with a distribution Url_ : `https\://services.gradle.org/distributions/gradle-7.0.2-bin.zip`
* kotlin stable version: `1.5.20` 
---

<h1 align="center">Dependencies used</h1>

* `Coroutine`
* `RxJava` -- never used in this project
* `Room database` -- as a ORM for SQLite for handing local database
* `Retrofit` -- as a network layer for handling Remote Data Source
* `Dagger & Dagger Hilt` -- as a dependency injection for inject database and nome other staff into
  specific components/Activities/ Fragments
* `ViewModel` -- as an architecture components that uses to implement MVVM architecture pattern for
  handling application lifecycle
* `Navigation Component` -- for handling transaction between Fragments
* `Gson` -- as json converter factory
* `Code Scanner` -- for scanning [ BarCode, QrCode ]
* `SDP` -- for handling responsive ui diminutions
---

<h1 align="center">How To Get Started</h1>

* first: I have been used `https://mockapi.io` for createing a simulation for the api 

* you can find below the postman collection to the mockup apis that used to simulate the Goods
  data in some store repository you can:
  ```
  https://www.getpostman.com/collections/29fc332f2bf204aad384
  ```
* or you can hit this api below to get all items that stored in the fake repo:
  ```
  https://61eb3ca87ec58900177cdbe1.mockapi.io/api/v1/repository/commidity
  ```
  
* you can use this link to generate a BarCode that hold the __item id__
  ```
  https://barcode.tec-it.com/en
  ```

* then go to __BarCodeScanner__ screen to read it samply to add that new item into a local database 
---
<h1 align="center">Fake Data</h1>

I created a python script to generate some random items with a post request to the mockup api server
you can find it out from this link 
```
https://github.com/GeorgeNady/Goods-Expiry-Date-Tracker-Fake-Data-Generator
```


---

<h1 align="center">Database test</h1>

![Screenshot 2022-01-23 234051](https://user-images.githubusercontent.com/29967846/150698992-6d53c576-6f62-4c2a-a68a-5d83341eb650.png)



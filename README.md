
# Marvel API Test application:

Application to test the Marvel API, and test different libraries like the Jetpack Component suite.
Architecture approach inspired by DDD and CLEAN concepts.


## Screens 📱
The app currently consists of 2 screens:
- A list of characters screen, in which once the user enters de app, they can visualize a list of Marvel characters taken from the API, and select one.
- A character detail screen, in which after selecting one character from the list, the user can visualize detailed information from said character, like their name, description(if there is), comics or series.

## Libraries 🛠️:
 - MVVM
 - Flow
 - Dagger
 - Ktlint
 - Android Jetpack
 - Moshi
 - Retrofit

## Structure 🎨

- __Data Layer__: Contains the repositories Implementations and one or multiple Data Sources.
  - __Datasource__: In which we have the source of the data we are going to work with, let it be the API implementation and abstraction, and/or the database. In this case, we have the API calls.
   - __Repositories__: Repositories are responsible to coordinate data from the different Data Sources. A sort of abstraction for the data sources in order to avoid working directly with them. We make calls to them and we can ignore whether the data comes from the network or a local database.
- __DI__: the dependency injector package, where the modules and components are created.
- __Domain__: Collection of entity objects and related business logic that is designed to represent the enterprise business model.
  - __Models__: an abstraction of the objects that represent the logic of the project.
  -  __Use cases__: the interactors that define be the business logic of the application.
 - __UI__: with an MVVM pattern, everything is separated as features, the screens and logic behind them are found here.
- __Utils__: A variety of classes, extensions, and helpers to help and use across the application, that not necessarily have anything to do with the logic of the same.

## Testing 🧰
#### (There should totally be more tests, but because of the type of project, I haven't tested every part of the application. I decided to test the repositories and two use-cases since I felt it was representative enough. At least for the time being.)
- JUnit
- Mockito-Kotlin
- Kluent3


## Marvel API  Setup ![Marvel](https://cdn2.iconfinder.com/data/icons/avengers-filled/48/12_-_Spiderman_-_infinity_war_-_end_game_-_marvel_-_avengers_-_super_hero-32.png)

You'll need to create your own API keys on [Marvel's developer site](https://developer.marvel.com/documentation/getting_started), check more information on their website.

The API keys should be in the apikey.properties file in the root folder, you'll need to add the values accordingly.

        MARVEL_API_BASE_URL="https://gateway.marvel.com"
        MARVEL_API_PUBLIC_KEY="your public api"
        MARVEL_API_TS="MarvelApp"
        MARVEL_API_HASH="your md5(ts+privateKey+publicKey) hash"



## Screenshots

![list_characters](https://github.com/FelipeGDC/Marvel-Characters/blob/master/images/characters-list.png)
![character_detail](https://github.com/FelipeGDC/Marvel-Characters/blob/master/images/character-detail.png)



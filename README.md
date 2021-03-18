
# Marvel API Test application:

Application to test the Marvel API, and test different libraries like the Jetpack Component suite.
Architecture approach inspired by DDD and CLEAN concepts.

## Search functionality 🔍
Currently as it is, the API presents some limitations regarding the searching of characters (or anything actually). The service receives the name of the character through a Get parameter, but it needs to be the exact name, otherwise it will not return any results.
This presents us with the issues of if I wanted to search for the character of "Ms. Marvel (Kamala Khan)", I could not write Marvel, or Kamala and expect any results, it would have to be the entire name, which makes the whole interaction rather cumbersome.

Before this, I attempted to implement a search using my current "endless scrolling" functionality. Since is rather easy to filter through the elements already loaded into the adapter list, I thought of filtering for results, if I did not get any, I would load more characters through the service and add them to the current list, and search again.
This worked, but it was not particularly functional nor practical, for searching characters with the letter 'H', it would need to load all the characters before, and it would take a while. With a character starting with 'Z' it would be too long and unusable, nor it was easy to communicate what was happening.
For this reason, I decided to proceed with the first approach eve if the API was limiting, for the time being it felt like the most sensible approach.


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

        MARVEL_API_BASE_URL="hthttps://gateway.marvel.com/v1/public/"
        MARVEL_API_PUBLIC_KEY="your public api"
        MARVEL_API_TS="MarvelApp"
        MARVEL_API_HASH="your md5(ts+privateKey+publicKey) hash"



## Screenshots

![list_characters](https://github.com/FelipeGDC/Marvel-Characters/blob/master/images/characters-list.png)
![character_detail](https://github.com/FelipeGDC/Marvel-Characters/blob/master/images/character-detail.png)



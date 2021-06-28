# SkyBet

Please find attached my SkyBet project.

I did consider using JetPack compose for this project but I did not want to cause any delays if the developer did not have the correct Android Studio configuration. I also figured that SkyBet's Android code base is probably not written with JetPack Compose anyway so I’d be better off demonstrating my ability with XML.

I used Hilt for dependency injection which uses Dagger to provide a DI solution with no boilerplate code. Hilt enabled the app to create a single instance of the Repository class which was injected into the activity viewModel. The Repo's initialisation includes creating the NetworkService which can be a costly computational process, so doing it only once is beneficial. In a larger solution we may use the Repo in multiple viewModels, initialising it just once is much more efficient.

For the network service I made use of Retrofit which enabled the app to make network calls to my backend server that generated the random race data. An optimal solution might make use of a local storage service (possibly using the Room library) to cache the incoming race data and display it when the network data is unavailable. 

I used LiveData objects within the viewModels which enabled the RaceListFragment to listen out for changes to the races data as well as errors that might have occurred. The single Race object is also wrapped in a LiveData class so whenever a user selects a Race from the list, the single RaceFragment is updated straight away.

I used a material design theme in the app to provide a consistent and clean design.

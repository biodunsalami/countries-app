
## Countries App
Countries is an android app that showcases a list of the countries of the world.\  
The app presents users with useful information about the different countries of the world.

### App Description
When the app is launched, the user is met with a explore screen that shows an alphabetically sorted list of names of countries of the world including their flags and capitals.  
This is the main screen of the app and it contains most of the features of the app with includes the following:
1. A dark mode and light mode switch
2. A search field for users to search through the list of countries
3. A button that launches a list of languages for user to pick from
4. A filter button the presents user with parameter to query the list

Clicking on a country on the list navigates user to a details screen where user can find pictures of the flag and coat of arms of the selected country as well as other key information


### App Structure
The current structure is the codebase is quite simplistic in approach, yet open ended for the addition of more feature.  
**Models** - models consist of data classes that holds required fields   
**Adapter** - contains a base Recycler Adapter to bind various datasets to the different Recycler Views   
**Data** - consists of a Data object that is used as the data source
**Presentation** - the app uses a single activity with multiple fragments approach; all of which are contained in the Presentation package




### Design
The design showcases necessary information an a consise and user friendly way  
[Design Link](https://www.figma.com/file/v9AXj4VZNnx26fTthrPbhX/Explore?node-id=0%3A1&t=fDKQ7SGIU6MmYylU-0)




### Libraries
1. Android Jetpack Navigation Components was used to facilitate navigation
2. ViewPage2 was used to display sliding images
3. Circle indicator by Mikhael Lopez was used to show indication for image slider
4. Retrofit & Moshi were used to process network calls
5. Coil was used to load images from the api

### Possible feature
The search function should be better

### Apk Link
[Apk Link](https://drive.google.com/file/d/13xKOGA2kgFnJywZwByZX0Fzw6r6110b4/view?usp=sharing)


### Challenges
The major challenge was having to learn and implement many new things  in the given time.



### Appetize Link
[Appetize Link](https://appetize.io/app/2tlinvfptnp2d3gzaenbewskha)
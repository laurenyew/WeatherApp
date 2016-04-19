# WeatherApp
Android App that pulls weather data from Weather Services using zipcode user input

## Goals:
- Create a list­based application that allows the user to fetch weather information from a weather services API of your choice. 
- The user should be able to add/remove zip codes from the list. 
- Focus effort on the weather services API interface.

## Requirements:
­ [ ] Create a list­based application where the list should be pre­populated with at least 3 entries, where each entry represents a different US zip code.
­ [ ] 'Add' button that allows the user to add additional zip codes into the list which fetches weather information from a weather service API.
­ [ ] When adding a zip code, the user should be presented with a 'Details’ page containing weather information for the zip code.
­ [ ] Tapping on a zip code list item will make an API call to a weather service of your choice and display the weather data in a detail activity. Ideally, the weather results are cached.
­ [ ] The user should be able to navigate back to the zip code list and select another zip and display the 'Details' page for it.

## Evaluation Points
- [ ]Code/project organization
- [ ] Android fundamentals
- [ ] Android API design/best practices
- [ ] Error handling (connection, weather service down, etc.)
- [ ] Error validation (user input)
- [ ] API Error handling (API gives errors)
- [ ] Presentation

## Presentation Requirements:
- [ ] Summary detailing project requirements
­ [ ] Detail challenges experienced
­ [ ] Walk through code implementation
­ [ ] Detail improvements you would make if you had additional time.

## Third-Party Libraries Used
- [ ] Retrofit
- [ ] Jackson JSON Processor
- [ ] GSON
- [ ] Picasso

## Weather Services APIs used
- [ ] Weather Underground: http://www.wunderground.com/weather/api/
- [ ] Dark Sky: https://developer.forecast.io/docs
- [ ] Open Weather Map: http://openweathermap.org/api

## General Screens
- [ ] List View with Zipcode list items
- [ ] Floating Action button to add a new list item (zipcode)
- [ ] Add New ZipCode Screen/dialog
- [ ] Error dialog when add bad zipcode, returns to Add New Zipcode screen/dialog
- [ ] Detail Zipcode weather screen (added zipcode)
- [ ] Can navigate back to list 

NOTE: When Zipcode Weather Info is pulled down, they should be cached.

## QUESTIONS
- [ ] Since weather info details are cached, should we still expect an API call to happen when we exit and re-enter the same zipcode detail?
- [ ] The requirement "When adding a zip code, the user should be presented with a 'Details’ page" --> does this mean after the zipcode is added, or while its being added?

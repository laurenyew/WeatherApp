# WeatherApp
Android App that pulls weather data from Weather Services using zipcode user input

## Goals:
- Create a list­based application that allows the user to fetch weather information from a weather services API of your choice. 
- The user should be able to add/remove zip codes from the list. 
- Focus effort on the weather services API interface.

## Requirements:
- [ ] Create a list with at least 3 entries prepopulated
- [ ] where each entry represents a different US zip code.
- [ ] 'Add' button to add additional zip codes into the list
- [ ] When adding a zip code, the user should be presented with 'Details’ page 
- [ ] Details page should contain weather information for the zip code.
- [ ] Tapping on a zip code list item will make an API call to a weather service
- [ ] List detail will display the weather data in a detail activity. 
- [ ] The weather results are cached.
- [ ] The user should be able to navigate back to the zip code list
- [ ] NOTE TO SELF: The list should be stored in the phone's database

## Evaluation Points
- [ ] Code/project organization
- [ ] Android fundamentals
- [ ] Android API design/best practices
- [ ] Error handling (connection, weather service down, etc.)
- [ ] Error validation (user input: bad zipcode, duplicate zipcode)
- [ ] API Error handling (API gives errors)
- [ ] Presentation

## Presentation Requirements:
- [ ] Summary detailing project requirements
­ [ ] Detail challenges experienced
­ [ ] Walk through code implementation
­ [ ] Detail improvements you would make if you had additional time.

## Third-Party Libraries Used
- [x] Volley
- [x] GSON
- [x] Picasso

## Weather Services APIs used
- [x] Weather Underground: http://www.wunderground.com/weather/api/
	- Had the best documentation
	- Most widely used
	- Includes icons for showing weather
	- DarkSky had limited information
	- Github examples for OpenWeatherApp no longer available

## General Screens
- [x] List View with Zipcode list items
- [x] Floating Action button to add a new list item (zipcode)
- [ ] Add New ZipCode Screen/dialog
- [ ] Error dialog when add bad zipcode, returns to Add New Zipcode screen/dialog
- [x] Detail Zipcode weather screen (added zipcode)
- [x] Can navigate back to list 

NOTE: When Zipcode Weather Info is pulled down, they should be cached.

## QUESTIONS
- [ ] Since weather info details are cached, should we still expect an API call to happen when we exit and re-enter the same zipcode detail?
- [ ] The requirement "When adding a zip code, the user should be presented with a 'Details’ page" --> does this mean after the zipcode is added, or while its being added?
- [ ] Requirements mention 'sharing intents'. Not sure where this will be used. When would I need to share content?

## Acknowledgements
Original requirements for this project provided by Phunware, Inc.

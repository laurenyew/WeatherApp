# WeatherApp
Android App that pulls weather data from Weather Services using zipcode user input

## Goals:
- Create a list­based application that allows the user to fetch weather information from a weather services API of your choice. 
- The user should be able to add/remove zip codes from the list. 
- Focus effort on the weather services API interface.

## Requirements:
- [x] Create a list with at least 3 entries prepopulated
- [x] where each entry represents a different US zip code.
- [x] 'Add' button to add additional zip codes into the list
- [x] When adding a zip code, the user should be presented with 'Details’ page 
- [x] Details page should contain weather information for the zip code.
- [x] Tapping on a zip code list item will make an API call to a weather service
- [x] List detail will display the weather data in a detail activity. 
- [x] The weather results are cached.
- [x] The user should be able to navigate back to the zip code list
- [x] NOTE TO SELF: The list of zipcodes should probably be stored in the phone's database

## Evaluation Points
- [x] Code/project organization
- [x] Android fundamentals
- [x] Android API design/best practices
- [x] Error handling (connection to weather service down, invalid zipcode, etc.)
- [x] Error validation (user input: bad zipcode, duplicate zipcode)
	- Duplicate zipcodes do not produce errors, but do not add duplicate list entries
- [x] API Error handling (API gives errors)
- [ ] Presentation

## Presentation Requirements:
- [ ] Summary detailing project requirements
- [ ] Detail challenges experienced
- [ ] Walk through code implementation
- [ ] Detail improvements you would make if you had additional time.

## Third-Party Libraries Used
- [x] Volley
- [x] Picasso
- [x] Android Support Libraries (RecylcerView, CardView, etc.)

## Weather Services APIs used
- Weather Underground: http://www.wunderground.com/weather/api/
	- Had the best documentation
	- Most widely used
	- Includes icons for showing weather
	- DarkSky had limited information
	- Github examples for OpenWeatherApp no longer available
	- Tried OpenWeatherApp but couldn't get APIKey to work.

## General Screens
- [x] List View with Zipcode list items
- [x] Floating Action button to add a new list item (zipcode)
- [x] Add New ZipCode Screen/dialog
- [x] Error message when add bad zipcode on dialog
- [x] Detail Zipcode weather screen (added zipcode)
- [x] Can navigate back to list 

- [x] NOTE: When Zipcode Weather Info is pulled down, it should be cached.

## Extras
- [x] Clear List option on list screen
- [x] Phunware icon / splash screen
- [x] Zipcode list stored in Shared Preferences for the app, so even if app is closed, the zipcodes stored will remain.
- [x] Eviction time for asking new details = 1 minute (for demo purposes). For practical production cases, it should probably be more like 1 hour or 1 day. Can tell when the cache item has been 'evicted' by when the data reloads in the view on rotate/going back to detail.
- [ ] Toolbar for switching fragments for zipcode weather details
- [ ] 3-5 day forecast
- [ ] Content sharing
- [ ] weather map
- [ ] Swipe between fragments (weather views)

## Acknowledgements
- Original requirements for this project provided by Phunware, Inc.
- Using weather API from Weather Underground, Inc. http://www.wunderground.com
- Phunware icon from : http://i.stack.imgur.com/vZvvQ.png
- Phunware splash logo from : http://cdn2.phunware.com/wp-content/uploads/2015/03/pw_logo_HD.png

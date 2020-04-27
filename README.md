# CSIS 365 Final Project

General idea will be a battle plan app.

## High Level Overview
- User may input coordinates and areas for enemy strongholds
  - These will be shown on the map as circles with markers
- User may input details about companies under their command
  - Specify Infantry/Archers/Cavalry [radio]
  - Specify number [seekbar]
  - Specify if it's lead by a "hero" [checkbox]
- User may "plan an assault" on a stronghold by giving coordinates they place their companies.
  - Must specify a date [datepicker]
  - Add companies with a classic "N number of spinners with a + button to add another"
  - Companies will be drawn as squares on the map
  - The companies will be automatically oriented based on the direction between them and the
    stronghold.
  - The WIP assault is shown in a fragment
    - Seems a good place to use the MVVM architecture
- User may view all assaults on the map at any time
  - Same fragment as the WIP assault preview, only movement is unrestricted

## Requirements and Fulfillments
- [ ] SQLite database
  - [ ] Must have at LEAST 4 fields – do  not simply store login information this time!
  - [ ] Add a record
  - [ ] Delete a record
  - [ ] 3 queries to illustrate retrieving different information from the database
- [ ] RecyclerView
  - [ ] Display a list of items 
  - [ ] Allow user to click on the list to see more detail about an item
- [ ] Fragments
- [ ] Navigation Drawer
- [ ] Use Google maps
  - [ ] Have at least 2 markers
  - [ ] Provide a shape (circle or polygon) to highlight an area on the map
- [ ] Views (all required) – must use APPROPRIATELY!
  - [ ] Image button
  - [ ] Radio buttons
  - [ ] Checkboxes
  - [ ] Date picker
  - [ ] Seekbar – do not simply use this to rate the app!
  - [ ] Spinner
- [ ] Action bar 
- [ ] Options menu
- [ ] Use Styles and a different Theme
- [ ] At least one other feature not covered in class
  - androidx.Room
  - Kotlin
- [ ] Must do one of:
  - [ ] Consume data from a web service using Retrofit - must use a previously unused API
  - [ ] Use MVVM architecture


## SQLite Layout
- TABLE Companies
    - name TEXT
    - leader TEXT
    - ty CHAR -- I, A, C for Infantry, Archers, Cavalry
    - number INT -- How many men we got?
- TABLE Strongholds
    - name TEXT
    - lat DOUBLE -- Where is it?
    - lng DOUBLE
    - number INT -- How many soldiers inside?
- TABLE Assaults
    - id INT AUTOINC
    - which Strongholds.name
    - when DATE
- TABLE Sorties
    - assault Assaults.id -- What assault we a part of?
    - company Companies.name -- Which company is being deployed?
    - lat DOUBLE -- Where are we being positioned around the stronghold?
    - lng DOUBLE

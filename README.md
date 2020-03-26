# CSIS 365 Major Project 2
## Shopping List App

This is my shopping list app, created in Spring 2020 for CSIS 365 at MSUM.

### Minimum Requirements
- [ ] Fragments that communicate with one another
- [ ] Recycler view with clickable elements that provide more detail about the element
  - The shopping list display
- [ ] SQLite database with at least 3 queries
  - Holds the list itself
- [ ] Shared preferences
  - Holds theme
  - Holds default shopping day
- [ ] Bundles
  - Used to pass around what you clicked
- [ ] Radio buttons with radio group
  - Select the category of item
- [ ] Check boxes
  - Whether or not it's on the default shopping day
  - Whether it's a recurring purchase
- [ ] Text, edit text,
  - Item name/desc
- [ ] Image button
  - A better + button than I had in my todo app
- [ ] Date picker
  - Pick default shopping day and custom shopping day for items
- [ ] Spinner
  - Swap between lists
- [ ] Seekbar
  - Quantity (tied with a numeric field)
- [ ] Button
  - Submit, etc. This one's easy.
### Extra Effort
- [ ] Nav drawer on left.
- [ ] Programmatic UI generation in the ContextMenu fragment
- [ ] Notifications
- [ ] Popup windows
Possibilities:
- Network/web apis?
  - Could retrieve images associated with what the user typed for shopping list
### Plan
Backend
- Username
  - Stored in prefs
  - Just used to greet the user
- Shopping date
  - The day the user intends to go shopping each week
  - Stored in prefs
- Multiple lists
  - Has name (Text, prim)
  - Builtin "Main" list may not be removed
  - User may make new ones at will
  - Stored in SQLite
- Many items per list.
  - Has which list it's in (Text, foreign)
  - Has name of item (Text, prim)
  - Has the date this item should be bought by/on or ( Date or NULL for default day)
  - Has whether this item is a recurring purchase (Bool)
  - Has category of the item (Text)
  - Has price
  - Has quantity

Frontend
- User enters the app for the first time and is greeted by a form to input name and shopping day
  - Stored prefs holds whether we've done this already
- There's an app drawer on the (top) left
- Every screen is split into 2 fragments
  - Main frag: The screen itself
  - ContextMenu: A fragment with a grid of buttons depending on the screen you're in
    - Main frag passes what options should be displayed and what should be done for each
    - ContextMenu generates the fragment programmatically
- User goes to Primary screen (View lists)
  - Spinner at top to select which list they're in
  - RecyclerView to display contents of that list
    - Shows item name, price, and quant
    - Clicked: Passes the ListName and ItemName clicked in a bundle to the Detail screen
  - Button to add new lists
    - In ContextMenu
    - Creates a popup window to add a new list
  - Button in bottom right to add new items to this list
    - In the contextMenu
    - Clicked: Passes the ListName 
- Detail screen (View individual items)
  - All the stuff we stored about the item

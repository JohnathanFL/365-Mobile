# Major Project 1
## Johnathan Lee
## MSUM, Spring 2020
## CSIS 365 Mobile App Dev

Activities:
- Calendar view
  - Recycler list in GridView
  - Swipe left/right to change month
  - Swipe up/down to change year
  - Leads to any other view
    - ->Add Task, it defaults the date to that month/year
- Daily TODO view
  - Recycler list in listview
  - Leads to Calendar view
  - Swipe left to delete item
  - -> Add Task view
    - Add Task view starts with the date set to that day (passes data)
- Global TODO view
  - Recycler list in listview
  - Swipe left to delete item
  - Leads to Calendar view
    - Passes data that it was from global view
- Add Task
  - Contains a selector for either global? or a date
  - Leads to Daily or Global view
    - (After task is submitted)

New things not covered in class
- Data is maintained in a global Application state
- Swipe gestures

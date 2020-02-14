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
  - Click to view item -> Single Task View
  - -> Add Task view
    - Add Task view starts with the date set to that day (passes data)
- Single Task View
  - Shows all normal task details + a description
- Add Task
  - Form with
    - Due date
    - Task name
    - Task description
  - Leads to Daily or Global view
    - (After task is submitted)

Daily list of tasks displays
- An imageview saying whether it's done or not (checkbox on/off)
- The name of the task
- The date the task is due

New things not covered in class
- Data is maintained in a global Application state
  - [Application docs](https://developer.android.com/reference/android/app/Application.html)
  - Technically the docs say multiple singletons are better because they're more modular.
    However, I don't feel I have enough data here to justify multiple different singletons.
    - I may try to swap it to a proper singleton later.
- Swipe gestures
  - [Gesture Detector](https://developer.android.com/reference/android/view/GestureDetector?hl=en)
    - Looks like this doesn't quite do what I need?
  - [Motion Event](https://developer.android.com/reference/android/view/MotionEvent.html)
    - I can probably just implement it here with a simple slope detector on dx/dy

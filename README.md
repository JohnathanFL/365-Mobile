# Major Project 1
## Johnathan Lee
## MSUM, Spring 2020
## CSIS 365 Mobile App Dev

Activities:
- Calendar view
  - Uses the calendar widget
  - ->Add Task, it defaults the date to that month/year
  - -> Daily view,selected from the calendar widget]
    - Done through calendar's onSetDate
- Daily TODO view
  - Recycler list in listview
  - Leads to Calendar view
  - Leads to Single Task View
- Single Task View
  - Shows all normal task details + a description
  - Allows toggling completed status.
    - Refreshes Daily view when going back to it.
- Add Task
  - Form with
    - Due date
    - Task name
    - Task description
  - Leads to Daily or Global view
    - (After task is submitted)

Daily list of tasks displays
- An imageview saying whether it's done or not (ImageView)
- The name of the task (TextView)
- The date the task is due (TextView)

New things not covered in class
- Data is maintained in a global Application state
  - [Application docs](https://developer.android.com/reference/android/app/Application.html)
  - Technically the docs say multiple singletons are better because they're more modular.
    However, I don't feel I have enough data here to justify multiple different singletons.
    - I may try to swap it to a proper singleton later.
- AddTaskActivity has no history
  - Prevents it from being navigated back to
  - Set in the manifest
  - Also learned about finishing activities, rather than just letting them sit around.
- CalendarView and DatePicker
  - CalendarView only allows getting the current date from it, for some stupid reason.
    - Allows overriding onPickDate though.
  - DatePicker allows actually getting the selected date.
- Overriding the toolbar
  - CalendarView shows a + button to add a new task

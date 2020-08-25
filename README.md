
This file describes Cheqyn, an Android app created by Eric Biondic (username RicoBadico on GitHub) for CS50x 2020's final project. It was written in Java in Android Studio. The project can be found at https://github.com/ricobadico/Cheqyn.

Cheqyn is designed to manage and track repeated check-ins between the user and any number of other individuals. It was inspired by the difficulty many managers face keeping up with and recording regular, structured conversations with employees, and was designed with the goal to provide an easy, streamlined way to do so. However, it can be used outside of this context for any situation where you have meetings to hold and info to track.

This submission provides the foundational structure for such an app. It provides the creation of multiple "Conversations", recurring check-ins tied to a particular individual. Upon creating a conversation, you provide a title, time and date of an initial check-in, as well as any number of custom fields you like (e.x."Current Projects", "Obstacles", "What's new") - these fields will show up in any future check-ins in that Conversation. You can schedule and keep records of any number of check-ins within that Conversation for easy reminders and review over time.

The app's main page shows all Conversation threads, showing their title and the soonest upcoming check-in (dynamically determined), sorted with the thread with the soonest upcoming check-in at the top, followed by Conversations with check-ins farther in the future, and finally Conversations with no future check-ins scheduled in their own section at the bottom. Two RecyclerViews within a ScrollView are used to make dynamically display information and keep navigation easy. A floating button brings the user to a UI where they can create a new Conversation at any time.

Tapping a Conversation takes the user to another activity displaying all currently scheduled check-ins for that Conversation, ordered in a similar way to the above so the most important information is at the top. Again, a floating button allows for the creation of a new check-in within this Conversation, setting up a time, date, and description.

Each scheduled check-in can be tapped to go to an activity where you can fill out/review information in all of the custom fields the check-in's conversation provides.

All of this information is stored in a local SQL database through Room. Multiple tables are stored: for individual conversation threads; for individual checkins which are linked to specific conversation thread ids from the first table; and for fields which store the custom fields tracked in each conversation thread, along with saved data from completed checkins. Multiple queries are used to get, insert, and update data, as well manage the organization-of-time which is central to the app's display of data.

Future updates of this app would allow for more variety in the kinds of fields each conversation can track (rather than only providing textfields to fill, allowing for numerical ratings, radio choices, checkboxes, etc); data analysis tools to compare check-in data in more sophisticated ways than reading and comparing side-by-side; and ability to save and load check-in formats to standardize their use and utilize the data in other places.

Overall, this proof of concept has been an excellent opportunity to really test understanding of what was taught in the course and then moving beyond it. Not only did working on this app help further clear up concepts from the exercises, it also introduced me to the development workflow, spending all day coding and using online resources to figure out how to accomplish new things. I feel confident in my ability to continue working in this capacity to learn and create increasingly more. Thank you CS50x for providing this foundation!
Team name: Eagle

Team members: Elizabeth Mitchell

# Introduction

My app, to be called Commentarius (Latin for "diary"), has the goal of allowing users to write free-form  
journal entries without interrupting their flow of thoughts, but to easily categorize sections of text to be  
accessed later. This will be done using the app's most distinctive feature: tagging. Commentarius allows  
users to create custom tags according to their needs. While writing or editing an entry, the user can  
highlight text, and a dropdown will appear, allowing them to give that text an existing tag, or else create  
a new tag. Then all text that has been assigned a given tag can be accessed by clicking on that tag.

The interface of the app is designed to be simple and intuitive to use, with the home screen featuring  
a large text area to write in, with a scrollable list of all entries to the left, and a list of all existing tags  
to the right, which, when clicked, will show the user all text that has been assigned to each tag. When  
a new tag is created, it will immediately be added to the list of tags on the right side of the window.  
When a user is reading an entry or reading the text stored under each tag, the writing pane disappears,  
but will reappear on clicking the 'New Entry' button, which features prominently in the header of the app.

If the user wants to view whole entries, they can use the left pane of the application, which will by 
default show all entries, but allows usage of filters to select entries by year and/or month. Once the  
user selects a year under the year dropdown, the month dropdown will then show up so the user can  
filter further if they desire to do so. Only years and months in which entries were made will be shown  
in the dropdowns.

To see the usefulness of this, it may be useful to imagine a use case. The reason I thought of this project  
was because it would be useful in better organizing my medical notes. For example, when I write an entry  
in the little notebook I keep most of my notes in, it may contain some thoughts and feelings, some things  
I'd like to ask my doctors, some records of side effects, and some breakthrough I had. Using Commentarius,  
I could still write my entries exactly in the way I do currently, but I could tag my text with tags like, "Questions",  
"Side Effects", and "Breakthroughs." Then over time, as I accumulate more entries, I could easily find what  
I'm looking for. If I'm preparing for a doctor's appointment, I could click on my "Questions" tag and find all  
the questions I'd thought of. I could also use filters to find all the questions I'd thought of since my last  
appointment. This is just one usage of my application, but as I was able to find nothing like it currently in   
existence, I think it can be very useful to many.

# Anticipated Technologies

I plan to use JavaFX since I have some experience with it and believe it will allow me to create a clean,  
modern front-end, smoothly connected to a database and an efficient back-end. I plan to use SQLite  
for my database since it is simple to use and appropriately robust for the scale and requirements of  
this project. For an IDE, I plan to use IntelliJ. I will also use Trello to manage the tasks in my project.

# Method/Approach

Due to my surgery in Sepetember and knowledge that my medical treatments would take time away from 
development, I started early on this project. I began by creating user stories and requirements, and 
estimated that the time required for the project will take the better part of a semester's work, and 
then made wireframes of what I want the application to look like based on the requirements. Then I  
started figuring out in what order I plan to develop features. As I did this, I considered parts that looked 
especially difficult to complete, and took note of them. Then I set up the IntelliJ project and began   
development according to my plan. I have been and will continue to test frequently, attempting to find 
problems as much as possible. I will style the application once it works properly, and write tests afterward.

# Estimated Timeline

* Writing user stories and creating requirements - week 1
* Creating wireframes - week 1
* Decide order of features, including identifying difficult features, and set up Trello board - week 1
* Set up IntelliJ project, including creating and connecting database - week 1
* Create UI, using FXML as much as possible - week 3
* Enable creating entries - week 4
* Enable editing entries - week 4
* Enable tagging text - week 6
* Enable deleting entries - week 7
* Enable filtering entries - week 8
* Style UI - week 9
* Write tests and fix bugs- week 10

# Anticipated Problems

The first time I created a project in JavaFX, it took me a very long time to get my database connected  
and working. Since there have been updates to JavaFX since then, my original code no longer works.  
Because I can't go off what I did before, I think this task may take me some time. Upon starting the project,  
I don't know how to get only the user-selected text from a text area, so this will be something to research.  
I also have minimal experience with running SQL statements from Java; I have only used SELECT statements  
before. I know I lack skill in design, so styling the UI will likely take me some time. Also, I had difficulty  
in the past keeping my JavaFX application strictly in the MVC model, so this is something I will be  
especially careful about this time, and try my hardest to correctly apply. Lastly, I don't have a lot of experience  
with writing software tests, so this may present some difficulty for me.

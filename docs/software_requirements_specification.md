# Overview

This software requirements specification document has been created to enumerate the nonfunctional and functional  
requirements for Commentarius as well as to show how it should perform and why it should exist. Commentarius is  
an application created to allow users to write free-form diary entries and then create custom tags to be used to   
categorize parts of their text. This will allow them to easily reference things they wrote about later by clicking on   
created tags to see all text assigned that tag. Users can also browse complete entries and can use filters to see only  
entries within specific time ranges. Commentarius is different from other journal apps because it preserves the integrity  
of the user's thought process while typing, not requiring them to move from screen to screen to store text in different  
locations, but still allowing them to categorize text for later reference.

# Functional Requirements  

1. Diary Entry Browsing Pane
1. When the application creates a new diary entry, the entry’s date and time will appear in the left panel of the application.
2. Tagging Text
   1. When the user highlights text in the entry they are writing, the tag selection menu shall appear.
   2. If the user selects ‘New Tag’ from the tag selection dropdown, a box and button shall appear, allowing the user to create a new tag.
3. Creating a diary entry
   1. When the user clicks ‘New Entry’, a text area shall appear, taking up the center space of the application.  
    
# Non-Functional Requirements
1. Application user interface
   1. Users shall be able to understand without having to interact with the application first how to create a new entry.
2. Deleting entries
   1. To avoid accidental deletion of entries, users shall not be able to delete entries without having to confirm.
3. Tagging text
   1. Users shall be able to visually tell apart text that belongs to different tags.
4. Software design
   1. The code in the application shall adhere to the Model-View-Controller software design pattern.

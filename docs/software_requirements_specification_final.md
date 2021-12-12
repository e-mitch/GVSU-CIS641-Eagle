# Overview

This software requirements specification document has been created to enumerate the nonfunctional and functional  
requirements for Commentarius as well as to show how it should perform and why it should exist. Commentarius is  
an application created to allow users to write free-form diary entries and then create custom tags to be used to   
categorize parts of their text. This will allow them to easily reference things they wrote about later by clicking on   
created tags to see all text assigned that tag. Users can also browse complete entries and can use filters to see only  
entries within specific time ranges. Additionally, users can download all text assigned a given tag. Commentarius is   
different from other journal apps because it preserves the integrity of the user's thought process while typing, not  
requiring them to move from screen to screen to store text in different locations, but still allowing them to categorize  
text for later reference.  

# Functional Requirements  

1. Database
    1. The database shall contain a table containing all tags.  
    2. The database shall contain a table for each tag.  
    3. The database shall contain a table holding all entries.  
    4. The entries table shall also store the date and time of each entry.  
    5. The entries and tags table shall already exist in the database when the application is downloaded.
    6. Entries can be created, edited, retrieved, and deleted.
    7. Tags can be created, edited, and deleted.

2. Application Appearance
    1. The application shall contain a blank text area for writing entries.  
    2. The application shall have a “save” button below the entry area.  
    3. The application shall contain a sidebar displaying all existing tags.  
    4. The application shall contain a sidebar displaying all existing entries.  
    5. The application shall show the date and time of save above each tagged text segment.

3. Style
    1. The application’s colors shall clearly distinguish functional areas.  
    2. The application shall use no more than two fonts. 
    3. Each area of the application shall appropriately use padding to avoid visual clutter.  
    4. The color palette of the application shall use varied colors but not be bright or gaudy.  
    5. The buttons shall change style when hovered over in order to indicate that they are clickable.  

4. Response to User Interactions
    1. When the user highlights typed text in the entry area, a dropdown shall appear to create a tag or select an existing one.  
    2. When the user clicks “new tag”, a text field and "save" button shall appear.   
    3. When the user clicks on an entry in the entry sidebar, the application screen shall show the entry.  
    4. When the user clicks on a tag in the tag sidebar, the application screen shall show all text given that tag.  
    5. When the user clicks “Edit” next to a tag name, a popup shall appear in which they can type a new tag name.
    6. When the user views all text given a tag, functional edit and delete buttons shall be shown next to each portion of tagged text.

5. Application Capabilities
    1. The application shall show entries in order of creation.  
    2. The application shall allow users to filter entries by year and/or month.  
    3. The application shall show tags in order of creation.  
    4. The application shall allow users to order tags by alphabetical order, order created, and order updated.  
    5. The application shall not submit a database query if the user is making a query that would throw an exception.


# Non-Functional Requirements  

1. Usability
    1. The user shall be able to understand how to create a new entry by viewing the opening screen of the application.
    2. The application shall provide feedback to the user when needed.
    3. The user shall be able to easily understand how to view entries by viewing the opening screen of the application.
    4. The user shall be able to easily understand how to view tagged text by viewing the opening screen of the application.
    5. The user shall be able to directly get to any main function of the application with no more than two clicks.   

2. Maintainability
    1. The application shall be distributed from Github, allowing the distributor to create changes.
    2. The database structure shall be generic enough to support changes in the application.
    3. The application shall be developed in Java.
    4. Deployment of a new version of the application shall be almost instantaneous.
    5. The code shall be well-commented to allow for easy maintenance.  

3. Performance
    1. The user shall be able to instantaneously retrieve entries from the database.
    2. Existing tags and entries shall show when the application launches, with no delay.
    3. The database shall save edited tag names with no apparent delay.
    4. The database shall delete tags with no apparent delay.
    5. When a new tag or entry is created, it shall be displayed instantaneously.  

4. Scalability
    1. Code for data processing and sorting shall be efficient to ensure speed.
    2. All data shall be shown in the application regardless of quantity.
    3. All data shall be stored locally, avoiding the need for any server scaling.
    4. Where necessary, the application shall add features to ease navigation of abundant data.
    5. The application shall be distributed online for free to allow as many downloads as needed.    

5. Code Standards
    1. The code shall run without throwing errors.
    2. The code shall be properly spaced and indented.
    3. Where possible, functions shall be overloaded rather than new named functions being created.
    4. Function names shall indicate their purpose.
    5. Variable names shall indicate their purpose.




## Use Case Diagram Traceability
| Artifact ID | Artifact Name | Requirement ID |
| :-------------: | :----------: | :----------: |
| UC1 | Edit Tagged Text | FR4.6 |
| UC2 | Adding a Tag | FR4.2 |
| UC3 | Creating an Entry | FR1.6 |
| UC4 | Deleting an Entry| FR1.6 |

## Class Diagram Traceability
| Artifact Name | Requirement ID |
| :-------------: |:----------: |
| Journal | FR1-5 |

## Activity Diagram Traceability
| Artifact ID | Artifact Name | Requirement ID |
| :-------------: | :----------: | :----------: |
| [AD 1](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/AD%201.png) | Editing an Entry | FR1.6 |
| [AD 2](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/AD%202.png) | Deleting Tagged Text | FR4.6 |
| [AD 3](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/AD%203.png) | Deleting a Tag | FR1.7|

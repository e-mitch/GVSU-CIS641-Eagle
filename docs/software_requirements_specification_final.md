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

# Software Requirements
Functional and nonfunctional requirements are split into 5 sections, and   
each requirement has a unique ID.
## Functional Requirements

### Database

| ID | Requirement |
| :-------------: | :----------: |
| FR1 | The database shall contain a table containing all tags. |
| FR2 | The database shall contain a table for each tag.   |
| FR3 | The database shall contain a table holding all entries.|
| FR4 |The entries table shall also store the date and time of each entry.  |
| FR5 | The entries and tags table shall already exist in the database when the application is downloaded. |
| FR6 | Entries can be created, edited, retrieved, and deleted.
| FR7 | Tags can be created, edited, and deleted. |

### Application Appearance

| ID | Requirement |
| :-------------: | :----------: |
| FR8 | The application shall contain a blank text area for writing entries.   |
| FR9 | The application shall have a “save” button below the entry area.  |
| FR10 | The application shall contain a sidebar displaying all existing tags.  |
| FR11 | The application shall contain a sidebar displaying all existing entries.   |
| FR12 | The application shall show the date and time of save above each tagged text segment. |


### Style

| ID | Requirement |
| :-------------: | :----------: |
| FR13 | The application’s colors shall clearly distinguish functional areas.  |
| FR14 |  The application shall use no more than two fonts.  |
| FR15 | Each area of the application shall appropriately use padding to avoid visual clutter.  |
| FR16 | The color palette of the application shall use varied colors but not be bright or gaudy.  |
| FR17 | The buttons shall change style when hovered over in order to indicate that they are clickable.   |




### Response to User Interactions

| ID | Requirement |
| :-------------: | :----------: |
| FR18 | When the user highlights typed text in the entry area, a dropdown shall appear to create a tag or select an existing one.   |
| FR19 | When the user clicks “new tag”, a text field and "save" button shall appear.    |
| FR20 | When the user clicks on an entry in the entry sidebar, the application screen shall show the entry.  |
| FR21 | When the user clicks on a tag in the tag sidebar, the application screen shall show all text given that tag.  |
| FR22 | When the user clicks “Edit” next to a tag name, a popup shall appear in which they can type a new tag name.|
| FR23 | When the user views all text given a tag, functional edit and delete buttons shall be shown next to each portion of tagged text. |


### Application Capabilities

| ID | Requirement |
| :-------------: | :----------: |
| FR24 | The application shall show entries in order of creation.   |
| FR25 | The application shall allow users to filter entries by year and/or month.   |
| FR26 | The application shall show tags in order of creation.   |
| FR27 | The application shall allow users to order tags by alphabetical order, order created, and order updated.|
| FR28 | The application shall not submit a database query if the user is making a query that would throw an exception. |


## Non-Functional Requirements

### Usability

| ID | Requirement |
| :-------------: | :----------: |
| NFR1 | The user shall be able to understand how to create a new entry by viewing the opening screen of the application.|
| NFR2 | The application shall provide feedback to the user when needed.|
| NFR3 | The user shall be able to easily understand how to view entries by viewing the opening screen of the application.|
| NFR4 | The user shall be able to easily understand how to view tagged text by viewing the opening screen of the application.|
| NFR5 | The user shall be able to directly get to any main function of the application with no more than two clicks. |

### Maintainability

| ID | Requirement |
| :-------------: | :----------: |
| NFR6 | The application shall be distributed from Github, allowing the distributor to create changes.|
| NFR7 | The database structure shall be generic enough to support changes in the application. |
| NFR8 | The application shall be developed in Java. |
| NFR9 | Deployment of a new version of the application shall be almost instantaneous. |
| NFR10 |  The code shall be well-commented to allow for easy maintenance.  |

### Performance

| ID | Requirement |
| :-------------: | :----------: |
| NFR11 | The user shall be able to instantaneously retrieve entries from the database. |
| NFR12 | Existing tags and entries shall show when the application launches, with no delay. |
| NFR13 | The database shall save edited tag names with no apparent delay.|
| NFR14 |  The database shall delete tags with no apparent delay. |
| NFR15 | When a new tag or entry is created, it shall be displayed instantaneously.   |

### Scalability

| ID | Requirement |
| :-------------: | :----------: |
| NFR16 | Code for data processing and sorting shall be efficient to ensure speed. |
| NFR17 |  All data shall be shown in the application regardless of quantity.|
| NFR18 | All data shall be stored locally, avoiding the need for any server scaling. |
| NFR19 | Where necessary, the application shall add features to ease navigation of abundant data. |
| NFR20 | The application shall be distributed online for free to allow as many downloads as needed.   |

### Code Standards

| ID | Requirement |
| :-------------: | :----------: |
| NFR21 | The code shall run without throwing errors. |
| NFR22 | The code shall be properly spaced and indented.|
| NFR23 | Where possible, functions shall be overloaded rather than new named functions being created. |
| NFR24 | Function names shall indicate their purpose.> |
| NFR25 | Variable names shall indicate their purpose. |

# Change Management Plan

This section describes how to implement Commentarius, why it is useful, and how to address issues with implementation and transitioning 
into usage.  

## Why do we need Commentarius?
Typically, when we take notes on something or write down our thoughts, we may use something like a journal, Google docs, Microsoft Word,  
or something similar. While these options are convenient, they don’t necessarily make it easy to reference the things we’ve written later. Sure,  
we can flip through pages of diary entries to find a specific detail, or use the Control-F  keyboard shortcut to browse a document on a computer.  
But these methods are inefficient, and require us to remember exactly what we’re looking for, and that we’re looking for something in the first   
place.  

How many times have you gone into a one-on-one with your manager and  forgotten to ask a question because you jotted it down in some Google  
Doc and forgot it was there? How many times have you tried to find notes you  took on how to do your work more efficiently, or find some breakthrough  
you had and been unable to find them? Wouldn’t it be best if things like this were all kept in the same place?   

Implementing Commentarius will allow us to keep track of our thoughts, regardless of when we recorded them, and easily create and reference   
categories of information. This will help us stay organized, remember the things that are important, and keep important information in a centralized 
location.   

## Implementation
### Step 1: Announcing the Change  
The implementation of Commentarius will start by letting the people working for the company know that the software is coming and will be expected to be
used. This announcement will include explaining why Commentarius will be  useful to its users as well as describing for what it can be used.  

### Step 2: Distribution  
The following day, users shall receive an email detailing the steps of how to download the software. Reminder emails will be sent one and two weeks   
after this.  

### Step 3: Training  
There will be two methods of training: in-person and virtual. For in-person training, an IT employee who is proficient at using the program shall  demonstrate,  
using a computer connected to a projector, the following major features of the program:
* Creating, reading, editing, and deleting entries
* Creating, reading, editing, and deleting tags
* Creating, reading, editing, and deleting tagged text
* Downloading tagged text to save on one’s computer
* Filtering entries
* Sorting tags  

After the demonstration, all present will be invited to experiment with the program and ask questions as needed. Virtual training will be executed via a virtual  
meeting in which the same instructor will give the same presentation. This will also be recorded and shared within the company so that anyone who missed  
training will be able to catch up. After the presentation, users will be invited to experiment  with the program and ask questions. Commentarius is designed to be 
quite intuitive to use, so not many training issues are anticipated.

## Integration  
Since JavaFX applications are compatible with Windows and Mac, I don’t expect any integration issues for average computer users. The current version of the  
application will not work on Linux, so users on Linux  machines will have to use another computer, or else access a virtual  machine using a Microsoft operating system.  
According to data, not quite 2% of personal computer users are using Linux, so Linux compatibility is not expected to be a significant problem.

## Bug Detection
Contact information shall be provided for the creator of the application.  When users experience unexpected bugs or glitches in the program that  cannot be reasonably  
attributed to user error or lack of understanding, they will be instructed to contact the creators, describing as thoroughly as possible what led up to the issue and what happened.

The creator will use this feedback to search for the cause of the problem, fix the bug if one exists, and release a new version of the program without the previous issue.

## Feedback
After the application is implemented, an email shall be sent out allowing  users to anonymously give an approval rating of the program and describe  any major complaints  
or why they may or may not like it. Users will be  asked to report approximately how many times a day they use the application. They will also be asked to describe why the  
program should be used. If this demonstrates a lack of understanding of why the program is useful, a  virtual meeting shall be held to review the functions of the program and a 
new feedback email shall be sent out. 

## Managing Resistance
As always, it’s expected that some people will be reluctant to start using the new application. While usage isn’t mandatory, emails reminding  employees of the functionality of  
the program or user success stories shall be sent out based on the reported frequency of use of the application  (lower usage = more emails).

# Traceability Links
This section shows how all created artifacts link to requirements.  

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

# Software Artifacts 

This section contains links to all created software artifacts for this project.
* [Activity Diagram: Editing an Entry](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/AD%201.png)
* [Activity Diagram: Deleting Tagged Text](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/AD%202.png)
* [Activity Diagram: Deleting a Tag](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/AD%203.png) 
* [Class Diagram](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/CD.png)
* [Use Case Diagram: Editing Tagged Text](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/UCD%201.png)
* [Use Case Diagram: Adding a Tag](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/UCD%202.png)
* [Use Case Diagram: Creating an Entry](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/UCD%203.png)
* [Use Case Diagram: Deleting an Entry](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/UCD%204.png)
* [Use Case Description: Editing Tagged Text](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/functional-models/Use%20Case%20Description.png)
* [Windows Navigation Diagram](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/hci/WND.drawio.png)
* [Wireframes](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/artifacts/Commentarius%20Wireframes.png)
* [Midterm Software Requirements Specification](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/docs/software_requirements_specification.md)
* [Midterm Presentation](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/docs/Status%20report.pdf)
* [Project README](https://github.com/e-mitch/GVSU-CIS641-Eagle/blob/master/README.md)

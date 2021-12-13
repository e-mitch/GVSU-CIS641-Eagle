# Eagle

I am creating an app that I believe will be called Commentarius (Latin for "diary") which is intended  
to allow people to create freeform diary entries and tag specific areas of text within entries with  
custom tags. The user can then access individual entries or access all text which has been given  
a certain tag. Users can create, read, update, and delete entries or tagged text. They can also filter  
entries or tagged text by year or month. The app is especially targeted toward people who have  
serious medical diagnoses, whose entries may contain things like questions for doctors,  
breakthroughs, side effects, etc. Each of these categories could be assigned a tag. The overall  
goal of the app is to allow people to write freely but easily access specific categories of text later.

## Team Members and Roles

* [Ellie Mitchell](https://github.com/e-mitch/CIS641-HW2-Mitchell) (Whole Team)

## Prerequisites
* Be on a Windows or Mac machine

## Run Instructions

* Clone [this](https://github.com/e-mitch/GVSU-CIS641-Eagle.git) repository to your computer
* Open IntelliJ, click ‘Open’, and navigate to the GVSU-CIS641-Eagle folder, then open the folder ‘src’.  
Open the folder ‘commentarius’ in IntelliJ.
* Navigate to File > Project Structure > Project. If the Project   
SDK dropdown is already set to openjdk-15, click ‘Ok’ and proceed to   
the next step. If not, click on the SDK dropdown, mouse over ‘Add SDK’,   
and click ‘Download JDK’. Change Version to 15, leave the other fields default,   
and hit download. Click ‘Apply’, then ‘Ok.’ Allow some time for the download.
* Click ‘Add Configuration’ then, ‘Add new.’ Click ‘Application’ on the   
menu that pops up. In the name field, type ‘Main.’ The first dropdown in   
the Build and Run section should be set to java 15 already. Click ‘Modify Options’   
and click ‘Add VM Options’ in the menu that appears. Type  
--module-path javafx-sdk-16/lib --add-modules javafx.controls,javafx.fxml  
in the VM options field and ‘sample.commentarius.Main’ in the Main Class field.   
Leave everything else default. Click ‘Apply’ and then ‘Ok.’
* Run the application by pressing the green triangle next to the Configurations menu.

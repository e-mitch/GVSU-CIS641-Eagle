package sample.commentarius;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    Button saveButton;
    @FXML
    Label entryDate;
    @FXML
    TextArea entryArea;
    //@FXML
    //ComboBox tagCombo;
    @FXML
    Label newTagButton;
    @FXML
    TextField newTagField;
    @FXML
    Button saveNewTagButton;
    @FXML
    VBox entryVBox;
    @FXML
    HBox headerHBox;
    @FXML
    VBox readArea;
    @FXML
    Label readLabel;
    @FXML
    Label readEntryDate;
    @FXML
    VBox writeArea;
    @FXML
    VBox tagBox;
    @FXML
    VBox tagTextVBox;
    @FXML
    Button cancelButton;
    @FXML
    BorderPane borderpane;
    @FXML
    Button entryDeleteButton;
    @FXML
    ComboBox tagComboBox;
    @FXML
    HBox entryHeaderInfo = new HBox();
    @FXML
    ComboBox orderTagCombo;
    @FXML
    VBox leftBox;
    @FXML
    Button newEntry;

    private String highlightedText = "";

    private String entryPK = "";

    String openedEntry = "";

    Boolean editing = false;

    Boolean isSelectedText = true;

    String currentTag = "";

    String selectedYear = "";

    Boolean tagMode = false;

    Boolean tagCBVisible = false;

    public String getHighlightedText(){
        return highlightedText;
    }

    public void getHighlightedText(MouseDragEvent mouseDragEvent) {
        String text="";
        if(entryArea.getSelectedText() != ""){
            if(tagCBVisible == false){
                setUpTagCombo();
            }
            tagComboBox.setVisible(true);
            text = entryArea.getSelectedText();
        }
        setHighlightedText(text);
        entryArea.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(entryArea.getSelectedText().length() == 0){
                    isSelectedText = false;
                } else {
                    isSelectedText = true;
                }
                /**
                 if(!isSelectedText){
                 //what is this for? works better without. Was causing tagCombo to not show up again after being used once.
                 //figure this section out and why try is needed
                 try {
                 entryHeaderInfo.getChildren().remove(0);
                 } catch(Exception e){

                 }
                 }**/
            }
        });

        EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getText().length() > 0) {
                    tagComboBox.setVisible(false);
                    isSelectedText = false;
                    entryArea.setOnKeyPressed(null);
                }
            }
        };
        entryArea.setOnKeyPressed(keyHandler);
    }

    public String getEntryPK(){
        return entryPK;
    }

    public void setEntryPK(String pk){
        entryPK = pk;
    }

    public ArrayList<String> getTags(){
        ArrayList<String> existingTags = getData("tags", "tagName");
        return existingTags;
    }

    public void initialize(){
        cancelButton.setVisible(false);
        tagTextVBox.setVisible(false);
        tagTextVBox.setManaged(false);
        ArrayList<String> allEntries = new ArrayList<String>();
        allEntries = getData("entries", "entry");
        if (allEntries.size() == 0){
            entryArea.setPromptText("Start typing a new entry here. Highlight text to tag it.");
        }
        ArrayList<String> entries = getData("entries", "date");
        populateEntries(entries);
        ComboBox yearFilter = createYearFilter();
        try {
            leftBox.getChildren().add(0, yearFilter);
        } catch (Exception e){
            leftBox.getChildren().add(yearFilter);
        }
        populateRight();
        setUpOrderTags();
        //setUpDeleteConfirmation(entryDeleteButton);
    }

    public void setUpTagCombo() {
        tagCBVisible = true;
        ArrayList<String> existingTags = processTags(getTags());
        existingTags.add(0, "New Tag");
        System.out.println(tagComboBox.getItems().size());
        tagComboBox.valueProperty().set(null);
        tagComboBox.getItems().setAll(existingTags);
        tagComboBox.setPromptText("Select Tag");
        tagComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (tagComboBox.getSelectionModel().getSelectedItem() != null) {
                    if (tagComboBox.getSelectionModel().getSelectedItem().equals("New Tag")) {
                        newTagField.setVisible(true);
                        saveNewTagButton.setVisible(true);
                        newTagField.requestFocus();
                    } else {
                        newTagField.setVisible(false);
                        saveNewTagButton.setVisible(false);
                        String selectedTag = (String) tagComboBox.getSelectionModel().getSelectedItem();
                        tagText(selectedTag);
                        tagComboBox.setVisible(false);
                    }
                }
            }
        });
    }

    public void populateRight() {
        orderTagCombo.getItems().setAll("Most Recently Used", "Most Often Used", "Newest to Oldest Created", "Oldest to Newest Created", "A-Z", "Z-A");
        try{
            tagBox.getChildren().setAll();
        } catch(Exception e){
        }
        ArrayList<String> existingTags = processTags(getTags());
        for(int i = 0; i < existingTags.size(); i++){
            String pk = existingTags.get(i);
            //String color = getDataWithFilter("tags", "color", "tagName", pk).get(0);
            Button newTagButton = new Button(existingTags.get(i));
            //newTagButton.setStyle("-fx-background-color: #" + color + "; -fx-text-fill: #ffffff; -fx-font-size: 1.1em;");
            newTagButton.setId("tagButton");
            newTagButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    entryDate.setVisible(false);
                    currentTag = newTagButton.getText();
                    setUpTagText(newTagButton);
                }
            });
            tagBox.getChildren().add(0, newTagButton);
        }
        tagBox.getChildren().add(0, orderTagCombo);
    }

    public void setUpOrderTags(){
        orderTagCombo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                int selectedMethod = orderTagCombo.getSelectionModel().getSelectedIndex();
                populateRight(orderedTags(selectedMethod));
            }
        });
    }

    public ArrayList<String> orderedTags(int sortMethod){
        ArrayList<String> existingTags = processTags(getTags());
        ArrayList<String> orderedTags = new ArrayList<String>();
        if(sortMethod == 0){
            //most recently used
            ArrayList<String> updateDates = getData("tags", "dateUpdated");
            ArrayList<String> formattedDates = new ArrayList<String>();
            for(int a = 0; a < updateDates.size(); a++){
                String formatted = updateDates.get(a).substring(16, 26);
                formatted += "T";
                formatted += updateDates.get(a).substring(0, 15);
                formattedDates.add(formatted);
            }
            ArrayList<ArrayList<String>> tagAndUpdateDate = new ArrayList<ArrayList<String>>();
            for(int i = 0; i < updateDates.size(); i++){
                ArrayList<String> data = new ArrayList<String>();
                data.add(existingTags.get(i).strip());
                data.add(formattedDates.get(i));
                tagAndUpdateDate.add(data);
            }
            Collections.sort(tagAndUpdateDate, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    return o1.get(1).compareTo(o2.get(1));
                }
            });
            for(int a = 0; a < tagAndUpdateDate.size(); a++){
                orderedTags.add(tagAndUpdateDate.get(a).get(0));
            }
        } else if (sortMethod == 1){
            //most often used
            ArrayList<String> tags = getTags();
            ArrayList<ArrayList<String>> numRecordsPerTable = new ArrayList<ArrayList<String>>();
            for(int i = 0; i < tags.size(); i++){
                int numEntries = getData(tags.get(i), "PK").size();
                ArrayList<String> tableStats = new ArrayList<String>();
                tableStats.add(String.valueOf(numEntries));
                tableStats.add(tags.get(i));
                numRecordsPerTable.add(tableStats);
            }
            Collections.sort(numRecordsPerTable, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    return Integer.valueOf(o1.get(0)).compareTo(Integer.valueOf(o2.get(0)));
                }
            });
            for(int a = 0; a < numRecordsPerTable.size(); a++){
                orderedTags.add(numRecordsPerTable.get(a).get(1));
            }
        } else if (sortMethod == 2){
            //Newest to Oldest Created
            ArrayList<String> creationDates = getData("tags", "dateCreated");
            ArrayList<String> formattedDates = new ArrayList<String>();
            for(int a = 0; a < creationDates.size(); a++){
                String formatted = creationDates.get(a).substring(16, 26);
                formatted += "T";
                formatted += creationDates.get(a).substring(0, 15);
                formattedDates.add(formatted);
            }
            ArrayList<ArrayList<String>> tagAndCreationDate = new ArrayList<ArrayList<String>>();
            for(int i = 0; i < creationDates.size(); i++){
                ArrayList<String> data = new ArrayList<String>();
                data.add(existingTags.get(i).strip());
                data.add(formattedDates.get(i));
                tagAndCreationDate.add(data);
            }
            Collections.sort(tagAndCreationDate, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    return o1.get(1).compareTo(o2.get(1));
                }
            });
            for(int a = 0; a < tagAndCreationDate.size(); a++){
                orderedTags.add(tagAndCreationDate.get(a).get(0));
            }
        } else if (sortMethod == 3){
            //oldest to newest created
            ArrayList<String> creationDates = getData("tags", "dateCreated");
            ArrayList<String> formattedDates = new ArrayList<String>();
            for(int a = 0; a < creationDates.size(); a++){
                String formatted = creationDates.get(a).substring(16, 26);
                formatted += "T";
                formatted += creationDates.get(a).substring(0, 15);
                formattedDates.add(formatted);
            }
            ArrayList<ArrayList<String>> tagAndCreationDate = new ArrayList<ArrayList<String>>();
            for(int i = 0; i < creationDates.size(); i++){
                ArrayList<String> data = new ArrayList<String>();
                data.add(existingTags.get(i).strip());
                data.add(formattedDates.get(i));
                tagAndCreationDate.add(data);
            }
            Collections.sort(tagAndCreationDate, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                    return o1.get(1).compareTo(o2.get(1));
                }
            });
            for(int a = 0; a < tagAndCreationDate.size(); a++){
                orderedTags.add(tagAndCreationDate.get(a).get(0));
            }
            Collections.reverse(orderedTags);
        } else if (sortMethod == 4) {
            //a-z
            orderedTags = existingTags;
            Collections.sort(orderedTags);
            Collections.reverse(orderedTags);
        } else if (sortMethod == 5){
            //z-a
            orderedTags = existingTags;
            Collections.sort(orderedTags);
        }
        return orderedTags;
    }


    public void populateRight(ArrayList<String> orderedTags) {
        try{
            tagBox.getChildren().setAll();
        } catch(Exception e){

        }
        for(int i = 0; i < orderedTags.size(); i++){
            String pk = orderedTags.get(i);
            //String color = getDataWithFilter("tags", "color", "tagName", pk).get(0);
            Button newTagButton = new Button(orderedTags.get(i));
            //newTagButton.setStyle("-fx-background-color: #" + color + "; -fx-text-fill: #ffffff; -fx-font-size: 1.1em;");
            newTagButton.setId("tagButton");
            newTagButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    currentTag = newTagButton.getText();
                    setUpTagText(newTagButton);
                }
            });
            tagBox.getChildren().add(0, newTagButton);
        }
        tagBox.getChildren().add(0, orderTagCombo);
    }

    public void populateEntries(ArrayList<String> entries){
        for(int a = 0; a < entryVBox.getChildren().size(); a++){
            if(entryVBox.getChildren().get(a).getId().equals("newEntryButton")){
                entryVBox.getChildren().remove(a);
            }
        }
        ArrayList<Button> leftItems = new ArrayList<Button>();
        if(entries.size() > 0) {
            Collections.reverse(entries);
            for (int i = 0; i < entries.size(); i++) {
                String entryPK = entries.get(i);
                String[] thisEntry = entries.get(i).split("\\*");
                String date = thisEntry[1];
                String time = thisEntry[0].substring(0,5);
                Button newEntryButton = new Button(date + ", " + time);
                newEntryButton.setId("newEntryButton");
                newEntryButton.setMinWidth(240);
                leftItems.add(newEntryButton);
                //entryVBox.getChildren().add(newEntryButton);
                newEntryButton.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        ArrayList<Node> allChildren = new ArrayList<Node>(entryVBox.getChildren());
                        for(int x = 0; x < allChildren.size(); x++){
                            if(entryVBox.getChildren().get(x) instanceof Button){
                                entryVBox.getChildren().get(x).setStyle("-fx-background-color: #395b50");
                            }
                        }
                        Button clickedButton = (Button) event.getSource();
                        clickedButton.setStyle("-fx-background-color: #4c6b61");
                        openedEntry = clickedButton.getText().strip();
                        entryDate.setText("");
                        tagTextVBox.setVisible(false);
                        tagTextVBox.setManaged(false);
                        entryDate.setVisible(true);
                        writeArea.setVisible(false);
                        writeArea.setManaged(false);
                        saveNewTagButton.setVisible(false);
                        newTagField.setVisible(false);
                        String text = getDataWithFilter("entries", "entry", "date", entryPK).get(0);
                        readLabel.setText(text);
                        readArea.setBorder(new Border(new BorderStroke(Color.BLACK,
                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        readEntryDate.setText(date + ", " + time);
                        readArea.setVisible(true);
                        readArea.setManaged(true);
                    }
                });
            }
        }
        entryVBox.getChildren().setAll(leftItems);
    }

    public void setUpTagText(Button tagButton){
        tagTextVBox.setVisible(true);
        tagTextVBox.setManaged(true);
        tagTextVBox.getChildren().setAll();
        writeArea.setVisible(false);
        writeArea.setManaged(false);
        readArea.setVisible(false);
        readArea.setManaged(false);
        currentTag = currentTag.strip();
        HBox readTagHeaderBox = new HBox();
        Label tagLabel = new Label(currentTag);
        tagLabel.setId("tagLabel");
        Button deleteTagButton = new Button("Delete Tag: " + currentTag);
        deleteTagButton.setId("deleteTagButton");
        EventHandler<MouseEvent> clickDeleteTag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if(alert.getResult() == ButtonType.YES) {
                    deleteTable(currentTag);
                    deleteData("tags", "tagName", currentTag);
                    tagBox.getChildren().setAll();
                    populateRight();
                    ObservableList<Node> children = tagBox.getChildren();
                    Button nextTagButton = new Button();
                    for(int i = 0; i < children.size(); i++){
                        if(children.get(i).getId() == "tagButton"){
                            nextTagButton = (Button) children.get(i);
                            break;
                        }
                    } if(getTags().size() != 0) {
                        nextTagButton.fire();
                    } else {
                        newEntry.fire();
                    }
                }
            }
        };
        deleteTagButton.setOnMouseClicked(clickDeleteTag);
        Button editTagButton = new Button("Edit Tag Name");
        editTagButton.setId("editTagButton");
        EventHandler<MouseEvent> clickEditTag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TextInputDialog newTagDialog = new TextInputDialog("New Tag Name");
                newTagDialog.setTitle("Edit Tag Name");
                newTagDialog.setHeaderText("Enter your new tag name");
                Optional<String> newName = newTagDialog.showAndWait();
                String nameString = newName.toString();
                String cutDown = nameString.substring(9, nameString.length()-1);
                if (newName.isPresent()) {
                    ObservableList<Node> children = tagBox.getChildren();
                    int buttonIndex = 0;
                    for(int a = 0; a < children.size(); a++){
                        Button currentChild = (Button) children.get(a);
                        String currentButtonText = currentChild.getText();
                        if(currentTag.strip().equals(currentButtonText.strip())){
                            buttonIndex = a;
                        }
                    }
                    renameTable(currentTag, cutDown);
                    updateData("tags", "tagName", "tagName", currentTag.strip(), cutDown);
                    populateRight();
                    Button currentTagButton = (Button) tagBox.getChildren().get(buttonIndex);
                    currentTagButton.fire();
                }
            }
        };
        editTagButton.setOnMouseClicked(clickEditTag);
        Region headerSpacer = new Region();
        readTagHeaderBox.setHgrow(headerSpacer, Priority.ALWAYS);
        readTagHeaderBox.setId("readTagHeaderBox");
        readTagHeaderBox.getChildren().add(tagLabel);
        readTagHeaderBox.getChildren().add(headerSpacer);
        readTagHeaderBox.getChildren().add(editTagButton);
        readTagHeaderBox.getChildren().add(deleteTagButton);
        tagTextVBox.getChildren().add(readTagHeaderBox);
        String tagName = tagButton.getText();
        if(tagName.contains(" ")){
            tagName= tagName.strip().replace(" ", "_");
        }
        ArrayList<String> textDates = getData(tagName, "PK");
        ArrayList<String> taggedText = getData(tagName, "Entry");
        Collections.reverse(textDates);
        Collections.reverse(taggedText);
        for(int i = 0; i < taggedText.size(); i++){
            HBox headerBox = new HBox();
            String time = textDates.get(i).substring(0,5);
            String date = textDates.get(i).substring(16,26);
            String dateTime = date + ", " + time;
            Label textDate = new Label(dateTime);
            textDate.setId("dateLabel");
            Label text = new Label(taggedText.get(i));
            text.setId("textLabel");
            headerBox.getChildren().add(textDate);
            Button editButton = new Button("Edit");
            editButton.setId("editTTButton");
            Button deleteButton = new Button("Delete");
            deleteButton.setId("deleteTTButton");
            headerBox.getChildren().add(editButton);
            headerBox.getChildren().add(deleteButton);
            EventHandler<MouseEvent> clickEdit = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    editTaggedText(mouseEvent);
                }
            };
            EventHandler<MouseEvent> clickDelete = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    deleteTaggedText(mouseEvent);
                }
            };
            editButton.setOnMouseClicked(clickEdit);
            deleteButton.setOnMouseClicked(clickDelete);
            VBox sectionBox = new VBox();
            sectionBox.getChildren().add(headerBox);
            sectionBox.getChildren().add(text);
            tagTextVBox.getChildren().add(sectionBox);
        }
        HBox downloadArea = new HBox();
        Region downloadSpacer = new Region();
        downloadArea.setHgrow(downloadSpacer, Priority.ALWAYS);
        Button downloadTextButton = new Button("Download Text");
        downloadTextButton.setId("downloadButton");
        downloadArea.getChildren().add(downloadSpacer);
        downloadArea.getChildren().add(downloadTextButton);
        Region ttVertSpacer = new Region();
        tagTextVBox.setVgrow(ttVertSpacer, Priority.ALWAYS);
        tagTextVBox.getChildren().add(ttVertSpacer);
        tagTextVBox.getChildren().add(downloadArea);
        EventHandler<MouseEvent> clickDownload = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage currentStage = (Stage) saveButton.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(currentStage);
                if(file != null){
                    String newContent ="";
                    newContent += currentTag;
                    newContent += "\n" + "\n";
                    for(int i = 0; i < taggedText.size(); i++){
                        newContent += "-";
                        newContent += taggedText.get(i);
                        newContent += "\n";
                    }
                    SaveFile(newContent, file);
                }
            }
        };
        downloadTextButton.setOnMouseClicked(clickDownload);
    }

    public void editTaggedText(MouseEvent mouseEvent){
        Button source = (Button) mouseEvent.getSource();
        VBox sourceBox = (VBox) source.getParent().getParent();
        int boxIndex = tagTextVBox.getChildren().indexOf(sourceBox);
        Label selectedLabel = (Label) sourceBox.getChildren().get(1);
        String selectedText = selectedLabel.getText();
        TextArea editArea = new TextArea();
        editArea.setId("editArea" + boxIndex);
        editArea.setText(selectedText);
        sourceBox.getChildren().remove(1);
        sourceBox.getChildren().add(1, editArea);
        HBox saveCancelBox = new HBox();
        Button saveTTEditsButton = new Button("Save");
        Button cancelTTEditsButton = new Button("Cancel");
        saveCancelBox.getChildren().add(saveTTEditsButton);
        saveCancelBox.getChildren().add(cancelTTEditsButton);
        sourceBox.getChildren().add(2, saveCancelBox);
        EventHandler<MouseEvent> clickSave = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HBox headerBox = (HBox) sourceBox.getChildren().get(0);
                Label dateLabel = (Label) headerBox.getChildren().get(0);
                String currentDateTime = dateLabel.getText();
                String date = currentDateTime.substring(0,10);
                String time = currentDateTime.substring(12,17);
                String pk = getDataLike2Filter(currentTag, "PK", "PK", date, time, "entry", selectedText);
                TextArea currentTa = (TextArea) sourceBox.getChildren().get(1);
                String editedText = currentTa.getText();
                updateData(currentTag, "Entry", "PK", pk, editedText);
                Label updatedTextLabel = new Label(editedText);
                updatedTextLabel.setWrapText(true);
                updatedTextLabel.setId("textLabel");
                sourceBox.getChildren().remove(1);
                sourceBox.getChildren().add(1, updatedTextLabel);
                sourceBox.getChildren().remove(2);
            }
        };
        saveTTEditsButton.setOnMouseClicked(clickSave);
        EventHandler<MouseEvent> clickCancel = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sourceBox.getChildren().remove(1);
                sourceBox.getChildren().add(1, selectedLabel);
                sourceBox.getChildren().remove(2);
            }
        };
        cancelTTEditsButton.setOnMouseClicked(clickCancel);
    }

    public void deleteTaggedText(MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES) {
            Button source = (Button) mouseEvent.getSource();
            VBox sourceBox = (VBox) source.getParent().getParent();
            int boxIndex = tagTextVBox.getChildren().indexOf(sourceBox);
            HBox headerBox = (HBox) sourceBox.getChildren().get(0);
            Label dateLabel = (Label) headerBox.getChildren().get(0);
            String currentDateTime = dateLabel.getText();
            String date = currentDateTime.substring(0, 10);
            String time = currentDateTime.substring(12, 17);
            String pk = getDataLike(currentTag, "PK", "PK", date, time);
            deleteData(currentTag, "PK", pk);
            tagTextVBox.getChildren().remove(boxIndex);
        }
    }

    public ComboBox createYearFilter() {
        ComboBox yearFilterCombo = new ComboBox();
        yearFilterCombo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                selectedYear = (String) yearFilterCombo.getSelectionModel().getSelectedItem();
                ComboBox monthFilter = createMonthFilter();
                leftBox.getChildren().add(1, monthFilter);
            }
        });
        ArrayList<String> values = new ArrayList<String>();
        values = getData("entries", "year");
        yearFilterCombo.setId("yearFilterCombo");
        HashSet<String> uniqueValues = new HashSet<String>(values);
        yearFilterCombo.setPromptText("Select Year");
        yearFilterCombo.getItems().addAll(uniqueValues);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedValue = (String) yearFilterCombo.getSelectionModel().getSelectedItem();
                ArrayList<String> filteredEntries = new ArrayList<String>();
                filteredEntries = getDataWithFilter("entries", "date", "year", selectedValue);
                populateEntries(filteredEntries);
                addClearButton();
            }
        };
        yearFilterCombo.setOnAction(event);
        return yearFilterCombo;
    }

    public ComboBox createMonthFilter() {
        ComboBox monthFilterCombo = new ComboBox();
        ArrayList<String> values = new ArrayList<String>();
        values = getDataWithFilter("entries", "month", "year", selectedYear);
        monthFilterCombo.setId("monthFilterCombo");
        HashSet<String> uniqueValues = new HashSet<String>(values);
        monthFilterCombo.setPromptText("Select Month");
        monthFilterCombo.getItems().addAll(uniqueValues);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedValue = (String) monthFilterCombo.getSelectionModel().getSelectedItem();
                ArrayList<String> filteredEntries = new ArrayList<String>();
                filteredEntries = getData2Filter("entries", "date", "year", selectedYear, "month", selectedValue);
                populateEntries(filteredEntries);
            }
        };
        monthFilterCombo.setOnAction(event);
        return monthFilterCombo;
    }

    public void addClearButton(){
        Button clearButton = new Button("Clear Filters");
        clearButton.setId("clearButton");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                leftBox.getChildren().remove(1);
                leftBox.getChildren().set(0, createYearFilter());
                leftBox.getChildren().remove(clearButton);
                ArrayList<String> entries = getData("entries", "date");
                populateEntries(entries);
            }
        };
        clearButton.setOnAction(event);
        leftBox.getChildren().add(2, clearButton);
    }

    public ArrayList<String> processTags(ArrayList<String> tags){
        ArrayList<String> processedTags = new ArrayList<String>();
        for(int i = 0; i < tags.size(); i++){
            String formattedTag = "";
            String[] currentTag = tags.get(i).split("_");
            for(int a = 0; a < currentTag.length; a++){
                formattedTag += currentTag[a] + " ";
            }
            processedTags.add(formattedTag);
        }
        return processedTags;
    }

    public void deleteTable(String tableName){
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String sqlDelete = "DROP TABLE " + tableName;
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(sqlDelete);
            statement.executeUpdate();
        } catch (Exception e){
            System.out.println("Failed to delete tag");
        }
    }

    public void renameTable(String oldName, String newName){
        oldName = oldName.strip();
        if(oldName.contains(" ")){
            oldName = oldName.replace(" ", "_");
        }
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = "ALTER TABLE " + oldName + " RENAME TO " + newName + ";";
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            System.out.println("Executed");
        } catch(Exception e){
            System.out.println("Failed to execute");
        }
    }

    public ArrayList<String> getData(String table, String column){
        ArrayList<String> entries = new ArrayList<String>();
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = "SELECT " + column + " FROM " + table + ";";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                entries.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public ArrayList<String> getData2Filter(String table, String column, String filter1Name, String filter1Val, String filter2Name, String filter2Val){
        ArrayList<String> entries = new ArrayList<String>();
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = "SELECT " + column + " FROM " + table + " WHERE " + filter1Name + " = '"  + filter1Val + "' AND " + filter2Name + " = '" + filter2Val + "'";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entries.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public ArrayList<String> getDataWithFilter(String table, String column, String filterName, String filterVal){
        ArrayList<String> entries = new ArrayList<String>();
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = "SELECT " + column + " FROM " + table + " WHERE " + filterName + " = '"  + filterVal + "'";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entries.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public String getDataLike(String table, String column, String pkName, String date, String time){
        ArrayList<String> entries = new ArrayList<String>();
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String pkVal = time + "%" + date;
        String query = "SELECT " + column + " FROM " + table + " WHERE " + pkName + " LIKE '" + pkVal + "'";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entries.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries.get(0);
    }

    public String getDataLike2Filter(String table, String column, String pkName, String date, String time, String filter2Name, String filter2Val){
        ArrayList<String> entries = new ArrayList<String>();
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String pkVal = time + "%" + date;
        String query = "SELECT " + column + " FROM " + table + " WHERE " + pkName + " LIKE '" + pkVal + "' AND " + filter2Name + " = '" + filter2Val + "'";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entries.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries.get(0);
    }

    public void updateData(String table, String column, String pk, String pkVal, String data){
        String sqlUpdate = "UPDATE " + table + " SET " + column + " = ? WHERE " + pk + " = ?";
        String url = "jdbc:sqlite:diaryData.sqlite";
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, data);
            statement.setString(2, pkVal);
            int rowAffected = statement.executeUpdate();
        } catch (Exception e){

        }
    }

    public void updateDataLike(String table, String column, String pk, String date, String time, String data){
        String url = "jdbc:sqlite:diaryData.sqlite";
        String pkVal = time + "%" + date;
        String sqlUpdate = "UPDATE " + table + " SET " + column + " = ? WHERE " + pk + " LIKE ?";
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, data);
            statement.setString(2, pkVal);
            int rowAffected = statement.executeUpdate();
        } catch (Exception e){
            System.out.println("caught");
        }
    }

    public void insertData(String tableName, String[] columns, String[] values){
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        String query = "INSERT INTO " + tableName + "(";
        for(int b=0; b < columns.length-1; b++){
            query += columns[b] + ",";
        }
        query += columns[columns.length-1] + ") VALUES(";
        for(int i=0; i < values.length-1; i++){
            query += "?,";
        }
        query += "?)";
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            for(int a=0; a < values.length; a++){
                statement.setString(a+1, values[a]);
            }
            int row = statement.executeUpdate();
            if(row == 1){
                //System.out.println("entry saved!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createTable(String tableName, String[] columns){
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for(int b=0; b < columns.length-1; b++){
            query += columns[b] + " text,";
        }
        query += columns[columns.length-1] + " text)";
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteData(String tableName, String columnName, String value){
        String url = "jdbc:sqlite:diaryData.sqlite";
        Connection connection = null;
        String query = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, value);
            int rows = statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<String> entries = getData("entries", "date");
        populateEntries(entries);
    }

    public String generatePK(){
        return String.valueOf(java.time.LocalTime.now()) + "*" + String.valueOf(java.time.LocalDate.now());
    }

    public void saveEntry(ActionEvent actionEvent) {
        if(editing == false) {
            String entryDate = getEntryPK();
            if(entryDate.length() != 0){
                String text = entryArea.getText();
                updateData("entries", "entry", "date", getEntryPK(), text);
                entryArea.setText("");
            } else {
                entryDate = generatePK();
                String year = entryDate.substring(16, 20);
                String month = entryDate.substring(21, 23);
                setEntryPK(entryDate);
                String entryContent = entryArea.getText();
                String[] columns = {"date", "entry", "year", "month"};
                String[] values = {entryDate, entryContent, year, month};
                insertData("entries", columns, values);
                entryArea.setText("");
            }
            setEntryPK("");
            ArrayList<String> entries = getData("entries", "date");
            populateEntries(entries);
            ComboBox yearFilter = createYearFilter();
            yearFilter.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    selectedYear = (String) yearFilter.getSelectionModel().getSelectedItem();
                    ComboBox monthFilter = createMonthFilter();
                    entryVBox.getChildren().add(1, monthFilter);
                }
            });
            //entryVBox.getChildren().set(0, yearFilter);
        } else {
            String entryText = entryArea.getText();
            String entryDateValue = getEntryPK();
            String time = entryDateValue.substring(0,8);
            String date = entryDateValue.substring(16,26);
            updateDataLike("entries", "entry", "date", date, time, entryText);
            String pk = getDataLike("entries", "date", "date", date, time);
            writeArea.setVisible(false);
            writeArea.setManaged(false);
            readArea.setVisible(true);
            readArea.setManaged(true);
            String text = getDataWithFilter("entries", "entry", "date", pk).get(0);
            readLabel.setText(text);
        }
        editing = false;
        entryDate.setVisible(false);
    }

    public void saveEntry() {
        String entryDate = generatePK();
        String year = entryDate.substring(16,20);
        String month = entryDate.substring(21, 23);
        String entryContent= entryArea.getText();
        String[] columns = {"date", "entry", "year", "month"};
        String[] values = {entryDate, entryContent, year, month};
        if(getEntryPK().length() == 0) {
            setEntryPK(entryDate);
            insertData("entries", columns, values);
        } else {
            updateData("entries", "entry", "date", getEntryPK(), entryContent);
        }
        String pk = entryDate.replace(":", "");
        pk = pk.replace("*", "");
        pk = pk.replace("-", "");
        pk = pk.replace(".", "");
        pk = "e" + pk;
        String[] entryColumns = {"text", "tag"};
        createTable(pk, entryColumns);
    }

    public void setDate(){
        saveButton.setVisible(true);
        if(getEntryPK().length() == 0 && editing == false){
            entryDate.setText(String.valueOf(java.time.LocalDate.now()));
        }
        entryDate.setVisible(true);
        entryDate.setManaged(true);
    }

    public void startDrag(MouseEvent mouseEvent) {
        entryArea.startFullDrag();
    }

    public void setHighlightedText(String text){
        highlightedText = text;
    }

    public String getTagColor(){
        String[] tagColors = new String[]{"9B3D12", "AA5042", "C33C54", "B9314F", "8D3B72", "4D3245", "086375", "336699", "0C8346", "0D2818"};
        ArrayList<String> usedColors = getData("tags", "color");
        String selectedColor = "";
        if(usedColors.size() < 10) {
            do {
                Random rand = new Random();
                int number = rand.nextInt(10);
                selectedColor = tagColors[number];
            }
            while (usedColors.contains(selectedColor));
            usedColors.add(selectedColor);
            return selectedColor;
        } else {
            return ("000000");
        }
    }

    public void toggleViewMode(MouseEvent mouseEvent){
        if(tagMode){
            tagMode = false;
            exitTagMode();
        } else {
            tagMode = true;
            enterTagMode();
        }
    }

    private void enterTagMode() {
    }

    private void exitTagMode() {
    }

    public void addTag(MouseEvent mouseEvent) {
        String dateCreated = generatePK();
        String color = getTagColor();
        String newTag = newTagField.getText();
        if(newTag.contains("_")){
            Alert underscoreAlert = new Alert(Alert.AlertType.NONE);
            underscoreAlert.setAlertType(Alert.AlertType.ERROR);
            underscoreAlert.show();
            underscoreAlert.setContentText("Tag names cannot contain underscores");
            underscoreAlert.setHeight(200);
        }
        newTag = newTag.strip().replace(" ", "_");
        ArrayList<String> existingTags = getTags();
        if (existingTags.contains(newTag)) {
            Alert redundantTagAlert = new Alert(Alert.AlertType.NONE);
            redundantTagAlert.setAlertType(Alert.AlertType.ERROR);
            redundantTagAlert.show();
            redundantTagAlert.setContentText("This tag already exists. Please choose a new tag name!");
            redundantTagAlert.setHeight(200);
        } else if (newTag.matches("[0-9]+")){
            Alert allNumberAlert = new Alert(Alert.AlertType.NONE);
            allNumberAlert.setAlertType(Alert.AlertType.ERROR);
            allNumberAlert.show();
            Label allNumbersLabel = new Label("A tag name cannot consist of only numbers. Please use some letters!");
            allNumbersLabel.setWrapText(true);
            allNumbersLabel.setMaxWidth(100);
            allNumberAlert.getDialogPane().setContent(allNumbersLabel);
            allNumberAlert.setHeight(200);
        }else {
            tagComboBox.getItems().add(newTag);
            String[] names = {"PK", "Entry"};
            createTable(newTag, names);
            String[] values = {newTag, color, dateCreated, dateCreated};
            String[] columns = {"tagName", "color", "dateCreated", "dateUpdated"};
            insertData("tags", columns, values);
            tagText(newTag);
            newTagField.setVisible(false);
            newTagField.setText("");
            saveNewTagButton.setVisible(false);
            tagComboBox.setVisible(false);
            //tagCombo.getSelectionModel().clearSelection();
            populateRight();
        }
    }

    private void tagText(String tag) {
        String pk = "";
        saveEntry();
        pk = generatePK();
        String text=getHighlightedText();
        String[] columns = {"PK", "entry"};
        String[] values = {pk, text};
        insertData(tag, columns, values);
        updateData("tags", "dateUpdated", "tagName", tag.strip(), pk);
        entryArea.positionCaret(entryArea.getText().length());
        tagCBVisible = false;
    }

    public void setNewEntry(ActionEvent actionEvent) {
        setEntryPK("");
        entryArea.setText("");
        ArrayList<String> entries = getData("entries", "date");
        populateEntries(entries);
        editing = false;
        cancelButton.setVisible(false);
        tagTextVBox.setVisible(false);
        tagTextVBox.setManaged(false);
        entryDate.setVisible(false);
        writeArea.setVisible(true);
        readArea.setVisible(false);
    }

    public void clearEntry(ActionEvent actionEvent) {
        entryDate.setVisible(false);
        entryArea.setText("");
        tagComboBox.setVisible(false);
    }

    public void deleteEntry(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            String pk = "";
            ArrayList<String> allPKs = getData("entries", "date");
            for(int i = 0; i < allPKs.size(); i++) {
                String currentPK = allPKs.get(i);
                String currentDate = currentPK.substring(currentPK.length() - 10, currentPK.length());
                String currentTime = currentPK.substring(0, 5);
                String openedDate = openedEntry.substring(0, 10);
                String openedTime = openedEntry.substring(12, 17);

                if ((currentDate.equals(openedDate)) && (currentTime.equals(openedTime))) {
                    pk = currentPK;
                    break;
                }
            }
            deleteData("entries", "date", pk);
            readArea.setVisible(false);
            readArea.setManaged(false);
            writeArea.setVisible(true);
            writeArea.setManaged(true);
        }
    }

    public void editEntry(ActionEvent actionEvent) {
        editing = true;
        String entryDateValue = readEntryDate.getText();
        String entryText = readLabel.getText();
        cancelButton.setVisible(true);
        readArea.setVisible(false);
        readArea.setManaged(false);
        writeArea.setVisible(true);
        writeArea.setManaged(true);
        entryArea.setText(entryText);
        String date = entryDateValue.substring(0,10);
        String time = entryDateValue.substring(12,17);
        String pk = getDataLike("entries", "date", "date", date, time);
        String[] timeInfo = pk.split("\\*");
        entryDate.setText(timeInfo[1]);
        entryDate.setVisible(true);
        setEntryPK(pk);
        entryArea.setText(entryText);
    }

    public void cancelEdit(ActionEvent actionEvent) {
        String entryDateValue = readEntryDate.getText();
        String date = entryDateValue.substring(0,10);
        String time = entryDateValue.substring(12,17);
        String pk = getDataLike("entries", "date", "date", date, time);
        writeArea.setVisible(false);
        writeArea.setManaged(false);
        readArea.setVisible(true);
        readArea.setManaged(true);
        String text = getDataWithFilter("entries", "entry", "date", pk).get(0);
        readLabel.setText(text);
        editing = false;
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


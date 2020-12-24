package sample;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.util.logging.Level;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.stage.StageStyle;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.Arrays;

import java.io.File;


public class dragonGUI extends Application {


    static ObservableList<TableProps> data = FXCollections.observableArrayList();
    static TableView<TableProps> table;
    static ChoiceBox cb;
    static Panel pandesc;


    public static void main(String[] args) {

         launch(args);

    }



    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("dragon.png")));//stage icon


        Image image = new Image("sample/search2.png");//declare a new image
        ImageView iv1 = new ImageView();//to diplay the image

        iv1.setImage(image);//set the image
        iv1.setFitHeight(65);
        iv1.setFitWidth(70);
        iv1.setStyle("-fx-background-color:rgba(93,89,119,0.7);"
                + "-fx-background-color: BLACK;"
                + "-fx-background-size: 150.0 71.0;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
                + "-fx-background-color:transparent");

        TextField txt = new TextField();//
        txt.setPromptText("Dragon Search");
        txt.setFocusTraversable(false);
        txt.setMinWidth(600);//sets a pref size width then height
        txt.setMinHeight(58);
        txt.setStyle(" -fx-background-color:rgba(56,56,36,0.8);"
                + "-fx-text-inner-color: white; "
                + " -fx-font-size: 22pt;"
                + " -fx-font-family: \"Segoe UI Semibold\"; "
                + "-fx-effect: dropshadow(three-pass-box,rgba(0,0,0,0.4),10,0,0,0);");


              table = new TableView<>();
        table.setPlaceholder(new Label(""));
        table.setEditable(true);//the table can b edited
        table.setStyle("-fx-background-radius: 0 0 10 10;"
                + " -fx-background-color: rgba(106,204,249,0.8); "
                + "-fx-effect: dropshadow(three-pass-box,rgba(0,0,0,0.4),10,0,0,0);");
        table.setVisible(false);


        cb = new ChoiceBox(FXCollections.observableArrayList("All", "Local", "Web" ,"Ftp"));
        cb.setValue("All");
        cb.setMaxWidth(80);
        cb.setMaxHeight(58);
        cb.setStyle("-fx-background-color:rgba(58,154,220,0.9); "

                + "-fx-font-size: 19pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-background-radius: 3 10 3 3;  "

                + "-fx-padding: 3px; "
                +"-fx-effect: dropshadow(three-pass-box,rgba(0,0,0,0),0,0,0,10);");

        pandesc =  new Panel();
        pandesc.setVisible(true);
        pandesc.setLayout(new BorderLayout());

        final MenuButton choices = new MenuButton("Ext..");
        final List<CheckMenuItem> items = Arrays.asList(
                new CheckMenuItem(".exe"),
                new CheckMenuItem(".pdf"),
                new CheckMenuItem(".doc"),
                new CheckMenuItem(".mp3"),
                new CheckMenuItem(".ppt"),
                new CheckMenuItem(".mp4"),
                new CheckMenuItem(".jar")
        );

        choices.setMinWidth(80);//sets a pref size width then height
        choices.setMinHeight(58);

        choices.getItems().addAll(items);

        for (final CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                   CallableFileSearch.selectedItems.getItems().add(item.getText());
                } else {
                    CallableFileSearch. selectedItems.getItems().remove(item.getText());
                }
            });
        }



        choices.setStyle("-fx-background-color:rgba(58,154,220,0.9); "

                + "-fx-font-size: 19pt; -fx-font-family: \"Segoe UI Semibold\"; -fx-background-radius: 0 0 0 0;  "

                + "-fx-padding: 3px; "
                +"-fx-background-radius: 3 3 3 3;");



        Button lbl = new Button("johnh");
          lbl.setMaxWidth(300);
        lbl.setVisible(false);


        TableColumn<TableProps, String> clmTool = new TableColumn<>("Filename");
        clmTool.setMinWidth(160);
        clmTool.setCellValueFactory(new PropertyValueFactory<>("tool"));

        TableColumn<TableProps, String> clmChart = new TableColumn<>("File Location");
        clmChart.setMinWidth(270);
        clmChart.setCellValueFactory(new PropertyValueFactory<>("chart"));

        table.setItems(data);
        table.setMinWidth(450);


        table.getColumns().addAll(clmTool, clmChart);

        //Add change listener
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (table.getSelectionModel().getSelectedItem() != null) {
                try {
                    Desktop.getDesktop().open(new File("/EDU/data structure/C++ Plus Data Structures, 3rd Edition (2003).pdf"));
                } catch (IOException ex) {
                    Logger.getLogger(dragonGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });



        VBox root = new VBox();
        root.setPadding(new Insets(10,10,10,10));
        root.setBackground(Background.EMPTY);
        root.setStyle("-fx-spacing:10");


        Scene myscene = new Scene(root, 950, 400);
        inputsearch inp2 = new inputsearch(txt.getText());
     myscene.getStylesheets().add(getClass().getResource("darktheme.css").toExternalForm());
        txt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String text="";
            data.clear();

            if (text.matches(txt.getText())) {
                table.setVisible(false);
                txt.setFocusTraversable(false);
            }else

                new java.lang.Thread(new inputsearch(txt.getText())).start();

            try {
                java.lang.Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });






        HBox hbox1 = new HBox( iv1,txt,choices,cb);
        hbox1.setSpacing(2);
        HBox hbox2 = new HBox(lbl,table);
        hbox2.setSpacing(20);
        VBox vbox = new VBox(hbox1, hbox2);
        vbox.setSpacing(25);
        vbox.setFillWidth(true);

        root.getChildren().addAll(hbox1,hbox2);


        primaryStage.initStyle(StageStyle.TRANSPARENT);


        myscene.setFill(null);
        primaryStage.setScene(myscene);
        primaryStage.show();

    }


}

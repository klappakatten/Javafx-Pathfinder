// PROG2 VT2023, Inlämningsuppgift, del 2
// Grupp 151
// Martin Nyman many5992

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javafx.scene.shape.Line;
import javafx.stage.WindowEvent;

public class PathFinder extends Application {

    private final VBox root = new VBox();
    private final Pane imageContainer = new Pane();
    private Stage stage = null;
    private double defaultHeight = 0;
    private double defaultWidth = 0;
    private ListGraph<Place> graph = new ListGraph<>();

    private boolean unSavedChanges = false;

    private Place[] selected = new Place[2];

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("PathFinder");


        //Menu
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menu");

        //File
        Menu fileMenu = new Menu("File");
        fileMenu.setId("menuFile");
        menuBar.getMenus().add(fileMenu);

        //New Map
        MenuItem newMapMenuItem = new MenuItem("New Map");
        newMapMenuItem.setOnAction(e -> newMap("file:europa.gif"));
        //newMapMenuItem.setOnAction(e -> newMap("europa.gif"));
        newMapMenuItem.setId("menuNewMap");

        //Open
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(e -> openMap("europa.graph"));
        openMenuItem.setId("menuOpenFile");

        //Save
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> save("europa.graph"));
        saveMenuItem.setId("menuSaveFile");

        //Save Image
        MenuItem saveImageMenuItem = new MenuItem("Save Image");
        saveImageMenuItem.setOnAction(e -> saveImage("capture.png"));
        saveImageMenuItem.setId("menuSaveImage");

        //Exit
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setId("menuExit");
        exitMenuItem.setOnAction(e -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));

        fileMenu.getItems().addAll(newMapMenuItem, openMenuItem, saveMenuItem, saveImageMenuItem, exitMenuItem);
        root.getChildren().add(menuBar);

        //Buttons
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));
        buttons.setSpacing(10);

        //Find Path
        Button findPathButton = new Button("Find Path");
        findPathButton.setOnAction(e -> findPath());
        findPathButton.setId("btnFindPath");

        //Show connection
        Button showConnectionButton = new Button("Show Connection");
        showConnectionButton.setOnAction(e -> showConnection());
        showConnectionButton.setId("btnShowConnection");

        //New Place
        Button newPlaceButton = new Button("New Place");
        newPlaceButton.setOnAction(e -> {
            newPlace();
        });
        newPlaceButton.setId("btnNewPlace");

        //New Connection
        Button newConnectionButton = new Button("New Connection");
        newConnectionButton.setOnAction(e -> newConnection());
        newConnectionButton.setId("btnNewConnection");

        //Change Connection
        Button changeConnectionButton = new Button("ChangeConnection");
        changeConnectionButton.setOnAction(e -> changeConnection());
        changeConnectionButton.setId("btnChangeConnection");

        buttons.getChildren().addAll(findPathButton, showConnectionButton, newPlaceButton, newConnectionButton, changeConnectionButton);
        root.getChildren().addAll(buttons, imageContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                exit(windowEvent);
            }
        });

        defaultHeight = stage.getHeight();
        defaultWidth = stage.getWidth();
        imageContainer.setId("outputArea");

        //Listen to changes in image
        imageContainer.getChildren().addListener((ListChangeListener<Node>) change -> unSavedChanges = true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Exit app
    private void exit(WindowEvent event) {
        if (unSavedChanges) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("Unsaved changes, go exit anyway?");
            Optional<ButtonType> opt = alert.showAndWait();
            if (opt.isPresent() && opt.get() == ButtonType.CANCEL) {
                event.consume();
            }
        }
    }


    //Add new map
    private void newMap(String path) {
        //Remove old imageview
        if (!imageContainer.getChildren().isEmpty()) {
            clearMap();
        }
        Image image = new Image(path);
        //Add new imageview
        ImageView imageView = new ImageView(image);
        imageContainer.getChildren().add(imageView);
        double height = defaultHeight + image.getHeight();
        double width = Math.max(defaultWidth, image.getWidth());
        stage.setWidth(width);
        stage.setHeight(height);
    }

    private void clearMap() {
        imageContainer.getChildren().clear();
        selected = new Place[2];
        graph = new ListGraph<>();
    }


    //Open saved map
    private void openMap(String fileName) {
        if (unSavedChanges) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText(null);
            alert.setContentText("Unsaved changes, go continue anyway?");
            Optional<ButtonType> opt = alert.showAndWait();
            if (opt.isPresent() && opt.get() != ButtonType.OK) {
                return;
            }
        }

        try {
            FileReader file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);
            String name = reader.readLine();

            //Create new map
            newMap(name);

            HashMap<String, Place> places = new HashMap<>();

            //Add Places
            String[] split = reader.readLine().split(";");
            Iterator<String> iter = Arrays.stream(split).iterator();
            while (iter.hasNext()) {
                String placeName = iter.next();
                double x = Double.parseDouble(iter.next());
                double y = Double.parseDouble(iter.next());
                Place place = new Place(placeName, x, y);
                place.setId(placeName);
                place.setLayoutX(x);
                place.setLayoutY(y);
                place.setOnMouseClicked(evt -> togglePlace(place));
                places.put(place.getName(), place);
                graph.add(place);
                imageContainer.getChildren().add(place);
            }

            //Add Connections
            String connection;
            while ((connection = reader.readLine()) != null) {
                String[] parts = connection.split(";");
                Place placeOne = places.get(parts[0]);
                Place placeTwo = places.get(parts[1]);
                if (graph.getEdgeBetween(placeOne, placeTwo) == null) {
                    graph.connect(places.get(parts[0]), places.get(parts[1]), parts[2], Integer.parseInt(parts[3]));
                    drawLine(placeOne, placeTwo);
                }
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred");
            alert.setContentText("Failed to load file: " + fileName);
            alert.showAndWait();
        }
    }

    //Save data to file
    private void save(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.flush();

            //Save name
            writer.println("file:europa.gif");

            StringBuilder sb = new StringBuilder();
            String line = "";
            //Save places
            for (Place key : graph.getNodes()) {
                line = key.getName() + ";" + key.getX() + ";" + key.getY() + ";";
                sb.append(line);
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            writer.println(sb);

            //Save connections
            for (Place key : graph.getNodes()) {
                for (Edge<Place> edge : graph.getEdgesFrom(key)) {
                    writer.println(key.getName() + ";" + ((Place) edge.getDestination()).getName() + ";" + edge.getName() + ";" + edge.getWeight());
                }
            }
            unSavedChanges = false;
            fileWriter.close();
            writer.close();

        } catch (IOException ignored) {

        }

    }

    //Save image
    private void saveImage(String fileName) {

        try {
            WritableImage image = root.snapshot(null, null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", new File(fileName));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ooops something went wrong IO-EXCEPTION" + e.getMessage());
            alert.showAndWait();
        }

    }

    //Add new place to map
    private void newPlace() {
        imageContainer.setCursor(Cursor.CROSSHAIR);
        imageContainer.setOnMouseClicked(new ClickHandler());
    }

    //Handle mouse click
    private class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            imageContainer.setOnMouseClicked(null);
            imageContainer.setCursor(Cursor.DEFAULT);

            double x = e.getX();
            double y = e.getY();

            NameAlert alert = new NameAlert();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() != ButtonType.OK || alert.getName().equals("")) {
                return;
            }
            String name = alert.getName();
            Place place = new Place(name, x, y);
            place.setId(name);

            place.setLayoutX(x);
            place.setLayoutY(y);

            place.setOnMouseClicked(evt -> togglePlace(place));

            graph.add(place);
            imageContainer.getChildren().add(place);

        }
    }

    //Toggle place between selected or not
    private void togglePlace(Place place) {
        if (getAvailableSelection() == -1 && !place.isToggled()) {
            return;
        }
        if (!place.isToggled()) {
            selected[getAvailableSelection()] = place;
        } else {
            removeSelected(place);
        }
        place.toggle();
    }

    //Get index of available selection
    private int getAvailableSelection() {
        for (int i = 0; i < selected.length; i++) {
            if (selected[i] == null) {
                return i;
            }
        }
        return -1;
    }

    //Remove place from selected array
    private void removeSelected(Place place) {
        for (int i = 0; i < selected.length; i++) {
            if (selected[i] != null && selected[i].equals(place)) {
                selected[i] = null;
            }
        }
        if(selected[1]!=null && selected[0]==null){
            Place temp = selected[1];
            selected[1]=null;
            selected[0]=temp;
        }
    }

    //Create new connection between two places
    private void newConnection() {
        if (getAvailableSelection() != -1) {
            displayError("Two places must be selected");
            return;
        } else if (graph.getEdgeBetween(selected[0], selected[1]) != null) {
            displayError("There is already an connection");
            return;
        }

        //Handle alert
        ConnectAlert alert = new ConnectAlert(selected[0], selected[1]);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() != ButtonType.OK) {
            return;
        } else if (alert.getName().equals("") || alert.getTime() < 0) {
            displayError("Please enter valid information");
            return;
        }

        //Draw connection line
        drawLine(selected[0],selected[1]);

        //Connect places
        String name = alert.getName();
        int weight = alert.getTime();
        graph.connect(selected[0], selected[1], name, weight);
    }

    private void drawLine(Place from, Place to){
        //Draw connection line
        Line line = new Line(from.getX(), from.getY(), to.getX(), to.getY());
        line.setStrokeWidth(3);
        line.setDisable(true);
        imageContainer.getChildren().add(line);
    }

    //Display error message
    private void displayError(String text) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error!");
        alertError.setHeaderText(null);
        alertError.setContentText(text);
        alertError.showAndWait();
    }

    //Show connection between two places
    public void showConnection() {
        if (!isConnected(selected[0], selected[1])) return;

        //Alert
        ConnectAlert alert = new ConnectAlert(selected[0], selected[1]);

        //Set fields
        Edge<Place> edge = graph.getEdgeBetween(selected[0], selected[1]);
        alert.setName(edge.getName());
        alert.setTime(edge.getWeight());
        alert.setNameReadOnly();
        alert.setTimeReadOnly();
        alert.showAndWait();
    }

    //Change connection between two nodes
    private void changeConnection() {
        if (!isConnected(selected[0], selected[1])) return;

        //Handle Alert
        ConnectAlert alert = new ConnectAlert(selected[0], selected[1]);
        Edge<Place> edge = graph.getEdgeBetween(selected[0], selected[1]);
        alert.setName(edge.getName());
        alert.setTime(edge.getWeight());
        alert.setNameReadOnly();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() != ButtonType.OK) {
            return;
        } else if (alert.getName().equals("") || alert.getTime() < 0) {
            displayError("Please enter valid information");
            return;
        }

        //Update edge
        edge.setWeight(alert.getTime()); //Edge - From -> To
        Edge<Place> secondaryEdge = graph.getEdgeBetween(selected[1], selected[0]);
        secondaryEdge.setWeight(alert.getTime()); //Edge - To -> From
    }

    //Display error if nodes are not connected
    private boolean isConnected(Place from, Place to) {
        if (getAvailableSelection() != -1) {
            displayError("Two places must be selected");
            return false;
        } else if (graph.getEdgeBetween(from, to) == null) {
            displayError("The is no connection between these places");
            return false;
        } else {
            return true;
        }
    }

    //Search through graph and find a way between two places
    private void findPath() {
        if (getAvailableSelection() != -1) {
            displayError("Two places must be selected");
            return;
        } else if (!graph.pathExists(selected[0], selected[1])) {
            displayError("The is no path between these places");
            return;
        }

        //Add alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("The Path from " + selected[0].getName() + " to " + selected[1].getName());
        TextArea textArea = new TextArea();
        List<Edge<Place>> path = graph.getPath(selected[0], selected[1]);
        int time = 0;
        for (Edge<Place> edge : path) {
            textArea.appendText("to " + edge.getDestination().getName() + " by " + edge.getName() + " takes " + edge.getWeight() + "\n");
            time+=edge.getWeight();
        }
        textArea.appendText("Total " + time);
        //Display alert
        textArea.setEditable(false);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }
}
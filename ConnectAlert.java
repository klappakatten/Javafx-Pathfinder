// PROG2 VT2023, Inlämningsuppgift, del 2
// Grupp 151
// Martin Nyman many5992

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ConnectAlert extends Alert {

    private final TextField name = new TextField();
    private final TextField time = new TextField();
    public ConnectAlert(Place to, Place from){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.addRow(0,new Label("Name"), name);
        grid.addRow(1, new Label("Time"), time);

        grid.setAlignment(Pos.CENTER);

        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(10);

        setTitle("Connection");
        setHeaderText("Connection from " + to.getName() + " to " + from.getName());
        getDialogPane().setContent(grid);
    }

    public void setNameReadOnly(){
        name.setEditable(false);
    }

    public void setTimeReadOnly(){
        time.setEditable(false);
    }

    public String getName() {
        return name.getText();
    }
    
    //Get time and parse string to int
    public int getTime() {
        int num = -1;
        try{
            num = Integer.parseInt(time.getText());
        }catch(Exception e){
            return -1;
        }
        return num;
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setTime(int time){
        this.time.setText(String.valueOf(time));
    }

}

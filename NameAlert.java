
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NameAlert extends Alert {

    private final TextField nameField = new TextField();

    public NameAlert() {
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(5);

        // Input
        grid.addRow(0, new Label("Name of place:"), nameField);
        setHeaderText(null);
        setTitle("Name");
        getDialogPane().setContent(grid);
    }

    public String getName() {
        return nameField.getText();
    }
}

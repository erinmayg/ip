package duke.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class DialogBox extends HBox {

    private static final Color DUKE_COLOR = Color.valueOf("#160e3c");
    private static final CornerRadii DUKE_CORNER = new CornerRadii(0, 10, 10, 10, false);
    private static final Background DUKE_BG = new Background(new BackgroundFill(DUKE_COLOR, DUKE_CORNER, Insets.EMPTY));

    private static final Color USER_COLOR = Color.valueOf("#3c317c");
    private static final CornerRadii USER_CORNER = new CornerRadii(10, 0, 10, 10, false);
    private static final Background USER_BG = new Background(new BackgroundFill(USER_COLOR, USER_CORNER, Insets.EMPTY));

    @FXML
    private Label dialog;
    @FXML
    private Polygon triangle;
    @FXML
    private Circle circleMask;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        circleMask.setFill(new ImagePattern(img));
    }

    /** Flips the dialog box such that the ImageView is on the left and text on the right. */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialog.setBackground(USER_BG);
        db.triangle.setFill(USER_COLOR);
        return db;
    }

    public static DialogBox getDukeDialog(Message msg, Image img) {
        var db = new DialogBox(msg.getText(), img);
        db.flip();
        db.dialog.setBackground(DUKE_BG);
        db.triangle.setFill(DUKE_COLOR);
        db.triangle.setScaleX(-1);

        if (msg.isError()) {
            db.dialog.setStyle("-fx-text-fill: red");
        }

        return db;
    }
}

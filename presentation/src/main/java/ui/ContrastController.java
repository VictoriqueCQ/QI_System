package ui;/**
 * Created by chenyuyan on 17/3/3.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContrastController extends Application {
    private Main main;

    @FXML
    private ChoiceBox choiceBox1;

    @FXML
    private  ChoiceBox choiceBox2;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button maxAndMinContrast;

    @FXML
    private Button riseAndDownContrast;

    @FXML
    private Button closeContrast;

    @FXML
    private Button incomeProContrast;

    @FXML
    private Button incomeVaContrast;

    @FXML
    private AnchorPane showPane;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void setMain(Main main) {
        this.main = main;

    }
    @FXML
    public void gotoMaxAndMinCompare(){

        main.gotoMaxAndMinContrast();
    }

}

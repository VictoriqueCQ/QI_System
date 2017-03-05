package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ClientOverviewController implements Initializable {
	private Main main;


	@FXML
	private SplitPane topSplitPane;
	
	@FXML
	private ImageView headImageView;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private Button imagekButton;

	@FXML
	private Button comparsionButton;

	@FXML
	private Button thermometerButton;

	



	@FXML
	private Label nameLabel;



	@FXML
	private void exit() {
		main.exitSystem();
	}

	@FXML
	private void gotoCandlestickChar() throws RemoteException {
		main.gotoCandlestickChart();
	}

	@FXML
	private void gotoComparsion() throws RemoteException {
		main.gotoCompareFunction();
	}

	@FXML
	private void gotoThermometer() throws RemoteException {
		main.gotoThermometer();
	}

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public ClientOverviewController() {

	}

	public void setMain(Main main) {
		this.main = main;

	}

}

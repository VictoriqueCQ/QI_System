package ui;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;

public class ClientOverviewController implements Initializable {
	private Main main;


	@FXML
	private SplitPane leftPane;
	
	@FXML
	private ImageView head;
	
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
	private void gotoImageK() throws RemoteException {
		System.out.println("1");
	}

	@FXML
	private void gotoComparsion() throws RemoteException {
		System.out.println("2");
	}

	@FXML
	private void gotoThermometer() throws RemoteException {
		System.out.println("3");
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

package WealtherInformation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class WealtherInformationController implements Initializable {
	
	@FXML private Label cityL;
	@FXML private Label countryL;
	@FXML private Label currentTempL;
	@FXML private Label maxTempL;
	@FXML private Label minTempL;
	@FXML private Button close;
	
	private String[] info;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void setInfo(String[] info) {

		countryL.setText(info[0]);
		cityL.setText(info[1]);
		currentTempL.setText(info[2] + " °C");
		maxTempL.setText(info[3] + " °C");
		minTempL.setText(info[4] + " °C");
		
		this.info = info;
	}
	
	 @FXML
	 public void closeButton(ActionEvent event) throws IOException {
		    Stage stage = (Stage) close.getScene().getWindow();
		    stage.close();
	 }
	
	
}

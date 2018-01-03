package Main;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.textfield.TextFields;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import City.City;
import WealtherInformation.WealtherInformationController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;


public class MainController implements Initializable  {

	
	@FXML private TextField userCity;
	@FXML private Button showButton;
	
	private ArrayList<String> nameCity = new ArrayList<String>();
	private ArrayList<City> list;
	private String[] data = new String[3];
	private String[] info = new String[5];
	
	  @Override
	  public void initialize(URL location, ResourceBundle resources){
		  
		  Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					readJsonList();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		 
		t1.start();
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  userCity.textProperty().addListener((observable, oldValue, newValue) -> {
	            
				String newWordT = StringUtils.capitalize(newValue);
				userCity.setText(newWordT);
				
				TextFields.bindAutoCompletion(userCity, nameCity);
			});
		  
	  }
	  
	  public void readJsonList() throws InterruptedException{
			 try {
				  FileReader reader = new FileReader("city.list.json");
			 			
			 	  Type type = new TypeToken<ObservableList<City>>(){}.getType();
			 	  list = new Gson().fromJson(reader, type);
			 	  
			 	  for(City read : list){
			 		  String x = read.getName() + " " + read.getCountry();
			 		  nameCity.add(x);
			 	  }
				  
			 	  reader.close();	
				  } catch (IOException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  }
	  }

	 
	 public void getTemp() throws NumberFormatException, APIException{
	        OWM owm = new OWM("6cfc768bc8028ec3e1aeb901c0a52c42");

	        CurrentWeather cwd = owm.currentWeatherByCityId(Integer.parseInt(data[0]));
	     
	        String city = cwd.getCityName();

	        double max = Math.round(cwd.getMainData().getTempMax() - 273.15);
	        double min = Math.round(cwd.getMainData().getTempMin() - 273.15);
	        double current = Math.round(cwd.getMainData().getTemp() - 273.15);
	        
	        info[0] = data[2];
	        info[1] = city;
	        info[2] = String.valueOf(current);
	        info[3] = String.valueOf(max);
	        info[4] = String.valueOf(min);
	 }
	  
	  	@FXML
		public void showButton(ActionEvent event) throws IOException, InterruptedException {
		  	
		  	for(City x : list){
		  		String y = x.getName() + " " + x.getCountry();
		  		if(y.equals(userCity.getText())){
		  			data[0] = x.getId();
		  			data[1] = x.getName();
		  			data[2] = x.getCountry();
		  		}
		  	}
		  	
		  	try {
				getTemp();
			} catch (NumberFormatException | APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  	
		  	Stage primaryStage = new Stage();
		    FXMLLoader loader = new FXMLLoader();
		    Pane root = loader.load(getClass().getResource("/WealtherInformation/WealtherInformation.fxml").openStream());
		    WealtherInformationController wea = (WealtherInformationController)loader.getController();
			wea.setInfo(getInfo());
			
		    Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Main/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Wealther Information");
			primaryStage.show();

	  }

	public String[] getInfo() {
		return info;
	}
	
}
	  


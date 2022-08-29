package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.scene.control.TableColumn;

public class FXMLDocumentController implements Initializable{
	String psuid;
	boolean status;
	Timestamp entry_time;
	String role;
	@FXML
	private ChoiceBox<String> cbStatus;
	@FXML
	private TextField txtPSUiD;
	@FXML
	private Button btnAdd;
	@FXML
	private TextField txtTimeEntry;
	@FXML
	private TextField txtRole;
	@FXML
	private Button btnReset;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnSearch;
	@FXML
	private TableView table;
	@FXML
	private TableColumn colPSUID;
	@FXML
	private TableColumn colStatus;
	@FXML
	private TableColumn colTimeEntry;
	@FXML
	private TableColumn colRole;
    @FXML
    private Button btn_date_of_entry_search;

    @FXML
    private Button btn_psu_id_search;

    @FXML
    private Button btn_time_range_search;
    @FXML
    private DatePicker search_date_of_entry;

    @FXML
    private TextField search_psu_id;

    @FXML
    private TextField search_time_from;

    @FXML
    private TextField search_time_to;

	Connection con;
	PreparedStatement pst;

    private String connectionStr = "jdbc:mysql://127.0.0.1:3306/sunlab_management?user=root";
    
    
    // Add user
	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void add(ActionEvent event) {
		try {

			con = DriverManager.getConnection(connectionStr);
			psuid = txtPSUiD.getText();
			status = cbStatus.getValue() == "Active";
			entry_time = Timestamp.valueOf(txtTimeEntry.getText());
			role = txtRole.getText();
			PreparedStatement pst_accounts = con.prepareStatement("INSERT INTO sunlab_accounts(psu_id,user_status,user_role)VALUES (?,?,?)");
			pst_accounts.setString(1, psuid);
			pst_accounts.setBoolean(2, status);
			pst_accounts.setString(3, role);
			pst_accounts.executeUpdate();
			
			PreparedStatement pst_time = con.prepareStatement("INSERT INTO sunlab_entry_records(psu_id,entry_time)VALUES(?, ?)");
			pst_time.setString(1, psuid);
			pst_time.setTimestamp(2, entry_time);
			pst_time.executeUpdate();
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Student Registation");
			alert.setHeaderText("Student Registation");
			alert.setContentText("Record added!");
			alert.showAndWait();
			displayTable();
		}
			catch (Exception e){
				e.printStackTrace();
			}
		
	}
	
	@SuppressWarnings("unchecked")
	public void displayTable() {
		try {
			ObservableList<user_model> users = FXCollections.observableArrayList();
			con = DriverManager.getConnection(connectionStr);
			PreparedStatement pst_table = con.prepareStatement("SELECT sunlab_accounts.psu_id, user_status, user_role, entry_time FROM sunlab_accounts INNER JOIN sunlab_entry_records ON sunlab_accounts.psu_id = sunlab_entry_records.psu_id");
			ResultSet rs = pst_table.executeQuery();
			
			while(rs.next()) {
				user_model user = new user_model(rs.getString("psu_id"), rs.getBoolean("user_status"), rs.getString("user_role"), rs.getTimestamp("entry_time"));
				users.add(user);
			}
			
			table.setItems(users);
			colPSUID.setCellValueFactory(new PropertyValueFactory<user_model, String>("psu_id"));
			colStatus.setCellValueFactory(new PropertyValueFactory<user_model, Boolean>("user_status"));
			colRole.setCellValueFactory(new PropertyValueFactory<user_model, String>("user_role"));
			colTimeEntry.setCellValueFactory(new PropertyValueFactory<user_model, Timestamp>("entry_time"));
		}
			catch (Exception e){
				e.printStackTrace();
			}
	}
	
    @SuppressWarnings("unchecked")
	@FXML
    void search_date_of_entry(ActionEvent event) {
    		try {
    			ObservableList<user_model> users = FXCollections.observableArrayList();
    			con = DriverManager.getConnection(connectionStr);
    			PreparedStatement pst_table = con.prepareStatement("SELECT sunlab_accounts.psu_id, user_status, user_role, entry_time FROM sunlab_accounts INNER JOIN sunlab_entry_records ON sunlab_accounts.psu_id = sunlab_entry_records.psu_id WHERE date(entry_time) =?");
    			LocalDate tempDate = search_date_of_entry.getValue();
    			Date date = Date.valueOf(tempDate);
    			pst_table.setDate(1, date);
    			ResultSet rs = pst_table.executeQuery();	
    			while(rs.next()) {
    				user_model user = new user_model(rs.getString("psu_id"), rs.getBoolean("user_status"), rs.getString("user_role"), rs.getTimestamp("entry_time"));
    				users.add(user);
    			}
    			
    			table.setItems(users);
    			colPSUID.setCellValueFactory(new PropertyValueFactory<user_model, String>("psu_id"));
    			colStatus.setCellValueFactory(new PropertyValueFactory<user_model, Boolean>("user_status"));
    			colRole.setCellValueFactory(new PropertyValueFactory<user_model, String>("user_role"));
    			colTimeEntry.setCellValueFactory(new PropertyValueFactory<user_model, Timestamp>("entry_time"));
    		}
    		catch (Exception e){
				e.printStackTrace();
			}
    		
    }

    @SuppressWarnings("unchecked")
	@FXML
    void search_psu_id(ActionEvent event) {
    	try {
			ObservableList<user_model> users = FXCollections.observableArrayList();
			con = DriverManager.getConnection(connectionStr);
			PreparedStatement pst_table = con.prepareStatement("SELECT sunlab_accounts.psu_id, user_status, user_role, entry_time FROM sunlab_accounts INNER JOIN sunlab_entry_records ON sunlab_accounts.psu_id = sunlab_entry_records.psu_id WHERE sunlab_accounts.psu_id =?");
			pst_table.setString(1, search_psu_id.getText());
			ResultSet rs = pst_table.executeQuery();	
			while(rs.next()) {
				user_model user = new user_model(rs.getString("psu_id"), rs.getBoolean("user_status"), rs.getString("user_role"), rs.getTimestamp("entry_time"));
				users.add(user);
			}
			
			table.setItems(users);
			colPSUID.setCellValueFactory(new PropertyValueFactory<user_model, String>("psu_id"));
			colStatus.setCellValueFactory(new PropertyValueFactory<user_model, Boolean>("user_status"));
			colRole.setCellValueFactory(new PropertyValueFactory<user_model, String>("user_role"));
			colTimeEntry.setCellValueFactory(new PropertyValueFactory<user_model, Timestamp>("entry_time"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
    }

    @SuppressWarnings("unchecked")
	@FXML
    void search_time_range(ActionEvent event) {
    	try {
			ObservableList<user_model> users = FXCollections.observableArrayList();
			con = DriverManager.getConnection(connectionStr);
			PreparedStatement pst_table = con.prepareStatement("SELECT sunlab_accounts.psu_id, user_status, user_role, entry_time FROM sunlab_accounts INNER JOIN sunlab_entry_records ON sunlab_accounts.psu_id = sunlab_entry_records.psu_id WHERE entry_time >= ? AND entry_time <=?");
			Timestamp from_time = Timestamp.valueOf(search_time_from.getText());
			Timestamp to_time = Timestamp.valueOf(search_time_to.getText());
			pst_table.setTimestamp(1, from_time);
			pst_table.setTimestamp(2, to_time);
			ResultSet rs = pst_table.executeQuery();	
			while(rs.next()) {
				user_model user = new user_model(rs.getString("psu_id"), rs.getBoolean("user_status"), rs.getString("user_role"), rs.getTimestamp("entry_time"));
				users.add(user);
			}
			
			table.setItems(users);
			colPSUID.setCellValueFactory(new PropertyValueFactory<user_model, String>("psu_id"));
			colStatus.setCellValueFactory(new PropertyValueFactory<user_model, Boolean>("user_status"));
			colRole.setCellValueFactory(new PropertyValueFactory<user_model, String>("user_role"));
			colTimeEntry.setCellValueFactory(new PropertyValueFactory<user_model, Timestamp>("entry_time"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
    }
	// Event Listener on Button[#btnUpdate].onAction
	@FXML
	public void Reset_table(ActionEvent event) {
		displayTable();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			con = DriverManager.getConnection(connectionStr);
			cbStatus.getItems().addAll("Active", "Suspend", "Inactive");
			displayTable();
		}
			catch (Exception e){
				e.printStackTrace();
			}
	}
}

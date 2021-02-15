package it.unipi.dii.lsdb.group13.guiController;

import java.io.IOException;

import org.apache.log4j.Logger;

import it.unipi.dii.lsdb.group13.App;
import it.unipi.dii.lsdb.group13.database.EmployerDao;
import it.unipi.dii.lsdb.group13.database.JobSeekerDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class UpdateAccountJobSeekerController {
	public UpdateAccountJobSeekerController() {
    	Logger logger = Logger.getLogger(UpdateAccountJobSeekerController.class.getName());    
}
	@FXML
	private PasswordField npwd;
	@FXML
	private PasswordField cpwd;
	@FXML
	private Label error;
	@FXML
	private TextField skill;
	@FXML
	private TextField dskill;
	

	@FXML
	private void changePassword() throws IOException{
		if(npwd.getText().isEmpty() || cpwd.getText().isEmpty())
		{
			error.setText("Please enter both passwords!");
			error.setTextFill(Color.web("#ff0000",0.8));
			return;
		}
		else
		{
			if(npwd.getText().equals(cpwd.getText()))
			{
	        	JobSeekerDao seeker= new JobSeekerDao();
	        	boolean isValid= seeker.changePassword(npwd.getText(),cpwd.getText());
	        	if(isValid==true)
	        	{
	        		error.setText("Password Updated!");
	        	}
	        	else {
	        		error.setText("Password couldn't update!");
					error.setTextFill(Color.web("#ff0000",0.8));
	        	}
			}
			else
			{
				error.setText("Passwords don't match!");
				error.setTextFill(Color.web("#ff0000",0.8));
			}
			return;
		}
	}
		
	@FXML
	private void addSkill() throws IOException{
		if(skill.getText().isEmpty())
		{
			error.setText("Please enter new skill in order to add it!");
			error.setTextFill(Color.web("#ff0000",0.8));
			return;
		}
		else
		{
			JobSeekerDao seeker= new JobSeekerDao();
			boolean isValid= seeker.addSkill(skill.getText());
			if(isValid==true)
			{
				error.setText("New skill added!");
			}
			else
			{
				error.setText("Skill could not be added!");
				error.setTextFill(Color.web("#ff0000",0.8));
			}
			return;
		}
	}
	@FXML
	private void deleteSkill() throws IOException{
		if(dskill.getText().isEmpty())
		{
			error.setText("Please enter the skill that you want to remove!");
			error.setTextFill(Color.web("#ff0000",0.8));
			return;
		}
		else
		{
			JobSeekerDao seeker= new JobSeekerDao();
			boolean isValid= seeker.deleteSkill(dskill.getText());
			if(isValid==true)
			{
				error.setText("Skill deleted!");
			}
			else
			{
				error.setText("Skill could not be deleted!");
				error.setTextFill(Color.web("#ff0000",0.8));
			}
			return;
		}
	}
	@FXML
	private void goHome() throws IOException{
		App.setRoot("JobSeekerHomePage");
	}
}

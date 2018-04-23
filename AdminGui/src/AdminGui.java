import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminGui extends Application
{
	//cannot be seen if compiled code is distributed
	int hashKey = 57;
	int attempts = 0;
	int maxAttempts = 3;
	LocalDateTime lockoutStamp = LocalDateTime.now();
	
	public static void main(String[] args) {
		launch(args);
	}
	static double[] toDoubleArr(String[] s)
	{
		double[] d = new double[s.length];
		for(int i = 0; i < s.length; i++)
		{
			d[i] = Double.parseDouble(s[i]);
		}
		return d;
	}
	static String detectBadQuestion(int correctAnswers, int resets, double averageCorrectRate, double averageResetRate, int attempts, double significance)
	{
		if(significance > 0.2)
		{
			significance = 0.2;
		}
		boolean isResetMore = false, isTooHard = false, isTooEasy = false;
		isResetMore = 1 - MathsHelper.cumulativePoisson(averageResetRate * attempts, correctAnswers) < significance;
		isTooEasy = 1 - MathsHelper.cumulativePoisson(averageCorrectRate * attempts, correctAnswers) < significance / 2;
		isTooHard = MathsHelper.cumulativePoisson(averageCorrectRate * attempts, resets - 1) < significance / 2;
		if(!(isResetMore && isTooEasy && isTooHard))
		{
			return "question is adequate";
		}
		else
		{
			String out = "";
			if(isResetMore)
			{
				out = "question is unlikely to be answered. ";
			}
			if(isTooHard)
			{
				out += "question is too hard.";
			}
			if(isTooEasy)
			{
				out += "question is too easy.";
			}
			return out;
		}
	}
	
	static double[] getQuizStats(String filePath) throws IOException
	{
		File statsFile = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(statsFile));
		String userData = reader.readLine();
		String[] scoreStrings = reader.readLine().split(",");
		double[] scores = toDoubleArr(scoreStrings) ;
		double meanScore = MathsHelper.mean(scores);
		double medianScore = MathsHelper.mean(scores);
		double scoreErr = MathsHelper.standardDeviation(scores);
		reader.close();
		return new double[] {meanScore, medianScore, scoreErr};
	}
	static double[] qetQuestionStats(String filePath, int questionNumber) throws IOException
	{
		File statsFile = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(statsFile));
		String nextLine;
		double[] questionStats = {0};
		while((nextLine = reader.readLine()) != null)
		{
			questionStats = toDoubleArr(nextLine.split(","));
			if(questionStats[0] == questionNumber)
			{
				break;
			}
		}
		reader.close();
		return questionStats;
	}
	
	
	
	int adminPassword;
	public void passwordCheck(Stage stage)
	{
		stage.setTitle("login");
		Group root = new Group();
		Scene scene = new Scene(root, 600, 600);
		PasswordField passwordBox = new PasswordField(); 
		passwordBox.setLayoutX(200);
		passwordBox.setLayoutY(200);
		Button loginButton = new Button("enter");
		loginButton.setLayoutX(400);
		loginButton.setLayoutY(200);
		
		File file = new File("password.txt");
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			adminPassword = Integer.parseInt(reader.readLine());
			reader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("password not found");
		}
		catch(IOException e)
		{
			System.out.println("password not found");
		}
		Text passwordMsg = new Text();
		passwordMsg.setLayoutX(200);
		passwordMsg.setLayoutY(250);
		root.getChildren().add(passwordMsg);
		loginButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				LocalDateTime now = LocalDateTime.now();
				if(attempts < maxAttempts || now.getMinute() - lockoutStamp.getMinute() > 15)
				{
					int passwordIn = MathsHelper.privateHashFunction(passwordBox.getText(), hashKey);
					if(adminPassword == passwordIn)
					{
						passwordMsg.setText("correct");
						displayMainPage();
						stage.hide();
					}
					else
					{
						passwordMsg.setText("incorrect");
						attempts++;
						LocalDateTime lockoutStamp = LocalDateTime.now();
					}
				}
				else
				{
					passwordMsg.setText("lockout. Time remaining:" + (now.getMinute() - lockoutStamp.getMinute() + 15) + "minutes");
				}
			}
		});
		stage.setScene(scene);
		root.getChildren().addAll(passwordBox, loginButton);
		stage.show();
	}
	
	public String[] createQuestion()
	{
		String[] args = {"", "", "", "", "", "", "", "", "", "", "", "", "", ""};
		return editQuestion(args);
	}
	
	public String[] editQuestion(String[] previousValues)
	{
		Group root = new Group();
		Stage stage = new Stage();
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		InputBox question = new InputBox("question in English:");
		question.box.setText(previousValues[0]);
		InputBox questionWelsh = new InputBox("question in Welsh:");
		questionWelsh.box.setText(previousValues[1]);
		InputBox[][] answersWrong = new InputBox[3][3];
		InputBox[] answerCorrect = new InputBox[3];
		
		int[] position = {100, 100};
		
		question.setCentrePosition(position);
		questionWelsh.setCentrePosition(new int[] {position[0] + 200, position[1]});
		position[1] += 50;
		InputBox engBox = new InputBox("Correct answer in English:", position);
		engBox.box.setText(previousValues[2]);
		answerCorrect[0] = engBox;
		InputBox welshBox = new InputBox("Correct answer in Welsh:", new int[] {position[0] + 200, position[1]});
		welshBox.box.setText(previousValues[3]);
		answerCorrect[1] = welshBox;
		InputBox pictureBox = new InputBox("Picture location:", new int[] {position[0] + 400, position[1]});
		pictureBox.box.setText(previousValues[4]);
		answerCorrect[2] = pictureBox;
		position[1] += 100;
		root.getChildren().addAll(question.box, question.name, questionWelsh.box, questionWelsh.name, engBox.box, engBox.name, welshBox.box, welshBox.name, pictureBox.box, pictureBox.name);
		
		for(int i = 0; i < 3; i++)
		{
			engBox = new InputBox("Wrong answer " + (i + 1) + " in English:", position);
			engBox.box.setText(previousValues[5 + 3 * i]);
			welshBox = new InputBox("Wrong answer " + (i + 1) + " in Welsh:", new int[] {position[0] + 200, position[1]});
			welshBox.box.setText(previousValues[6 + 3 * i]);
			pictureBox = new InputBox("Picture location:", new int[] {position[0] + 400, position[1]});
			welshBox.box.setText(previousValues[7 + 3 * i]);
			answersWrong[i] = new InputBox[] {engBox, welshBox, pictureBox};
			position[1] += 50;
			root.getChildren().addAll(engBox.box, engBox.name, welshBox.box, welshBox.name, pictureBox.box, pictureBox.name);
		}
		String questionStr = question.box.getText();
		String questionWelStr = questionWelsh.box.getText();
		
		String correctEngStr = answerCorrect[0].box.getText();
		String correctWelStr = answerCorrect[1].box.getText();
		String correctpicStr = answerCorrect[2].box.getText();
		
		String wrongEng1Str = answersWrong[0][0].box.getText();
		String wrongWel1Str = answersWrong[0][1].box.getText();
		String wrongPic1Str = answersWrong[0][2].box.getText();
		String wrongEng2Str = answersWrong[1][0].box.getText();
		String wrongWel2Str = answersWrong[1][1].box.getText();
		String wrongPic2Str = answersWrong[1][2].box.getText();
		String wrongEng3Str = answersWrong[2][0].box.getText();
		String wrongWel3Str = answersWrong[2][1].box.getText();
		String wrongPic3Str = answersWrong[2][2].box.getText();
		
		Button submitBtn = new Button("submit");
		submitBtn.setLayoutX(position[0] + 50);
		submitBtn.setLayoutY(position[1] + 100);
		submitBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				return;
			}
		});
		stage.show();
		
		return new String[] {questionStr, questionWelStr,
				correctEngStr, correctWelStr, correctpicStr,
				wrongEng1Str, wrongWel1Str, wrongPic1Str,
				wrongEng2Str, wrongWel2Str, wrongPic2Str, 
				wrongEng3Str, wrongWel3Str, wrongPic3Str};
	}
	
	public String changeQuizPath(String newPath, Stage stage)
	{
		TextField input = new TextField();
		Button submit = new Button("continue");
		submit.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				return;
			}
		});
		return input.getText();
	}
	
	public void createStatsFile(String filePath, String school, String region, int ageMin, int ageMax, int questions) throws IOException
	{
		File newStatsFile = new File(filePath);
		newStatsFile.createNewFile();
		String writeString = school + "," + region + "," + ageMin + "," + ageMax + System.getProperty("line.separator");
		for(int i = 0; i < questions; i++)
		{
			writeString += System.getProperty("line.separator") + i + ",0,0,0,0,0,0,0";
		}
		FileWriter writer = new FileWriter(newStatsFile);
		writer.write(writeString);
		writer.close();
	}
	public void displayStatsTable(Group root, ObservableList<questionStats> stats)
	{
		TableView<questionStats> table = new TableView<>();
		TableColumn<questionStats, String> col1 = new TableColumn<>();
		TableColumn<questionStats, String> col2 = new TableColumn<>();
		TableColumn<questionStats, String> col3 = new TableColumn<>();
		TableColumn<questionStats, String> col4 = new TableColumn<>();
		
		TableColumn<questionStats, String> colReset = new TableColumn<>();
		
		table.setItems(stats);
		col1.setCellValueFactory(new PropertyValueFactory<>("answer1"));
		col2.setCellValueFactory(new PropertyValueFactory<>("answer2"));
		col2.setCellValueFactory(new PropertyValueFactory<>("answer3"));
		col2.setCellValueFactory(new PropertyValueFactory<>("answer4"));
		
		table.getColumns().addAll(col1, col2);
		root.getChildren().add(table);
	}
	public void displayStatsGraph()
	{
		
	}
	
	public void displayMainPage()
	{
		Group root = new Group();
		Stage stage = new Stage();
		Scene scene = new Scene(root, 700, 500);
		//TODO: make a menu and do all that stuff
		stage.show();
		createQuestion();
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		passwordCheck(stage);
	}
}
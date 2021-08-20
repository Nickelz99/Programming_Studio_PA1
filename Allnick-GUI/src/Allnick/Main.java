package Allnick; 

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Main extends Application
{
	Stage window; // stores the main scene of java fx
	Connection gcon; // stores connection data globally
	String N_team1 = null; //stores team1 name for Q1
	String N_team2 = null; //stores team2 name for Q1
	String N_player1f = null; //stores player1 first name for Q2
	String N_player2f = null; //stores player2 first name for Q2
	String N_player1l = null; //stores player1 last name for Q2
	String N_player2l = null; //stores player2 last name for Q2
	String N_team3 = null; //stores team name for Q3
	Boolean Q1A = false; // check which questions to get answers for.
	Boolean Q2A = false;
	Boolean Q3A = false;
	int Q1team = 0;	// stores team codes and player codes
	int Q1team2 = 0;
	int Q2player1 = 0;
	int Q2player2 = 0;
	int Q3team = 0;
	Stage window2;
	
	public void start(Stage stage) {
        // initial pop up box
		window = stage;
		stage.setTitle("Database GUI");
		VBox init = new VBox(10);
		Label instruct = new Label("Connection Established, Press Continue to query or Quit to exit");
		HBox Conn = new HBox(5);
		Button cont = new Button("Continue");
		Button quit = new Button("Quit");
		Conn.getChildren().addAll(cont,quit);
		Conn.setAlignment(Pos.CENTER);
		init.getChildren().addAll(instruct,Conn);
		init.setAlignment(Pos.TOP_CENTER);
		init.setPadding(new Insets(150, 10, 10, 10));
		Scene sc = new Scene(init,640,480);
		stage.setScene(sc);
		//end pop up
		
		//connecting to database
        dbsetup my = new dbsetup();
		Connection conn = null;
	    try 
	    {
	    	Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/allnick",my.user, my.pswd);
	        gcon = conn;
	    } 
	    catch (Exception e)
	    {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
	    }
	    //System.out.println("Opened database successfully");
	    // end connecting to database
	    
	    // Launching GUI or Closing program based off user input
	    stage.show(); // launches initial pop up box
	    cont.setOnAction(event -> rGUI());
	    quit.setOnAction(event -> closeProgram());
	   // decsion made on launching GUI or Closing program
    }
	

	public void rGUI()
	{
		//Actual user interface setup
		//Grid
	    VBox top = new VBox(10);
	    top.setPadding(new Insets(10, 10, 10, 10));
	    HBox layer01 = new HBox(15);
	    HBox layer1 = new HBox(15);
	    HBox layer02 = new HBox(15);
	    HBox layer2 = new HBox(15);
	    HBox layer03 = new HBox(15);
	    HBox layer3 = new HBox(15);
	    layer3.setPadding(new Insets(5,100,5,5));
	    HBox layer4 = new HBox(15);
	    
	    //Labels
	    Label t_name1 = new Label("Team name 1: ");
		Label t_name2 = new Label("Team name 2: ");
		Label instruct2 = new Label("Please Query Database by entering input into the following fields.");
		Label instruct3 = new Label("Please enter the name of the team you want bragging rights for as team 1, hit the check box to query: ");
	    Label p_name1f = new Label("Player 1 first name: ");
		Label p_name2f = new Label("Player 2 first name: ");
	    Label p_name1l = new Label("Player 1 last name: ");
		Label p_name2l = new Label("Player 2 last name: ");
		Label instruct4 = new Label("Please enter the names of the players you wish to find a connection for, hit the check box to query: ");
	    Label t_name3 = new Label("Team name: ");
		Label instruct5 = new Label("Please enter the name of the team against which you want to find the team with most rushing yards, hit the check box to query: ");
	    
	    //Text input boxes
	    TextField team1 = new TextField();
	    TextField team2 = new TextField();
	    TextField player1f = new TextField();
	    TextField player2f = new TextField();
	    TextField player1l = new TextField();
	    TextField player2l = new TextField();
	    TextField team3 = new TextField();
	    
	    //checkboxes
	    CheckBox Q1 = new CheckBox();
	    CheckBox Q2 = new CheckBox();
	    CheckBox Q3 = new CheckBox();

	    
	    //Search button
	    Button genent = new Button("Search");
	    genent.setOnAction(e -> 
	    {
	    	N_team1 = team1.getText();
	    	N_team2 = team2.getText();
	    	Q1A = Q1.isSelected();
	    	
	    	N_player1f = player1f.getText();
	    	N_player2f = player2f.getText();
	    	N_player1l = player1l.getText();
	    	N_player2l = player2l.getText();
	    	Q2A = Q2.isSelected();
	    	
	    	N_team3 = team3.getText();
	    	Q3A = Q3.isSelected();
	    	reqdata();
	    	
	    });
	    
    	Button Q = new Button("Quit");
    	Q.setOnAction(e -> closeProgram());
	    // putting all elements in Grid
    	layer01.getChildren().addAll(instruct3,Q1);
	    layer01.setAlignment(Pos.CENTER);
    	layer1.getChildren().addAll(t_name1,team1,t_name2,team2);
	    layer1.setAlignment(Pos.CENTER);
    	layer02.getChildren().addAll(instruct4,Q2);
	    layer02.setAlignment(Pos.CENTER);
	    layer2.getChildren().addAll(p_name1f,player1f,p_name1l,player1l,p_name2f,player2f,p_name2l,player2l);
	    layer2.setAlignment(Pos.CENTER);
	   	layer03.getChildren().addAll(instruct5,Q3);
	    layer03.setAlignment(Pos.CENTER);
	    layer3.getChildren().addAll(t_name3,team3);
	    layer3.setAlignment(Pos.CENTER);
	    layer4.getChildren().addAll(genent,Q);
	    layer4.setAlignment(Pos.CENTER);
	    top.getChildren().addAll(instruct2,layer01,layer1,layer02,layer2,layer03,layer3,layer4);
		top.setAlignment(Pos.TOP_CENTER);
		window.setScene(new Scene(top,1200,400));
		window.setTitle("Allnick GUI");
	}
	
	
	public void closeProgram() // function to kill program cleanly if user wants to close
	{
	    //closing the connection
	    try 
	    {
	      gcon.close();
	      System.out.println("Connection Closed.");
	    } 
	    catch(Exception e)
	    {
	      System.out.println("Connection NOT Closed.");
	    }//end try catch
		window.close();
	    System.exit(0);
		
	}
		
	public void reqdata() // calls various functions based on data requested.
	{
		if(Q1A)
		{
			Q1(gcon);
		}
		if(Q2A)
		{
			Q2(gcon);
		}
		if(Q3A)
		{
			Q3(gcon);
		}

		
	}
	
	
	public int Q1(Connection conn)
	{
		String Q1out = null;
		// Requesting team code based of team name input
		try
	    {
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Team Code\" FROM team WHERE \"Name\" = '"+ N_team1 +"';");

			while (result.next())
	    	{
	    		Q1team = result.getInt("Team Code");
	        }
			
	    	//create a statement object
	    	Statement stmt2 = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result2 = stmt2.executeQuery("SELECT \"Team Code\" FROM team WHERE \"Name\" = '"+ N_team2 +"';");

			while (result2.next())
	    	{
	    		Q1team2 = result2.getInt("Team Code");
	        }
			// END Requesting team code based of team name input
			
			//checking if team2 directly lost to team 1
			int h1 = 0;
			long h2 = 0;
			int h3 = 0;
			//create a statement object
	    	Statement stmt3 = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result3 = stmt3.executeQuery("SELECT \"Team Code\", team_game_statistics.\"Game Code\",team_game_statistics.\"Year\" FROM team_game_statistics INNER JOIN game ON game.\"Game Code\" = team_game_statistics.\"Game Code\" WHERE (game.\"Visit Team Code\" = '"+Q1team+"' OR game.\"Home Team Code\" = '"+Q1team+"') AND team_game_statistics.\"Win\" = 'f' AND \"team_game_statistics\".\"Team Code\" != '"+Q1team+"';");

			while (result3.next())
	    	{
	    		h1 = result3.getInt("Team Code");
	    		h2 = result3.getLong("Game Code");
	    		h3 = result3.getInt("Year");
	    		if (h1 == Q1team2)
	    		{
	    			Q1out = N_team1 + " beat " + N_team2 + " " + h3;
	    			AlertBox.display("Q1 Response", Q1out);
	    			return 0;
	    		}
	        }
			//END checking if team2 directly lost to team 1

			//Using DFS to find a team that team 1 beat that in turn beat team2
			//create a statement object
	    	Statement stmt4 = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result4 = stmt4.executeQuery("SELECT \"Team Code\", team_game_statistics.\"Game Code\",team_game_statistics.\"Year\" FROM team_game_statistics INNER JOIN game ON game.\"Game Code\" = team_game_statistics.\"Game Code\" WHERE (game.\"Visit Team Code\" = '"+Q1team+"' OR game.\"Home Team Code\" = '"+Q1team+"') AND team_game_statistics.\"Win\" = 'f' AND \"team_game_statistics\".\"Team Code\" != '"+Q1team+"';");

			while (result4.next())
	    	{
	    		h1 = result4.getInt("Team Code");
	    		h2 = result4.getLong("Game Code");
	    		h3 = result4.getInt("Year");
	    		String Q1_output = Q1_assist(gcon,h1,h2,h3);
	    		if(Q1_output != "0")
	    		{
	    			String Q1_op4 = null; // opposing team
	    	    	Statement stmt5 = conn.createStatement();
	    			ResultSet result5 = stmt5.executeQuery("SELECT \"Name\" FROM team WHERE \"Team Code\" = '"+ h1 +"';");
	    			while (result5.next())
	    			{
	    				Q1_op4 = result5.getString("Name");
	    			}
	    			Q1out = N_team1 + " beat " + Q1_op4 + " " + h3 +", " + Q1_output;
	    			AlertBox.display("Q1 Response", Q1out);
	    			return 0;
	    		}
	    		
	        }
			//END Using DFS to find a team that team 1 beat that in turn beat team2
			
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
	    }
		Q1out = "They still don't have bragging rights";
		AlertBox.display("Q1 Response", Q1out);
		return -1;

	}
	
	// Function that actually calls itself recursively to do the DFS for Q1
	public String Q1_assist(Connection conn, int TC, long GC, int year)
	{
		try {
			//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Team Code\", team_game_statistics.\"Game Code\",team_game_statistics.\"Year\" FROM team_game_statistics INNER JOIN game ON game.\"Game Code\" = team_game_statistics.\"Game Code\" WHERE (game.\"Visit Team Code\" = '"+TC+"' OR game.\"Home Team Code\" = '"+TC+"') AND team_game_statistics.\"Win\" = 'f' AND \"team_game_statistics\".\"Team Code\" != '"+TC+"';");
	    	
	    	int h1;
	    	long h2;
	    	int h3;
	    	String Q1_op42 = null;
	    	Statement stmt2 = conn.createStatement();
			ResultSet result2 = stmt2.executeQuery("SELECT \"Name\" FROM team WHERE \"Team Code\" = '"+ TC +"';");
			while (result2.next())
			{
				Q1_op42 = result2.getString("Name");
			}
			while (result.next())
	    	{
	    		h1 = result.getInt("Team Code");
	    		h2 = result.getLong("Game Code");
	    		h3 = result.getInt("Year");
    			String Q1_op43 = null;
    	    	Statement stmt3 = conn.createStatement();
    			ResultSet result3 = stmt3.executeQuery("SELECT \"Name\" FROM team WHERE \"Team Code\" = '"+ h1 +"';");
    			while (result3.next())
    			{
    				Q1_op43 = result3.getString("Name");
    			}
	    		if (h1 == Q1team2)
	    		{
	    			return Q1_op42 + " beat " + Q1_op43 + " " + h3;
	    		}
	    		else
	    		{
	    			String out = Q1_assist(gcon,h1,h2,h3);
	    			if(out != "0")
	    			{
	    				return Q1_op42 + " beat " + Q1_op43 + " " + h3 +", " + out;
	    			}
	    		}	
	    		
	    		
	        }
			
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
		}
		return "0";
	}
	
	
	
	
	
	
	public int Q2(Connection conn)
	{
		boolean same_team = false;
		boolean same_town = false;
		int player_code = 0;
		int team_code = 0;
		String home_town = null;
		String Game_path = null;
		ArrayList<Integer> Teams_p1 = new ArrayList<Integer>();
		ArrayList<Integer> Teams_p2 = new ArrayList<Integer>();
		ArrayList<String> Home_p1 = new ArrayList<String>();
		ArrayList<String> Home_p2 = new ArrayList<String>();
		//checks for connection at a team level
		try
	    {
			//fetching player code for both players
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Player Code\" FROM player WHERE \"Last Name\" = '" + N_player1l + "' AND \"First Name\" = '" + N_player1f + "' limit 1;");

			while (result.next())
	    	{
				Q2player1 = result.getInt("Player Code");
	        }
			
			
	    	//create a statement object
	    	Statement stmt2 = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result2 = stmt2.executeQuery("SELECT \"Player Code\" FROM player WHERE \"Last Name\" = '" + N_player2l + "' AND \"First Name\" = '" + N_player2f + "' limit 1;");

			while (result2.next())
	    	{
				Q2player2 = result2.getInt("Player Code");
	        }
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
	    }
		// END fetching player codes for both players
		
		// Tries to find Connection thru team
		try
	    {
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Player Code\", \"Team Code\" FROM player WHERE (\"Team Code\") IN (SELECT \"Team Code\" FROM player GROUP BY \"Team Code\" having count(*) > 1 ) AND ((\"Player Code\" = '" + Q2player1 + "') OR (\"Player Code\" = '" + Q2player2 + "')) order by \"Team Code\";");

			while (result.next())
	    	{
				player_code = result.getInt("Player Code");
	    		team_code = result.getInt("Team Code");

	    		if (player_code == Q2player1)
	    		{
	    			Teams_p1.add(team_code);
	    		}
	    		else if(player_code == Q2player2)
	    		{
	    			Teams_p2.add(team_code);
	    		}
	    		
	        }
			for(int i = 0; i < Teams_p2.size(); i++)
    		{
    			if(Teams_p1.contains(Teams_p2.get(i)))
    			{
    				same_team = true;
    				team_code = Teams_p2.get(i);
    			}
    		}
    		if(same_team)
    		{
    			//getting team name from opposing team code
    			String Q2_same_team = null;
    	    	Statement stmt2 = conn.createStatement();
    			ResultSet result2 = stmt2.executeQuery("SELECT \"Name\" FROM team WHERE \"Team Code\" = '"+ team_code +"';");
    			while (result2.next())
    			{
    				Q2_same_team = result2.getString("Name");
    			}
    			//END getting team name from opposing team code

    			String out1 = N_player1f + " " + N_player1l + " -> " + Q2_same_team + " -> " + N_player2f + " " + N_player2l;
    			AlertBox.display("Q2 Response", out1);
    			return 1;
    		}
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
	    }
		//END Tries to find Connection thru team
		
		//Tries to find Connection thru home town
		try
	    {
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Player Code\", \"Home Town\" FROM player WHERE (\"Team Code\") IN ( SELECT \"Team Code\" FROM player GROUP BY \"Team Code\" having count(*) > 1 ) AND ((\"Player Code\" = '" + Q2player1 + "') OR (\"Player Code\" = '" + Q2player2 + "')) order by \"Team Code\";");

			while (result.next())
	    	{
				player_code = result.getInt("Player Code");
	    		home_town = result.getString("Home Town");

	    		if (player_code == Q2player1)
	    		{
	    			Home_p1.add(home_town);
	    		}
	    		else if(player_code == Q2player2)
	    		{
	    			Home_p2.add(home_town);
	    		}
	    		
	        }
			for(int i = 0; i < Teams_p2.size(); i++)
    		{
    			if(Home_p1.contains(Home_p2.get(i)))
    			{
    				same_town = true;
    				home_town = Home_p2.get(i);
    			}
    		}
    		if(same_town)
    		{
    			String out1 = N_player1f + " " + N_player1l + " -> " + home_town + " -> " + N_player2f + " " + N_player2l;
    			AlertBox.display("Q2 Response", out1);
    			return 1;
    		}
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
	    }
		//END Tries to find Connection thru home town
		
		//Tries to find Connection thru games
		try
	    {
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("WITH RECURSIVE cte(\"Player Code\", \"Team Code\", \"First Name\", \"Last Name\", distance, message) AS (\n" + 
	    			"    SELECT player.\"Player Code\", player.\"Team Code\", player.\"First Name\", player.\"Last Name\", 0, ''\n" + 
	    			"    FROM player\n" + 
	    			"    WHERE player.\"First Name\" = '"+ N_player1f +"' AND player.\"Last Name\" = '"+ N_player1l +"'\n" + 
	    			"    UNION\n" + 
	    			"    SELECT player.\"Player Code\", player.\"Team Code\", player.\"First Name\", player.\"Last Name\", cte.distance + 1,\n" + 
	    			"        cte.message || '> ' || cte.\"First Name\" || ' ' || cte.\"Last Name\" || ' played in GameCode: '\n" + 
	    			"        || game.\"Game Code\" || ' with ' || player.\"First Name\" || ' ' || player.\"Last Name\" || ' '\n" + 
	    			"    FROM cte\n" + 
	    			"    JOIN team_game_statistics AS tgs1 ON (cte.\"Team Code\" = tgs1.\"Team Code\")\n" + 
	    			"    JOIN game ON (tgs1.\"Game Code\" = game.\"Game Code\")\n" + 
	    			"    JOIN team_game_statistics AS tgs2 ON (game.\"Game Code\" = tgs2.\"Game Code\")\n" + 
	    			"    JOIN player ON (tgs2.\"Team Code\" = player.\"Team Code\")\n" + 
	    			"    WHERE cte.\"Player Code\" <> player.\"Player Code\" AND cte.distance + 1 <= 1),\n" + 
	    			"    distance_table (\"Player Code\", distance) AS (\n" + 
	    			"        SELECT \"Player Code\", MIN(distance) AS distance\n" + 
	    			"        FROM cte\n" + 
	    			"        GROUP BY \"Player Code\")\n" + 
	    			"SELECT DISTINCT ON (\"Player Code\") message " +
	    			"FROM cte\n" + 
	    			"WHERE (\"Player Code\", distance) IN (SELECT * FROM distance_table) AND \"First Name\" = '"+ N_player2f +"' AND \"Last Name\" = '"+ N_player2l +"'\n" + 
	    			"ORDER BY \"Player Code\", \"distance\";");

			while (result.next())
	    	{
				Game_path = result.getString("message");
	        }
    		if(Game_path != null);
    		{
    			String out1 = Game_path;
    			AlertBox.display("Q2 Response", out1);
    			return 1;
    		}
	    }
	    catch (Exception e)
	    {
	    	 System.out.println(e);
	    }
		//END Tries to find Connection thru games
		AlertBox.display("Q2 Response", "There is no connection between these players.");
		return 0;

	}
	
	public void Q3(Connection conn)
	{
		ArrayList<Integer> Teams = new ArrayList<Integer>();
		
		// Requesting team code based of team name input
		try
	    {
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Team Code\" FROM team WHERE \"Name\" = '"+ N_team3 +"';");

			while (result.next())
	    	{
	    		Q3team = result.getInt("Team Code");
	        }
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
	    }
		// END Requesting team code based of team name input

		
		try
	    {
			//getting opposing team codes and storing them in teams arraylist
	    	//create a statement object
	    	Statement stmt = conn.createStatement();
	    	//create an SQL statement
	        //send statement to DBMS
	    	ResultSet result = stmt.executeQuery("SELECT \"Visit Team Code\", \"Home Team Code\" FROM game WHERE (\"Home Team Code\" = '"+ Q3team +"') OR (\"Visit Team Code\" = '"+ Q3team +"');");
			//System.out.println(N_team3);
	    	int h_team;
	    	int v_team;
	    	long cur_g_code;
	    	Game_Rush Current_Leader = new Game_Rush(0,0);
	    	Game_Rush Challenger = new Game_Rush(0,0);
			while (result.next())
	    	{
				v_team = result.getInt("Visit Team Code");
				h_team = result.getInt("Home Team Code");
				if((v_team == Q3team) && (!Teams.contains(h_team)))
				{
					Teams.add(h_team);
				}
				else if((h_team == Q3team) && (!Teams.contains(v_team)))
				{
					Teams.add(v_team);						
				}
			}
			// END getting opposing team codes and storing them in teams arraylist
			// Comparing all teams in array with their total rushing yards.
			for(int i = 0; i < Teams.size();i++)
			{
				Challenger.setYards_Rushed(0);
				if (i == 0)
				{
			    	Statement stmt2 = conn.createStatement();
			    	ResultSet result2 = stmt2.executeQuery("SELECT \"Game Code\" FROM game WHERE (((\"Home Team Code\" = '"+ Q3team +"') AND (\"Visit Team Code\" = '"+ Teams.get(i) +"'))) OR (((\"Home Team Code\" = '"+ Teams.get(i) +"') AND (\"Visit Team Code\" = '"+ Q3team +"')));");
					while (result2.next())
					{
						cur_g_code = result2.getLong("Game Code");
				    	Statement stmt3 = conn.createStatement();
						ResultSet result3 = stmt3.executeQuery("SELECT \"Rush Yard\" FROM team_game_statistics WHERE (\"Team Code\" = '"+ Teams.get(i) +"') AND (\"Game Code\" = '"+ cur_g_code +"');");
						while(result3.next())
						{
							Current_Leader.setYards_Rushed(result3.getInt("Rush Yard")+Current_Leader.getYards_Rushed());
							Current_Leader.setR_team(Teams.get(i));
						}

					}
				}
				else
				{
					Statement stmt2 = conn.createStatement();
					ResultSet result2 = stmt2.executeQuery("SELECT \"Game Code\" FROM game WHERE (((\"Home Team Code\" = '"+ Q3team +"') AND (\"Visit Team Code\" = '"+ Teams.get(i) +"'))) OR (((\"Home Team Code\" = '"+ Teams.get(i) +"') AND (\"Visit Team Code\" = '"+ Q3team +"')));");
					while (result2.next())
					{
						cur_g_code = result2.getLong("Game Code");
				    	Statement stmt3 = conn.createStatement();
						ResultSet result3 = stmt3.executeQuery("SELECT \"Rush Yard\" FROM team_game_statistics WHERE (\"Team Code\" = '"+ Teams.get(i) +"') AND (\"Game Code\" = '"+ cur_g_code +"');");
						while(result3.next())
						{
							Challenger.setYards_Rushed(result3.getInt("Rush Yard")+Challenger.getYards_Rushed());
							Challenger.setR_team(Teams.get(i));
						}
						if(Current_Leader.getYards_Rushed() < Challenger.getYards_Rushed())
						{
							Current_Leader.setR_team(Challenger.getR_team());
							Current_Leader.setYards_Rushed(Challenger.getYards_Rushed());
						}

					}

				}
		    	
			}
			//END Comparing all teams in array with their total rushing yards.
			//getting team name from opposing team code
			String Q3_rush_team = null;
	    	Statement stmt4 = conn.createStatement();
			ResultSet result4 = stmt4.executeQuery("SELECT \"Name\" FROM team WHERE \"Team Code\" = '"+ Current_Leader.getR_team() +"';");
			while (result4.next())
			{
				Q3_rush_team = result4.getString("Name");
			}
			//END getting team name from opposing team code
			//Printing out team with most rushing yards and their yards
			Integer out = Current_Leader.getYards_Rushed();
			String output = out.toString();
			String Final_output = "The Team with most rushing yards against " + N_team3 + " was " + Q3_rush_team + " with " + output + " yards";
			AlertBox.display("Q3 Response", Final_output);
			//END Printing out team with most rushing yards and their yards
	    }
	    catch (Exception e)
	    {
	    	 System.out.println("Error accessing Database.");
	    }

		
	}
	
	
	public static void main(String args[])
	{
		launch(args);
	}//end main




}//end Class

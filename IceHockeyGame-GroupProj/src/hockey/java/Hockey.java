package hockey.java;


import hockey.java.controller.GameController;
import hockey.java.controller.GameOverController;
import hockey.java.controller.LoggedController;
import hockey.java.controller.LoginController;
import hockey.java.controller.MenuController;
import hockey.java.controller.SignupController;
import hockey.java.controller.StatsController;
import hockey.java.controller.WaitController;
import hockey.java.front.User;
import hockey.java.network.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// ClientProgram
public class Hockey extends Application {
	
	private static Network network = new Network();
	private static User user = null;// = new User(); 
	
	public static final int NUMSCENES = 8;
	private static Scene[] scenes = new Scene[NUMSCENES];
	private static Parent gameRoot;

	private static Stage primaryStage;
	private static Scene menuScene, loginScene, signupScene, loggedScene, statsScene, gameScene, waitScene, gameOverScene;
	private static MenuController menuController;
	private static LoginController loginController;
	private static SignupController signupController;
	private static LoggedController loggedController;
	private static StatsController statsController;
	private static GameController  gameController;
	private static WaitController waitController;
	private static GameOverController  gameOverController;

	public static void setUser(User user) {
		Hockey.user = user;
	}

	public static User getUser() {
		return user;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Hockey.primaryStage = primaryStage;
		
		// https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
		// FXML
		//Parent root = FXMLLoader.load(getClass().getResource("/hockey/fxml/Menu.fxml"));

		/*** All Scenes Start ***/
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Menu.fxml"));
		Parent menuRoot = (Parent)menuLoader.load();
		menuScene = new Scene(menuRoot);
		scenes[0] = menuScene;
		
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Login.fxml"));
		Parent loginRoot = (Parent)loginLoader.load();
		loginScene = new Scene(loginRoot);
		scenes[1] = loginScene;
		
		FXMLLoader signupLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Signup.fxml"));
		Parent signupRoot = (Parent)signupLoader.load();	
		signupScene = new Scene(signupRoot);
		scenes[2] = signupScene;
		
		FXMLLoader loggedLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Logged.fxml"));
		Parent loggedRoot = (Parent)loggedLoader.load();	
		loggedScene = new Scene(loggedRoot);
		scenes[3] = loggedScene;
		
		FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Stats.fxml"));
		Parent statsRoot = (Parent)statsLoader.load();	
		statsScene = new Scene(statsRoot);
		scenes[4] = statsScene;
		
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Game.fxml"));
		gameRoot = (Parent)gameLoader.load();	
		gameScene = new Scene(gameRoot);
		scenes[5] = gameScene;

		FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/GameOver.fxml"));
		Parent gameOverRoot = (Parent)gameOverLoader.load();
		gameOverScene = new Scene(gameOverRoot);
		scenes[6] = gameOverScene;

		FXMLLoader waitLoader = new FXMLLoader(getClass().getResource("/hockey/fxml/Wait.fxml"));
		Parent waitRoot = (Parent)waitLoader.load();	
		waitScene = new Scene(waitRoot);
		scenes[7] = waitScene;
		/*** All Scenes End ***/
		
		/*** All Controllers Start ***/
		menuController = menuLoader.getController();
		loginController = loginLoader.getController();
		signupController = signupLoader.getController();
		loggedController = loggedLoader.getController();
		statsController = statsLoader.getController();
		gameController = gameLoader.getController();
		gameOverController = gameOverLoader.getController();
		waitController = waitLoader.getController();
		/*** All Controllers End ***/

		
		primaryStage.setScene(menuScene);
		primaryStage.show();
		
		// DEBUG
		//primaryStage.setScene(gameScene);
		//System.out.println("menuRoot is " + menuRoot.getClass());
		
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Network getNetwork() {
		return network;
	}

	public static void setNetwork(Network network) {
		Hockey.network = network;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Hockey.primaryStage = primaryStage;
	}

	public static Scene getMenuScene() {
		return menuScene;
	}

	public static void setMenuScene(Scene menuScene) {
		Hockey.menuScene = menuScene;
	}

	public static Scene getLoginScene() {
		return loginScene;
	}

	public static void setLoginScene(Scene loginScene) {
		Hockey.loginScene = loginScene;
	}

	public static Scene getSignupScene() {
		return signupScene;
	}

	public static void setSignupScene(Scene signupScene) {
		Hockey.signupScene = signupScene;
	}

	public static Scene getLoggedScene() {
		return loggedScene;
	}

	public static void setLoggedScene(Scene loggedScene) {
		Hockey.loggedScene = loggedScene;
	}

	public static Scene getStatsScene() {
		return statsScene;
	}

	public static void setStatsScene(Scene statsScene) {
		Hockey.statsScene = statsScene;
	}

	public static Scene getGameScene() {
		return gameScene;
	}

	public static void setGameScene(Scene gameScene) {
		Hockey.gameScene = gameScene;
	}

	public static void setGameOverScene(Scene gameOverScene) {
		Hockey.gameOverScene = gameOverScene;
	}
	
	public static Scene getGameOverScene() {
		return gameOverScene;
	}

	public static MenuController getMenuController() {
		return menuController;
	}

	public static void setMenuController(MenuController menuController) {
		Hockey.menuController = menuController;
	}

	public static LoginController getLoginController() {
		return loginController;
	}

	public static void setLoginController(LoginController loginController) {
		Hockey.loginController = loginController;
	}

	public static SignupController getSignupController() {
		return signupController;
	}

	public static void setSignupController(SignupController signupController) {
		Hockey.signupController = signupController;
	}

	public static LoggedController getLoggedController() {
		return loggedController;
	}

	public static void setLoggedController(LoggedController loggedController) {
		Hockey.loggedController = loggedController;
	}

	public static StatsController getStatsController() {
		return statsController;
	}

	public static void setStatsController(StatsController statsController) {
		Hockey.statsController = statsController;
	}

	public static GameController getGameController() {
		return gameController;
	}

	public static void setGameController(GameController gameController) {
		Hockey.gameController = gameController;
	}
	
	public static GameOverController getGameOverController() {
		return gameOverController;
	}

	public static void setGameOverController(GameOverController gameOverController) {
		Hockey.gameOverController = gameOverController;
	}

	public static Parent getGameRoot() {
		return gameRoot;
	}

	public static void setGameRoot(Parent gameRoot) {
		Hockey.gameRoot = gameRoot;
	}

	public static Scene getWaitScene() {
		return waitScene;
	}

	public static void setWaitScene(Scene waitScene) {
		Hockey.waitScene = waitScene;
	}


	public static WaitController getWaitController() {
		return waitController;
	}

	public static void setWaitController(WaitController waitController) {
		Hockey.waitController = waitController;
	}

}
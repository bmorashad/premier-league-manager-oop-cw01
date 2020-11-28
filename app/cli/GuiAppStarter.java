package cli;
import java.io.File;
import java.net.URI;
import java.awt.Desktop;

public final class GuiAppStarter {
	private static ProcessBuilder processBuilder;
	private static Process serverProcess;
	private static Process appProcess;
	private final static String appDir ="/home/bmora/Documents/Learning/LinkedInLearning/SpringFramework/Learning Spring with Spring Boot/Exercise Files/Chapter5/05_02/05_02_end/learning-spring";
	// private final static String appDir ="/home/bmora/Documents/PlayFrameworkPractice/premier-league-api/example/target/universal/example-1.0-SNAPSHOT/bin";
	private final static String appPort = "http://localhost:8080";
	// private final static String appPort = "http://localhost:9000/app/html/professional";

	private GuiAppStarter() {}

	public static void start() {
		if(serverProcess == null) {
			processBuilder = new ProcessBuilder();
			File commandDir = new File(appDir);
			processBuilder.command("bash", "-c", "mvn spring-boot:run");
			// processBuilder.command("bash", "-c", 
					// "./example -Dplay.http.secret.key=\'QCY?tAnfk?aZ?iwrNwnxIlR6CTf:G3gf:90Latabg@5241AB`R5W:1uDFN];Ik@n\'");
			processBuilder.directory(commandDir);
			try {
				serverProcess = processBuilder.start();
			} catch (Exception e) {
				System.out.println("Error: something went wrong when starting the app");
				e.printStackTrace();
			}
		}
	}
	public static void stop() {
		stopServer();
		closeApp();
	}

	// helper methods to stop server and to close gui app
	private static void closeApp() {
		if (appProcess !=null) {
			appProcess.destroy();
			if(appProcess.isAlive()) {
				appProcess.destroyForcibly();
			}
		} 
	}
	private static void stopServer() {
		if (serverProcess !=null) {
			serverProcess.destroy();
			if(serverProcess.isAlive()) {
				serverProcess.destroyForcibly();
			}
		} 
	}

	public static void open() {
		// if (appProcess != null && appProcess.isAlive()) {
		// System.out.println("App is already opened");
		// return;
		// } 
		processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "google-chrome-stable --app=" + appPort);
		try {
			appProcess = processBuilder.start();
		} catch (Exception e) {
			System.out.println("Error: something went wrong when starting the app");
			e.printStackTrace();
		}
	}


	@Deprecated
	public static void openApp() {
		try {
			URI uri = new URI(appPort);
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

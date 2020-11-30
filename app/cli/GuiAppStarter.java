package cli;
import java.net.URI;
import java.awt.Desktop;
import conf.PathConfiguration;

public final class GuiAppStarter {
	private static ProcessBuilder processBuilder;
	private static Process serverProcess;
	private static Process appProcess;
	private final static String startScript = PathConfiguration.projectFinalBuildExecutable;
	private final static String serverPID = PathConfiguration.projectRoot + "premier-league-manager/premier-league/target/universal/stage/RUNNING_PID";
	private final static String appPort = "http://localhost:9000/app/premier-league";

	private GuiAppStarter() {}

	public static void start() {
		if(serverProcess == null) {
			processBuilder = new ProcessBuilder();
			processBuilder.command("bash", "-c", startScript);
			try {
				serverProcess = processBuilder.start();
				Thread.sleep(3000);
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
		processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "rm", serverPID);
		Process removePIDFile = null;
		try {
			removePIDFile = processBuilder.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (serverProcess !=null) {
			serverProcess.destroy();
			if(serverProcess.isAlive()) {
				serverProcess.destroyForcibly();
			}
		} 
		if (removePIDFile != null) {
			removePIDFile.destroy();
			if(removePIDFile.isAlive()){
				removePIDFile.destroyForcibly();
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

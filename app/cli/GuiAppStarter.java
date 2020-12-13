package cli;
import java.net.URI;
import java.awt.Desktop;
import java.io.File;

import conf.PathConfiguration;

public final class GuiAppStarter {
	private static ProcessBuilder processBuilder;
	private static Process serverProcess;
	private static Process appProcess;
	public static final String projectFinalBuildExecutablePath = PathConfiguration.projectRoot + "target/universal/stage/bin/"; 
	private final static String startScript = "./premier-league";
	private final static String serverPIDPath = PathConfiguration.projectRoot + "target/universal/stage/";
	private final static String appPort = "http://localhost:9000/app/premier-league";

	private GuiAppStarter() {}

	public static void start() {
		removeAndStopPID();
		if(serverProcess == null) {
			processBuilder = new ProcessBuilder();
			processBuilder.directory(new File(projectFinalBuildExecutablePath));
			processBuilder.command("bash", "-c", startScript);
			try {
				serverProcess = processBuilder.start();
			} catch (Exception e) {
				System.out.println("Error: something went wrong when starting the app");
				e.printStackTrace();
			}
		}
		try {
			System.out.print("Starting server...\r");
			Thread.sleep(3000);
			System.out.print("");
		} catch(Exception e) {
			e.printStackTrace();
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
	private static void removeAndStopPID() {
		processBuilder = new ProcessBuilder();
		processBuilder.directory(new File(serverPIDPath));
		try {
			processBuilder.command("bash", "-c", "kill `cat RUNNING_PID`");
			processBuilder.start();
			processBuilder.command("bash", "-c", "rm RUNNING_PID");
			processBuilder.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void stopServer() {
		if (serverProcess !=null) {
			serverProcess.destroy();
			if(serverProcess.isAlive()) {
				serverProcess.destroyForcibly();
			}
		} 
		removeAndStopPID();
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

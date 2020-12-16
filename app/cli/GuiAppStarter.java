package cli;
import java.net.URI;
import java.awt.Desktop;
import java.io.File;

import conf.PathConfiguration;

public final class GuiAppStarter {
	private final static String OS;
	public static final String SERVER_EXECUTABLE = PathConfiguration.projectRoot + "target/universal/stage/bin/"; 
	private final static String START_SCRIPT = "./premier-league";
	private final static String SERVER_PID = PathConfiguration.projectRoot + "target/universal/stage/";
	private final static String APP_PORT = "http://localhost:9000/app/premier-league";
	private static ProcessBuilder processBuilder;
	private static Process serverProcess;
	private static Process appProcess;
	static {
		OS = System.getProperty("os.name").toLowerCase();
	}
	private GuiAppStarter() {}

	public static void start() {
		removeAndStopPID();
		if(serverProcess == null) {
			processBuilder = new ProcessBuilder();
			processBuilder.directory(new File(SERVER_EXECUTABLE));
			if (isWindows()) {
				processBuilder.command("cmd.exe", "/c", "start " + START_SCRIPT + ".bat");
			} else {
				processBuilder.command("bash", "-c", START_SCRIPT);
			}
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
		processBuilder.directory(new File(SERVER_PID));
		try {
			if(isWindows()) {
				processBuilder.command("cmd.exe", "/c", "taskkill /F /PID `type RUNNING_PID`");
	 		} else {
				processBuilder.command("bash", "-c", "kill `cat RUNNING_PID`");
			}
			processBuilder.start();
			if(isWindows()) {
				processBuilder.command("cmd.exe", "/c", "del RUNNING_PID");
			} else {
				processBuilder.command("bash", "-c", "rm RUNNING_PID");
			}
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
		if(isWindows()) {
			processBuilder.command("cmd.exe", "/c", "start chrome --app=" + APP_PORT);
		} else {
			processBuilder.command("bash", "-c", "google-chrome-stable --app=" + APP_PORT);
		}
		try {
			appProcess = processBuilder.start();
		} catch (Exception e) {
			System.out.println("Error: something went wrong when starting the app");
			e.printStackTrace();
		}
	}

	private static boolean isWindows() {
		return OS.contains("windows");
	}
	@Deprecated
	public static void openApp() {
		try {
			URI uri = new URI(APP_PORT);
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

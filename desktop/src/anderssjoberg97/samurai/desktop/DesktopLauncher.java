package anderssjoberg97.samurai.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import anderssjoberg97.samurai.Samurai;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Samurai";
		config.width = 800;
		config.height = 480;
		
		new LwjglApplication(new Samurai(), config);
	}
}

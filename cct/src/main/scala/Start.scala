import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

object Start extends App {
  val cfg = new LwjglApplicationConfiguration()
  cfg.title = "Ctt"
  cfg.width = 1280
  cfg.height = 720

  new LwjglApplication(new AppList(), cfg)
}

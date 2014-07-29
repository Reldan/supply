import com.badlogic.gdx.{Game, ApplicationListener}

class AppList extends Game {
  override def create(): Unit = setScreen(new GameScreen())
}

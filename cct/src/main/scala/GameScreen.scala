import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.{Gdx, Screen}

class GameScreen extends Screen {
  val world = new World(100, 100)
  val renderer = new WorldRenderer(world)

  override def render(delta: Float) {
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    renderer.render()
  }

  override def hide(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def dispose(): Unit = {}

  override def pause(): Unit = {}

  override def show(): Unit = {}

  override def resume(): Unit = {}
}

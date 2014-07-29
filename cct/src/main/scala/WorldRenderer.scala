import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType

class WorldRenderer(world: World) {
  val debugRenderer = new ShapeRenderer()
  //create helper
  private val cam = new OrthographicCamera(10, 7)
  cam.position.set(5, 3.5f, 0)
  cam.update()


  def render() {
    // render blocks
    debugRenderer.setProjectionMatrix(cam.combined)
    debugRenderer.begin(ShapeType.Line)

    for (y <- 0 until world.heightFields;
         x <- 0 until world.widthFields) {
      debugRenderer.setColor(world.fields(y)(x).getColor)
      debugRenderer.rect(x * 1f, y * 1f, 1f, 1f)
    }
    debugRenderer.end()


//    for (Block block : world.getBlocks()) {
//      val rect = block.getBounds()
//      float x1 = block.getPosition().x + rect.x
//      float y1 = block.getPosition().y + rect.z
//      debugRenderer.setColor(new Color(1, 0, 0, 1))
//      debugRenderer.rect(x1, y1, rect.width, rect.height)
//    }
//    // render Bob
//    Bob bob = world.getBob()
//    Rectangle rect = bob.getBounds()
//    float x1 = bob.getPosition().x + rect.x
//    float y1 = bob.getPosition().y + rect.y
//    debugRenderer.setColor(new Color(0, 1, 0, 1))
//    debugRenderer.rect(x1, y1, rect.width, rect.height)
//    debugRenderer.end()

  }

}

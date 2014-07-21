package com.supply.game.libgdx

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d._
import com.badlogic.gdx.graphics.g3d.model.data.{ModelMeshPart, ModelMesh, ModelData}
import com.badlogic.gdx.graphics.g3d.utils.{MeshBuilder, ModelBuilder}
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.{Gdx, ApplicationListener}
import com.badlogic.gdx.graphics._
import collection.JavaConversions._

class TestAppScala extends ApplicationListener {
  private var camera: PerspectiveCamera = null
  private var modelBatch: ModelBatch = null
  private var box: Model = null
  private var boxInstance: ModelInstance = null
  private var environment: Environment = null
  private var mesh: Mesh = null

  def create() {
    camera = new PerspectiveCamera(75, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    camera.position.set(0f, 0f, 3f)
    camera.lookAt(0f, 0f, 0f)
    camera.near = 0.1f
    camera.far = 300.0f
    modelBatch = new ModelBatch
    val meshBuilder = new MeshBuilder()

    val modelBuilder = new ModelBuilder
//    meshBuilder.triangle()
    meshBuilder.begin(Usage.Position | Usage.Normal, GL20.GL_TRIANGLES)
    mesh = meshBuilder.end()

    val modelMesh = new ModelMesh()

    val t3: Array[ModelMeshPart] = modelMesh.parts
//    modelMesh.parts = Array(meshBuilder.end())
//    modelData.meshes.add(meshBuilder.end())



    box = modelBuilder.createBox(2f, 2f, 2f, new Material(ColorAttribute.createDiffuse(Color.BLUE)), Usage.Position | Usage.Normal)
    boxInstance = new ModelInstance(box, 0, 0, 0)
    environment = new Environment
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f))
    environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -10f, -10f, -10f))
  }

  def dispose() {
    modelBatch.dispose()
    box.dispose()
  }

  def render() {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    Gdx.gl.glClearColor(1, 1, 1, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    camera.rotateAround(Vector3.Zero, new Vector3(0, 1, 0), 1f)
    camera.update()
    modelBatch.begin(camera)
    modelBatch.render(boxInstance, environment)
    modelBatch.end()
  }

  def resize(width: Int, height: Int) {
  }

  def pause() {
  }

  def resume() {
  }

}

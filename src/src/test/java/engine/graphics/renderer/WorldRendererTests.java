package engine.graphics.renderer;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Shader;
import engine.graphics.Vertex3D;
import engine.graphics.image.Image;
import engine.graphics.mesh.Mesh;
import engine.graphics.model.Model;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import engine.utils.ColourUtils;
import java.awt.Color;
import java.util.concurrent.TimeUnit;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorldRendererTests {
  static final String TEST_IMAGES_DIRECTORY = "/images/";
  static final String[] TEST_IMAGES_NAMES = {
      "bad-tile.png",
      "default_texture.png",
      "mid-tier-tile.png",
      "water-tile.png"
  };
  static final int LOOP_MAX = 100;
  static final String SHADERS_PATH = "/shaders/";
  static final String VERTEX_DIR = "vertex/";
  static final String FRAGMENT_DIR = "fragment/";
  static final String VERTEX_COLOUR_SHADER_FILE_NAME = "testColourVertex.glsl";
  static final String FRAGMENT_COLOUR_SHADER_FILE_NAME = "testColourFragment.glsl";
  static final String VERTEX_IMAGE_SHADER_FILE_NAME = "testImageVertex.glsl";
  static final String FRAGMENT_IMAGE_SHADER_FILE_NAME = "testImageFragment.glsl";
  static final String VERTEX_MATRICES_FILE_NAME = "testMatricesVertex.glsl";
  static final String FRAGMENT_MATRICES_FILE_NAME = "testMatricesFragment.glsl";
  static final int WINDOW_HEIGHT = 600;
  static final int WINDOW_WIDTH = 800;
  static final String WINDOW_TITLE = "Test Window";
  static int[] rectangleIndices = {0, 1, 2, 3};
  static int[] triangleIndices = {0, 1, 2};
  static int[] imageRectangleIndices = rectangleIndices;
  Vertex3D topLeftVertex = new Vertex3D(new Vector3f(-0.5f, 0.5f, 0));
  Vertex3D topRightVertex = new Vertex3D(new Vector3f(0.5f, 0.5f, 0));
  Vertex3D topCenterVertex = new Vertex3D(new Vector3f(0f, 0.5f, 0));
  Vertex3D bottomLeftVertex = new Vertex3D(new Vector3f(-0.5f, -0.5f, 0));
  Vertex3D bottomRightVertex = new Vertex3D(new Vector3f(0.5f, -0.5f, 0));
  Vertex3D[] rectangleVertices = {topLeftVertex, topRightVertex, bottomLeftVertex,
      bottomRightVertex};
  Vertex3D[] triangleVertices = {topCenterVertex, bottomLeftVertex, bottomRightVertex};
  Vertex3D topLeftImageVertex = new Vertex3D(new Vector3f(-0.5f, 0.5f, 0),
      ColourUtils.convertColor(Color.WHITE), new Vector3f(0f, 0f, 0));
  Vertex3D topRightImageVertex = new Vertex3D(new Vector3f(0.5f, 0.5f, 0),
      ColourUtils.convertColor(Color.WHITE), new Vector3f(1f, 0f, 0));
  Vertex3D bottomLeftImageVertex = new Vertex3D(new Vector3f(-0.5f, -0.5f, 0),
      ColourUtils.convertColor(Color.WHITE), new Vector3f(0f, 1f, 0));
  Vertex3D bottomRightImageVertex = new Vertex3D(new Vector3f(0.5f, -0.5f, 0),
      ColourUtils.convertColor(Color.WHITE), new Vector3f(1f, 1f, 0));
  Vertex3D[] imageRectangleVertices = {topLeftImageVertex, topRightImageVertex,
      bottomLeftImageVertex, bottomRightImageVertex};
  private int sleepTime = 1;
  private Window window;
  private WorldRenderer worldRenderer;
  private Shader shader;
  private Camera camera;

  @BeforeEach
  public void setup() {
    assumeTrue(glfwInit());
  }

  /**
   * Sets .
   */
  public void setupWindow(String vertexFileName, String fragmentFileName) {
    camera = new Camera(new Vector3f(0, 0, 10f), new Vector3f(0, 0, 0));
    shader = new Shader(SHADERS_PATH + VERTEX_DIR + vertexFileName,
        SHADERS_PATH + FRAGMENT_DIR + fragmentFileName);
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
    window.setBackgroundColour(1.0f, 1.0f, 1.0f, 1.0f);
    worldRenderer = new WorldRenderer(window, shader);
    window.create();
    shader.create();
  }


  private void drawObject(GameObject object) throws InterruptedException {
    window.update();
    object.getMesh().create();
    worldRenderer.renderObject(object, camera);
    window.swapBuffers();
    window.update();
    TimeUnit.SECONDS.sleep(sleepTime);
  }

  private void shutDownWindow() {
    window.destroy();
    shader.destroy();
  }

  // Testing fundamental draw functionality
  @Test
  public void testRectangle() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(rectangleVertices, rectangleIndices), new Material());
    GameObject testObject = new GameObject(testMesh);
    drawObject(testObject);
  }

  @Test
  public void testTriangle() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(triangleVertices, triangleIndices), new Material());
    GameObject testObject = new GameObject(testMesh);
    drawObject(testObject);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  public void testBoth() {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh rectangleMesh = new Mesh(new Model(rectangleVertices, rectangleIndices), new Material());
    GameObject rectangleObject = new GameObject(rectangleMesh);
    Mesh triangleMesh = new Mesh(new Model(triangleVertices, triangleIndices), new Material());
    GameObject triangleObject = new GameObject(triangleMesh);

    for (int i = 0; i < 100; i++) {
      triangleMesh.create();
      rectangleMesh.create();
      window.update();
      worldRenderer.renderObject(triangleObject, camera);
      worldRenderer.renderObject(rectangleObject, camera);
      window.swapBuffers();
    }

    triangleMesh.destroy();
    rectangleMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testBlueSquare() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(rectangleVertices, rectangleIndices), new Material());
    testMesh.getModel().setColour(ColourUtils.convertColor(ChartColor.BLUE));
    GameObject testObject = new GameObject(testMesh);
    drawObject(testObject);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testPinkTriangle() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(triangleVertices, triangleIndices), new Material());
    testMesh.getModel().setColour(ColourUtils.convertColor(ChartColor.PINK));
    GameObject testObject = new GameObject(testMesh);
    drawObject(testObject);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testDefaultImage() throws InterruptedException {
    setupWindow(VERTEX_IMAGE_SHADER_FILE_NAME, FRAGMENT_IMAGE_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(imageRectangleVertices, imageRectangleIndices),
        new Material());
    GameObject testObject = new GameObject(testMesh);
    drawObject(testObject);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testExistingImages() throws InterruptedException {
    for (String testImage : TEST_IMAGES_NAMES) {
      setupWindow(VERTEX_IMAGE_SHADER_FILE_NAME, FRAGMENT_IMAGE_SHADER_FILE_NAME);
      Mesh testMesh = new Mesh(new Model(imageRectangleVertices, imageRectangleIndices),
          new Material(new Image(TEST_IMAGES_DIRECTORY + testImage)));
      GameObject testObject = new GameObject(testMesh);
      drawObject(testObject);
      testMesh.destroy();
      shutDownWindow();
    }
  }

  // Testing Uniforms
  @Test
  public void testUniformScale() throws InterruptedException {
    setupWindow(VERTEX_IMAGE_SHADER_FILE_NAME, FRAGMENT_IMAGE_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(imageRectangleVertices, imageRectangleIndices),
        new Material());
    this.sleepTime = 0;
    double scale = 0.1;
    for (int i = 0; i < LOOP_MAX; i++) {
      shader.bind();
      shader.setUniform("scale", (float) Math.sin(scale));
      shader.unbind();
      GameObject testObject = new GameObject(testMesh);
      drawObject(testObject);
      scale += 0.02;
    }
    this.sleepTime = 1;
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  public void testScaleMatrix() throws InterruptedException {
    setupWindow(VERTEX_MATRICES_FILE_NAME, FRAGMENT_MATRICES_FILE_NAME);
    Mesh testMesh = new Mesh(new Model(imageRectangleVertices, imageRectangleIndices),
        new Material());
    GameObject testObject = new GameObject(testMesh);
    this.sleepTime = 0;
    for (int i = 0; i < LOOP_MAX; i++) {
      float val = (float) Math.sin(i / 100.0);
      drawObject(testObject);
      testObject.getScale().set(new Vector3f(val, val, val));
    }
    this.sleepTime = 1;
    testMesh.destroy();
    shutDownWindow();
  }
}

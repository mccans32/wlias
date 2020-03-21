package game.menu;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.io.Input;
import engine.objects.gui.ButtonObject;
import engine.objects.gui.GuiImage;
import engine.objects.world.Camera;
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import game.world.Gui;
import game.world.World;
import java.awt.Color;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;

public class MainMenu {
  private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_CYAN.brighter());
  private static final Vector3f TEXT_COLOUR = ColourUtils.convertColor(Color.WHITE);
  private static final float BUTTON_FONT_SIZE = 2.2f;
  private static final float BUTTON_WIDTH = 0.7f;
  private static final float BUTTON_HEIGHT = 0.3f;
  private static final String BUTTON_TEXTURE = "/images/buttonTexture.png";
  private static final String BACKGROUND_TEXTURE = "/images/mainMenuBackground.jpg";
  private static float[] START_REPOSITION_VALUES = {0, 0, 1, -0.6f};
  private static float[] EXIT_REPOSITION_VALUES = {0, 0, -1, 0.6f};
  private static ButtonObject startButton;
  private static ButtonObject exitButton;
  private static ButtonObject[] buttons = new ButtonObject[2];
  private static GuiImage backgroundImage;

  /**
   * Create.
   *
   * @param window the window
   */
  public static void create(Window window, Camera camera) {
    camera.freeze();
    setBackgroundColour(BACKGROUND_COLOUR, window);
    createButtons();
    createBackground();
  }

  /**
   * Update.
   *
   * @param window the window
   */
  public static void update(Window window, Camera camera) {
    resize();
    updateButtons(window);
    checkButtonClick(window, camera);
  }

  /**
   * Destroy.
   */
  public static void destroy() {
    // Destroy Buttons
    for (ButtonObject button : buttons) {
      button.destroy();
    }
  }


  private static void updateButtons(Window window) {
    for (ButtonObject button : buttons) {
      button.update(window);
    }
  }

  /**
   * Check button click.
   *
   * @param window the window
   */
  public static void checkButtonClick(Window window, Camera camera) {
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      if (startButton.isMouseOver(window)) {
        // Change the Game State
        Game.setState(GameState.GAME);
        // Destroy the Main Menu
        destroy();
        // Create the Gui
        Gui.create();
        // Create the World
        World.create(camera);
      } else if (exitButton.isMouseOver(window)) {
        GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
      }
    }
  }

  public static ButtonObject[] getButtonObjects() {
    return buttons.clone();
  }

  private static void createButtons() {
    // Create Start Button
    Text startButtonText = new Text("Start", BUTTON_FONT_SIZE, TEXT_COLOUR);
    startButtonText.setCentreHorizontal(true);
    startButtonText.setCentreVertical(true);
    startButton = initButton(startButtonText, START_REPOSITION_VALUES[0],
        START_REPOSITION_VALUES[1], START_REPOSITION_VALUES[2], START_REPOSITION_VALUES[3]);
    startButton.create();
    buttons[0] = startButton;
    // Create Exit Button
    Text exitButtonText = new Text("Exit", BUTTON_FONT_SIZE, TEXT_COLOUR);
    exitButtonText.setCentreHorizontal(true);
    exitButtonText.setCentreVertical(true);
    exitButton = initButton(exitButtonText, EXIT_REPOSITION_VALUES[0],
        EXIT_REPOSITION_VALUES[1], EXIT_REPOSITION_VALUES[2], EXIT_REPOSITION_VALUES[3]);
    exitButton.create();
    buttons[1] = exitButton;
  }

  /**
   * Render.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer,
                            GuiRenderer backgroundRenderer) {
    startButton.render(guiRenderer, textRenderer);
    exitButton.render(guiRenderer, textRenderer);
    backgroundImage.render(backgroundRenderer);
  }

  /**
   * Resize.
   */
  public static void resize() {
    startButton.reposition();
    exitButton.reposition();
  }

  private static void setBackgroundColour(Vector3f colour, Window window) {
    window.setBackgroundColour(colour.getX(), colour.getY(), colour.getZ(), 1f);
  }

  private static ButtonObject initButton(
      Text buttonText,
      float edgeX,
      float offsetX,
      float edgeY,
      float offsetY) {
    RectangleMesh tempMesh = new RectangleMesh(BUTTON_WIDTH, BUTTON_HEIGHT,
        new Material(BUTTON_TEXTURE));

    return new ButtonObject(tempMesh, buttonText, edgeX, offsetX, edgeY, offsetY);
  }

  private static void createBackground() {
    RectangleMesh backgroundMesh = new RectangleMesh(2, 2, new Material(BACKGROUND_TEXTURE));
    backgroundImage = new GuiImage(backgroundMesh, -1, 1, 1, 1);
    backgroundImage.create();
  }
}
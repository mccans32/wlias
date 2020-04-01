package engine.graphics.renderer;

import engine.Window;
import engine.graphics.Shader;
import engine.objects.gui.HudImage;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * The type WorldRenderer.
 */
public class GuiRenderer {
  private static Vector2f DEFAULT_ROTATION = new Vector2f(0, 0);
  protected Shader shader;
  protected Window window;

  public GuiRenderer(Window window, Shader shader) {
    this.shader = shader;
    this.window = window;
  }


  /**
   * Render mesh.
   *
   * @param object the object.
   */
  public void renderObject(HudImage object) {
    bindObject(object);
    drawObject(object);
    unbindObject();
  }

  private void bindObject(HudImage object) {
    // Bind Mesh VAO
    GL30.glBindVertexArray(object.getMesh().getModel().getVao());
    // Enable Index 0 for Shaders (Position)
    GL30.glEnableVertexAttribArray(0);
    // Enable Index 1 for Shaders (Colour)
    GL30.glEnableVertexAttribArray(1);
    // Enable Index 2 for Shaders (Texture)
    GL30.glEnableVertexAttribArray(2);
    // Bind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getModel().getIbo());
    // Set Active Texture
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    // Bind the Texture
    GL13.glBindTexture(GL11.GL_TEXTURE_2D,
        object.getMesh().getMaterial().getImage().getTextureID());
    //Bind Shader
    shader.bind();
    // Set Uniforms
    setUniforms(object);

  }

  private void setUniforms(HudImage object) {
    setModelUniform(object);
    setProjectionUniform();
    setColourOffsetUniform(object);
  }

  private void setColourOffsetUniform(HudImage object) {
    shader.setUniform("colourOffset", object.getMesh().getMaterial().getColorOffsetRgb());
  }

  private void setModelUniform(HudImage object) {
    shader.setUniform(
        "model",

        Matrix4f.transform(
            new Vector3f(object.getPosition().getX(), object.getPosition().getY(), 0f),
            new Vector3f(DEFAULT_ROTATION.getX(), DEFAULT_ROTATION.getY(), 0),
            new Vector3f(1, 1, 1)));
  }

  protected void setProjectionUniform() {
    shader.setUniform("projection", window.getOrthographicMatrix());
  }

  private void unbindObject() {
    //Unbind Shader
    shader.unbind();
    // Unbind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL30.glDisableVertexAttribArray(0);
    GL30.glDisableVertexAttribArray(1);
    GL30.glDisableVertexAttribArray(2);
    // Unbind VAO
    GL30.glBindVertexArray(0);
  }

  private void drawObject(HudImage object) {
    GL11.glDrawElements(
        GL11.GL_TRIANGLE_STRIP,
        object.getMesh().getModel().getIndices().length,
        GL11.GL_UNSIGNED_INT,
        0);
  }
}
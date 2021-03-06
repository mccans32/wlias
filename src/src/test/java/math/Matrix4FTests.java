package math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Matrix4FTests {
  private static final int TEST_SIZE = 4;
  private Matrix4f testMatrix;
  private int[][] testDimensionsAndIndex = {{1, 1, 5}, {0, 2, 8}, {2, 3, 14}, {3, 1, 7}};

  @BeforeEach
  public void setup() {
    testMatrix = new Matrix4f();
  }

  @Test
  public void testSize() {
    assertEquals(Matrix4f.getSize(), TEST_SIZE);
  }

  @Test
  public void testCalculateIndex() {
    for (int[] values : testDimensionsAndIndex) {
      int row = values[0];
      int col = values[1];
      int index = values[2];
      assertEquals(Matrix4f.calculateIndex(row, col), index);
    }
  }

  @Test
  public void testSettersAndGetters() {
    for (int[] values : testDimensionsAndIndex) {
      int col = values[0];
      int row = values[1];
      int value = values[2];
      testMatrix.set(col, row, value);
      assertEquals(testMatrix.get(col, row), value);
    }
  }

  @Test
  public void testIdentityMatrix() {
    assertArrayEquals(Matrix4f.identity().getAll().clone(),
        new float[] {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1});
  }

  @Test
  public void testScale() {
    float valX = 2.0f;
    float valY = 1f;
    float valZ = -2f;
    // Test base scaling on identity matrix
    assertEquals(Matrix4f.scale(new Vector3f(0f, 0f, valZ)).get(2, 2), valZ);
    assertEquals(Matrix4f.scale(new Vector3f(valX, 0f, 0f)).get(0, 0), valX);
    assertEquals(Matrix4f.scale(new Vector3f(0f, valY, 0f)).get(1, 1), valY);
    assertEquals(Matrix4f.scale(new Vector3f(valX, valY, valZ)).get(0, 0), valX);
    assertEquals(Matrix4f.scale(new Vector3f(valX, valY, valZ)).get(1, 1), valY);
    assertEquals(Matrix4f.scale(new Vector3f(valX, valY, valZ)).get(2, 2), valZ);
  }

  @Test
  public void testTranslate() {
    float valX = 2.0f;
    float valY = 1f;
    float valZ = -2f;
    // Test base translation on identity matrix
    assertEquals(Matrix4f.translate(new Vector3f(0f, 0f, valZ)).get(3, 2), valZ);
    assertEquals(Matrix4f.translate(new Vector3f(valX, 0f, 0f)).get(3, 0), valX);
    assertEquals(Matrix4f.translate(new Vector3f(0f, valY, 0f)).get(3, 1), valY);
  }

  @Test
  public void testMatrixToArray() {
    Matrix4f testMatrix = Matrix4f.identity();
    double[][] testArray = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
    assertArrayEquals(Matrix4f.matrixToArray(testMatrix), testArray);
    testMatrix.set(3, 2, 4.5f);
    testArray[2][3] = 4.5;
    assertEquals(Matrix4f.arrayToMatrix(testArray), testMatrix);
  }

  @Test
  public void testEquals() {
    Matrix4f matrix1 = Matrix4f.identity();
    Matrix4f matrix2 = Matrix4f.identity();
    assertEquals(matrix1, matrix2);
    matrix1 = Matrix4f.invert(matrix1);
    matrix2 = Matrix4f.invert(matrix2);
    assertEquals(matrix1, matrix2);
    matrix1.set(3, 2, 4.5f);
    assertNotEquals(matrix1, matrix2);
    matrix2.set(3, 2, 4.5f);
    assertEquals(matrix1, matrix2);
  }

  @Test
  public void testArrayToMatrix() {
    double[][] testArray = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
    Matrix4f testMatrix = Matrix4f.identity();
    assertEquals(Matrix4f.arrayToMatrix(testArray), testMatrix);
  }

}
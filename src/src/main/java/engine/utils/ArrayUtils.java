package engine.utils;

import engine.graphics.Vertex3D;
import java.util.List;

public class ArrayUtils {

  /**
   * Vertex 3 d list to array vertex 3 d [ ].
   *
   * @param vertexList the vertex list
   * @return the vertex 3 d [ ]
   */
  public static Vertex3D[] vertex3DListToArray(List<Vertex3D> vertexList) {
    Vertex3D[] vertexArray = new Vertex3D[vertexList.size()];
    for (int i = 0; i < vertexArray.length; i++) {
      vertexArray[i] = vertexList.get(i);
    }
    return vertexArray;
  }

  /**
   * Integer list to int array int [ ].
   *
   * @param integerList the integer list
   * @return the int [ ]
   */
  public static int[] integerListToIntArray(List<Integer> integerList) {
    int[] intArray = new int[integerList.size()];
    for (int i = 0; i < intArray.length; i++) {
      intArray[i] = integerList.get(i);
    }
    return intArray;
  }

  /**
   * get the maximum value from an array of floats.
   *
   * @param floats the floats
   * @return the float
   */
  public static float max(float[] floats) {
    float max = floats[0];
    for (float flt : floats) {
      if (flt > max) {
        max = flt;
      }
    }
    return max;
  }

  /**
   * Index of int.
   *
   * @param floats the floats
   * @param value  the value
   * @return the int
   */
  public static int indexOf(float[] floats, float value) {
    int index = -1;
    for (int i = 0; i < floats.length; i++) {
      if (floats[i] == value) {
        index = i;
        break;
      }
    }
    return index;
  }
}

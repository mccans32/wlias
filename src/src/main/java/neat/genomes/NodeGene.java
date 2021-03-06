package neat.genomes;

import java.io.Serializable;

public class NodeGene extends Gene implements Serializable {

  //default serialVersion id
  private static final long serialVersionUID = 1L;

  private double valueX;
  private double valueY;

  public NodeGene(int innovationNumber) {
    super(innovationNumber);
  }

  public double getX() {
    return valueX;
  }

  public void setX(double x) {
    this.valueX = x;
  }

  public double getY() {
    return valueY;
  }

  public void setY(double y) {
    this.valueY = y;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof NodeGene)) {
      return false;
    }
    return innovationNumber == ((NodeGene) o).getInnovationNumber();
  }

  @Override
  public String toString() {
    return "NodeGene{" + "innovation_number=" + innovationNumber + '}';
  }

  @Override
  public int hashCode() {
    return innovationNumber;
  }
}
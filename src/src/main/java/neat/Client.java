package neat;

import java.io.Serializable;
import neat.calculations.Calculator;
import neat.genomes.Genome;

public class Client implements Serializable {

  //default serialVersion id
  private static final long serialVersionUID = 1L;

  private Calculator calculator;

  private Genome genome;
  private double score;
  private Species species;

  public void generateCalculator() {
    this.calculator = new Calculator(genome);
  }

  /**
   * Calculate the outputs for this client's genome.
   *
   * @param input the input
   * @return the double [ ]
   */
  public double[] calculate(double... input) {
    if (this.calculator == null) {
      generateCalculator();
    }
    return this.calculator.calculate(input);
  }

  public double distance(Client other) {
    return this.getGenome().distance(other.getGenome());
  }

  public void mutate() {
    getGenome().mutate();
  }

  public Calculator getCalculator() {
    return calculator;
  }

  public Genome getGenome() {
    return genome;
  }

  public void setGenome(Genome genome) {
    this.genome = genome;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public Species getSpecies() {
    return species;
  }

  public void setSpecies(Species species) {
    this.species = species;
  }
}
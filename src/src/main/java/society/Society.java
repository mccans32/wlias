package society;

import engine.graphics.model.dimension.two.RectangleModel;
import engine.objects.world.TileWorldObject;
import game.world.World;
import java.util.ArrayList;
import java.util.Random;
import math.Vector3f;
import society.person.Person;
import society.person.dataobjects.Gender;

public class Society {
  private static final int DEFAULT_POPULATION_SIZE = 10;
  public int personIdCounter;
  private Vector3f societyColor;
  private ArrayList<Person> population;
  private int societyId;
  private int averageLifeExpectancy;
  private int totalFoodResource = 0;
  private int totalRawMaterialResource = 0;
  private ArrayList<TileWorldObject> territory = new ArrayList<>();
  private ArrayList<TileWorldObject> claimableTerritory;

  /**
   * Instantiates a new Society.
   *
   * @param initialPopulationSize the initial population
   * @param societyId             the society id
   */
  public Society(int initialPopulationSize, int societyId, Vector3f societyColor) {
    this.societyId = societyId;
    this.societyColor = societyColor;
    generateInitialPopulation(initialPopulationSize);

  }

  /**
   * Instantiates a new Society with a unique ID and BorderColour.
   *
   * @param societyId    the society id
   * @param societyColor the society color
   */
  public Society(int societyId, Vector3f societyColor) {
    this.societyId = societyId;
    this.societyColor = societyColor;
    generateInitialPopulation(DEFAULT_POPULATION_SIZE);
  }

  public static int getDefaultPopulationSize() {
    return DEFAULT_POPULATION_SIZE;
  }

  public int getTotalFoodResource() {
    return totalFoodResource;
  }

  public void setTotalFoodResource(int totalFoodResource) {
    this.totalFoodResource = totalFoodResource;
  }

  public int getTotalRawMaterialResource() {
    return totalRawMaterialResource;
  }

  public void setTotalRawMaterialResource(int totalRawMaterialResource) {
    this.totalRawMaterialResource = totalRawMaterialResource;
  }

  public Vector3f getSocietyColor() {
    return societyColor;
  }

  /**
   * Update the borders for each tile owned by this society.
   *
   * @param tileModel the tile model
   */
  public void updateBorders(RectangleModel tileModel) {
    calculateResources();
    for (TileWorldObject worldTile : this.territory) {
      if (worldTile.getBorderMesh() == null) {
        worldTile.setBorderMesh(this.societyColor, tileModel);
      }
    }
  }

  /**
   * Calculate Food and Raw Material resource values.
   */
  public void calculateResources() {
    int foodTotal = 0;
    int rawMaterials = 0;
    for (TileWorldObject worldTile : this.territory) {
      foodTotal += worldTile.getFoodResource();
      rawMaterials += worldTile.getRawMaterialResource();
    }
    setTotalFoodResource(foodTotal);
    setTotalRawMaterialResource(rawMaterials);
  }

  public void claimTile(TileWorldObject worldTile) {
    worldTile.setClaimed(true);
    this.territory.add(worldTile);
  }

  public ArrayList<TileWorldObject> getTerritory() {
    return territory;
  }

  /**
   * Claim tiles.
   */
  public ArrayList<TileWorldObject> calculateClaimableTerritory() {
    claimableTerritory = new ArrayList<>();
    for (TileWorldObject worldTile : territory) {
      addClaimableTiles(worldTile.getRow(), worldTile.getColumn());
    }
    return claimableTerritory;
  }

  private void addClaimableTiles(int row, int column) {

    TileWorldObject[][] map = World.getWorldMap();
    // Check left side of the territory
    if (!map[row][column - 1].isClaimed() & column - 1 != 0) {
      claimableTerritory.add(map[row][column - 1]);
    }
    // Check right side of the territory
    if (!map[row][column + 1].isClaimed() & column + 1 != map.length - 1) {
      claimableTerritory.add(map[row][column + 1]);
    }
    // Check top of territory
    if (!map[row - 1][column].isClaimed() & row - 1 != 0) {
      claimableTerritory.add(map[row - 1][column]);
    }
    // check bottom of territory
    if (!map[row + 1][column].isClaimed() & row + 1 != map.length - 1) {
      claimableTerritory.add(map[row + 1][column]);
    }
  }


  private void generateInitialPopulation(int initialPopulationSize) {
    population = new ArrayList<>();
    Gender[] genderList = Gender.class.getEnumConstants();
    population.add(new Person(0, Gender.FEMALE));
    population.add(new Person(1, Gender.MALE));
    for (personIdCounter = 2; personIdCounter < initialPopulationSize; personIdCounter++) {
      Random r = new Random();
      int randomIndex = r.nextInt(genderList.length);
      population.add(new Person(personIdCounter, genderList[randomIndex]));
    }
  }

  public ArrayList<Person> getPopulation() {
    return population;
  }

  public void setPopulation(ArrayList<Person> population) {
    this.population = population;
  }

  public int getSocietyId() {
    return societyId;
  }

  public void setSocietyId(int societyId) {
    this.societyId = societyId;
  }

  public int getAverageLifeExpectancy() {
    return averageLifeExpectancy;
  }

  public void setAverageLifeExpectancy() {
    this.averageLifeExpectancy = calculateLifeExpectancy();
  }

  private int calculateLifeExpectancy() {
    return 0;
  }
}

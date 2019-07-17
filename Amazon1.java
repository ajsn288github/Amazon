package com.Amazon;// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * In ABCD country due to natural disaster all the roads were damaged very badly and most of the cities were disconnected due to that. Since most of the Amazon's customer base lies around that cities, Amazon decided to repair the road along with the local bodies.
 * <p>
 * As a software development engineer, in Amazon delivery centers, you will need to write a method. Given the total number of available cities, list of roads a available, number of roads to be repaired and cost for each roads to be repaired, Need to find out minimum cost to repair the roads so that all the cities are connected at least by one road.
 * <p>
 * Input:
 * The input to the function/method consists of five arguments:-
 * numTotalAvailableCities, number of available cities connected buy the road in that country.
 * numTotalAvailableRoads, number of available roads available to travel between the cities.
 * roadsAvailable, a list pair of cities for which the road is available.
 * numRoadsToBeRepaired, number of roads damaged and needs to be repaired.
 * costRoadsToBeRepaired, representing a list of pair of cities between which a road is currently unusable and the cost of repairing that road, respectively
 * (e.g., [1,3,10] means ro repair a road between cities 1 and 3 the cost would be 10)
 * <p>
 * Output:
 * Return an integer representing the minimum total cost to repair some roads so that all the cities are accessible from each other.
 * <p>
 * Constraints:
 * 0 <= numTotalAvailableCities <= 50
 * 0 <= costRoadsToBeRepaired[i][2] <= 1000
 * 0 <= i < numRoadsToBeRepaired
 * <p>
 * Example:
 * numTotalAvailableCities = 5
 * numTotalAvailableRoads = 5
 * roadsAvailable = [[1,2], [2,3], [3,4], [4,5], [1,5]]
 * numRoadsToBeRepaired = 3
 * costRoadsToBeRepaired = [[1,2,12], [3,4,30], [1,5,8]]
 * <p>
 * Output:
 * 20
 * <p>
 * Explanation:
 * There are three networks due to unusable roads: [1], [2,3], [4,5]
 * We can connect these networks into a single network by repairing the road between cities 1 and 2, and cities 1 and 5 ata minimum cost of 12 + 8
 * So, the output is 20.
 *
 * The SOLUTION is based on the Kruskal Algorithm.
 *
 * @author Selva
 * @since 3/24/2019
 */
public class Amazon1 {
    public static void main(String[] args) {
        Amazon1 amazon = new Amazon1();
        System.out.println("Minimum Amount required to Repair the Road: " + amazon.getMinimumCostToRepair(5, 5,
                Arrays.asList(Arrays.asList(1, 2), Arrays.asList(2, 3), Arrays.asList(3, 4), Arrays.asList(4, 5), Arrays.asList(1, 5)), 3,
                Arrays.asList(Arrays.asList(1, 2, 12), Arrays.asList(3, 4, 30), Arrays.asList(1, 5, 8))));
        System.out.println("Minimum Amount required to Repair the Road: " + amazon.getMinimumCostToRepair(5, 6,
                Arrays.asList(Arrays.asList(1, 2), Arrays.asList(2, 3),  Arrays.asList(1, 3), Arrays.asList(3, 4), Arrays.asList(4, 5), Arrays.asList(1, 5)), 4,
                Arrays.asList(Arrays.asList(1, 2, 12), Arrays.asList(1, 3, 5), Arrays.asList(3, 4, 30), Arrays.asList(1, 5, 8))));
    }

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    int getMinimumCostToRepair(int numTotalAvailableCities,
                               int numTotalAvailableRoads,
                               List<List<Integer>> roadsAvailable,
                               int numRoadsToBeRepaired,
                               List<List<Integer>> costRoadsToBeRepaired) {
        // WRITE YOUR CODE HERE
        Edge[] listOfRoads = roadsAvailable.stream()
                .map(roadDetail -> {
                    int source = roadDetail.get(0);
                    int dest = roadDetail.get(1);

                    //if the road is not damaged, set the cost to Zero.
                    int cost = costRoadsToBeRepaired.stream()
                            .filter(costDetail -> costDetail.get(0) == source && costDetail.get(1) == dest)
                            .map(costDetail -> costDetail.get(2))
                            .findAny()
                            .orElse(0);

                    //In program we follow o to n-1 practice, so we are assigning the values by reducing 1
                    return new Edge(source - 1, dest - 1, cost);
                }).collect(Collectors.toList())
                 .toArray(new Edge[numTotalAvailableRoads]);

        AtomicInteger totalCostRequiredToRepair = new AtomicInteger();

        //sort the array by their cost to repair
        Arrays.sort(listOfRoads);

        CityInformation[] cityInformations = new CityInformation[numTotalAvailableCities + 1];
        IntStream.range(0, numTotalAvailableCities).forEach(i -> cityInformations[i] = new CityInformation(i, 0));

        IntStream.range(0, numTotalAvailableRoads - 1).forEach(roadIndex -> {
            Edge road = listOfRoads[roadIndex];

            int sourceParent = findParentNode(cityInformations, road.getSource());
            int destParent = findParentNode(cityInformations, road.getDestination());

            //if both source and destination parent are same then it means its forming cycle.
            if(sourceParent != destParent){
                totalCostRequiredToRepair.addAndGet(road.getCost());
                mergeTheTwoDiffRoutes(cityInformations, sourceParent, destParent);
            }

        });

        return totalCostRequiredToRepair.get();
    }

    //Utility method to find the parent of the current node
    private int findParentNode(CityInformation[] cityInformations, int cityIndex){

        if(cityInformations[cityIndex].getParentCity() != cityIndex){
            cityInformations[cityIndex].setParentCity(findParentNode(cityInformations, cityInformations[cityIndex].getParentCity()));
        }

        return cityInformations[cityIndex].getParentCity();
    }

    private void mergeTheTwoDiffRoutes(CityInformation[] cityInformations, int cityIndex1, int cityIndex2){
        int cityParent1 = findParentNode(cityInformations, cityIndex1);
        int cityParent2 = findParentNode(cityInformations, cityIndex2);

        //Attach the two different tree by comparing the rank
        if(cityInformations[cityParent1].getParentCity() < cityInformations[cityParent2].getParentCity()){
            cityInformations[cityParent1].setParentCity(cityParent2);
        } else if(cityInformations[cityParent1].getParentCity() > cityInformations[cityParent2].getParentCity()){
            cityInformations[cityParent2].setParentCity(cityParent1);
        } else {
            // If ranks are same, then make one as root and increment
            // its rank by one
            cityInformations[cityParent2].setParentCity(cityParent1);
            cityInformations[cityParent1].setParentCity(cityInformations[cityParent1].getParentCity() + 1);
        }
    }
    // METHOD SIGNATURE ENDS
}

class Edge implements Comparable<Edge> {

    private int source;
    private int destination;
    private int cost;

    public Edge(int source, int destination, int cost) {
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge edgeToBeCompared) {
        return this.cost - edgeToBeCompared.cost;
    }
}

class CityInformation {
    private int parentCity;
    private int rank;

    public CityInformation(int parentCity, int rank) {
        this.parentCity = parentCity;
        this.rank = rank;
    }

    public int getParentCity() {
        return parentCity;
    }

    public void setParentCity(int parentCity) {
        this.parentCity = parentCity;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
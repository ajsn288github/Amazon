package com.Amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * In Amazon's sort center, a computer system decides what packages are to be loaded on what trucks. All rooms and spaces are abstracted into space units which is represented as an integer. For each type of truck, they have different space units. For each package, they will be occupying different space units.
 *
 * As a software development engineer in sort centers, you will need to write a method. Given truck space units and a list of product space units, find out exactly TWO products that fit into the truck. You will also implement an internal rule that the truck has to reserve exactly 30 space units for safety purposes. Each package is assigned a unique ID, numbered from O to N-l.
 *
 * Assumptions:
 * 1. You will pick up exactly 2 packages.
 * 2. You cannot pick up one package twice.
 * 3. If you have multiple pairs, select the pair with the largest package.
 *
 * Input:
 * The input to the function/method consists of two arguments:-
 * truckSpace, an integer representing the truck space.
 * packagesSpace, a list of integers representing the space units occupying by packages.
 *
 * Output:
 * Return a list of integers representing the IDs of two packages whose combined space will leave exactly 30 space units on the truck.
 *
 * @author Selva
 * @since 3/24/2019
 */
public class Amazon {


    public static void main(String[] args) {
        Amazon amazon = new Amazon();
        System.out.println("Output: " + amazon.IDsOfPackages(110, Arrays.asList(20, 70, 90, 30, 60, 110)));
        System.out.println("Output: " + amazon.IDsOfPackages(250, Arrays.asList(100, 180, 40, 120, 10, 200)));
        System.out.println("Output: " + amazon.IDsOfPackages(250, Arrays.asList(5, 180, 40, 120, 10, 210)));
    }

    public ArrayList<Integer> IDsOfPackages(int truckSpace, List<Integer> packagesSpace) {

        int reserveSpace = 30; //reserved for truck safety purposes
        List<ArrayList<Integer>> listOfSuitablePackages = new ArrayList<>();

        for (int i = 0; i < packagesSpace.size(); i++) {
            int packageWeight = packagesSpace.get(i);
            ArrayList<Integer> selectedPackages = new ArrayList<>();
            for (int j = i + 1; j < packagesSpace.size(); j++) {
                if (packageWeight + packagesSpace.get(j) + reserveSpace == truckSpace) {

                        selectedPackages.add(i);
                        selectedPackages.add(j);
                    break;
                }
            }

            if (selectedPackages.size() > 0) {
                listOfSuitablePackages.add(selectedPackages);
            }
        }

        if (listOfSuitablePackages.size() == 0) {
            return new ArrayList<>();
        } else if (listOfSuitablePackages.size() == 1) {
            return listOfSuitablePackages.get(0);
        } else {
            ArrayList<Integer> selectedPackage = new ArrayList<>();
            int largestPackage = 0;
            for (ArrayList<Integer> pckge : listOfSuitablePackages) {
                for (int i = 0; i < pckge.size(); i++) {
                    int pack = pckge.get(i);
                    if (packagesSpace.get(pack) > largestPackage) {
                        largestPackage = packagesSpace.get(pack);
                        selectedPackage = pckge;
                    }
                }
            }

            return selectedPackage;
        }
    }
}
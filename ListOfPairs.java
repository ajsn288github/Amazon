package com.Amazon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ListOfPairs {

    public static void main(String[] args) {
        List<Pair> inputPairRray = new ArrayList<>();

        inputPairRray.add(new Pair(5, 6));
        inputPairRray.add(new Pair(1, 3));
        inputPairRray.add(new Pair(2, 4));
        inputPairRray.add(new Pair(7, 16));
        inputPairRray.add(new Pair(10, 12));
        inputPairRray.add(new Pair(12, 20));
        inputPairRray.add(new Pair(3, 9));
        inputPairRray.add(new Pair(30, 35));
        inputPairRray.add(new Pair(32, 33));
        inputPairRray.add(new Pair(36, 40));
        inputPairRray.add(new Pair(15, 37));

        System.out.println(mergeList(inputPairRray));
    }

    private static List<Pair> mergeList(List<Pair> inputListArray) {
        List<Pair> mergedList = new ArrayList<>();

        inputListArray.sort(Comparator.comparingInt(Pair::getX));

        boolean isSortAgainRequired = true;
        int startIndex = 0;
        while (isSortAgainRequired) {
            isSortAgainRequired = loopTheArray(inputListArray, mergedList, startIndex++);
            if (isSortAgainRequired && startIndex < mergedList.size()) {
                inputListArray = mergedList;
                mergedList = new ArrayList<>();
            }
        }

        return mergedList;
    }

    private static boolean loopTheArray(List<Pair> inputListArray, List<Pair> mergedList, int startIndex) {
        Pair startBin = inputListArray.get(startIndex);
        boolean isSortAgainRequired = false;

        for (int i = startIndex; i < inputListArray.size(); i++) {
            Pair temp = inputListArray.get(i);
            if (temp.getX() > startBin.getX() && temp.getY() < startBin.getY()) {
                inputListArray.remove(temp);
                i--;
            } else if (temp.getX() < startBin.getY() && temp.getY() > startBin.getX()) {
                startBin.setY(temp.getY());
            } else {
                mergedList.add(temp);
                isSortAgainRequired = true;
            }
        }

        mergedList.add(startBin);

        if(startIndex > 0){
            IntStream.range(0, startIndex).mapToObj(inputListArray::get).forEach(mergedList::add);
        }

        mergedList.sort(Comparator.comparingInt(Pair::getX));

        return isSortAgainRequired;
    }

}

class Pair {
    private int x;
    private int y;

    public Pair() {
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Start: " +
                this.x +
                "; End: " +
                this.y;
    }
}

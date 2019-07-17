package com.Amazon;

/**
 * @author PL64640
 * @since 6/8/2019
 */
// Java program to implement 3 Array Max heap
public class ThreeArrayMaxHeap {
    private int[] heap;
    private int size;

    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.
    private ThreeArrayMaxHeap() {
        this.size = 0;
        heap = new int[4];
        heap[0] = Integer.MAX_VALUE;
    }

    // Returns position of parent
    private int parent(int pos) {
        return pos / 2;
    }

    private void swap(int fpos, int spos) {
        int tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    // Inserts a new element to max heap
    private void insert(int element) {

        if (size == 3) {
            if (heap[1] > element) {
                heap[1] = element;
            }
        } else {
            heap[++size] = element;
        }

        // Traverse up and fix violated property
        int current = size;
        boolean isAllSwapDone = false;

        while (!isAllSwapDone) {
            //Inserted element or right child is greater than parent
            if (heap[current] > heap[parent(current)]) {
                swap(current, parent(current));
                current = parent(current);
            } //if Parent is less than the left child then
            else if ((current < size && heap[current + 1] > heap[parent(current + 1)])) {
                swap(current + 1, parent(current) + 1);
                current = parent(current + 1);
            } //If current is not the parent and if the parent is less than left child node.
            else if (heap[current - 1] > heap[parent(current - 1)]) {
                swap(current - 1, parent(current - 1));
                current = parent(current - 1);
            } else {
                isAllSwapDone = true;
            }
        }

    }

    private void print() {
        for (int i = 1; i <= size / 2; i++) {
            System.out.println(" PARENT : " + heap[i] + " LEFT CHILD : " +
                    heap[2 * i] + " RIGHT CHILD :" + heap[2 * i + 1]);
        }
    }

    public static void main(String[] arg) {
        System.out.println("The Max heap is ");
        ThreeArrayMaxHeap maxHeap = new ThreeArrayMaxHeap();
        maxHeap.insert(15);
        maxHeap.insert(13);
        maxHeap.insert(17);
        maxHeap.insert(10);
        maxHeap.insert(0);
        maxHeap.insert(9);
        maxHeap.insert(0);
        maxHeap.insert(2);
        maxHeap.insert(90);

        maxHeap.print();

        System.out.println("The max val is " + maxHeap.heap[1]);
    }
}
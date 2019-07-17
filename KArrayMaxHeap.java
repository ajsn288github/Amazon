package com.Amazon;

/**
 * @author PL64640
 * @since 6/8/2019
 */
// Java program to implement Max heap with K size
public class KArrayMaxHeap {
    private int[] heap;
    private int size;
    private int maxsize;

    // Constructor to initialize an
    // empty max heap with given maximum
    // capacity.
    private KArrayMaxHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new int[this.maxsize + 1];
        heap[0] = Integer.MAX_VALUE;
    }

    // Returns position of parent
    private int parent(int pos) {
        return pos / 2;
    }

    // Below two functions return left and
    // right children.
    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    // Returns true of given node is leaf
    private boolean isLeaf(int pos) {
        return pos >= (size / 2) && pos <= size;
    }

    private void swap(int fpos, int spos) {
        int tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    // A recursive function to max heapify the given
    // subtree. This function assumes that the left and
    // right subtrees are already heapified, we only need
    // to fix the root.
    private void maxHeapify(int pos) {
        if (isLeaf(pos))
            return;

        if (heap[pos] < heap[leftChild(pos)]
                || heap[pos] < heap[rightChild(pos)]) {

            if (heap[leftChild(pos)] > heap[rightChild(pos)]) {
                swap(pos, leftChild(pos));
                maxHeapify(leftChild(pos));
            } else {
                swap(pos, rightChild(pos));
                maxHeapify(rightChild(pos));
            }
        }
    }

    // Inserts a new element to max heap
    private void insert(int element) {

        if (size == maxsize) {
            maxHeapify(1);
            if (heap[1] > element) {
                heap[1] = element;
            }
        } else {
            heap[++size] = element;
        }

        // Traverse up and fix violated property
        int current = size;

        while (heap[current] > heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    private void print() {
        for (int i = 1; i <= size / 2; i++) {
            StringBuilder output = new StringBuilder();

            output.append(" PARENT : ")
                    .append(heap[i])
                    .append(" LEFT CHILD : ")
                    .append(heap[2 * i]);

            //if right child is there then only print it or else ArrayIndexOutOfBoundException will come
            if(2 * i + 1 < maxsize){
                output.append(" RIGHT CHILD :")
                        .append(heap[2 * i + 1]);
            }

            System.out.println(output.toString());
        }
    }

    // Remove an element from max heap
    private int extractMax() {
        int popped = heap[1];
        heap[1] = heap[size--];
        maxHeapify(1);
        return popped;
    }

    public static void main(String[] arg) {
        System.out.println("The Max heap is ");
        KArrayMaxHeap maxHeap = new KArrayMaxHeap(5);
        maxHeap.insert(15);
        maxHeap.insert(13);
        maxHeap.insert(17);
        maxHeap.insert(10);
        maxHeap.insert(107);
        maxHeap.insert(9);
        maxHeap.insert(1);
        maxHeap.insert(22);
        maxHeap.insert(90);

        maxHeap.print();

        System.out.println("The max val is " + maxHeap.extractMax());
    }
}
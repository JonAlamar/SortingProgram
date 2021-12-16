import javax.crypto.spec.PSource;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SortingProgram {

    public static int comparisons = 0;
    public static int exchanges = 0;
    public static int sequence;
    public static int divide;
    public static int[] ciuraSequence = {701, 301, 132, 57, 23, 10, 4, 1, 0};


    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("\nWhich set would you like to test? Prototype or Large");
        String choice1 = input.nextLine();

        System.out.println("Which set would you like to sort? Ordered, Reverse, or Random?");
        String choice2 = input.nextLine();
        String testSet = null;

        if (choice1.equalsIgnoreCase("prototype") && (choice2.equalsIgnoreCase("ordered"))) {
            testSet = "PrototypeOrdered.txt";
        } else if (choice1.equalsIgnoreCase("prototype") && (choice2.equalsIgnoreCase("reverse"))) {
            testSet = "PrototypeReverse.txt";
        } else if (choice1.equalsIgnoreCase("prototype") && (choice2.equalsIgnoreCase("random"))) {
            testSet = "PrototypeRandom.txt";
        } else if (choice1.equalsIgnoreCase("large") && (choice2.equalsIgnoreCase("ordered"))) {
            testSet = "LargeOrdered.txt";
        } else if (choice1.equalsIgnoreCase("large") && (choice2.equalsIgnoreCase("reverse"))) {
            testSet = "LargeReverse.txt";
        } else if (choice1.equalsIgnoreCase("large") && (choice2.equalsIgnoreCase("random"))) {
            testSet = "LargeRandom.txt";
        }


        File file = new File(testSet);
        Scanner scan = new Scanner(file);
        int count = 0;

        while (scan.hasNextInt()) {
            scan.nextInt();
            count++;
        }

        int[] arr = new int[count];
        //int[] arrH = new int[count + 1]; //array for heap, starts at index 1
        //int[] arrHeap = new int[count + 1]; //array after converting to heap
        File newFile = new File(testSet);
        Scanner newScan = new Scanner(newFile);

        System.out.println("****** Unsorted ******");
        for (int i = 0; newScan.hasNextInt(); i++) {
            int num = newScan.nextInt();
            System.out.println(num);
            arr[i] = num;
            //arrH[i + 1] = num;
        }

        System.out.println("Which sort? bubble, selection, insertion, shell, quicksort, heap, or for Ciura's Shellsort 'cs'");
        String sortChoice = input.nextLine();

        if (sortChoice.equalsIgnoreCase("bubble")) {
            System.out.println("\n*******BUBBLE SORT*******");
            bubbleSort(arr);
            printArray(arr);
        } else if (sortChoice.equalsIgnoreCase("selection")) {
            System.out.println("\n*******SELECTION SORT*******");
            selectionSort(arr);
            printArray(arr);
        } else if (sortChoice.equalsIgnoreCase("insertion")) {
            System.out.println("\n*******INSERTION SORT*******");
            insertionSort(arr);
            printArray(arr);
        } else if (sortChoice.equalsIgnoreCase("shell")) {
            System.out.println("What number would you like to start the sequence?");
            sequence = Integer.parseInt(input.nextLine());
            System.out.println("Divide by 2 or 3?");
            divide = Integer.parseInt(input.nextLine());
            System.out.println("\n*******SHELL SORT*******");
            shellSort(arr, divide);
            printArray(arr);
        } else if (sortChoice.equalsIgnoreCase("cs")) {
            experimentalShellSort(arr, ciuraSequence);
            printArray(arr);
        } else if (sortChoice.equalsIgnoreCase("quicksort")) {
            quickSort(arr, 0, arr.length - 1);
            printArray(arr);
        } else if (sortChoice.equalsIgnoreCase("heap")) {
            System.out.println("****** Building Heap ******");
            //for (int i = 0; i < arrH.length; i++) {
            //insertHeap(arrHeap, arrH[i], size);
            //size++;
            //}
            //printArray(arrHeap);
            heapSort(arr);
            System.out.println("\n****** Completed Heap Sort ******");
            printArray(arr);
        } else {
            System.out.println("You made a spelling error, please try again");
        }
    }


    public static void bubbleSort(int[] arr) {
        int temp = 0;
        boolean count = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (count) {
                count = false;
                for (int j = 0; j < arr.length - i - 1; j++) {
                    comparisons++;
                    if (arr[j] > arr[j + 1]) {
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        count = true;
                        exchanges++;
                    }
                }
            }
        }
    }


    public static void selectionSort(int[] arr) {
        int temp, min = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                comparisons++;
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
            exchanges++;
        }
    }


    public static void insertionSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                exchanges++;
                comparisons++;
            }
            arr[j + 1] = key;
        }
    }

    public static void shellSort(int[] arr, int divide) {
        int h = sequence;
        System.out.println("Starting sequence is " + h);
        System.out.println("Formula is " + divide + " * H + 1");
        while (h > 0) {
            segmentedInsertionSort(arr, h);
            h = h / divide;
            System.out.println("*** " + h + " ***");
        }
    }

    public static void segmentedInsertionSort(int[] arr, int h) {

        for (int i = h; i < arr.length; i++) {
            int current = arr[i];
            int j = i;
            while (j > h - 1 && (arr[j - h] > current)) {
                arr[j] = arr[j - h];
                j = j - h;
                comparisons++;
                exchanges++;
            }
            arr[j] = current;
        }
    }


    public static int partition(int[] arr, int low, int hi) {
        int pivot = arr[low];

        while (low < hi) {
            while (pivot < arr[hi] && low < hi) {
                hi--;
                comparisons++;
            }
            if (hi != low) {
                arr[low] = arr[hi];
                low++;
                comparisons++;
                exchanges++;
            }
            while (arr[low] < pivot && low < hi) {
                low++;
                comparisons++;
            }
            if (hi != low) {
                arr[hi] = arr[low];
                hi--;
                exchanges++;
                comparisons++;
            }
        }
        arr[hi] = pivot;
        return hi;
    }

    public static void quickSort(int[] arr, int low, int hi) {

        int pivotPoint = partition(arr, low, hi);

        if (low < pivotPoint) {
            quickSort(arr, low, pivotPoint - 1);
        }
        if (pivotPoint < hi) {
            quickSort(arr, pivotPoint + 1, hi);
        }
    }

    public static void heapSort(int[] arrHeap) {

        int n = arrHeap.length - 1;
        int y = n / 2;

        while (y >= 0) {
            downHeap(arrHeap, n, y);
            y--;
        }
        y = n;
        while (y >= 0) {
            int temp = arrHeap[0];
            arrHeap[0] = arrHeap[y];
            arrHeap[y] = temp;
            comparisons++;
            y--;
            downHeap(arrHeap, y, 0);
        }
    }

    public static void downHeap(int[] arrHeap, int n, int j) {

        boolean foundSpot = false;
        int i = j;
        int key = arrHeap[i];
        int k = 2 * i;
        while ((k <= n) && (!foundSpot)) {
            if ((k < n) && (arrHeap[k + 1] > arrHeap[k])) {
                comparisons++;
                k++;
            }
            if (arrHeap[k] > key) {
                comparisons++;
                exchanges++;
                arrHeap[i] = arrHeap[k];
                i = k;
                k = 2 * i;
            } else {
                foundSpot = true;
            }
        }
        arrHeap[i] = key;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println("\nComparisons: " + comparisons +
                "\nSwaps: " + exchanges);
    }


    /*
    The following shell sort was used to test Ciura's sequence
    and has been modified to read the sequence from the static arrays called
    ciuraSequence.
    Normal shell sort is above
     */

    public static void experimentalShellSort(int[] arr, int[] experimentalSequence) {
        int i = 0;
        int h = experimentalSequence[0];
        System.out.println("*** " + h + " ***");
        while (h > 0) {
            segmentedInsertionSort(arr, h);
            i++;
            h = experimentalSequence[i];
            System.out.println("*** " + h + " ***");
        }
    }
}
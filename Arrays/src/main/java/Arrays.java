import java.util.ArrayList;

public class Arrays {


    public static void main(String[] args) {

        int[] intArray1 = {0, 1, 2, 4, 6, 7, 4, 8};
        int[] intArray2 = {4, 3, 1, 2, 5, 6, 7, 4};
        int[] intArray3 = {4, 4, 4, 7, 4};

        int[] boolArray1 = {1, 1, 1, 4, 4, 1, 4, 4};
        int[] boolArray2 = {1, 1, 1, 1, 1, 1};
        int[] boolArray3 = {4, 4, 4, 4};
        int[] boolArray4 = {1, 4, 4, 1, 1, 4, 3};


    }


    public ArrayList<Integer> arraysCompare(int[] inputArray) {

        ArrayList<Integer> List = new ArrayList<>();

        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] == 4) {
                List.add(inputArray[i + 1]);
            }
        }

        try {
            int x = 1 / List.size();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return List;
    }

    public static boolean fourOrOne(int[] inputArray) {

        int count4 = 0;
        int count1 = 0;
        int countErr = 0;

        for (int i = 0; i < inputArray.length; i++) {

            if (inputArray[i] == 4) {
                count4 = 1;
            }
            if (inputArray[i] == 1) {
                count1 = 1;
            }

            if (inputArray[i] != 1 && inputArray[i] != 4) {
                countErr = 1;
            }
        }

        if(count1 + count4 + countErr == 2) {
        return true;}

        return false;
    }
}



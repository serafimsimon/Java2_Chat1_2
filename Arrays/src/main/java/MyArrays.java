import java.util.ArrayList;

public class MyArrays {


    public static void main(String[] args) {

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

    public boolean fourOrOne(int[] inputArray) {

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



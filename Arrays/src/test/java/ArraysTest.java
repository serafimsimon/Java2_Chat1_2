import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class ArraysTest {
    private ArraysTest arraysTest;


    @ParameterizedTest
    @MethodSource("dataForArrays")
    public void arraysCompare(ArrayList<Integer> result, int[] intArray) {


        Assertions.assertArrayEquals(result, arraysTest.arraysCompare(intArray));}

    public static Stream<Arguments> dataForArrays() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new ArrayList<>(Arrays.asList(6, 8)), new int[] {0, 1, 2, 4, 6, 7, 4, 8}));
        out.add(Arguments.arguments(new ArrayList<>(Arrays.asList(3)), new int[] {4, 3, 1, 2, 5, 6, 7, 4}));
        out.add(Arguments.arguments(new ArrayList<>(Arrays.asList(4, 4, 7)), new int[] {4, 4, 4, 7, 4}));
        return out.stream();}






    @Test
    void fourOrOne() {
    }
}
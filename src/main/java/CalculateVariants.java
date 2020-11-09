import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalculateVariants {

    public static AtomicInteger variants;

    public static void calculatePossibleVariants(ArrayList<Integer> initialIntegerList, int target) {
        variants = new AtomicInteger(0);
        List<Integer> requiredList = getListWithoutUnnecessaryNumbers(initialIntegerList, target);

        sumUpRecursively(requiredList, target, new ArrayList<>());
        System.out.println(" Target " + target + "  Variants " + variants);
    }

    private static List<Integer> getListWithoutUnnecessaryNumbers(ArrayList<Integer> initialList, int target) {
        List<Integer> requiredList = IntStream.rangeClosed(1, target - 1)
                .boxed().collect(Collectors.toList());
        requiredList.removeAll(initialList);
        return requiredList;
    }

    private static void sumUpRecursively(List<Integer> numbers, int target,
                                         ArrayList<Integer> partial) {
        int s = 0;
        for (int x : partial) s += x;
        if (s == target)
            variants.getAndAdd(1);
        if (s >= target)
            return;
        for (int i = 0; i < numbers.size(); i++) {
            ArrayList<Integer> remaining = new ArrayList<>();
            int n = numbers.get(i);
            for (int j = i + 1; j < numbers.size(); j++)
                remaining.add(numbers.get(j));
            ArrayList<Integer> partial_rec = new ArrayList<>(partial);
            partial_rec.add(n);
            sumUpRecursively(remaining, target, partial_rec);
        }
    }

    public static void main(String args[]) {
        Integer[] numbersToBeExcluded = {1, 3};
        Arrays.stream(numbersToBeExcluded).filter(number -> number <= 0).forEach(number -> {
            throw new RuntimeException("Number must be positive or not zero");
        });
        int target = 6;
        calculatePossibleVariants(new ArrayList<Integer>(Arrays.asList(numbersToBeExcluded)), target);
    }
}

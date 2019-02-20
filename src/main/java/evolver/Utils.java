package evolver;

import java.util.Random;

public class Utils {

    //TODO: skriv unit-tester för denna
    public static int mutateInInterval(int minInclusive, int maxInclusive, int currentVal, int maxStep) {
        int returnVal = currentVal;
        Random rand = new Random();
        do {
            int delta = 0;
            do {
                delta = rand.nextInt(maxStep * 2 + 1) - maxStep;
            } while (delta == 0);
            returnVal = currentVal + delta;
        } while (returnVal < minInclusive || returnVal > maxInclusive);
        return returnVal;
    }


    /**
     * Returns a random in the given interval (including max/min value)
     *
     * @param minInclusive minimum value
     * @param maxInclusive maximum value
     * @return a random int int the given interval
     */
    static int mutateInWholeInterval(int minInclusive, int maxInclusive) {
        if (minInclusive < 0 || maxInclusive < 0) {
            throw new IllegalArgumentException("Parameters must be a positive number or zero.");
        }
        Random rand = new Random();
        return (rand.nextInt(maxInclusive - minInclusive + 1) + minInclusive);
    }


}

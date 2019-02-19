package evolver;

import java.util.Random;

public class Utils {

    public static int mutateInInterval(int minInclusive, int maxInclusive, int currentVal, int maxStep) {
        int returnVal = currentVal;
        Random rand = new Random();

        do {
            //System.out.println("Utils.mutateInInterval outer"+minInclusive + " " + maxInclusive + " " + currentVal);
            int delta = 0;
            do {
                //System.out.println("Utils.mutateInInterval inner");
                delta = rand.nextInt(maxStep * 2 + 1) - maxStep;
            } while (delta == 0);
            returnVal = currentVal + delta;
        } while (returnVal < minInclusive || returnVal > maxInclusive);

        return returnVal;
    }

    public static int mutateInWholeInterval(int minInclusive, int maxInclusive) {
        Random rand = new Random();
        return (rand.nextInt(maxInclusive - minInclusive + 1) + minInclusive);
    }


    public static float mutateAlphaInWholeInterval(float minInclusive, float maxInclusive) {
        Random rand = new Random();
        float returnFloat = rand.nextFloat() * (maxInclusive - minInclusive)+minInclusive;
        return returnFloat;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            //System.out.println(mutateInInterval(0, 10, 4, 6));
            //System.out.println(mutateInWholeInterval(3, 8));
            System.out.println(mutateAlphaInWholeInterval(0.6f, 0.8f));
        }

    }
}

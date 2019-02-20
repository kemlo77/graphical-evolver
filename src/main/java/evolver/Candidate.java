package evolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Candidate {

    //TODO: fler konstruktorer med andra alternativ?

    private static TargetImage targetImage = GraphicalEvolver.getTargetImage();

    private List<Trait> traitsList = new ArrayList<>();

    private long calculatedDifference;
    private long successfulMutations;
    private long maximumDifference;
    private int width;
    private int height;
    private Graphics2D candidateGraphics2D;
    private BufferedImage candidateBI;


    Candidate(int numberOfTraits) {
        width = targetImage.getImageWidth();
        height = targetImage.getImageHeight();
        maximumDifference = width * height * 3 * 255;

        for (int i = 0; i < numberOfTraits; i++) {
            traitsList.add(new Polygon(6, width, height));
        }

        System.out.println("image-type is: " + targetImage.getImageType());

        candidateBI = new BufferedImage(width, height, targetImage.getImageType());
        candidateGraphics2D = candidateBI.createGraphics();
        //candidateGraphics2D = (Graphics2D) candidateGraphics;

        //TODO: duplicerad kod i denna klassen
        candidateGraphics2D.setBackground(Color.WHITE);
        candidateGraphics2D.clearRect(0, 0, width, height);
        for (Trait trait : traitsList) {
            trait.draw(candidateGraphics2D);
        }


        //TODO: utvärdera tidsskillnad på bildSkillnad1 och bildskillnad2
        try {
            calculatedDifference = Differ.imageDifference(targetImage.getBufferedImage(), candidateBI);
            ImageIO.write(candidateBI, "png", new File(System.getProperty("user.dir") + "/out/images/" + "candidateBI.png"));
            System.out.println("Skillnaden är: " + calculatedDifference);
        } catch (IOException e) {
            System.out.println("kunde inte beräkna initiala skillnaden");
            e.printStackTrace();
        }

    }


    void evolve() {
        Random rand = new Random();
        int randomTraitNr = rand.nextInt(traitsList.size());

        System.out.print(" #" + randomTraitNr + " ");
        Trait randomTrait = traitsList.get(randomTraitNr);

        //TODO:snyggare slumpfunktion här
//        int selectMutateAction = rand.nextInt(3);
//        if (selectMutateAction == 0) randomTrait.mutateShape();
//        if (selectMutateAction == 1) randomTrait.mutateRGB();
//        if (selectMutateAction == 2) randomTrait.mutateAlpha();


        //Mutating
        randomTrait.mutateAll();

        //Drawing
        candidateGraphics2D.setBackground(Color.WHITE);
        candidateGraphics2D.clearRect(0, 0, width, height);
        for (Trait trait : traitsList) {
            trait.draw(candidateGraphics2D);
        }

        //Comparing
        long differenceAfterMutation = Differ.imageDifference(targetImage.getBufferedImage(), candidateBI);

        System.out.print("efter: " + differenceAfterMutation + " before: " + calculatedDifference + " ");
        if (differenceAfterMutation > calculatedDifference) {
            System.out.print("(throw)");
            randomTrait.removeLastMutation();
        } else {
            System.out.print("(keep)");
            calculatedDifference = differenceAfterMutation;
            successfulMutations++;
        }


        System.out.print(" Successful mutations:" + successfulMutations + " ");
        System.out.printf("Fitness: %.2f", (1 - (float) calculatedDifference / maximumDifference) * 100);
        System.out.println();


    }

    void saveToFile(String fileName) {
        String pathToNewFile = System.getProperty("user.dir") + "/out/images/" + fileName;
        System.out.println("Saving candidate to: " + pathToNewFile);
        try {
            ImageIO.write(candidateBI, "png", new File(pathToNewFile));
        } catch (IOException e) {
            System.out.println("Could not save candidate image file to: " + pathToNewFile);
            e.printStackTrace();
        }
    }


}

package evolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Candidate {

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
        maximumDifference=width*height*3*255;

        for (int i = 0; i < numberOfTraits; i++) {
            traitsList.add(new Polygon(6,width,height));
        }

        candidateBI = new BufferedImage(width,height,targetImage.getImageType());
        candidateGraphics2D = candidateBI.createGraphics();
        //candidateGraphics2D = (Graphics2D) candidateGraphics;

        //TODO: duplicerad kod i denna klassen
        candidateGraphics2D.setBackground(Color.WHITE);
        candidateGraphics2D.clearRect(0,0,width,height);
        for(Trait trait: traitsList){
            trait.draw(candidateGraphics2D);
        }


        //TODO: utvärdera tidsskillnad på bildSkillnad1 och bildskillnad2
        try {
            calculatedDifference = Differ.bildSkillnad2(targetImage.getBufferedImage(), candidateBI);
            //ImageIO.write(targetImage.getBufferedImage(), "png", new File("targetImage.png"));
            ImageIO.write(candidateBI, "png", new File("candidateBI.png"));

            System.out.println("Skillnaden är: " + calculatedDifference);
        }catch(IOException e){
            System.out.println("kunde inte beräkna initiala skillnaden");
            e.printStackTrace();
        }

    }


    public void evolve() {



        Random rand = new Random();
        int randomTraitNr = rand.nextInt(traitsList.size());

        System.out.print(" #"+randomTraitNr+" ");
        Trait randomTrait = traitsList.get(randomTraitNr);

        //System.out.println("O"+randomTrait);

        //TODO:snyggare slumpfunktion här
//        int selectMutateAction = rand.nextInt(3);
//        if(selectMutateAction==0){
//            System.out.print("muterar shape ");
//            randomTrait.mutateShape();
//        }
//        if(selectMutateAction==1){
//            System.out.print("muterar RGB   ");
//            randomTrait.mutateRGB();
//        }
//        if(selectMutateAction==2){
//            System.out.print("muterar alpha ");
//            randomTrait.mutateAlpha();
//        }

        randomTrait.mutateAll();;

        //randomTrait.mutateShape();
        //randomTrait.mutateRGB();
        //randomTrait.mutateAlpha();

        //System.out.println("M"+randomTrait);




        //System.out.println("ritar");
        candidateGraphics2D.setBackground(Color.WHITE);
        candidateGraphics2D.clearRect(0,0,width,height);
        for(Trait trait: traitsList){
            trait.draw(candidateGraphics2D);
        }




        //TODO: göra den här exception-hanteringen i metoden istället?
        try {
            //System.out.println("jämför");
            long differenceAfterMutation= Differ.bildSkillnad2(targetImage.getBufferedImage(), candidateBI);

            System.out.print("efter: "+differenceAfterMutation+" before: "+ calculatedDifference+" ");
            if(differenceAfterMutation>calculatedDifference){
                System.out.print("(slänger)");
                randomTrait.removeLastMutation();
                //System.out.println("O"+randomTrait);
            }
            else{
                System.out.print("(behåller)");
                calculatedDifference=differenceAfterMutation;
                successfulMutations++;
            }


        }catch(IOException e){
            System.out.println("kunde inte beräkna skillnaden");
            e.printStackTrace();
            randomTrait.removeLastMutation();
        }

        System.out.print(" lyckade:"+successfulMutations+" ");
        System.out.printf("fitness: %.2f", (1-(float)calculatedDifference/maximumDifference)*100);
        System.out.println();



    }

    public void saveToFile(String pathToNewFile){

        System.out.println("Sparar diffad bild till: " + pathToNewFile);
        try{
            ImageIO.write(candidateBI, "png", new File(pathToNewFile));
        }catch(IOException e){
            System.out.println("Kunde inte spara Candidate-bildfilen till: " +pathToNewFile);
            e.printStackTrace();
        }

    }



    //public Candidate clone() {}

    //public Trait removeTrait(int traitNumber) {}

    //public void addNewTrait(){}

    //public void shuffleTraits(){}

    //remove traitsList that do not contribute (invisible, hidden or zero area)
    //public void removeInhibitedTrait(){}


}

package evolver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class GraphicalEvolver {




    private static TargetImage targetImage;

    public static TargetImage getTargetImage() {
        return targetImage;
    }







    public static void main(String[] args)   {

        try{
            targetImage = new TargetImage(new File(System.getProperty("user.dir") + "/mona_small.png"));
        }catch(IOException e){
            e.printStackTrace();

        }
        //BufferedImage targetImage = ImageIO.read(new File(System.getProperty("user.dir") + "/red.png"));




        Candidate candidate = new Candidate(40);
        LocalTime start = LocalTime.now();
        candidate.evolve();
        candidate.saveToFile("first.png");
        int antalUpprepningar = 5000;
        for (int i = 0; i < antalUpprepningar; i++) {
            System.out.print(i+" ");
            candidate.evolve();
        }
        candidate.saveToFile("second.png");
        LocalTime slut = LocalTime.now();
        Duration duration = Duration.between(start,slut);
        System.out.println(antalUpprepningar+" upprepningar tog " +duration.getSeconds()+ " sekunder.");
        System.out.println("det blir "+ (float)antalUpprepningar/duration.getSeconds()+ " per sekund");






        //TODO: spara till fil


    }




}

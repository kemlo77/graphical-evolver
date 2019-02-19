package evolver;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Polygon implements Trait {

    //TODO: ej komplexa polygoner?
    //TODO: fler konstruktorer ex: utan parametrar
    //enkel: 8 vertices, random color
    // utförlig: Point-list, r,g,b, transparens

    static int mutationMaxSteps = 80;


    private List<Point> pointList;
    private Color color;
    private Point oldPoint;
    private int mutadedPointNo;
    private Color oldColor;
    private int width;
    private int height;


    public Polygon(int numberOfVertices, int width, int height) {

        //TODO: hantera width och height bättre så att det inte måste finnas med i varje objekt
        this.width=width;
        this.height=height;

        ArrayList<Point> points = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, width + 1);
            int y = ThreadLocalRandom.current().nextInt(0, height + 1);
            points.add(new Point(x,y));
        }
        this.pointList = points;

        int r = ThreadLocalRandom.current().nextInt(0, 255);
        int g = ThreadLocalRandom.current().nextInt(0, 255);
        int b = ThreadLocalRandom.current().nextInt(0, 255);

        this.color = new Color(r, g, b, 10);
    }


    @Override
    public void mutateShape() {

        //TODO: jag kommer mutera fler punkter i andra klasser, kanske borde ligga i Utils-klassen (samma med färg etc)
        Random rand = new Random();
        mutadedPointNo=rand.nextInt(pointList.size());



        Point pointToBeMutated = pointList.get(mutadedPointNo);

        //backing upp old point location
        oldPoint = new Point(pointToBeMutated);

        //moving/mutating
        int newX= Utils.mutateInWholeInterval(0,width);
        int newY= Utils.mutateInWholeInterval(0,height);
        //int newX= Utils.mutateInInterval(0,width,oldPoint.x,mutationMaxSteps);
        //int newY= Utils.mutateInInterval(0,height,oldPoint.y,mutationMaxSteps);
        pointToBeMutated.setLocation(newX,newY);



    }

    @Override
    public void mutateRGB() {
        //TODO: skriv om så att man bara muterar en färg alternativt en metod per färg
        oldColor=color;
        //int newR = Utils.mutateInInterval(0,255,color.getRed(),mutationMaxSteps);
        //int newG = Utils.mutateInInterval(0,255,color.getGreen(),mutationMaxSteps);
        //int newB = Utils.mutateInInterval(0,255,color.getBlue(),mutationMaxSteps);
        int newR = Utils.mutateInWholeInterval(0,255);
        int newG = Utils.mutateInWholeInterval(0,255);
        int newB = Utils.mutateInWholeInterval(0,255);

        color = new Color(newR,newG,newB,color.getAlpha());

        //System.out.println(oldColor+" "+color);


    }

    @Override
    public void mutateAlpha() {
        oldColor=color;
        int newAlpha = Utils.mutateInInterval(0,255,color.getAlpha(),mutationMaxSteps);
        color = new Color(color.getRed(),color.getGreen(),color.getBlue(),newAlpha);
    }


    @Override
    public void mutateAll(){

        Random rand = new Random();
        mutadedPointNo=rand.nextInt(pointList.size());
        Point pointToBeMutated = pointList.get(mutadedPointNo);

        //backing upp old point location
        oldPoint = new Point(pointToBeMutated);

        //moving/mutating
        int newX= Utils.mutateInWholeInterval(0,width);
        int newY= Utils.mutateInWholeInterval(0,height);
        pointToBeMutated.setLocation(newX,newY);

        //backing up old color
        oldColor=color;
        //creating new color
        int newR = Utils.mutateInWholeInterval(0,255);
        int newG = Utils.mutateInWholeInterval(0,255);
        int newB = Utils.mutateInWholeInterval(0,255);
        int newAlpha = Utils.mutateInInterval(0,255,color.getAlpha(),mutationMaxSteps);
        color = new Color(newR,newG,newB,newAlpha);
    }


    @Override
    public void removeLastMutation() {
        if(oldPoint!=null){
            pointList.set(mutadedPointNo,oldPoint);
            oldPoint=null;
        }
        if(oldColor!=null){
            color=oldColor;
            oldColor=null;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        //TODO: skriva om detta så den använder den nya Graphics2D som inparameter istället
        //Graphics2D g2d = (Graphics2D) graphics;
        Path2D.Double polygon = new Path2D.Double();
        polygon.moveTo(pointList.get(0).x, pointList.get(0).y);

        for (int i = 1; i < pointList.size(); i++) {
            polygon.lineTo(pointList.get(i).x, pointList.get(i).y);
        }
        polygon.closePath();
        g2d.setPaint(color);
        g2d.fill(polygon);


    }


    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public String toString(){
        String returnString="[";
        returnString+=color.getRed()+",";
        returnString+=color.getGreen()+",";
        returnString+=color.getBlue()+",";
        returnString+=color.getAlpha()+"] ";
        for(Point p:pointList){
            returnString+=p.x+","+p.y+" ";
        }
        return returnString;
    }






}

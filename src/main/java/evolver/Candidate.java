package evolver;

import evolver.traits.Trait;
import evolver.traits.TraitFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Candidate {

  private List<Trait> traitsList = new ArrayList<>();
  private MutationInfo mutationInfo;


  Candidate(int numberOfTraits) {

    traitsList.add(TraitFactory.getTrait("background"));
    addRandomTraits(numberOfTraits);
    TargetImage.redrawCandidate(traitsList);

    mutationInfo = new MutationInfo();
    mutationInfo.setCalculatedDifference(TargetImage.calculateDifference());
  }


  MutationInfo getMutationInfo() {
    return this.mutationInfo;
  }


  int removeTraitsThatContributeUnderLimit(float contributionLimit) {
    float currentFitness = mutationInfo.getFitnessPercentage();
    int antalTraits = traitsList.size();
    for (int i = 0; i < antalTraits; i++) {

      TargetImage.redrawCandidate(traitsList, i);
      long calculatedDifference = TargetImage.calculateDifference();
      float newCalculatedFitness =
          (1 - (float) calculatedDifference / TargetImage.getMaximumDifference()) * 100;

      System.out.println(""
          + String.format("%.3f",currentFitness) + " - "
          + String.format("%.3f",newCalculatedFitness) + " = "
          + String.format("%.3f",(currentFitness - newCalculatedFitness)));

      if ((currentFitness - newCalculatedFitness) < contributionLimit) {
        traitsList.get(i).setDead();
      }

    }
    traitsList.removeIf(Trait::isDead);
    int numberOfTraitsRemoved = (antalTraits - traitsList.size());
    System.out.println("Removed " + numberOfTraitsRemoved + " traits.");
    TargetImage.redrawCandidate(traitsList);
    mutationInfo.setCalculatedDifference(TargetImage.calculateDifference());

    return numberOfTraitsRemoved;
  }


  void addRandomTraits(int numberOfNewTraits) {
    Random rand = new Random();
    for (int i = 0; i < numberOfNewTraits; i++) {
      int randomInt = rand.nextInt(3);
      if (randomInt == 0) {
        traitsList.add(TraitFactory.getTrait("polygon"));
      }
      if (randomInt == 1) {
        traitsList.add(TraitFactory.getTrait("circle"));
      }
      if (randomInt == 2) {
        traitsList.add(TraitFactory.getTrait("line"));
      }
    }
    System.out.println("Added " + numberOfNewTraits + " new traits.");
  }


  void evolve(float degree) {
    mutationInfo.startTime();
    Random rand = new Random();

    Trait randomTrait = traitsList.get(rand.nextInt(traitsList.size()));

    //Mutating
    //TODO: Ändra hur/vad som muteras beroende på degree. T.ex bara color eller bara shape
    randomTrait.mutateColor(degree);
    randomTrait.mutateShape(degree);

    //Drawing
    TargetImage.redrawCandidate(traitsList);

    mutationInfo.middleTime();
    //Comparing
    long differenceAfterMutation = TargetImage.calculateDifference();

    if (differenceAfterMutation > mutationInfo.getCalculatedDifference()) {
      randomTrait.removeLastShapeMutation();
      randomTrait.removeLastColorMutation();
    } else {
      mutationInfo.setCalculatedDifference(differenceAfterMutation);
      mutationInfo.upSuccessfulMutations();
    }
    mutationInfo.upTotNumberOfMutations();
    mutationInfo.stopTime();
  }


  void saveToFile(String fileName) {
    TargetImage.saveToFile(traitsList, fileName);
  }


  String toSvg() {
    StringBuilder sb = new StringBuilder();
    sb
        .append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n")
        .append(
            "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" "
                + "\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n")
        .append(
            "<svg xmlns=\"http://www.w3.org/2000/svg\" "
                + "xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" >\n")

        .append("<defs>\n")
        .append("\t<clipPath id=\"limits\">\n")
        .append("\t\t<rect x=\"0\" y=\"0\" width=\"")
        .append(TargetImage.getImageWidth())
        .append("\" height=\"")
        .append(TargetImage.getImageHeight())
        .append("\" />\n")
        .append("\t</clipPath>\n")
        .append(" </defs>\n")
        .append("<g clip-path=\"url(#limits)\">\n");
    for (Trait trait : traitsList) {
      sb.append("\t").append(trait.toSvg()).append("\n");
    }

    sb.append("</g>\n").append("</svg>");
    return sb.toString();
  }

}
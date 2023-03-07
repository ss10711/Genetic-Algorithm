import java.util.*;

public class main {
public static void main(String[] args) {
    /*GeneticAlgorithm g1 = new GeneticAlgorithm();
    //generates a n queens problem of any length, I am using 8 for example
    g1.population(8);
    boolean goal;
    /*we are going to find the fitness of the first generation and see if any of them match the goal if yes we change goal to true.
    if not we generate the probability of each one and based of that probability we choose which ones will survive and mate
    we then perform a crossover and mutation and get the new population which then leads us to the beginning of the loop to see if the goal has been reached
     */
   /* do {
        g1.fitness();
        goal=g1.isGoal();
        g1.probability();
        g1.selected();
        g1.crossover();
        g1.mutation();
        g1.NewPopulation();


    }while(goal==false); */

    GeneticAlgorithm g2 = new GeneticAlgorithm();
     List<Integer> items = new ArrayList<>();
     Random rd= new Random();
     for(int j=0;j<20;j++){
         items.add(rd.nextInt(1,20));
     }
     System.out.println(items);
    //list of items with weights bag can only hold five items, whats the maximum weight we can get
    g2.population(5,items);
     System.out.println("Hello");
     System.out.println(g2.population);
     boolean goal2=false;
        /*For my real world problem I created a random list of integers which represents a weight of an item. We can only have five items in out bag and we want to
        see which combination can give us the highest weight. So we go through the same process as the N queens.

         */
     do{
         g2.fitnessBag();
         goal2=g2.isGoalBag(items);
         g2.probability();
         g2.selected();
         g2.crossover();
         g2.mutationBag();
         g2.NewPopulation();


     }while(goal2==false);



}

}

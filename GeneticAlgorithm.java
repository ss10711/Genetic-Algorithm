import java.util.*;

public class GeneticAlgorithm {
    List<Integer> chromosome;
    List<Integer> fitness;
    List<List> population;
    List<Integer> probability;
    List <List> children;
    List <Integer> previousChromosome;
    int prevChromProb;
    int length;
    int generation=0;
    List<Integer> items = new ArrayList<>();
    int goalFitness;

    public List<Integer> chromosome(int length) {
        //makes an individual chromosome that will be stored in our population, only used for N-queens
        List<Integer> chromosome = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            chromosome.add(rd.nextInt(1, (length + 1)));

        }
        Collections.shuffle(chromosome);
        this.length=length;
        return this.chromosome = chromosome;
    }


    public List<List> population(int length) {
        //initializes population of chromosomes for N-queens problem
        int popLen = length - 1;
        List<List> population = new ArrayList<>();
        for (int i = 0; i < popLen; i++) {
            population.add(chromosome(length));
        }
        return this.population = population;
    }

    public List<List> population(int length, List<Integer> items) {
        //Initializes population for Bag problem
        int popLen = length;
        List<List> population = new ArrayList<>();
        for (int i = 0; i < popLen; i++) {
            population.add(chromosome(length,items));
        }
        return this.population = population;
    }
    public List<Integer> chromosome(int length,List<Integer> items) {
        //initializes chromosome for bag problem
        this.length=length;
        List<Integer> chromosome = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
           int index= rd.nextInt(0,20);
            chromosome.add(items.get(index));
        }
        this.items=items;
        this.length=length;
        return this.chromosome = chromosome;
    }
    public List<Integer> fitnessBag(){
        //used to find the fitness of a chromosome for bag problem. fitness score is all the numbers/weights in chromosome added together
        List <Integer> fitness = new ArrayList<>();

        for(int i=0;i<population.size();i++){
            int fit=0;
            chromosome= population.get(i);
            for(int j=0;j<chromosome.size();j++) {
                fit += chromosome.get(j);
            }
            fitness.add(fit);

        }
   return this.fitness=fitness; }

    public boolean isGoalBag(List <Integer> items){
        // we check to see if one of our chromosomes has the highest possible weight
        int goalFitness=0;
        boolean goal= false;
        for(int i=0;i< population.size();i++){
            chromosome=population.get(i);
            Collections.sort(chromosome);
            goalFitness+= chromosome.get(4);
        }
        this.goalFitness=goalFitness;
        System.out.println("Goal is " + goalFitness + " generation is " + generation);
        for(int j=0;j<fitness.size();j++){
            System.out.println("Chromosome is " + population.get(j) + "Fitness is " + fitness.get(j));
            if(goalFitness==fitness.get(j)){
                goal=true;
                System.out.println("Goal is reached!");
                break;
            }else generation++;
        }
        return goal;}
    public void mutationBag(){
        //we keep our mutation rate low and switch random indexes for children nodes and for the node that had the lowest probability we choose a random index and replace it with random number
        List <Integer> mutation= new ArrayList<>();
        if(prevChromProb<=11){
            Random rd = new Random();
            int index = rd.nextInt(0,chromosome.size());
            int number = rd.nextInt(1,length+1);
            previousChromosome.set(index,number);

        }
        for(int i=0;i< probability.size();i++){
            if(probability.get(i)<=13){
                Random rd= new Random();
                int swap1= rd.nextInt(0, probability.size()-1);
                int swap2= rd.nextInt(0, probability.size()-1);
                mutation=children.get(i);
                int d= mutation.get(swap1);
                int r= mutation.get(swap2);
                mutation.set(swap1,r);
                mutation.set(swap2,d);
                children.set(i,mutation);
            }
                }

    }




    public List<Integer> fitness() {
        //calculates fitness for N-queen
        List<Integer> fitness = new ArrayList<>();
        int Q1, Q2, fit = 0;
        List<Integer> chromosome;
        //Fitness function is pairs of non-attacking queens
        //take one chromosome at a time and add one to fitness score each time Q1 is not equal two Q2
        for (int i = 0; i < population.size(); i++) {
            chromosome = population.get(i);
            for (int k = 0; k < chromosome.size(); k++) {
                Q1 = chromosome.get(k);
                for (int j = chromosome.size() - 1; j > k; j--) {
                    Q2 = chromosome.get(j);
                    if (Q1 != Q2) {
                        fit += 1;
                    }

                }
            }
            fitness.add(fit);
            fit=0;

        }

        return this.fitness = fitness;
    }

    public List<Integer> probability() {
        //finds the probabilty and can be used for both problems.

        List<Integer> probability = new ArrayList<>();
        int totalfitness = 0;

        //add all the fitness scores of the population together
        for (int i = 0; i < fitness.size(); i++) {
            totalfitness += fitness().get(i);

        }
        for (int j = 0; j < fitness.size(); j++) {
            double prob;
            int probab;
            int p = fitness.get(j);
            prob = Math.round((p*100)/totalfitness);
            probab= (int)prob;
            probability.add(probab);
        }


        return this.probability = probability;
    }

    public void selected() {
        //Temporarily gets rid of node with lowest probability so that we can create children from the strongest parents.
        List<Integer> previousChromosome;
        int low = 500;
        int index = 0;
        for (int i = 0; i < probability.size() - 1; i++) {
            if (probability.get(i) < low) {
                low = probability.get(i);
                index = i;
            }
        }
        previousChromosome =population.get(index);
        this.previousChromosome=previousChromosome;
        prevChromProb = probability.get(index);
        probability.remove(index);
        fitness.remove(index);
        population.remove(index);

    }

    public void crossover() {
        // We perform the crossover between the second and third numbers
        List<Integer> parent1 = new ArrayList<>();
        List<Integer> parent2 = new ArrayList<>();
        List<List> children =  new ArrayList<>();

        int crossover = 1;
        for (int i = 0; i < population.size()-1; i+=2) {
            List<Integer> child = new ArrayList<>();
            List<Integer> child2= new ArrayList<>();
            parent1 = population.get(i);
            parent2 = population.get(i + 1);
            for (int j = 0; j <= crossover; j++) {
                child.add(j, parent1.get(j));
                child2.add(j, parent2.get(j));
            }
                for (int k = crossover + 1; k < parent1.size(); k++) {
                    child.add(k, parent2.get(k));
                    child2.add(k,parent1.get(k) );

                }

                 children.add(child);
                children.add(child2);
                this.children=children;

            }
        }
        public void mutation(){
        //we keep our mutation rate low and switch ramdom indexes for children nodes and for the node that had the lowest probability we choose a random index and replace it with random number
        List <Integer> mutation= new ArrayList<>();
        if(prevChromProb<=13){
            Random rd = new Random();
            int index = rd.nextInt(0,chromosome.size());
            int number = rd.nextInt(1,length+1);
            previousChromosome.set(index,number);

        }
        for(int i=0;i< probability.size();i++){
            if(probability.get(i)<=13){
                Random rd= new Random();
               int swap1= rd.nextInt(0, probability.size()-1);
               int swap2= rd.nextInt(0, probability.size()-1);
                mutation=children.get(i);
               int d= mutation.get(swap1);
               int r= mutation.get(swap2);
               mutation.set(swap1,r);
               mutation.set(swap2,d);
                children.set(i,mutation);
            }
        }

        }

        public List<List> NewPopulation(){
        //we define our new population
        population.clear();
        population=children;
        population.add(previousChromosome);

        return population; }
    public boolean isGoal(){
        //sees if we have reached the goal for N-queens problem
        int goalFitness=0;
        boolean goal= false;
        for(int i=1;i<length;i++){
           goalFitness+= Math.abs(i-length);
        }
        System.out.println("Goal is " + goalFitness + " generation is " + generation);
        for(int j=0;j<fitness.size();j++){
            System.out.println("Chromosome is " + population.get(j) + "Fitness is " + fitness.get(j));
            if(goalFitness==fitness.get(j)){
                goal=true;
                System.out.println("Goal is reached!");
                break;
            }else generation++;
        }
    return goal;}


    }









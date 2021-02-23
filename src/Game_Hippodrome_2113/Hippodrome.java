package Game_Hippodrome_2113;

import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    public static Hippodrome game;

    private List<Horse> horses;

    public List<Horse> getHorses() {
        return horses;
    }

    public Hippodrome(List<Horse> horses){
        this.horses = horses;
    }

    public void run(){
        for (int i = 0; i < 100; i++) {
            move();
            print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move(){
        for (Horse single: horses) {
            single.move();
        }
    }

    public void print(){
        for (Horse single: horses) {
            single.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public Horse getWinner(){
        Horse winner = null;
        double maxDist = 0d;
        for (Horse h: getHorses()) {
            if (h.getDistance()>maxDist){
                maxDist = h.getDistance();
                winner = h;
            }
        }
        return winner;
    }

    public void printWinner(){
        System.out.printf("Winner is %s!%n", getWinner().getName());
    }



    public static void main(String[] args) {
        List<Horse> someHorses = new ArrayList<>();
        someHorses.add(new Horse("Жучка", 3d, 0d));
        someHorses.add(new Horse("Снежанна", 3d, 0d));
        someHorses.add(new Horse("Лидка", 3d, 0d));

        game = new Hippodrome(someHorses);

        game.run();

        game.printWinner();
    }
}
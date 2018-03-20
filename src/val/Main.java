package val;

import java.util.Scanner;


public class Main {
    private static final Mojo mojo = new Mojo();

    public static void main(String[] args) {
        System.out.println("Mojo GAME Starts!");
        System.out.println(mojo.whoIsFirst() + " starts.");
        System.out.println("Please set you'r figures on board.");
        Scanner scanner = new Scanner(System.in);

        String colour, figure;
        int dots, x ,y;
        while (true) {
            System.out.print("Colour: ");
            colour = scanner.nextLine();
            System.out.print("dots: ");
            dots = scanner.nextInt();
            System.out.print("X Axis: ");
            x = scanner.nextInt();
            System.out.print("Y Axis: ");
            y = scanner.nextInt();
            scanner.nextLine();

            if (mojo.setFigureOnBoard(colour, dots, x, y) != null) {
                break;
            }
        }

            System.out.println("Lets play!");

            while (true){

                System.out.print("Figure: ");
                figure = scanner.nextLine();
                System.out.print("dots: ");
                dots = scanner.nextInt();
                System.out.print("X Axis: ");
                x = scanner.nextInt();
                System.out.print("Y Axis: ");
                y = scanner.nextInt();
                scanner.nextLine();

                if(mojo.play(figure, dots, x, y) != null){
                    break;
                }
            }

    }
}

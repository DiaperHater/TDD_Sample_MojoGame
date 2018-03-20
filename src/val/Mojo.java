package val;

import Exceptions.CellOccupiedException;
import Exceptions.OutOfBoardRuntimeException;
import Exceptions.WrongFigureRuntimeException;

import java.util.Random;

public class Mojo {
    private final int BOARD_SIZE = 3;
    private final String[][] board;
    private final String[] allPlayers = {"Green", "Red"};

    {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++){
            for (int y = 0; y < BOARD_SIZE; y++){
                board[x][y] = "";
            }
        }

    }

    public String whoIsFirst() {
        Random random = new Random();
        boolean greenOrRed = random.nextInt(2) % 2 == 0;

        return (greenOrRed ? "Green" : "Red");
    }
    public String setFigureOnBoard(String figure, int dots, int posX, int posY) {
        posX = posX-1;
        posY = posY-1;


        checkFigure(figure, dots);
        checkBounds(posX, posY);

        if (isCellOccupied(posX, posY)){
            throw new CellOccupiedException();
        }else {
            setCell(figure, dots, posX, posY);
        }

        if(isAllSet()){
            System.out.println("All cells is set!");
            return "All set!";
        }

        return null;
    }
    public String play(String figure, int dots, int posX, int posY) {
        posX = posX-1;
        posY = posY-1;

        checkFigure(figure, dots);

        if (isMoveForbidden(figure, dots, posX, posY)){
            return null;
        }

        int[] pos;
        final int xIndex = 0;
        final int yIndex = 1;

        pos = getFigurePos(formatFigure(figure, dots));
        board[pos[xIndex]][pos[yIndex]] = "";
        board[posX][posY] = formatFigure(figure, dots);


        return getWinner();

    }


    private boolean isAllSet() {
        int count = 0;
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                if(!board[x][y].equals("")){
                    count++;
                }
            }
        }

        return count == 8;
    }
    private void setCell(String figure, int dots, int posX, int posY) {
        if(figure.contains("Pawn")){
            board[posX][posY] = figure;
            return;
        }
        board[posX][posY] = formatFigure(figure, dots);
    }
    private String formatFigure(String figure, int dots) {
        String d = "";
        if (dots == 1) d = ".";
        else if (dots == 2) d = ":";
        else if (dots == 3) d = ".:";
        else if (dots == 4) d = "::";
        else if (dots == 5) d = ".::";
        else if (dots == 6) d = ":::";
        return figure + (figure.contains("Pawn") ? "" : "|") + d;
    }
    private boolean isCellOccupied(int posX, int posY) {
        return !board[posX][posY].equals("");
    }
    private void checkBounds(int posX, int posY) {
        if(posX > BOARD_SIZE-1 || posX < 0
                || posY > BOARD_SIZE-1 || posY < 0){
            throw new OutOfBoardRuntimeException();
        }
    }
    private void checkFigure(String figure, int dots) {
        checkColour(figure);
        checkName(figure, dots);
        checkDots(figure, dots);
    }
    private void checkColour(String figure) {
        if(!figure.contains("Green") && !figure.contains("Red")){
            throw new WrongFigureRuntimeException();
        }

    }
    private void checkName(String figure, int dots) {
        if(!figure.equals("Green")
                && !figure.equals("Red")
                && !figure.contains("Pawn")){
            throw new WrongFigureRuntimeException();
        }
    }
    private void checkDots(String figure, int dots) {
        if(figure.equals("Green")){
            if(dots != 1 && dots != 3 && dots != 5){
                throw new WrongFigureRuntimeException();
            }
        }else if(figure.equals("Red")){
            if(dots != 2 && dots != 4 && dots != 6){
                throw new WrongFigureRuntimeException();
            }
        }
    }
    private String getWinner() {

        Win[] possibleWins = new Win[]{
                this::getHorizontalWinner,
                this::getVerticalWinner,
                this::getDiagonalWinner
        };

        String winner;
        for (Win win : possibleWins){
            if( (winner = win.check()) != null ){
                System.out.println(winner + " Win!!!");
                return winner;
            }

        }

        return null;
    }
    private String getDiagonalWinner() {
        for (String onePlayer : allPlayers){
            String winner = getUpToBottomWinner(onePlayer);
            if (winner != null){
                return winner;
            }
            winner = getBottomUpWinner(onePlayer);
            if (winner != null){
                return winner;
            }
        }
        return null;
    }
    private String getBottomUpWinner(String player) {
        for(int x = 0, y = BOARD_SIZE-1; x < BOARD_SIZE; x++, y--){
            if(!board[x][y].contains(player) || board[x][y].contains("Pawn")){
                break;
            }else if (x == BOARD_SIZE-1){
                return player;
            }
        }

        return null;
    }
    private String getUpToBottomWinner(String player) {
        for(int i = 0; i < BOARD_SIZE; i++){
            if(!board[i][i].contains(player) || board[i][i].contains("Pawn")){
                break;
            }else if (i == BOARD_SIZE-1){
                return player;
            }
        }

        return null;
    }
    private String getVerticalWinner() {
        for(int x = 0 ; x < BOARD_SIZE; x++){
            for (String onePlayer : allPlayers){
                if( (board[x][0].contains(onePlayer) && !board[x][0].contains("Pawn"))
                        && (board[x][1].contains(onePlayer) && !board[x][1].contains("Pawn"))
                        && (board[x][2].contains(onePlayer) && !board[x][2].contains("Pawn")) ){
                    return onePlayer;
                }
            }
        }
        return null;
    }
    private String getHorizontalWinner() {
        for(int y = 0 ; y < BOARD_SIZE; y++){
            for (String onePlayer : allPlayers){
                if( (board[0][y].contains(onePlayer) && !board[0][y].contains("Pawn"))
                        && (board[1][y].contains(onePlayer) && !board[1][y].contains("Pawn"))
                        && (board[2][y].contains(onePlayer) && !board[2][y].contains("Pawn")) ){
                    return onePlayer;
                }
            }
        }
        return null;
    }
    private int[] getFigurePos(String figure) {
        int[] pos = new int[2];
        final int xIndex = 0;
        final int yIndex = 1;

        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                if (board[x][y].equals(figure)){
                    pos[xIndex] = x;
                    pos[yIndex] = y;
                }
            }
        }

        return pos;
    }
    private boolean isMoveForbidden(String figure, int dots, int posX, int posY) {
        String formattedFigure = formatFigure(figure, dots);

        if(isNonexistentFigure(formattedFigure)){
            System.out.println("Can't move nonexistent figure !");
            return true;
        }
        if(isMoveOutOfBoard(posX, posY)){
            System.out.println("Can't move out of board!");
            return true;
        }
        if(isMoveToLong(formattedFigure, posX, posY)){
            System.out.println("Can't move so far!");
            return true;
        }
        if(isCellOccupied(posX, posY)){
            System.out.println("Cell is occupied!");
            return true;
        }

        return false;
    }
    private boolean isNonexistentFigure(String formattedFigure) {
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                if(board[x][y] != null && board[x][y].equals(formattedFigure)){
                    return false;
                }
            }
        }

        return true;
    }
    private boolean isMoveOutOfBoard(int posX, int posY) {
        try {
            checkBounds(posX, posY);
        }catch (OutOfBoardRuntimeException e){
            return true;
        }
        return false;
    }
    private boolean isMoveToLong(String formattedFigure, int posX, int posY) {
        for (int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                if(board[x][y] != null
                        && board[x][y].equals(formattedFigure)){
                    int diffX = Math.abs( x - posX );
                    int diffY = Math.abs( y - posY );
                    return (diffX > 1) || (diffY > 1);
                }
            }
        }
        return false;
    }
}

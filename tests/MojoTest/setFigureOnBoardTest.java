package MojoTest;

import Exceptions.CellOccupiedException;
import Exceptions.OutOfBoardRuntimeException;
import Exceptions.WrongFigureRuntimeException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import val.Mojo;
import java.io.*;
import java.lang.reflect.Field;

public class setFigureOnBoardTest {

    private Mojo mojo;

    @BeforeEach
    void beforeEach(){
        mojo = new Mojo();
    }

    @Test
    void figureColourIsWrongThenRunTimeException(){
        assertThrows(WrongFigureRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Blue",2, 1,1));
    }

    @Test
    void setFigureOnBoard_WrongFigure_WrongFigureRuntimeException(){
        Executable executable = ()-> mojo.setFigureOnBoard("Green card", 0, 1,1);
        assertThrows(WrongFigureRuntimeException.class, executable);
    }

    @Test
    void figureDotsIsWrongThenRuntimeException(){
        assertThrows(WrongFigureRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Green", 4, 1,1));
    }

    @Test
    void figureDotsIsWrongThenRuntimeException_2(){
        assertThrows(WrongFigureRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Red", 8, 1,1));
    }

    @Test
    void X_AxisOutOfRangeThenRuntimeException(){
        assertThrows(OutOfBoardRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Green", 1, 0, 2));
        assertThrows(OutOfBoardRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Green", 1, 4, 2));
    }

    @Test
    void Y_AxisOutOfRangeThenRuntimeException(){
        assertThrows(OutOfBoardRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Green", 1, 2, 0));
        assertThrows(OutOfBoardRuntimeException.class,
                ()-> mojo.setFigureOnBoard("Green", 1, 2, 4));
    }

    @Test
    void setFigureOnBoard_OnOccupiedCell_CellOccupiedException(){
        mojo.setFigureOnBoard("Green", 3, 2,2);
        Executable executable = ()-> mojo.setFigureOnBoard("Green", 1, 2,2);
        assertThrows(CellOccupiedException.class, executable);
    }

    @Test
    void setFigureOnBoard_Green5Dots_Set() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 5, 2,2);
        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        String[][] board = (String[][]) field.get(mojo);
        assertEquals("Green|.::",  board[2-1][2-1]);
    }

    @Test
    void setFigureOnBoard_Red4Dots_Set() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Red", 4, 2,2);
        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        String[][] board = (String[][]) field.get(mojo);
        assertEquals("Red|::",  board[2-1][2-1]);
    }

    @Test
    void setFigureOnBoard_GreenPawn_Set() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green Pawn", 0, 1, 1);
        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);
        String[][] board = (String[][]) field.get(mojo);

        assertEquals("Green Pawn", board[0][0]);
    }

    @Test
    void setFigureOnBoard_RedPawn_Set() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Red Pawn", 0, 3, 2);
        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);
        String[][] board = (String[][]) field.get(mojo);

        assertEquals("Red Pawn", board[3-1][2-1]);
    }

    @Test
    void setFigureOnBoard_RedPawnWithDots_Set() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Red Pawn", 3, 3, 2);
        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);
        String[][] board = (String[][]) field.get(mojo);

        assertEquals("Red Pawn", board[3-1][2-1]);
    }

    @Test
    void setFigureOnBoard_AllSet_PrintsMessageAllSet() throws FileNotFoundException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.setFigureOnBoard("Green", 3, 2,1);
        mojo.setFigureOnBoard("Red", 2, 2,2);
        mojo.setFigureOnBoard("Green", 5, 3,2);
        mojo.setFigureOnBoard("Red", 2, 3,1);
        mojo.setFigureOnBoard("Red Pawn", 0, 3,3);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.setFigureOnBoard("Green Pawn", 0, 1,3);

        assertEquals("All cells is set!\r\n", msg.toString());

    }

    @Test
    void setFigureOnBoard_AllSet_ReturnAllSetString() throws FileNotFoundException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.setFigureOnBoard("Green", 3, 2,1);
        mojo.setFigureOnBoard("Red", 2, 2,2);
        mojo.setFigureOnBoard("Green", 5, 3,2);
        mojo.setFigureOnBoard("Red", 2, 3,1);
        mojo.setFigureOnBoard("Red Pawn", 0, 3,3);

        assertEquals("All set!",
                mojo.setFigureOnBoard("Green Pawn", 0, 1,3));

    }

    @Test
    void setFigureOnBoard_NotAllSet_ReturnNull(){
        assertEquals(null, mojo.setFigureOnBoard("Green", 1, 1,1));
    }
}

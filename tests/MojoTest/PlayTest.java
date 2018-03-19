package MojoTest;
import Exceptions.WrongFigureRuntimeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import val.Mojo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class PlayTest {

    private Mojo mojo;

    @BeforeEach
    void beforeEach(){
        mojo = new Mojo();
    }

    @Test
    void play_WrongFigureColour_Exception(){
        Executable exec = ()->{mojo.play("en", 1, 2,3);};
        assertThrows(WrongFigureRuntimeException.class,
                exec);
    }

    @Test
    void play_WrongFigureName_Exception(){
        Executable exec = ()->{mojo.play("Green **", 1, 2,3);};
        assertThrows(WrongFigureRuntimeException.class,
                exec);
    }

    @Test
    void play_WrongFigureDots_Exception(){
        Executable exec = ()->{mojo.play("Green", 0, 2,3);};
        assertThrows(WrongFigureRuntimeException.class,
                exec);
    }

    @Test
    void play_WrongFigureDots_Exception_2(){
        Executable exec = ()->{mojo.play("Green", 4, 2,3);};
        assertThrows(WrongFigureRuntimeException.class,
                exec);
    }

    @Test
    void play_WrongFigureDots_Exception_3(){
        Executable exec = ()->{mojo.play("Green", 10, 2,3);};
        assertThrows(WrongFigureRuntimeException.class,
                exec);
    }

    @Test
    void play_ForbiddenMoveToLong_WarningMessageNoMove(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green", 1, 1,3);
        assertEquals("Can't move so far!\r\n", msg.toString());
    }

    @Test
    void play_ForbiddenMoveOutOfBoardAxisX_WarningMessageNoMove(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green", 1, 0,2);
        assertEquals("Can't move out of board!\r\n", msg.toString());
    }

    @Test
    void play_ForbiddenMoveOutOfBoardAxisY_WarningMessageNoMove(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green", 1, 1,0);
        assertEquals("Can't move out of board!\r\n", msg.toString());
    }

    @Test
    void play_ForbiddenMoveToOccupiedCell_WarningMessageNoMove(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 2,2);
        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green", 1, 2,2);
        assertEquals("Cell is occupied!\r\n", msg.toString());
    }

    @Test
    void play_ForbiddenMoveNonexistentFigure_WarningMessageNoMove(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green Pawn", 0, 3,3);

        assertEquals("Can't move nonexistent figure !\r\n", msg.toString());
    }

    @Test
    void play_MoveOk__FigureSetOnNewCell() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.play("Green", 1,2,1);

        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        assertEquals("Green|.", ((String[][])field.get(mojo))[1][0] );
    }

    @Test
    void play_MoveOk__FigureRemovedFromOldCell() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.play("Green", 1,2,1);

        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        assertEquals("", ((String[][])field.get(mojo))[0][0] );
    }

    @Test
    void play_MoveOk__FigureSetOnNewCell_2() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.play("Red", 2,2,1);

        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        assertEquals("Red|:", ((String[][])field.get(mojo))[1][0] );
    }

    @Test
    void play_MoveOk__FigureRemovedFromOldCell_2() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.play("Red", 2,2,1);

        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        assertEquals("", ((String[][])field.get(mojo))[0][1] );
    }

    @Test
    void play_MoveOk__FigureSetOnNewCell_3() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.setFigureOnBoard("Green Pawn", 0, 3,3);
        mojo.play("Green Pawn", 0,2,3);

        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        assertEquals("Green Pawn", ((String[][])field.get(mojo))[1][2] );
    }

    @Test
    void play_MoveOk__FigureRemovedFromOldCell_3() throws NoSuchFieldException, IllegalAccessException {
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.setFigureOnBoard("Green Pawn", 0, 3,3);
        mojo.play("Green Pawn", 0,2,3);

        Field field = mojo.getClass().getDeclaredField("board");
        field.setAccessible(true);

        assertEquals("", ((String[][])field.get(mojo))[2][2] );
    }


    @Test
    void play_GreenHorizontalWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Green", 3, 2,1);
        mojo.setFigureOnBoard("Green", 5, 2,2);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.setFigureOnBoard("Red", 4, 1,3);
        mojo.setFigureOnBoard("Red", 6, 2,3);
        mojo.setFigureOnBoard("Green Pawn", 0, 3,3);
        mojo.setFigureOnBoard("Red Pawn", 0, 3,1);

        mojo.play("Red Pawn", 0, 3, 2);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green", 5, 3, 1);

        assertEquals("Green Win!!!\r\n", msg.toString());
    }

    @Test
    void play_RedHorizontalWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Green", 3, 2,1);
        mojo.setFigureOnBoard("Green", 5, 2,2);
        mojo.setFigureOnBoard("Red", 2, 1,2);
        mojo.setFigureOnBoard("Red", 4, 1,3);
        mojo.setFigureOnBoard("Red", 6, 3,3);
        mojo.setFigureOnBoard("Green Pawn", 0, 3,2);
        mojo.setFigureOnBoard("Red Pawn", 0, 3,1);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Red", 2, 2, 3);

        assertEquals("Red Win!!!\r\n", msg.toString());
    }

    @Test
    void play_GreenVerticalWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Green", 1, 2,1);
        mojo.setFigureOnBoard("Green", 3, 2,2);
        mojo.setFigureOnBoard("Green", 5, 1,2);
        mojo.setFigureOnBoard("Red", 2, 1,1);
        mojo.setFigureOnBoard("Red", 4, 1,3);
        mojo.setFigureOnBoard("Red", 6, 3,1);
        mojo.setFigureOnBoard("Green Pawn", 0, 3,2);
        mojo.setFigureOnBoard("Red Pawn", 0, 3,3);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Green", 5, 2, 3);

        assertEquals("Green Win!!!\r\n", msg.toString());
    }

    @Test
    void play_RedVerticalWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Red", 2, 2,1);
        mojo.setFigureOnBoard("Red", 4, 2,2);
        mojo.setFigureOnBoard("Red", 6, 1,2);
        mojo.setFigureOnBoard("Green", 1, 1,1);
        mojo.setFigureOnBoard("Green", 3, 1,3);
        mojo.setFigureOnBoard("Green", 5, 3,1);
        mojo.setFigureOnBoard("Red Pawn", 0, 3,2);
        mojo.setFigureOnBoard("Green Pawn", 0, 3,3);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Red", 6, 2, 3);

        assertEquals("Red Win!!!\r\n", msg.toString());
    }

    @Test
    void play_RedDiagonalUpToBottomWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Red", 2, 1,1);
        mojo.setFigureOnBoard("Red", 4, 2,2);
        mojo.setFigureOnBoard("Red", 6, 2,3);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));
        mojo.play("Red", 6, 3, 3);

        assertEquals("Red Win!!!\r\n", msg.toString());
    }

    @Test
    void play_GreenDiagonalUpToBottomWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Green", 1, 1,3);
        mojo.setFigureOnBoard("Green", 3, 2,3);
        mojo.setFigureOnBoard("Green", 5, 3,2);
        mojo.play("Green", 5, 2, 1);
        mojo.play("Green", 5, 1, 1);
        mojo.play("Green", 1, 2, 2);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));

        mojo.play("Green", 3, 3, 3);
        assertEquals("Green Win!!!\r\n", msg.toString());
    }

    @Test
    void play_GreenDiagonalBottomUpWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Green", 1, 1,3);
        mojo.setFigureOnBoard("Green", 3, 2,3);
        mojo.setFigureOnBoard("Green", 5, 3,2);
        mojo.play("Green", 3, 2, 2);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));

        mojo.play("Green", 5, 3, 1);
        assertEquals("Green Win!!!\r\n", msg.toString());
    }

    @Test
    void play_RedDiagonalBottomUpWin_CongratulationMessage(){
        mojo.setFigureOnBoard("Red", 2, 2,1);
        mojo.setFigureOnBoard("Red", 4, 2,2);
        mojo.setFigureOnBoard("Red", 6, 3,3);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));

        mojo.play("Red", 2, 1, 1);
        assertEquals("Red Win!!!\r\n", msg.toString());
    }

    @Test
    void play_NoWinner_NoMessages(){
        mojo.setFigureOnBoard("Red", 2, 2,1);
        mojo.setFigureOnBoard("Red", 4, 2,2);
        mojo.setFigureOnBoard("Red", 6, 3,3);

        ByteArrayOutputStream msg = new ByteArrayOutputStream();
        System.setOut(new PrintStream(msg));

        mojo.play("Red", 2, 3, 2);
        assertEquals(0, msg.size());
    }
}

package MojoTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import val.Mojo;

class WhoIsFirstTest {

    private Mojo mojo;

    @BeforeEach
    void beforeEach(){
        mojo = new Mojo();
    }

    @Test
    void returnsOnlyTwoColours(){
        Set<String> colours = new HashSet<>();
        for(int i = 0; i < 20; i++){
            colours.add(mojo.whoIsFirst());
        }

        assertEquals(2, colours.size());
    }

    @Test
    void returnsOnlyGreenOrRed(){
        Set<String> colours = new HashSet<>();
        for(int i = 0; i < 20; i++){
            colours.add(mojo.whoIsFirst());
        }

        assertTrue(colours.contains("Green") && colours.contains("Red"));
    }

    @Test
    void returnsColoursInRandomOrder(){
        List<String> firstSet= new ArrayList<>();
        for(int i = 0; i < 20; i++){
            firstSet.add(mojo.whoIsFirst());
        }

        mojo = new Mojo();
        List<String> secondSet = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            secondSet.add(mojo.whoIsFirst());
        }

        assertNotEquals(firstSet, secondSet);
    }
}

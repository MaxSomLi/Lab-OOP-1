package test.system;

import main_j.system.Park;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ParkTest {
    @Test
    public void testPark() throws IOException {
        Park p = new Park();
        assertEquals(p.getClass(), Park.class);
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadHandlerTest {
    Thread t= new Thread();

        @Test
        void makeNewThreadIfClosedtest() throws InterruptedException {
            t.start();
            t.interrupt();
            ThreadHandler.getThreadHandlerOBJ().makeNewThreadIfClosed(t);
            assertTrue(t.isAlive());
            t.interrupt();
        }
}
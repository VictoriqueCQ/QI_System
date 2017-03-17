package java;

import org.junit.Test;
import ui.Net;

import java.net.Socket;

import static org.junit.Assert.assertEquals;

/**
 * Created by xjwhhh on 2017/3/17.
 */
public class NetTest {
    Net net = new Net();

    @Test
    public void test_setUpNet() {
        net.setUpNet();
        Socket socket = net.getSock();
        assertEquals(9000, socket.getPort());
    }
}

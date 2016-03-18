import java.io.IOException;
import java.net.Socket;

/**
 * Created by KMacioszek on 2016-03-17.
 */
public class main {

    public static void main(String args[]) throws IOException {
        String host = "localhost";
        String port = "1024";


//        if (args.length != 2)
//            throw new RuntimeException("Syntax: ChatClient <host><port>");
        Socket s = new Socket(host, Integer.parseInt(port));
        new ChatClient ("Chat " + host + ":" + port, s.getInputStream(), s.getOutputStream());
    }

}

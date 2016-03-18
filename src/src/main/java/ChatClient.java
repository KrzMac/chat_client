import java.awt.*;
import java.io.*;

/**
 * Created by MSI on 2016-03-18.
 */
public class ChatClient extends Frame implements Runnable{

    protected DataInputStream i;
    protected DataOutputStream o;
    protected TextArea output;
    protected TextField input;
    protected Thread listener;

    public ChatClient (String title, InputStream i, OutputStream o) {
        super(title);

        this.i = new DataInputStream(new BufferedInputStream(i));
        this.o = new DataOutputStream(new BufferedOutputStream(o));
        setLayout(new BorderLayout());
        add("Center", output = new TextArea());
        output.setEditable(false);
        add("South", input = new TextField());
        pack();
        show();
        input.requestFocus();
        listener = new Thread(this);
        listener.start();
    }

    public void run() {
        try {
            while(true) {
                String line = i.readUTF();
                output.appendText(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            listener = null;
            input.hide();
            validate();
            try {
                o.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean handleEvent (Event e) {
        if ((e.target == input) && (e.id == Event.ACTION_EVENT)) {
            try {
                o.writeUTF ((String) e.arg);
                o.flush ();
            } catch (IOException ex) {
                ex.printStackTrace();
                listener.stop ();
            }
            input.setText ("");
            return true;
        } else if ((e.target == this) && (e.id == Event.WINDOW_DESTROY)) {
            if (listener != null)
                listener.stop ();
            hide ();
            return true;
        }
        return super.handleEvent (e);
    }

}

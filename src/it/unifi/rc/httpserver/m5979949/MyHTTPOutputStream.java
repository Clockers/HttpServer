package it.unifi.rc.httpserver.m5979949;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A class that writes {@link HTTPRequest}/{@link HTTPReply} into a given {@link OutputStream}.
 */
public class MyHTTPOutputStream extends HTTPOutputStream {

    /**
     * {@link OutputStream} used to write HTTP request/ reply.
     */
    private OutputStream os;

    /**
     * Creates an instance of {@link HTTPOutputStream} that uses the {@link OutputStream} passed
     * as parameter.
     *
     * @param os an {@link OutputStream} that is used to write data.
     */
    public MyHTTPOutputStream(OutputStream os) {
        super(os);
        this.os = os;
    }

    /**
     * Set internal data structures to refer to a given OutputStream.
     *
     * @param os <code>OutputStream</code> used to retrieves data.
     */
    @Override
    protected void setOutputStream(OutputStream os) {
        this.os = os;
    }

    /**
     * Writes a reply into the inner {@link OutputStream}.
     *
     * @param reply reply to write.
     * @throws IOException
     */
    @Override
    public void writeHttpReply(HTTPReply reply) throws IOException {
        os.write(reply.toString().getBytes("US-ASCII"));
    }

    /**
     * Writes a request into the inner {@link OutputStream}.
     *
     * @param request request to write.
     */
    @Override
    public void writeHttpRequest(HTTPRequest request) {
    }

    /**
     * Closes the stream and releases all the used resources.
     *
     * @throws IOException if an error occurs.
     */
    @Override
    public void close() throws IOException {
        this.os.close();
    }

    /**
     * Get the {@link OutputStream} used to write request/ reply.
     *
     * @return an {@link OutputStream}.
     */
    public OutputStream getOs() {
        return os;
    }
}

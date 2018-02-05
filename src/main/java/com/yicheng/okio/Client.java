package com.yicheng.okio;


import okio.*;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private Socket socket = null;
    BufferedSource input = null;
    Sink output = null;

    private Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private boolean connect() {
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket(host, port);
                input = Okio.buffer(Okio.source(socket));
                output = Okio.sink(socket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return socket != null && socket.isConnected();
    }

    private boolean isConnect() {
        return socket != null && socket.isConnected();
    }

    private void close() {
        if (socket != null && socket.isConnected()) {
            try {
                socket.close();
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Client client = new Client("47.92.155.60", 10001);
        if (client.connect()) {
            Buffer buffer = new Buffer();
            buffer.write(ByteString.decodeHex("7E80000200000000000000000000000000827E"));
            try {
                client.output.write(buffer,buffer.size());
                client.output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                if (client.isConnect()) {
                    ByteString byteString = client.input.readByteString();
                    System.out.println(byteString.hex());
                } else {
                    if (client.connect()) {

                    }
                }

            } catch (IOException e) {
                client.close();
                e.printStackTrace();
                break;
            }
        }


    }
}

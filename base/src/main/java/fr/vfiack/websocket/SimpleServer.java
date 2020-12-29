package fr.vfiack.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleServer extends WebSocketServer {
    private final Map<String, List<WebSocket>> socketsByPath = new HashMap<>();

    public SimpleServer(InetSocketAddress address) {
        super(address);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::sendCounters, 1, 1, TimeUnit.SECONDS);
    }

    // sending some arbitrary binary data; here the number of clients as an int
    private void sendCounters() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        socketsByPath.forEach(
                (k, v) -> {
                    System.out.println("sending: " + k + ", " + v.size());
                    byteBuffer.putInt(0, v.size());
                    v.forEach(s -> s.send(byteBuffer));
                });
    }

    private void registerClient(WebSocket conn) {
        List<WebSocket> webSockets =
                socketsByPath.computeIfAbsent(conn.getResourceDescriptor(), k -> new ArrayList<>());
        webSockets.add(conn);
        System.out.println(
                "registered client to " + conn.getResourceDescriptor() + ":" + webSockets.size());
    }

    private void unregisterClient(WebSocket conn) {
        socketsByPath.computeIfPresent(
                conn.getResourceDescriptor(),
                (k, webSockets) -> {
                    webSockets.remove(conn);
                    System.out.println(
                            "unregistered client from "
                                    + conn.getResourceDescriptor()
                                    + ":"
                                    + webSockets.size());
                    return webSockets;
                });
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); // This method sends a message to the new client
        broadcast(
                "new connection: "
                        + handshake.getResourceDescriptor()); // This method sends a message to all
        // clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());

        registerClient(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(
                "closed "
                        + conn.getRemoteSocketAddress()
                        + " with exit code "
                        + code
                        + " additional info: "
                        + reason);
        unregisterClient(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(
                "received message from " + conn.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println(
                "an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }
}

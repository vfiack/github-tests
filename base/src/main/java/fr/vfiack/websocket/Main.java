package fr.vfiack.websocket;

import org.java_websocket.server.DefaultSSLWebSocketServerFactory;
import org.java_websocket.server.WebSocketServer;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.security.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class Main {
    public static final String KEYSTORE = "keystore.jks";
    public static final String STORETYPE = "JKS";
    public static final String STOREPASSWORD = "password";
    public static final String ALGORITHM = "SunX509";

    public static final String HOST = "localhost";
    public static final int PORT = 8887;

    private static SSLContext createTlsSslContext(KeyStore keyStore, String keyPassword)
            throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException,
                    KeyManagementException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(ALGORITHM);
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(ALGORITHM);
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }

    public static void main(String[] args) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(STORETYPE);
        keyStore.load(new FileInputStream(KEYSTORE), STOREPASSWORD.toCharArray());

        SSLContext sslContext = createTlsSslContext(keyStore, STOREPASSWORD);

        WebSocketServer server = new SimpleServer(new InetSocketAddress(HOST, PORT));
        server.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(sslContext));
        server.start();
    }
}

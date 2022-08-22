package client.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class CommHostNameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}

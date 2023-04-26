package cc.assignment_1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class URLValidator {
    private String url;

    public URLValidator(String url) {
        this.url = url;
    }

    public boolean urlIsValid() {
        url = addProtocolIfMissing();
        return urlNotBroken();
    }

    protected String addProtocolIfMissing() {
        if (!url.matches("^(https?)://.*$")) {
            url = "https://" + url;
        }
        return url;
    }

    protected boolean urlNotBroken() {
        try {
            int responseCode = connectToUrl();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP;
        } catch (URISyntaxException | IOException e) {
            return false;
        }
    }

    protected int connectToUrl() throws URISyntaxException, IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("HEAD");
        return connection.getResponseCode();
    }
}

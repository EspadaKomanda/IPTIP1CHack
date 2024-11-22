package ru.espada.ep.iptip.git;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.espada.ep.iptip.config.GitConfig;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Component
public class GitHubUtil {

    @Autowired
    private GitConfig gitConfig;

    public String getFilePathFromUrl(String url) {
        String[] path = url.split("/");
        String user = path[3];
        String repo = path[4].replace(".git", "");
        String branch = "main";
        if (path.length == 7 && path[5].equals("tree")) {
            branch = path[6];
        }

        return String.format("%s//%s/%s/%s/archive/refs/heads/%s.zip", path[0], path[2], user, repo, branch);
    }

    /**
     * Сохраняет архив с проектом из GitHub
     * @param fileURL Путь до скачиваемого архива
     * @param fileName Название файла без расширения (желательно UUID)
     */
    public void downloadFile(String fileURL, String fileName) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                File file = new File(gitConfig.getSave_dir());
                file.getParentFile().mkdirs();
                try (InputStream inputStream = new BufferedInputStream(httpConn.getInputStream());
                     FileOutputStream outputStream = new FileOutputStream(gitConfig.getSave_dir() + fileName + ".zip")) {
                    byte[] buffer = new byte[gitConfig.getMax_repo_size()];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + httpConn.getResponseCode());
            }
            httpConn.disconnect();
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

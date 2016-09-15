package wolfsoft.invincible.utils.storage;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by aka on 26/04/2015.
 */
public class FileManager {
    private static FileManager instance;
    private File appFolder;

    private FileManager() {
        createAppFolder();
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    private void createAppFolder() {
        appFolder = new File(Environment.getExternalStorageDirectory(), "/RfidApp/");

        if (!appFolder.exists()) {
            appFolder.mkdir();
        }
    }

    public File getAppFolder() {
        return appFolder;
    }


    public void moveFile(File oldFile, File newFile) {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(newFile);
            byte[] moveBuff = new byte[1024];
            int butesRead;
            while ((butesRead = in.read(moveBuff)) > 0) {
                out.write(moveBuff, 0, butesRead);
            }
            in.close();
            out.close();
            oldFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveImageFile(Bitmap bitmap, String fileName) {
        try {
            File file = new File(appFolder, fileName + ".jpeg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
            Log.i("absolutepath", "::" + file.getAbsolutePath().toString());
            return file.getAbsolutePath().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void createFile(File folder, String fileName, String fileContent) {
        try {
            File settingsFile = new File(folder, fileName);
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(fileContent);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileContent(File folder, String fileName) {
        StringBuilder sb = null;
        try {
            File settingsFile = new File(folder, fileName);
            FileInputStream fileInputStream = new FileInputStream(settingsFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fileInputStream.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return sb.toString();
    }
}

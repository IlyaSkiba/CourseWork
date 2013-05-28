package integration.services;

import integration.message.UnzippedMessage;
import integration.message.ZippedMessage;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Ilya Skiba
 */
@MessageEndpoint
public class UnzipMessageTransformer {
    @Transformer(inputChannel = "zippedCodeChannel", outputChannel = "unzippedCodeChannel")
    public UnzippedMessage transform(ZippedMessage zipped) {
        unZipIt(zipped.getZippedData(), "bla bla bla");
        return new UnzippedMessage();
    }


    /**
     * Unzip it.
     *
     * @param data         input zip file
     * @param outputFolder zip file output folder
     */
    public void unZipIt(byte[] data, String outputFolder) {

        byte[] buffer = new byte[1024];

        try {
            //create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new ByteArrayInputStream(data));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

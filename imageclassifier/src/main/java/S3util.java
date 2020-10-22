import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class S3util {
    static String srcBucket = "whizdm.lending.bucket", destBucket = "test.whizdm", excelFileKey = "AadharExcel.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException {

        // create a client connection based on credentials and region
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .build();

        System.out.println("Reading from excel sheet in s3...");
        S3Object object = s3Client.getObject(new GetObjectRequest(destBucket, excelFileKey));
        InputStream objectData = object.getObjectContent();
        Workbook workbook = WorkbookFactory.create(objectData);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        int rowNum = 0;
        System.out.println("Getting keys of the files and copying...");
        listObjects(destBucket,s3Client);

        for (Row row : sheet) {
            if (rowNum == 0) {
                rowNum++;
                continue;
            }

            Cell cell = row.getCell(1);
            String cellValue = dataFormatter.formatCellValue(cell);
            String a[] = cellValue.split("\\/+|\\?");
            String bucketName = a[1].substring(0, a[1].length() - 20);

            String temp = a[2];
            String keyName = a[2].replace("%2F", "/");

            System.out.println(keyName);
            try {

                //copyObj(srcBucket, keyName, destBucket, "AadharSamples/" + temp, s3Client);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());

            }


            objectData.close();
        }

    }


    public static void uploadObj(String bucketname, String key, String pathname, AmazonS3 client) {
        client.putObject(bucketname, key, new File(pathname));

    }

    public static void copyObj(String sourcebucketname, String sourcekey, String destinationbucketname, String destinationkey, AmazonS3 client) {
        client.copyObject(sourcebucketname, sourcekey, destinationbucketname, destinationkey);
    }

    public static void deleteObj(String bucket_name, String key, AmazonS3 client) {
        client.deleteObject(bucket_name, key);
    }

    public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);

        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folderName + "/", emptyContent, metadata);

        // send request to S3 to create folder
        client.putObject(putObjectRequest);
    }


    public static void listObjects(String bucket_name, AmazonS3 client) {
        ListObjectsV2Result result = client.listObjectsV2(bucket_name);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        int i = 0;
        for (S3ObjectSummary os : objects) {
            System.out.println(os.getKey());
            System.out.print(" " + i++);
        }


    }
}








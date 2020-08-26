package com.otongsoetardjoe.freakmangabrandnew.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.BadPdfFormatException;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.ColumnText;
//import com.itextpdf.text.pdf.PdfCopy;
//import com.itextpdf.text.pdf.PdfImportedPage;
//import com.itextpdf.text.pdf.PdfReader;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivityMenuBinding;

//import org.apache.pdfbox.io.MemoryUsageSetting;
//import org.apache.pdfbox.multipdf.PDFMergerUtility;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//
//import lombok.SneakyThrows;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMenuBinding menuBinding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());
        menuBinding.textNekoMenu.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainNekoActivity.class));
            finish();
        });
        menuBinding.textNhenMenu.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
            finish();
        });
        //just experimental code
//        mergePdfFromURL();
//        mergePdfWithiText();
    }
    //just experimental code
//    private void mergePdfWithiText() {
//        Thread thread = new Thread(() -> {
//            try {
//                PdfReader reader1 = new PdfReader(downloadFileThrowing("https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf"));
//                PdfReader reader2 = new PdfReader(getAssets().open("firstsample.pdf"));
//                Document document = new Document();
//                PdfCopy copy = new PdfCopy(document, createFile2());
//                document.open();
//                PdfImportedPage page;
//                PdfCopy.PageStamp stamp;
//                Phrase phrase;
//                BaseFont bf = BaseFont.createFont();
//                Font font = new Font(bf, 9);
//                int n = reader1.getNumberOfPages();
//                for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
//                    page = copy.getImportedPage(reader1, i);
//                    stamp = copy.createPageStamp(page);
//                    phrase = new Phrase("page " + i, font);
//                    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, phrase, 520, 5, 0);
//                    stamp.alterContents();
//                    copy.addPage(page);
//                }
//                for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
//                    page = copy.getImportedPage(reader2, i);
//                    stamp = copy.createPageStamp(page);
//                    phrase = new Phrase("page " + (n + i), font);
//                    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, phrase, 520, 5, 0);
//                    stamp.alterContents();
//                    copy.addPage(page);
//                }
//                document.close();
//                reader1.close();
//                reader2.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        thread.start();
//    }
//
//    private void mergePdfFromURL() {
//        Thread thread = new Thread(() -> {
//            try {
//                PDFMergerUtility ut = new PDFMergerUtility();
//                //get pdf file from url sample
//                ut.addSource(downloadFileThrowing("https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf"));
//                ut.addSource(downloadFileThrowing("https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf"));
//                ut.addSource(downloadFileThrowing("https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf"));
//                //get pdf file from assets folder sample
//                ut.addSource(getAssets().open("firstsample.pdf"));
//                ut.addSource(getAssets().open("secondsample.pdf"));
//                ut.addSource(getAssets().open("thirdsample.pdf"));
//                ut.setDestinationStream(createFile());
//                ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        thread.start();
//    }
//
//    private FileOutputStream createFile() throws FileNotFoundException {
//        return new FileOutputStream(new File(getApplicationContext().getExternalCacheDir(), "Merge Result.pdf"));
//    }
//
//    private FileOutputStream createFile2() throws FileNotFoundException {
//        return new FileOutputStream(new File(getApplicationContext().getExternalCacheDir(), "Merge Results.pdf"));
//    }
//
//    public static InputStream downloadFileThrowing(String url) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder().url(url).build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Download not successful.response:" + response);
//        } else {
//            return response.body().byteStream();
//        }
//    }
}
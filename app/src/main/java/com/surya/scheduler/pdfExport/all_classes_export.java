package com.surya.scheduler.pdfExport;

import static android.graphics.Color.rgb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import com.surya.scheduler.R;
import com.surya.scheduler.models.offline.Class;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class all_classes_export {

    int pageWidth = 595;
    int pageHeight = 842;

    // constructor
    public all_classes_export(){
        createAllClassDetailsPdf();
    }

    // method to create the all classes pdf
    private void createAllClassDetailsPdf(){
        // creating an instance to PdfDocument class
        PdfDocument pdfDocument = new PdfDocument();
        // specifying the height, width and number of pages of the pdf
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // writing the pdf
        Paint paint = new Paint();
        Canvas canvas = page.getCanvas();

        // creating a border line
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawRect(25, 25, 570, 817, paint);

        // AMRITA INSTITUTIONS text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(12f);
        canvas.drawText("AMRITA INSTITUTIONS", pageWidth/2, 43, paint);

        // AMRITA COLLEGE OF ENGINEERING AND TECHNOLOGY text
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(12f);
        canvas.drawText("AMRITA COLLEGE OF ENGINEERING AND TECHNOLOGY", pageWidth/2, 60, paint);

        // ERACHAKULAM TEXT
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10f);
        canvas.drawText("Erachakulam, Nagercoil", pageWidth/2, 75, paint);

        // have to draw the table
        // table will have four columns
        // column 1 --> className
        // column 2 --> Department
        // column 3 --> Strength of the class
        // column 4 --> Class advisor
        // header rectangle
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawRect(50, 90, 545, 120, paint);

        // width is 495
        // have to be divided into 4 parts
        // setting the background of the header row
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(rgb(232,244,248));

        int diff = 545 - 50;
        for(int i = 1; i < diff; i++){
            canvas.drawLine(50 + i, 91, 50 + i, 119, paint);
        }

        // Name, department, strength, class advisor texts
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(10);
        paint.setColor(Color.BLACK);
        canvas.drawText("CLASS NAME", 111, 110, paint);
        canvas.drawText("DEPARTMENT", 234, 110, paint);
        canvas.drawText("STRENGTH", 357, 110, paint);
        canvas.drawText("CLASS ADVISOR", 480, 110, paint);

        // inputting the data from the allClasses array list
        int startXPosition = 50;
        int endXPosition = 545;
        int startYPosition = 120;
        int endPosition = 0;

        int helper = 0;

        for(Class classx : Class.allClasses){
            // drawing the rectangle
            if(! (helper%2 == 0)){
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);
                paint.setColor(rgb(232,244,248));

                int diff1 = 545 - 50;
                for(int i = 1; i < diff1; i++){
                    canvas.drawLine(startXPosition + i, startYPosition, startXPosition + i, startYPosition + 30, paint);
                }
            }

            // outline rectangle
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            canvas.drawRect(startXPosition, startYPosition, endXPosition, startYPosition + 30, paint);

            // incrementing the startYPosition by 30
            startYPosition += 30;
            endPosition = startYPosition;

            // drawing the text
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(10);
            paint.setColor(Color.BLACK);

            // className text
            canvas.drawText(classx.getName(), 111, startYPosition - 10, paint);

            // department text
            canvas.drawText(classx.getDepartment(), 234, startYPosition - 10, paint);

            // class strength
            canvas.drawText(String.valueOf(classx.getNumberOfStudents()), 357, startYPosition - 10, paint);

            // class advisor name
            canvas.drawText(classx.getTeachers()[0], 480, startYPosition - 10, paint);

            helper++;
        }

        // drawing the four vertical lines
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        canvas.drawLine(174, 90, 174, endPosition, paint);
        canvas.drawLine(297, 90, 297, endPosition, paint);
        canvas.drawLine(420, 90, 420, endPosition, paint);

        pdfDocument.finishPage(page);

        // writing to the external storage
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() +"All Classes.pdf";
        // creating a File object
        File file = new File(path);

        // writing the pdf to the file created
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            //Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_LONG).show();
        }
        catch (Exception exception){
            exception.printStackTrace();
            //Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
        }

        pdfDocument.close();
    }
}

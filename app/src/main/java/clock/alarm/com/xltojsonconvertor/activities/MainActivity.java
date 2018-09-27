package clock.alarm.com.xltojsonconvertor.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import clock.alarm.com.xltojsonconvertor.R;
import clock.alarm.com.xltojsonconvertor.helper.Validation;
import clock.alarm.com.xltojsonconvertor.model.GetData;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etname)
    EditText etname;
    @BindView(R.id.etNote)
    EditText etNote;
    @BindView(R.id.etPrize)
    EditText etPrize;

    private List<GetData> data;
    private GetData getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
            if (isPermited()) {
                File sdCard = Environment.getExternalStorageDirectory();
                directory = new File(sdCard.getAbsolutePath() + "/XLFiles");
                directory.mkdirs();
            } else
                setPermistion();

    }

    private boolean isPermited() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else
            return true;
    }

    private void setPermistion() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    @OnClick(R.id.btnExcel)
    void btnExcel() {
        if (isValidate())
            listOfUserWithOutDevice(data);

    }

    @OnClick(R.id.btnPDF)
    void setBtnPDF() {
        try {
            if (isValidate())
                convertToPDF3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnCSV)
    void setBtnCSV() {
        if (isValidate())
            new ExportDatabaseCSVTask().execute();
    }


    private boolean isValidate() {
        if (isPermited()) {
            if (!Validation.isEmpty(etname) || !Validation.isEmpty(etNote) || !Validation.isEmpty(etPrize)) {
                if (Validation.isNum(etPrize)) {
                    if (getData == null) {
                        getData = new GetData();
                        getData.setBy("abc");
                        getData.setName(etname.getText().toString());
                        getData.setNote(etNote.getText().toString());
                        getData.setPrice(Integer.parseInt(etPrize.getText().toString()));
                        getData.setDueDate(new Date());
                        getData.setcAt(new Date());
                        getData.setId(String.valueOf(new Random().nextInt(1000)));
                        data = new ArrayList<>();
                        data.add(getData);
                    }
                    return true;
                } else
                    Toast.makeText(this, "Enter prize filed number.", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Please fill all data first", Toast.LENGTH_SHORT).show();
        } else
            setPermistion();
        return false;
    }

    private File file, directory;


    private void convertToPDF3() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(directory + "/HelloWorld-Table.pdf"));

            document.open();

            PdfPTable table; // 3 columns.
            table = new PdfPTable(7);

            PdfPCell Name = new PdfPCell(new Paragraph("Name"));
            PdfPCell ID = new PdfPCell(new Paragraph("ID"));
            PdfPCell Note = new PdfPCell(new Paragraph("Note"));
            PdfPCell By = new PdfPCell(new Paragraph("By"));
            PdfPCell Prize = new PdfPCell(new Paragraph("Prize"));
            PdfPCell CAt = new PdfPCell(new Paragraph("CAt"));
            PdfPCell DueDate = new PdfPCell(new Paragraph("DueDate"));

            table.addCell(Name);
            table.addCell(ID);
            table.addCell(Note);
            table.addCell(By);
            table.addCell(Prize);
            table.addCell(CAt);
            table.addCell(DueDate);


            for (int i = 0; i < data.size(); i++) {
                PdfPCell Name1 = new PdfPCell(new Paragraph(data.get(i).getName()));
                PdfPCell ID1 = new PdfPCell(new Paragraph(data.get(i).getId()));
                PdfPCell Note1 = new PdfPCell(new Paragraph(data.get(i).getNote()));
                PdfPCell By1 = new PdfPCell(new Paragraph(data.get(i).getBy()));
                PdfPCell Prize1 = new PdfPCell(new Paragraph(data.get(i).getPrice()));
                PdfPCell CAt1 = new PdfPCell(new Paragraph(String.valueOf(data.get(i).getcAt())));
                PdfPCell DueDate1 = new PdfPCell(new Paragraph(String.valueOf(data.get(i).getDueDate())));

                table.addCell(Name1);
                table.addCell(ID1);
                table.addCell(Note1);
                table.addCell(By1);
                table.addCell(Prize1);
                table.addCell(CAt1);
                table.addCell(DueDate1);
            }

            Toast.makeText(this, "PDF convert", Toast.LENGTH_SHORT).show();
            document.add(table);

            document.close();
        } catch (Exception e) {
            System.out.println("Error PDF " + e.getMessage());
        }
    }


    private class ExportDatabaseCSVTask extends AsyncTask<String, String, String> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected String doInBackground(final String... args) {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(directory, "ExcelFile.csv");
            try {

                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                String arrStr1[] = {"Name", "Id", "Note", "By", "Prize", "cAt", "DueDate"};
                csvWrite.writeNext(arrStr1);

                for (GetData d : data) {

                    String arrStr[] = {d.getName(), d.getId(), d.getNote(), d.getBy(),
                            String.valueOf(d.getPrice()), d.getcAt().toString(), d.getDueDate().toString()};
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
                return "";
            } catch (IOException e) {
                Log.e("MainActivity", e.getMessage(), e);
                return "";
            }
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(final String success) {

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success.isEmpty()) {
                Toast.makeText(MainActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private void listOfUserWithOutDevice(List<GetData> getDataList) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df;
        df = new SimpleDateFormat("hh:mm a");
        String time = df.format(Calendar.getInstance().getTime());
        df = new SimpleDateFormat("dd_MM_yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        String Fnamexls = date + "_" + time + "_Tenant_No_MacID.xls";

        file = new File(directory, Fnamexls);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            WritableSheet sheet = workbook.createSheet("FirstSheet", 0);
            Label labelName0 = new Label(0, 0, "Name");
            Label labelID0 = new Label(1, 0, "ID");
            Label labelNote0 = new Label(2, 0, "Note");
            Label labelBy0 = new Label(3, 0, "By");
            Label labelPrize0 = new Label(4, 0, "Prize");
            Label labelCAtData0 = new Label(5, 0, "CAt");
            Label labelDueDate0 = new Label(6, 0, "DueDate");
            try {
                sheet.addCell(labelName0);
                sheet.addCell(labelID0);
                sheet.addCell(labelNote0);
                sheet.addCell(labelBy0);
                sheet.addCell(labelPrize0);
                sheet.addCell(labelCAtData0);
                sheet.addCell(labelDueDate0);
                for (int i = 1; i < getDataList.size() + 1; i++) {
                    Label labelName = new Label(0, i, getDataList.get(i - 1).getName());
                    Label labelID = new Label(1, i, getDataList.get(i - 1).getId());
                    Label labelNote = new Label(2, i, getDataList.get(i - 1).getNote());
                    Label labelBy = new Label(3, i, getDataList.get(i - 1).getBy());
                    Label labelPrize = new Label(4, i, String.valueOf(getDataList.get(i - 1).getPrice()));
                    Label labelCAtData = new Label(5, i, String.valueOf(getDataList.get(i - 1).getcAt()));
                    Label labelDueDate = new Label(6, i, String.valueOf(getDataList.get(i - 1).getDueDate()));
                    sheet.addCell(labelName);
                    sheet.addCell(labelID);
                    sheet.addCell(labelNote);
                    sheet.addCell(labelBy);
                    sheet.addCell(labelPrize);
                    sheet.addCell(labelCAtData);
                    sheet.addCell(labelDueDate);
                }
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            Toast.makeText(this, "Save Excel", Toast.LENGTH_SHORT).show();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

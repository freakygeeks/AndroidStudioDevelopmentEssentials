package com.chrysalis.storagedemo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.renderscript.ScriptGroup;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;



public class StorageDemoActivity extends AppCompatActivity {

    private static EditText textView;

    private static final int CREATE_REQUEST_CODE = 40;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int SAVE_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_demo);

        textView = (EditText)findViewById(R.id.fileText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void newFile (View view) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt");
        startActivityForResult(intent, CREATE_REQUEST_CODE);
    }

    public void saveFile (View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, SAVE_REQUEST_CODE);
    }

    public void openFile(View view)
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }

    private void writeFileContent (Uri uri) {
        try {
            ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            String textContent = textView.getText().toString();
            fileOutputStream.write(textContent.getBytes());
            fileOutputStream.close();
            pfd.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileContent(Uri uri) throws IOException {

        InputStream inputStream =
                getContentResolver().openInputStream(uri);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(
                        inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String currentline;
        while ((currentline = reader.readLine()) != null) {
            stringBuilder.append(currentline + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        Uri currentUri = null;

        if (requestCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {
                if (resultData != null) {
                    textView.setText("");
                }
            }
            else if (requestCode == SAVE_REQUEST_CODE) {
                if (resultData != null) {
                    currentUri = resultData.getData();
                    writeFileContent(currentUri);
                }
            }
            else if (requestCode == OPEN_REQUEST_CODE) {
                if (resultData != null) {
                    currentUri = resultData.getData();

                    try {
                        String content = readFileContent(currentUri);
                        textView.setText(content);
                    }
                    catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
    }*/

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        Uri currentUri = null;

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == CREATE_REQUEST_CODE)
            {
                if (resultData != null) {
                    textView.setText("");
                }
            } else if (requestCode == SAVE_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();
                    writeFileContent(currentUri);
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();

                    try {
                        String content =
                                readFileContent(currentUri);
                        textView.setText(content);
                    } catch (IOException e) {
                        // Handle error here
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storage_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

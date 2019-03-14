package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    public Button btn;
    public Button save;
    public Button cancel;
    public LinearLayout layout2;
    public EditText name;
    public EditText phone;
    public EditText email;
    public EditText address;
    public EditText job;
    public EditText company;
    public EditText site;
    public EditText username;
    public boolean showLayout2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        btn = (Button) findViewById(R.id.btn);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        job = (EditText) findViewById(R.id.job);
        company = (EditText) findViewById(R.id.company);
        site = (EditText) findViewById(R.id.site);
        username = (EditText) findViewById(R.id.username);

        Intent intent = getIntent();
        if (intent != null) {
            String phonen = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phone.setText(phonen);
            } else {
                Toast.makeText(this, "error contacts", Toast.LENGTH_LONG).show();
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showLayout2) {
                    //ascund layout2 si schimb textul btn
                    layout2.setVisibility(View.GONE);
                    btn.setText(R.string.show);
                } else {
                    //afisez layout2 si schimb textul btn
                    layout2.setVisibility(View.VISIBLE);
                    btn.setText(R.string.hide);
                }
                showLayout2 = !showLayout2;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (!name.getText().toString().isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
                }
                if (!phone.getText().toString().isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText().toString());
                }
                if (!email.getText().toString().isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText().toString());
                }
                if (!address.getText().toString().isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.getText().toString());
                }
                if (!job.getText().toString().isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.getText().toString());
                }
                if (!company.getText().toString().isEmpty()) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText().toString());
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (!site.getText().toString().isEmpty()) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, site.getText().toString());
                    contactData.add(websiteRow);
                }
                if (!username.getText().toString().isEmpty()) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, username.getText().toString());
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivityForResult(intent, 3);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case 3:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}

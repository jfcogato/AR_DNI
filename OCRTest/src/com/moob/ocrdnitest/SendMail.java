package com.moob.ocrdnitest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SendMail extends Activity {
	private ImageView mCardView;
	String str = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.sendmail);
		
		NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		Intent intent = getIntent();
        /*Bundle bundle = intent.getExtras();
        
        Tag tag = bundle.getParcelable(mNfcAdapter.EXTRA_TAG);
        
        for (String techListItem : tag.getTechList())
        {
            str += "\n" + techListItem;
        }*/
		
		Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        NdefRecord cardRecord = msg.getRecords()[0];
        str = new String(cardRecord.getPayload()).replace("dni-scanner.com/", "");
        
        TextView text1 = (TextView)this.findViewById(R.id.dni_text);

        text1.setText(str.replace("\\n", "\r\n"));
                
        Button button = (Button)this.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Datos escaneados con DNI SCANNER");
				sendIntent.setType("plain/text");
				sendIntent.putExtra(Intent.EXTRA_TEXT, str.replace("\\n", "\r\n"));
				startActivity(sendIntent);
			}
        	
        });
        
		// ImageView that we'll use to display cards
        //mCardView = (ImageView)findViewById(R.id.mymo);
        // see if app was started from a tag and show game console
        //Intent intent = getIntent();
            
        //Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        //NdefMessage msg = (NdefMessage) rawMsgs[0];
        //NdefRecord cardRecord = msg.getRecords()[0];
        //String url = new String(cardRecord.getPayload());
            
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        //startActivity(browserIntent);
        
	}
	
}

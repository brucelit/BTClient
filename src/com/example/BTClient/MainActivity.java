package com.example.BTClient;

import java.util.ArrayList;
import java.util.Set;


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Tabhost ÊµÀý
 * @author  china1988s   Email: china1988s@126.com
 * @email   china1988s@126.com
 */
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    ListView mListView;
	String[] mDemosArrary; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView=(ListView)findViewById(R.id.liv_demos);
        mDemosArrary=getResources().getStringArray(R.array.demosArray);
        ArrayAdapter<String> arraryAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mDemosArrary);
        mListView.setAdapter(arraryAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					Intent intent=new Intent(MainActivity.this,Controller.class);
					startActivity(intent);
					break;
				
				}
				
			}
		});
        
        
    }
    
    
}
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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class Controller extends Activity implements View.OnClickListener {

	public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    
    private TextView mTitle;
    private TextView mTitle2;
    private Button text_input;
    private Button mbtnOn_Off;
    private Button mbtnSearch; //------> 在菜单中可以搜索
    private Button mbtnCreate; //------> 在菜单中设置"可被发现"
    private Button mbtnSend;
    private Button mbtnConnect;
    private Button mbtnPc;
    private Button mbtnClean;
    //连接到的蓝牙设备的名称
	private String mConnectedDeviceName;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
    
    private ArrayList<String> mPairedDevicesList = new ArrayList<String>();
    private ArrayList<String> mNewDevicesList = new ArrayList<String>();
    private String[] strName;
    private String address;
    private String mstrPin = "0000";
    private BluetoothDevice mDevice;
    private String newCode = "";
	private String newCode2 = "";
    
    public int send_flat;
	private boolean longClicked;
	private TextView textView_X_Eulerian,textView_Y_Eulerian,textView_Z_Eulerian,
	                 textView_X_magnetometer,textView_Y_magnetometer,textView_Z_magnetometer,
	                 textView_X_Gyro,textView_Y_Gyro,textView_Z_Gyro,
	                 textView_X_LinearAcceleration,textView_Y_LinearAcceleration,textView_Z_LinearAcceleration,
                  // textView_X_LinearAcceleration,textView_Y_LinearAcceleration,textView_Z_LinearAcceleration,
                     textView_X_LinearAcceleration1,textView_Y_LinearAcceleration1,textView_Z_LinearAcceleration1;
                 //    textView_X_LinearAcceleration1,textView_Y_LinearAcceleration1,textView_Z_LinearAcceleration1
	                 ;
	private Button button_motor1_stop,button_motor1_forward,button_motor1_backward,
	               button_motor2_stop,button_motor2_forward,button_motor2_backward,
	               button_motor3_stop,button_motor3_forward,button_motor3_backward,
	               btn_YAW_ANGLE_Angle,btn_YAW_ANGLE_Velocity,
	               btn_ROLL_ANGLE_Angle,btn_ROLL_ANGLE_Velocity,
	               btn_PITCH_ANGLE_Angle,btn_PITCH_ANGLE_Velocity
	               ;
	
	private EditText editText_motor1_setforward,editText_motor1_stop,editText_motor1_setbackward, 
	                 editText_motor1_PMW_min,editText_motor1_PMW_max,
	                 editText_motor1_min,editText_motor1_max,
	                 editText_motor1_P_min, editText_motor1_P_max,
	                 editText_motor1_I_min, editText_motor1_I_max,
	                 editText_motor1_D_min, editText_motor1_D_max,   
	                 editText_motor2_setforward,editText_motor2_stop,editText_motor2_setbackward, 
	                 editText_motor2_PMW_min,editText_motor2_PMW_max,   
	                 editText_motor3_setforward,editText_motor3_stop,editText_motor3_setbackward, 
	                 editText_motor3_PMW_min,editText_motor3_PMW_max,
	                 editText_YAW_ANGLE_Angle,editText_ROLL_ANGLE_Angle,editText_YAW_ANGLE_Velocity,
	                 editText_ROLL_ANGLE_Velocity,editText_PITCH_ANGLE_Angle,editText_PITCH_ANGLE_Velocity,
	                 editText_motor3_PMW_progress
	                 ;
	
	private SeekBar seekBar_motor1,
	                seekBar_motor1_PMW,seekBar_motor1_P,
	                seekBar_motor1_I,seekBar_motor1_D,
	                seekBar_motor2_PMW,seekBar_motor3_PMW;
	
    private TextView textView_motor1_setforward,textView_motor1_stop,textView_motor1_setbackward,     
                     textView_motor1,textView_motor1_1,textView_motor1_progress,
                     textView_motor1_PMW,textView_motor1_PMW_1,textView_motor1_PMW_2,textView_motor1_PMW_progress,
                     textView_motor1_P, textView_motor1_I,textView_motor1_D,
                     textView_motor1_P_progress, textView_motor1_I_progress, textView_motor1_D_progress,
                     textView_motor1_P_1,textView_motor1_I_1,textView_motor1_D_1,
                     textView_motor1_2,textView_motor1_P_2,textView_motor1_I_2,textView_motor1_D_2,
                    
                     textView_motor2_setforward,textView_motor2_stop,textView_motor2_setbackward,     
                     textView_motor2_PMW,textView_motor2_PMW_1,textView_motor2_PMW_2,textView_motor2_PMW_progress,
                     
                     textView_motor3_setforward,textView_motor3_stop,textView_motor3_setbackward,     
                     textView_motor3_PMW,textView_motor3_PMW_1,textView_motor3_PMW_2,
                     //textView_motor3_PMW_progress,
                     
                      textView_PITCH_ANGLE_Angle,textView_PITCH_ANGLE_Velocity,  
                      textView_ROLL_ANGLE_Angle,textView_ROLL_ANGLE_Velocity,
                      textView_YAW_ANGLE_Angle,textView_YAW_ANGLE_Velocity
                      ;
                  
    private int i=0;
                     
                    // textView_msg2;
	private TabHost tabHost=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info1);
		 firstPage();
		 secondPage();
		 thirdPage();
		 forthPage();
		 fifthPage();
	     initData();
	     initBluetooth();
	   //  int motor=Integer.parseInt(textView_motor1_D_progress.getText().toString());
	  //   seekBar_motor1.setProgress(motor);
	        registerReciver();
		tabHost=(TabHost)findViewById(R.id.tabhost_info1);
    	tabHost.setup(); 
    	tabHost.addTab(tabHost.newTabSpec("信息")
    	             .setContent(R.id.info_include01)
    	             .setIndicator("蓝牙设置")
    	            		
    	);
    	tabHost.addTab(tabHost.newTabSpec("更多信息")
	             .setContent(R.id.info_include02)
	             .setIndicator("电机一")
	             
	            		
	);
    	tabHost.addTab(tabHost.newTabSpec("附件下载")
	             .setContent(R.id.info_include03)
	             .setIndicator("电机二")
	            		
	);	
    	tabHost.addTab(tabHost.newTabSpec("附件下载")
	             .setContent(R.id.info_include04)
	             .setIndicator("电机三")
	            
    );
    	tabHost.addTab(tabHost.newTabSpec("附件下载")
	             .setContent(R.id.info_include05)
	             .setIndicator("陀螺仪")
	            
   );	
	}
	
	  private void firstPage() {
	        mTitle = (TextView)  findViewById(R.id.title);
	        mTitle2 = (TextView)  findViewById(R.id.textView_title);
	        mbtnOn_Off = (Button)  findViewById(R.id.btn_off_on);
	        mbtnSearch = (Button)  findViewById(R.id.btn_search_div);
	        mbtnConnect = (Button)  findViewById(R.id.btn_connect_div);
	        mbtnPc = (Button)  findViewById( R.id.btn_startPc);
 
	        mbtnOn_Off.setOnClickListener(new BluetoothListener());
	        mbtnSearch.setOnClickListener(new BluetoothListener());
	        mbtnConnect.setOnClickListener(new BluetoothListener());
	        mbtnPc.setOnClickListener(new BluetoothListener());
	        textView_X_LinearAcceleration=(TextView) findViewById(R.id.textView_X_LinearAcceleration);
	        textView_Y_LinearAcceleration=(TextView) findViewById(R.id.textView_Y_LinearAcceleration);
	        textView_Z_LinearAcceleration=(TextView) findViewById(R.id.textView_Z_LinearAcceleration);
	        textView_X_Gyro=(TextView) findViewById(R.id.textView_X_Gyro);
	        textView_Y_Gyro=(TextView) findViewById(R.id.textView_Y_Gyro);
	        textView_Z_Gyro=(TextView) findViewById(R.id.textView_Z_Gyro);
	        textView_X_Eulerian=(TextView) findViewById(R.id.textView_X_Eulerian);
	        textView_Y_Eulerian=(TextView) findViewById(R.id.textView_Y_Eulerian);
	        textView_Z_Eulerian=(TextView) findViewById(R.id.textView_Z_Eulerian);
	        textView_X_magnetometer=(TextView) findViewById(R.id.textView_X_magnetometer);
	        textView_Y_magnetometer=(TextView) findViewById(R.id.textView_Y_magnetometer);
	        textView_Z_magnetometer=(TextView) findViewById(R.id.textView_Z_magnetometer);
	        
	  }
	  
	 //驱动轮电机
	  private void secondPage() {
		    textView_motor1_setforward=(TextView)  findViewById(R.id.textView_motor1_setforward);
			textView_motor1_setbackward=(TextView)  findViewById(R.id.textView_motor1_setbackward);
			editText_motor1_setforward=(EditText)  findViewById(R.id.editText_motor1_setforward);
			editText_motor1_setbackward=(EditText)  findViewById(R.id.editText_motor1_setbackward);
			button_motor1_forward = (Button)  findViewById(R.id.button_motor1_forward);
			button_motor1_forward.setOnClickListener(new MotorButtonListener());
			button_motor1_stop = (Button)  findViewById(R.id.button_motor1_stop);
			button_motor1_stop.setOnClickListener(new MotorButtonListener());
			button_motor1_backward = (Button)  findViewById(R.id.button_motor1_backward);
			button_motor1_backward.setOnClickListener(new MotorButtonListener());
			
			seekBar_motor1= (SeekBar)  findViewById(R.id.seekBar_motor1_TARGET);
		    seekBar_motor1.setOnSeekBarChangeListener(new Motor1_SeekBarChangeListener());
		    textView_motor1=(TextView)  findViewById(R.id.textView_motor1_TARGET);
		    textView_motor1_1=(TextView)  findViewById(R.id.textView_motor1_TARGET_2);
		    editText_motor1_min=(EditText)  findViewById(R.id.editText_motor1_TARGET_min);
		    editText_motor1_max=(EditText)  findViewById(R.id.editText_motor1_TARGET_max);
		    textView_motor1_2=(TextView)  findViewById(R.id.textView_motor1_TARGET_2);
			textView_motor1_progress= (TextView)  findViewById(R.id.textView_motor1_TARGET_progress);
			
			seekBar_motor1_PMW= (SeekBar)  findViewById(R.id.seekBar_motor1_PMW);
		    seekBar_motor1_PMW.setOnSeekBarChangeListener(new Motor1_PMW_SeekBarChangeListener());
		    textView_motor1_PMW=(TextView)  findViewById(R.id.textView_motor1_PMW);
		    textView_motor1_PMW_1=(TextView)  findViewById(R.id.textView_motor1_PMW_1);
		    editText_motor1_PMW_min=(EditText)  findViewById(R.id.editText_motor1_PMW_min);
		    editText_motor1_PMW_max=(EditText)  findViewById(R.id.editText_motor1_PMW_max);
		    textView_motor1_PMW_2=(TextView)  findViewById(R.id.textView_motor1_PMW_2);
			textView_motor1_PMW_progress= (TextView)  findViewById(R.id.textView_motor1_PMW_progress);
			
			seekBar_motor1_P= (SeekBar)  findViewById(R.id.seekBar_motor1_P);
		    seekBar_motor1_P.setOnSeekBarChangeListener(new Motor1_P_SeekBarChangeListener());
		    textView_motor1_P=(TextView)  findViewById(R.id.textView_motor1_P);
		    textView_motor1_P_1=(TextView)  findViewById(R.id.textView_motor1_P_1);
		    editText_motor1_P_min=(EditText)  findViewById(R.id.editText_motor1_P_min);
		    editText_motor1_P_max=(EditText)  findViewById(R.id.editText_motor1_P_max);
		    textView_motor1_P_2=(TextView)  findViewById(R.id.textView_motor1_P_2);
			textView_motor1_P_progress= (TextView)  findViewById(R.id.textView_motor1_P_progress);
			
			seekBar_motor1_I= (SeekBar)  findViewById(R.id.seekBar_motor1_I);
		    seekBar_motor1_I.setOnSeekBarChangeListener(new Motor1_I_SeekBarChangeListener());
		    textView_motor1_I=(TextView)  findViewById(R.id.textView_motor1_I);
		    textView_motor1_I_1=(TextView)  findViewById(R.id.textView_motor1_I_1);
		    editText_motor1_I_min=(EditText)  findViewById(R.id.editText_motor1_I_min);
		    editText_motor1_I_max=(EditText)  findViewById(R.id.editText_motor1_I_max);
		    textView_motor1_I_2=(TextView)  findViewById(R.id.textView_motor1_I_2);
			textView_motor1_I_progress= (TextView)  findViewById(R.id.textView_motor1_I_progress);
			
			seekBar_motor1_D= (SeekBar) findViewById(R.id.seekBar_motor1_D);
		    seekBar_motor1_D.setOnSeekBarChangeListener(new Motor1_D_SeekBarChangeListener());
		    textView_motor1_D=(TextView)  findViewById(R.id.textView_motor1_D);
		    textView_motor1_D_1=(TextView)  findViewById(R.id.textView_motor1_D_1);
		    editText_motor1_D_min=(EditText)  findViewById(R.id.editText_motor1_D_min);
		    editText_motor1_D_max=(EditText)  findViewById(R.id.editText_motor1_D_max);
		    textView_motor1_D_2=(TextView)  findViewById(R.id.textView_motor1_D_2);
			textView_motor1_D_progress= (TextView)  findViewById(R.id.textView_motor1_D_progress);
	    }
	  //横滚电机
	  private void thirdPage() {
		    textView_motor2_setforward=(TextView)  findViewById(R.id.textView_motor2_setforward);
			textView_motor2_setbackward=(TextView)  findViewById(R.id.textView_motor2_setbackward);
			
			editText_motor2_setforward=(EditText)  findViewById(R.id.editText_motor2_setforward);
			editText_motor2_setbackward=(EditText)  findViewById(R.id.editText_motor2_setbackward);
			
			button_motor2_forward = (Button)  findViewById(R.id.button_motor2_forward);
			button_motor2_forward.setOnClickListener(new MotorButtonListener());
			button_motor2_stop = (Button)  findViewById(R.id.button_motor2_stop);
			button_motor2_stop.setOnClickListener(new MotorButtonListener());
			button_motor2_backward = (Button)  findViewById(R.id.button_motor2_backward);
			button_motor2_backward.setOnClickListener(new MotorButtonListener());
			
			seekBar_motor2_PMW= (SeekBar)  findViewById(R.id.seekBar_motor2_PMW);
		    seekBar_motor2_PMW.setOnSeekBarChangeListener(new Motor2_PMW_SeekBarChangeListener());
		    textView_motor2_PMW=(TextView)  findViewById(R.id.textView_motor2_PMW);
		    textView_motor2_PMW_1=(TextView)  findViewById(R.id.textView_motor2_PMW_1);
		    editText_motor2_PMW_min=(EditText)  findViewById(R.id.editText_motor2_PMW_min);
		    editText_motor2_PMW_max=(EditText)  findViewById(R.id.editText_motor2_PMW_max);
		    textView_motor2_PMW_2=(TextView)  findViewById(R.id.textView_motor2_PMW_2);
			textView_motor2_PMW_progress= (TextView)  findViewById(R.id.textView_motor2_PMW_progress);
	    }
	  //航向角电机
	  private void forthPage() {
		  textView_motor3_setforward=(TextView)  findViewById(R.id.textView_motor3_setforward);
			textView_motor3_setbackward=(TextView)  findViewById(R.id.textView_motor3_setbackward);
			editText_motor3_setforward=(EditText)  findViewById(R.id.editText_motor3_setforward);
			editText_motor3_setbackward=(EditText)  findViewById(R.id.editText_motor3_setbackward);
			button_motor3_forward = (Button)  findViewById(R.id.button_motor3_forward);
			button_motor3_forward.setOnClickListener(new MotorButtonListener());
			button_motor3_stop = (Button)  findViewById(R.id.button_motor3_stop);
			button_motor3_stop.setOnClickListener(new MotorButtonListener());
			button_motor3_backward = (Button)  findViewById(R.id.button_motor3_backward);
			button_motor3_backward.setOnClickListener(new MotorButtonListener());
			 seekBar_motor3_PMW= (SeekBar)  findViewById(R.id.seekBar_motor3_PMW);
			 seekBar_motor3_PMW.setOnSeekBarChangeListener(new  Motor3_PMW_SeekBarChangeListener());
			 
			 textView_motor3_PMW=(TextView)  findViewById(R.id.textView_motor3_PMW);
			 textView_motor3_PMW_1=(TextView)  findViewById(R.id.textView_motor3_PMW_1);
			 editText_motor3_PMW_min=(EditText)  findViewById(R.id.editText_motor3_PMW_min);
			 editText_motor3_PMW_max=(EditText)  findViewById(R.id.editText_motor3_PMW_max);
			 editText_motor3_PMW_min.setText("0");
			 editText_motor3_PMW_max.setText("150");
			 textView_motor3_PMW_2=(TextView)  findViewById(R.id.textView_motor3_PMW_2);
			 editText_motor3_PMW_progress= (EditText)  findViewById(R.id.editText_motor3_PMW_progress);
			 editText_motor3_PMW_progress.setText("150");
	    }

	  private void fifthPage() {
		  editText_ROLL_ANGLE_Angle=(EditText) findViewById(R.id.editText_PITCH_ANGLE_Angle);
		  editText_ROLL_ANGLE_Velocity=(EditText) findViewById(R.id.editText_ROLL_ANGLE_Velocity);
		  editText_PITCH_ANGLE_Angle=(EditText) findViewById(R.id.editText_PITCH_ANGLE_Angle);
		  editText_PITCH_ANGLE_Velocity=(EditText) findViewById(R.id.editText_PITCH_ANGLE_Velocity);
		  editText_YAW_ANGLE_Angle=(EditText) findViewById(R.id.editText_YAW_ANGLE_Angle);
		  editText_YAW_ANGLE_Velocity=(EditText) findViewById(R.id.editText_YAW_ANGLE_Velocity);
		  btn_ROLL_ANGLE_Angle=(Button) findViewById(R.id.btn_ROLL_ANGLE_Angle);
		  btn_ROLL_ANGLE_Velocity=(Button) findViewById(R.id.btn_ROLL_ANGLE_Velocity);
		  btn_PITCH_ANGLE_Angle=(Button) findViewById(R.id.btn_PITCH_ANGLE_Angle);
		  btn_PITCH_ANGLE_Velocity=(Button) findViewById(R.id.btn_PITCH_ANGLE_Velocity);
		  btn_YAW_ANGLE_Angle=(Button) findViewById(R.id.btn_YAW_ANGLE_Angle);
		  btn_YAW_ANGLE_Velocity=(Button) findViewById(R.id.btn_YAW_ANGLE_Velocity);
		  btn_YAW_ANGLE_Velocity.setOnClickListener(new GyroListener());
		  btn_ROLL_ANGLE_Velocity.setOnClickListener(new GyroListener());
		  btn_PITCH_ANGLE_Velocity.setOnClickListener(new GyroListener());
		  btn_YAW_ANGLE_Angle.setOnClickListener(new GyroListener());
		  btn_ROLL_ANGLE_Angle.setOnClickListener(new GyroListener());
		  btn_PITCH_ANGLE_Angle.setOnClickListener(new GyroListener());
	  }

	  


	  public class BluetoothListener implements OnClickListener{
	        @Override
	        public void onClick(View v) {
	            switch(v.getId()) {
	            case R.id.btn_off_on:
	                if(!mBluetoothAdapter.isEnabled()){
	                    mBluetoothAdapter.enable();
	                    Toast.makeText(Controller.this, "蓝牙已开启", Toast.LENGTH_SHORT).show();
	                    mbtnOn_Off.setText("关闭");
	                }else{
	                    mBluetoothAdapter.disable();
	                    Toast.makeText(Controller.this, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
	                    mbtnOn_Off.setText("开启");
	                }
	                break;
	            case R.id.btn_search_div:
	                searchDevice();
	                break;
	           
	            case R.id.btn_connect_div:
	                if (null != mDevice && null != mChatService) {
	                    mChatService.connect(mDevice);
	                } else {
	                    Toast.makeText(Controller.this, "连接蓝牙设备失败,请重新配对和连接", Toast.LENGTH_SHORT).show();
	                }
	                break;
	            case R.id.btn_startPc:
	            	 
	                if(mbtnPc.getText()=="PC端未开启"){
	                	ServerRunnable serverRunnable =new ServerRunnable();
	         		    serverRunnable.run(0,15);
	                    Toast.makeText(Controller.this, "PC端已开启", Toast.LENGTH_SHORT).show();
	                    mbtnPc.setText("PC端已开启");
	                    new Thread(serverRunnable).start();
	                }else{
	                	ServerRunnable serverRunnable =new ServerRunnable();
	         			serverRunnable.run(0,16);
	                    Toast.makeText(Controller.this, "PC端未开启", Toast.LENGTH_SHORT).show();
	                    mbtnPc.setText("PC端未开启");
	                    new Thread(serverRunnable).start();
	                }
		                
	                break;
	           
	            }
	        }
	        }
	        
	  
      public class Motor1_SeekBarChangeListener implements OnSeekBarChangeListener{

  		@Override
  		public void onProgressChanged(SeekBar seekBar, int progress,
  				boolean fromUser) {
  			// TODO Auto-generated method stub
  			 seekBar_motor1.setMax(Integer.parseInt(editText_motor1_max.getText().toString())
  					 -Integer.parseInt(editText_motor1_min.getText().toString()));
  			 progress = progress+ Integer.parseInt(editText_motor1_min.getText().toString().trim());
  			 textView_motor1_progress.setText(""+progress);
  			 send_flat=1;
  			 ServerRunnable serverRunnable =new ServerRunnable();
  			 serverRunnable.run(progress,send_flat);
  		     new Thread(serverRunnable).start();
  		}

  		@Override
  		public void onStartTrackingTouch(SeekBar seekBar) {
  			// TODO Auto-generated method stub
  			
  		}

  		@Override
  		public void onStopTrackingTouch(SeekBar seekBar) {
  			// TODO Auto-generated method stub
  			
  		}
      	
      }
	        
	        public class Motor1_PMW_SeekBarChangeListener implements OnSeekBarChangeListener{

	    		@Override
	    		public void onProgressChanged(SeekBar seekBar, int progress,
	    				boolean fromUser) {
	    			// TODO Auto-generated method stub
	    			 seekBar_motor1_PMW.setMax(Integer.parseInt(editText_motor1_PMW_max.getText().toString())
	    					 -Integer.parseInt(editText_motor1_PMW_min.getText().toString()));
	    			 progress = progress+ Integer.parseInt(editText_motor1_PMW_min.getText().toString().trim());
	    			 textView_motor1_PMW_progress.setText(""+progress);
	    			 send_flat=2;
	    			 ServerRunnable serverRunnable =new ServerRunnable();
	    			 serverRunnable.run(progress,send_flat);
	    		     new Thread(serverRunnable).start();
	    		}

	    		@Override
	    		public void onStartTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onStopTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        }
	        
	        public class Motor2_PMW_SeekBarChangeListener implements OnSeekBarChangeListener{

	    		@Override
	    		public void onProgressChanged(SeekBar seekBar, int progress,
	    				boolean fromUser) {
	    			// TODO Auto-generated method stub
	    			 seekBar_motor2_PMW.setMax(Integer.parseInt(editText_motor2_PMW_max.getText().toString())
	    					 -Integer.parseInt(editText_motor2_PMW_min.getText().toString()));
	    			 progress = progress+ Integer.parseInt(editText_motor2_PMW_min.getText().toString().trim());
	    			 textView_motor2_PMW_progress.setText(""+progress);
	    			 send_flat=7;
	    			 ServerRunnable serverRunnable =new ServerRunnable();
	    			 serverRunnable.run(progress,send_flat);
	    		     new Thread(serverRunnable).start();
	    		}

	    		@Override
	    		public void onStartTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onStopTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        }
	        
	        public class Motor3_PMW_SeekBarChangeListener implements OnSeekBarChangeListener{

	    		@Override
	    		public void onProgressChanged(SeekBar seekBar, int progress,
	    				boolean fromUser) {
	    			// TODO Auto-generated method stub
	    			 seekBar_motor3_PMW.setMax(Integer.parseInt(editText_motor3_PMW_max.getText().toString())
	    					 -Integer.parseInt(editText_motor3_PMW_min.getText().toString()));
	    			 progress = progress+ Integer.parseInt(editText_motor3_PMW_min.getText().toString().trim());
	    			 editText_motor3_PMW_progress.setText(""+progress);
	    			 send_flat=8;
	    			 ServerRunnable serverRunnable =new ServerRunnable();
	    			 serverRunnable.run(progress,send_flat);
	    		     new Thread(serverRunnable).start();
	    		}

	    		@Override
	    		public void onStartTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onStopTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        }
	        
	        public class Motor1_P_SeekBarChangeListener implements OnSeekBarChangeListener{

	    		@Override
	    		public void onProgressChanged(SeekBar seekBar, int progress,
	    				boolean fromUser) {
	    			// TODO Auto-generated method stub
	    			 seekBar_motor1_P.setMax(Integer.parseInt(editText_motor1_P_max.getText().toString())
	    					 -Integer.parseInt(editText_motor1_P_min.getText().toString()));
	    			 progress = progress+ Integer.parseInt(editText_motor1_P_min.getText().toString().trim());
	    			 textView_motor1_P_progress.setText(""+progress);
	    			 send_flat=3;
	    			 ServerRunnable serverRunnable =new ServerRunnable();
	    			 serverRunnable.run(progress,send_flat);
	    		     new Thread(serverRunnable).start();
	    		}

	    		@Override
	    		public void onStartTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onStopTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        }
	        
	        public class Motor1_I_SeekBarChangeListener implements OnSeekBarChangeListener{

	    		@Override
	    		public void onProgressChanged(SeekBar seekBar, int progress,
	    				boolean fromUser) {
	    			// TODO Auto-generated method stub
	    			 seekBar_motor1_I.setMax(Integer.parseInt(editText_motor1_I_max.getText().toString())
	    					 -Integer.parseInt(editText_motor1_I_min.getText().toString()));
	    			 progress = progress+ Integer.parseInt(editText_motor1_I_min.getText().toString().trim());
	    			 textView_motor1_I_progress.setText(""+progress);
	    			 send_flat=4;
	    			 ServerRunnable serverRunnable =new ServerRunnable();
	    			 serverRunnable.run(progress,send_flat);
	    		     new Thread(serverRunnable).start();
	    		}

	    		@Override
	    		public void onStartTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onStopTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        }
	        
	        public class Motor1_D_SeekBarChangeListener implements OnSeekBarChangeListener{

	    		@Override
	    		public void onProgressChanged(SeekBar seekBar, int progress,
	    				boolean fromUser) {
	    			// TODO Auto-generated method stub
	    			
	    			 seekBar_motor1_D.setMax(Integer.parseInt(editText_motor1_D_max.getText().toString())
	    					 -Integer.parseInt(editText_motor1_D_min.getText().toString()));
	    			 progress = progress+ Integer.parseInt(editText_motor1_D_min.getText().toString().trim());
	    			 textView_motor1_D_progress.setText(""+progress);
	    			 send_flat=5;
	    			 ServerRunnable serverRunnable =new ServerRunnable();
	    			 serverRunnable.run(progress,send_flat);
	    		     new Thread(serverRunnable).start();
	    		}

	    		@Override
	    		public void onStartTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onStopTrackingTouch(SeekBar seekBar) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        }
	        
	        
	        
	      
	      
	        
	        public void initData(){     
	            SharedPreferences preferences = getSharedPreferences("information", MODE_PRIVATE);
 
	              int  motor1_setforward= preferences.getInt("editText_motor1_setforward", 1), 
	     	      	motor1_setbackward = preferences.getInt("editText_motor1_setbackward", -1),
	     	        motor2_setforward= preferences.getInt("editText_motor2_setforward", 1), 
	 	     	  	motor2_setbackward = preferences.getInt("editText_motor2_setbackward", -1),
	 	     	  	motor3_setforward= preferences.getInt("editText_motor3_setforward", 1), 
	 	     	 	motor3_setbackward = preferences.getInt("editText_motor3_setbackward", -1), 
	 	     	 	motor1 = preferences.getInt("motor1", 0), 
	 		    	motor1_min = preferences.getInt("motor1_min", 0), 
	 		    	motor1_max = preferences.getInt("motor1_max", 150),	    
	      			motor1_PMW = preferences.getInt("motor1_PMW", 0), 
	    	        motor1_PMW_min = preferences.getInt("motor1_PMW_min", 0), 
	    	        motor1_PMW_max = preferences.getInt("motor1_PMW_max", 150),	      	
	    	        motor1_P = preferences.getInt("motor1_P", 0), 
	    	        motor1_P_min = preferences.getInt("motor1_P_min", 0), 
	      	        motor1_P_max = preferences.getInt("motor1_P_max", 150),
	      	        motor1_I = preferences.getInt("motor1_I", 0), 
	    	        motor1_I_min = preferences.getInt("motor1_I_min", 0), 
	    	    	motor1_I_max = preferences.getInt("motor1_I_max", 150),	  
                    motor1_D = preferences.getInt("motor1_D", 0), 
	    	        motor1_D_min = preferences.getInt("motor1_D_min", 0), 
	    	    	motor1_D_max = preferences.getInt("motor1_D_max", 150),	  
	    	     	motor2_PMW = preferences.getInt("motor2_PMW", 0), 
	    	        motor2_PMW_min = preferences.getInt("motor2_PMW_min", 0), 
	    	        motor2_PMW_max = preferences.getInt("motor2_PMW_max", 150),	      	
	    	    	motor3_PMW = preferences.getInt("motor3_PMW", 0), 
	    	        motor3_PMW_min = preferences.getInt("motor3_PMW_min", 0), 
	    	    	motor3_PMW_max = preferences.getInt("motor3_PMW_max", 150),  
	                YAW_ANGLE_Velocity= preferences.getInt("YAW_ANGLE_Velocity", 3000), 
	                YAW_ANGLE_Angle= preferences.getInt("YAW_ANGLE_Angle", 3000),
	                ROLL_ANGLE_Velocity= preferences.getInt("ROLL_ANGLE_Velocity", 3000),
	                ROLL_ANGLE_Angle= preferences.getInt("ROLL_ANGLE_Angle", 3000),
	                PITCH_ANGLE_Velocity= preferences.getInt("PITCH_ANGLE_Velocity", 3000), 
	                PITCH_ANGLE_Angle= preferences.getInt("PITCH_ANGLE_Angle", 3000); ;
	    	    	    	        
	                editText_motor1_setforward.setText(motor1_setforward + "");
	                editText_motor1_setbackward.setText(motor1_setbackward + "");
	                editText_motor1_PMW_max.setText(motor1_PMW_max + "");
	                textView_motor1_PMW_progress.setText(motor1_PMW + "");
	        		editText_motor1_PMW_min.setText(motor1_PMW_min + "");
	        		editText_motor1_P_max.setText(motor1_P_max + "");
			        textView_motor1_P_progress.setText(motor1_P + "");
			        editText_motor1_P_min.setText(motor1_P_min + "");
			        editText_motor1_I_max.setText(motor1_I_max + "");
			        textView_motor1_I_progress.setText(motor1_I + "");
			        editText_motor1_I_min.setText(motor1_I_min + "");
			        editText_motor1_D_max.setText(motor1_D_max + "");
			        textView_motor1_D_progress.setText(motor1_D + "");
			        editText_motor1_D_min.setText(motor1_D_min + "");
			        editText_motor1_max.setText(motor1_max + "");
			        textView_motor1_progress.setText(motor1 + "");
			        editText_motor1_min.setText(motor1_min + "");
	        		
	        	    editText_motor3_setforward.setText(motor3_setforward + "");
		            editText_motor3_setbackward.setText(motor3_setbackward + "");
		            editText_motor3_PMW_max.setText(motor3_PMW_max + "");
		            editText_motor3_PMW_progress.setText(motor3_PMW + "");
		            editText_motor3_PMW_min.setText(motor3_PMW_min + "");
		            
		            editText_motor2_setforward.setText(motor2_setforward + "");
		            editText_motor2_setbackward.setText(motor2_setbackward + "");
			        editText_motor2_PMW_max.setText(motor2_PMW_max + "");
			        textView_motor2_PMW_progress.setText(motor2_PMW + "");
			        editText_motor2_PMW_min.setText(motor2_PMW_min + "");
			        
	
		        		editText_YAW_ANGLE_Angle.setText(YAW_ANGLE_Angle + "");
		        		editText_ROLL_ANGLE_Angle.setText(ROLL_ANGLE_Angle + "");
		        		editText_PITCH_ANGLE_Angle.setText(PITCH_ANGLE_Angle + "");
		        		editText_YAW_ANGLE_Velocity.setText(YAW_ANGLE_Velocity + "");
		        		editText_ROLL_ANGLE_Velocity.setText(ROLL_ANGLE_Velocity + "");
		        		editText_PITCH_ANGLE_Velocity.setText(PITCH_ANGLE_Velocity + "");
		        		
		        		
	        		seekBar_motor1_PMW.setProgress(motor1_PMW
	    					- Integer.parseInt(editText_motor1_PMW_min.getText()
	    							.toString().trim()));
	        		seekBar_motor2_PMW.setProgress(motor2_PMW
	    					- Integer.parseInt(editText_motor2_PMW_min.getText()
	    							.toString().trim()));
	        		seekBar_motor3_PMW.setProgress(motor3_PMW
	    					- Integer.parseInt(editText_motor3_PMW_min.getText()
	    							.toString().trim()));
	        		
	        		seekBar_motor1_P.setProgress(motor1_P
	    					- Integer.parseInt(editText_motor1_P_min.getText()
	    							.toString().trim()));
	        		seekBar_motor1_I.setProgress(motor1_I
	    					- Integer.parseInt(editText_motor1_I_min.getText()
	    							.toString().trim()));
	        		seekBar_motor1_D.setProgress(motor1_D
	    					- Integer.parseInt(editText_motor1_D_min.getText()
	    							.toString().trim()));
	        		
	        		seekBar_motor1.setProgress(motor1_PMW);
	        		
	        			Toast.makeText(Controller.this, "已读出数据", 5000).show();
	        }
	        
	        public void onStop(){
	        	super.onStop();
	        	SharedPreferences preferences = getSharedPreferences("information", MODE_PRIVATE);
	        	SharedPreferences.Editor editor = getSharedPreferences("information", MODE_PRIVATE).edit();
	        	
	        	editor.putInt(
	    				"editText_motor1_setforward",
	    				Integer.parseInt(editText_motor1_setforward.getText().toString()
	    						.trim()));
	        	editor.putInt(
	    				"editText_motor1_setbackward",
	    				Integer.parseInt(editText_motor1_setbackward.getText().toString()
	    						.trim()));
	        
	        	
	        	    editor.putInt(
	    					"motor1_PMW",
	    					Integer.parseInt(textView_motor1_PMW_progress.getText()
	    							.toString().trim()));
	    			editor.putInt(
	    					"motor1_PMW_min",
	    					Integer.parseInt(editText_motor1_PMW_min.getText()
	    							.toString().trim()));
	    			editor.putInt(
	    					"motor1_PMW_max",
	    					Integer.parseInt(editText_motor1_PMW_max.getText()
	    							.toString().trim()));
	    			 editor.putInt(
		    					"motor1",
		    					Integer.parseInt(textView_motor1_progress.getText()
		    							.toString().trim()));
		    			editor.putInt(
		    					"motor1_min",
		    					Integer.parseInt(editText_motor1_min.getText()
		    							.toString().trim()));
		    			editor.putInt(
		    					"motor1_max",
		    					Integer.parseInt(editText_motor1_max.getText()
		    							.toString().trim()));
	    			   editor.putInt(
		    					"motor2_PMW",
		    					Integer.parseInt(textView_motor2_PMW_progress.getText()
		    							.toString().trim()));
		    			editor.putInt(
		    					"motor2_PMW_min",
		    					Integer.parseInt(editText_motor2_PMW_min.getText()
		    							.toString().trim()));
		    			editor.putInt(
		    					"motor2_PMW_max",
		    					Integer.parseInt(editText_motor2_PMW_max.getText()
		    							.toString().trim()));
		    			   editor.putInt(
			    					"motor3_PMW",
			    					Integer.parseInt(editText_motor3_PMW_progress.getText()
			    							.toString().trim()));
			    			editor.putInt(
			    					"motor3_PMW_min",
			    					Integer.parseInt(editText_motor3_PMW_min.getText()
			    							.toString().trim()));
			    			editor.putInt(
			    					"motor3_PMW_max",
			    					Integer.parseInt(editText_motor3_PMW_max.getText()
			    							.toString().trim()));
	    			
	    			  editor.putInt(
		    					"motor1_P",
		    					Integer.parseInt(textView_motor1_P_progress.getText()
		    							.toString().trim()));
		    			editor.putInt(
		    					"motor1_P_min",
		    					Integer.parseInt(editText_motor1_P_min.getText()
		    							.toString().trim()));
		    			editor.putInt(
		    					"motor1_P_max",
		    					Integer.parseInt(editText_motor1_P_max.getText()
		    							.toString().trim()));
		    			
		    			
		    			  editor.putInt(
			    					"motor1_I",
			    					Integer.parseInt(textView_motor1_I_progress.getText()
			    							.toString().trim()));
			    			editor.putInt(
			    					"motor1_I_min",
			    					Integer.parseInt(editText_motor1_I_min.getText()
			    							.toString().trim()));
			    			editor.putInt(
			    					"motor1_I_max",
			    					Integer.parseInt(editText_motor1_I_max.getText()
			    							.toString().trim()));
			    			
			    			  editor.putInt(
				    					"motor1_D",
				    					Integer.parseInt(textView_motor1_D_progress.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"motor1_D_min",
				    					Integer.parseInt(editText_motor1_D_min.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"motor1_D_max",
				    					Integer.parseInt(editText_motor1_D_max.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"YAW_ANGLE_Velocity",
				    					Integer.parseInt(editText_YAW_ANGLE_Velocity.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"YAW_ANGLE_Angle",
				    					Integer.parseInt(editText_YAW_ANGLE_Angle.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"PITCH_ANGLE_Velocity",
				    					Integer.parseInt(editText_PITCH_ANGLE_Velocity.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"PITCH_ANGLE_Angle",
				    					Integer.parseInt(editText_PITCH_ANGLE_Angle.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"ROLL_ANGLE_Velocity",
				    					Integer.parseInt(editText_ROLL_ANGLE_Velocity.getText()
				    							.toString().trim()));
				    			editor.putInt(
				    					"ROLL_ANGLE_Angle",
				    					Integer.parseInt(editText_ROLL_ANGLE_Angle.getText()
				    							.toString().trim()));
				    			
	    			editor.commit();
	    			Toast.makeText(Controller.this, "已存入数据", 5000).show();
	        }


	        
	        @Override
	        public void onStart() {
	            super.onStart();
	            // If BT is not on, request that it be enabled.
	            // setupChat() will then be called during onActivityResult
	            if (!mBluetoothAdapter.isEnabled()) {
	                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
	            // Otherwise, setup the chat session
	            } else {
	                if (mChatService == null) setupChat();
	            }
	        }
	        
	        @Override
	        public synchronized void onResume() {
	            super.onResume();
	            // Performing this check in onResume() covers the case in which BT was
	            // not enabled during onStart(), so we were paused to enable it...
	            // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
	            if (mChatService != null) {
	                // Only if the state is STATE_NONE, do we know that we haven't started already
	                if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
	                  // Start the Bluetooth chat services
	                  mChatService.start();
	                }
	            }
	        }
	        
	        private void setupChat() {
	            // Initialize the TabhostFromXmlService to perform bluetooth connections
	            mChatService = new BluetoothChatService(this, mHandler);
	            // Initialize the buffer for outgoing messages
	            mOutStringBuffer = new StringBuffer("");
	        }
	        
	        @Override
	        public void onDestroy() {
	            super.onDestroy();
	            // Stop the Bluetooth chat services
	            if (mChatService != null) mChatService.stop();
	            
	            // Make sure we're not doing discovery anymore
	            if (mBluetoothAdapter != null) {
	                mBluetoothAdapter.cancelDiscovery();
	            }
	            // Unregister broadcast listeners
	            this.unregisterReceiver(mReceiver);
	        }
	        
	        /** 使本地的蓝牙设备可被发现 */
	        private void ensureDiscoverable() {
	            if (mBluetoothAdapter.getScanMode() !=
	                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
	                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
	                startActivity(discoverableIntent);
	            }
	        }
	        
	        /**
	         * Sends a message.
	         * @param message  A string of text to send.
	         */
	        private void sendMessage(byte[] message) {
	            // Check that we're actually connected before trying anything
	            if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
	                Toast.makeText(this,"没有连接上", Toast.LENGTH_SHORT).show();
	               // return;
	            }

	            // Check that there's actually something to send
	            if (message!=null) {
	                // Get the message bytes and tell the TabhostFromXmlService to write
	                
	                //byte[] send = this.convert2HexArray(message); 
	                mChatService.write(message);
	            }
	        }
	        
	        
	       
	        // The Handler that gets information back from the TabhostFromXmlService
	        private final Handler mHandler = new Handler() {

	        	
	            @Override
	            public void handleMessage(Message msg) {
	                switch (msg.what) {
	                case MESSAGE_STATE_CHANGE:
	                    switch (msg.arg1) {
	                    case BluetoothChatService.STATE_CONNECTED:
	                        mTitle.setText("已经连接");
	                        mTitle.append(mConnectedDeviceName);
	                        //mConversationArrayAdapter.clear();
	                        break;
	                    case BluetoothChatService.STATE_CONNECTING:
	                        mTitle.setText("正在连接中...");
	                        break;
	                    case BluetoothChatService.STATE_LISTEN:
	                    case BluetoothChatService.STATE_NONE:
	                        mTitle.setText("未连接上");
	                        break;
	                    }
	                    break;
	               
	                case MESSAGE_DEVICE_NAME:
	                    // save the connected device's name
	                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
	                    Toast.makeText(getApplicationContext(), "连接到 "
	                                   + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
	                    break;
	                case MESSAGE_READ:
	                	byte[] readBuf = (byte[]) msg.obj;
	                	Util utility=new Util();
	    				String readMessage = new String(readBuf, 0, msg.arg1); 
	    				newCode = utility.bytesToHexString2(readBuf);
	    				//newCode2 = utility.Stringspace(newCode);
	    				//char[] ch=newCode.toCharArray();	
	    				String Code0,Code1,Code2,Code3;
	    				int a1,a2,a3;
	    				if(newCode!=null)
	    				{
	    				    Code0=newCode.substring(0,7);
	    					Code1=newCode.substring(2,7);
	    					Code2=newCode.substring(4,7);
	    					Code3=newCode.substring(0,7);
	    					int i0 = Integer.parseInt(Code0, 16); 
	    					int i1 = Integer.parseInt(Code1, 16);  
	    					int i2 = Integer.parseInt(Code2, 16);  
	    					int i3 = Integer.parseInt(Code3, 16);  
	    					Code0=Integer.toString(i0);
	    					Code1=Integer.toString(i1);
	    					Code2=Integer.toString(i2);
	    					Code3=Integer.toString(i3);
	    					//if(i3==15)
	    				//	{
	    				    textView_X_LinearAcceleration.setText(Code0+"\n");
	    				    textView_Y_LinearAcceleration.setText(Code1+"\n");
	    				    textView_Z_LinearAcceleration.setText(Code2+"\n");
	    				/*	}
	    					if(i3==25)
	    					{
		    				    textView_X_Gyro.setText(Code0+"\n");
		    				    textView_Y_Gyro.setText(Code1+"\n");
		    				    textView_Z_Gyro.setText(Code2+"\n");
		    					}
	    					if(i3==35)
	    					{
		    				    textView_X_magnetometer.setText(Code0+"\n");
		    				    textView_Y_magnetometer.setText(Code1+"\n");
		    				    textView_Z_magnetometer.setText(Code2+"\n");
		    					}
	    					if(i3==45)
	    					{
		    				    textView_X_Eulerian.setText(Code0+"\n");
		    				    textView_Y_Eulerian.setText(Code1+"\n");
		    				    textView_Z_Eulerian.setText(Code2+"\n");
		    					}*/
	    				}
	                    break; 
	                case MESSAGE_TOAST:
	                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
	                                   Toast.LENGTH_SHORT).show();
	                    break;
	                }
	            }
	        };
	        
	        //连接蓝牙设备
	        private void linkDevice(){
	        	if(mBluetoothAdapter.isDiscovering()){
	    			mBluetoothAdapter.cancelDiscovery();
	    		}
	    		int cou = mPairedDevicesList.size() + mNewDevicesList.size();
	    		if(cou == 0){
	    			Toast.makeText(Controller.this, "没有搜索到可用的蓝牙设备", Toast.LENGTH_SHORT).show();
	    			return;
	    		}
	    		
	    		
	    		//把已经配对的蓝牙设备和新发现的蓝牙设备的名称都放入数组中，以便在对话框列表中显示
	    		strName = new String[cou];
	    		for(int i = 0; i < mPairedDevicesList.size(); i++){
	    			strName[i] = mPairedDevicesList.get(i);
	    		}
	    		for(int i = mPairedDevicesList.size(); i < strName.length; i++){
	    			strName[i] = mNewDevicesList.get(i - mPairedDevicesList.size());
	    		}
	    		address = strName[0].substring(strName[0].length() - 17);
	    		new AlertDialog.Builder(Controller.this)
	    		.setTitle("搜索到的蓝牙设备：")
	    		.setSingleChoiceItems(strName, 0, new DialogInterface.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(DialogInterface dialog, int which) {
	    				//当用户点击选中的蓝牙设备时，取出选中的蓝牙设备的MAC地址
	    				address = strName[which].split("\\n")[1].trim(); 
	    			}
	    		})
	    		.setPositiveButton("连接", new DialogInterface.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(DialogInterface dialog, int which) {
	    				if(address == null){
	    					Toast.makeText(Controller.this, "请先连接外部蓝牙设备", Toast.LENGTH_SHORT).show();
	    					return;
	    				}
	    				
	    				Log.i("sxd","address:"+address);
	    				// Get the BLuetoothDevice object
	    				
	    				
	                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address); //从
	                    if (device.getBondState() == BluetoothDevice.BOND_NONE) {
	                        Toast.makeText(Controller.this, "远程设备发送蓝牙配对请求", 5000).show();
	                        try {
	                            ClsUtils.createBond(device.getClass(), device);
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                    }
	    			}
	    		})
	    		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(DialogInterface dialog, int which) {
	    				
	    			}
	    		}).create().show();
	        }
	        
	        //搜索蓝牙设备蓝牙设备
	        private void searchDevice(){
	        	mTitle.setText("正在努力搜索中...");
	        	setProgressBarIndeterminateVisibility(true);
	        	if (mBluetoothAdapter.isDiscovering()) {
	                 mBluetoothAdapter.cancelDiscovery();
	            }
	        	mNewDevicesList.clear();
	            mBluetoothAdapter.startDiscovery();
	        }
	        
	        @Override
	        public boolean onCreateOptionsMenu(Menu menu) {
	        	menu.add(0, 1, 0, "搜索设备");
	        	menu.add(0, 2, 0, "可被发现");
	            return true;
	        }


	        
	        private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	            @Override
	            public void onReceive(Context context, Intent intent) {
	                String action = intent.getAction();
	                // 当发现一个新的蓝牙设备时
	                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	                    // If it's already paired, skip it, because it's been listed already
	                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
	                        String s = "未配对： "+ device.getName() + "\n" + device.getAddress();
	                        if(!mNewDevicesList.contains(s)) {
	                            mNewDevicesList.add(s);
	                        }
	                        int iPin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0);
	                        if (0 != iPin) {
	                            mstrPin = iPin + "";
	                        }
	                    } else {
	                        mDevice = device;
	                       /* if (null != mChatService) {
	                            mChatService.connect(device);
	                        } else {
	                            Toast.makeText(TabhostFromXml.this, "连接蓝牙设备失败", Toast.LENGTH_SHORT).show();
	                        }*/
	                    }
	                // When discovery is finished, change the Activity title
	                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	                    setProgressBarIndeterminateVisibility(false);
	                    if (mNewDevicesList.size() == 0) {
	                    	Toast.makeText(Controller.this, "没有发现新设备", Toast.LENGTH_SHORT).show();
	                    }
	                    mTitle.setText("未连接");
	                    linkDevice();
	                } else if ("android.bluetooth.device.action.PAIRING_REQUEST".equals(action)) {
	                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
	                        try {
	                            int iPin = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", 0);
	                            if (0 != iPin) {
	                                mstrPin = iPin + "";
	                            } 
	                            
	                            ClsUtils.setPin(device.getClass(), device, mstrPin); // 手机和蓝牙采集器配对
	                            Toast.makeText(context, "配对信息" + device.getName(), 5000).show();
	                            mDevice = device;
	                        } catch (Exception e) {
	                            Toast.makeText(context, "请求连接错误...", 1500).show();
	                        }
	                    }
	                }
	            }
	        };
	        
	        @Override
	        public boolean onOptionsItemSelected(MenuItem item) {
	            switch (item.getItemId()) {
	            case 1:
	            	searchDevice();
	                return true;
	            case 2:
	                // Ensure this device is discoverable by others
	                ensureDiscoverable();
	                return true;
	            }
	            return false;
	        }

	     

	        
	        
	        
	        public class ServerRunnable implements Runnable {

	        	@Override
	        	public void run() {
	        		// TODO Auto-generated method stub
	        	}

	        	public void run(int send_data,int send_flat) {
	        		// TODO Auto-generated method stub
	        		try {   
	        			Util utility=new Util();
	        			byte data[] =utility.packageToSend(send_data,send_flat);	
	        			Log.e("data","true");
	        			if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
	        	           Toast.makeText(getApplicationContext(),"没有连接到一个蓝牙设备",300).show();
	        	            return;
	        	        }
	        			else 
	        	            mChatService.write(data);   		
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        	}

	        	
	        }

	        public class GyroListener implements OnClickListener {
	    		@Override
	    		public void onClick(View v) {
	    				int motor_button_data = 0;
	    				Log.e(String.valueOf(motor_button_data),"true");
	    				if(v==btn_PITCH_ANGLE_Angle)
	    				{
	    					motor_button_data=Integer.parseInt(editText_PITCH_ANGLE_Angle.getText().toString().trim());
	    					send_flat=9;
	    				}
	    				if(v==btn_PITCH_ANGLE_Velocity)
	    				{
	    					motor_button_data=Integer.parseInt(editText_PITCH_ANGLE_Velocity.getText().toString().trim());
	    					send_flat=10;
	    				}
	    				if(v==btn_ROLL_ANGLE_Angle)
	    				{
	    					motor_button_data=Integer.parseInt(editText_ROLL_ANGLE_Angle.getText().toString().trim());
	    					send_flat=11;
	    				}
	    				if(v==btn_ROLL_ANGLE_Velocity)
	    				{
	    					motor_button_data=Integer.parseInt(editText_ROLL_ANGLE_Velocity.getText().toString().trim());
	    					send_flat=12;
	    				}
	    				if(v==btn_YAW_ANGLE_Angle)
	    				{
	    					motor_button_data=Integer.parseInt(editText_YAW_ANGLE_Angle.getText().toString().trim());
	    					send_flat=13;
	    				}
	    				if(v==btn_YAW_ANGLE_Velocity)
	    				{
	    					motor_button_data=Integer.parseInt(editText_YAW_ANGLE_Velocity.getText().toString().trim());
	    					send_flat=14;
	    				}
	    				Log.e(String.valueOf(motor_button_data),"true");
	    				ServerRunnable serverRunnable2 =new ServerRunnable();
	    				serverRunnable2.run(motor_button_data,send_flat);
	    				new Thread(serverRunnable2).start();
	    		}
	    	}
	    	
	       
	        
	        public class MotorButtonListener implements OnClickListener {
	    		@Override
	    		public void onClick(View v) {
	    			 
	    			    ServerRunnable serverRunnable =new ServerRunnable();
	    				serverRunnable.run(0,11);
	    				new Thread(serverRunnable).start();
	    				int motor_button_data = 0;
	    				if(v==button_motor1_forward)
	    				{
	    					motor_button_data=Integer.parseInt(editText_motor1_setforward.getText().toString().trim());
	    					send_flat=1;
	    				}
	    				if(v==button_motor2_forward)
	    				{
	    					motor_button_data=Integer.parseInt(editText_motor2_setforward.getText().toString().trim());
	    					send_flat=1;
	    				}
	    				if(v==button_motor3_forward)
	    				{
	    					motor_button_data=Integer.parseInt(editText_motor3_setforward.getText().toString().trim());
	    					send_flat=1;
	    				}
	    				
	    				if(v==button_motor1_backward)
	    				{
	    					motor_button_data=Integer.parseInt(editText_motor1_setbackward.getText().toString().trim());
	    					send_flat=1;
	    				}
	    				if(v==button_motor2_backward)
	    				{
	    					motor_button_data=Integer.parseInt(editText_motor2_setbackward.getText().toString().trim());
	    					send_flat=1;
	    				}
	    				if(v==button_motor3_backward)
	    				{
	    					motor_button_data=Integer.parseInt(editText_motor3_setbackward.getText().toString().trim());
	    					send_flat=1;
	    				}
	    				if(v==button_motor1_stop)
	    				{
	    					motor_button_data=0;
	    					send_flat=6;
	    				}
	    				if(v==button_motor2_stop)
	    				{
	    					motor_button_data=0;
	    					send_flat=6;
	    				}
	    				if(v==button_motor3_stop)
	    				{
	    					motor_button_data=0;
	    					send_flat=6;
	    				}
	    				
	    				ServerRunnable serverRunnable2 =new ServerRunnable();
	    				serverRunnable2.run(motor_button_data,send_flat);
	    				new Thread(serverRunnable2).start();
	    		}
	    	}
	    	

	        private void initBluetooth() {
	            //获得本地的蓝牙适配器
	            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	            //如果为null,说明没有蓝牙设备
	            if (mBluetoothAdapter == null) {
	                Toast.makeText(this, "没有蓝牙设备", Toast.LENGTH_LONG).show();
	                finish();
	                return;
	            }
	            
	            if(mBluetoothAdapter.isEnabled()){
	                mbtnOn_Off.setText("关闭");
	            }else{
	                mbtnOn_Off.setText("打开");
	            }
	            
	            // 获得一个已经配对的蓝牙设备的set集合
	            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
	            if (pairedDevices.size() > 0) {
	                for (BluetoothDevice device : pairedDevices) {
	                    mPairedDevicesList.add("已配对："+device.getName() + "\n" + device.getAddress());
	                }
	            } else {
	                Toast.makeText(this, "没有已配对的设备", Toast.LENGTH_SHORT).show();
	            }
	        }
	        
	        private void registerReciver() {
	          //当发现一个新的蓝牙设备时注册广播
	            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	            this.registerReceiver(mReceiver, filter);

	            //当搜索完毕后注册广播
	            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	            this.registerReceiver(mReceiver, filter);
	            
	            filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
	            this.registerReceiver(mReceiver, filter);
	        }

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
}

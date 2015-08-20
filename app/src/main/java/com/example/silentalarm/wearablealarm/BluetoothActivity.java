package com.example.silentalarm.wearablealarm;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class BluetoothActivity extends Activity {
    BluetoothAdapter bluetoothAdapter;
   // BluetoothAdapter bluetoothAdapterPaired;
    ArrayAdapter<String> btArrayAdapter;
    public int timePickerhour;
    public int timePickerMinute;
    ListView listDevicesFound;

    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // ArrayList<String> list = new ArrayList<String>();
    int i = 0;
    private final static int REQUEST_ENABLE_BT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        listDevicesFound = (ListView) findViewById(R.id.listBluetooth);
        btArrayAdapter = new ArrayAdapter<String>(BluetoothActivity.this, android.R.layout.simple_list_item_1);
        listDevicesFound.setAdapter(btArrayAdapter);
        listDevicesFound.setClickable(true);
        listDevicesFound.setSelected(true);

        CheckBlueToothState();
        btArrayAdapter.clear();
        bluetoothAdapter.startDiscovery();

        registerReceiver(ActionFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        listDevicesFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String info = listDevicesFound.getItemAtPosition(i).toString();
                bluetoothAdapter.cancelDiscovery();
                String address = info.substring(info.length() - 17);
                String list = new String();
                list = (info.substring(0, (info.length() - 17)));
                list = list.replaceAll("\\s+", "");
                BluetoothDevice selectedDevice = bluetoothAdapter.getRemoteDevice(address);
                //String address = bluetoothAdapter.getAddress();


                System.out.println("Details: " + address + " and " + selectedDevice);
                System.out.println("Device name:" + list);


                if (list.equalsIgnoreCase("BIBOX")) {
                    sendDataToPairedDevice("data", selectedDevice);
                    Toast.makeText(getApplicationContext(), "Connected to Wearable Device! ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));


                } else {
                    Toast.makeText(getApplicationContext(), "Wrong devices selected! Exiting to home screen. ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }


                // Toast.makeText(getApplicationContext(), "Is this being executed? ", Toast.LENGTH_LONG).show();


























               /* Time dtNow = new Time();
                dtNow.setToNow();
                int hours = dtNow.hour;
                int min = dtNow.minute;
                TimePickerFragment getTime = new TimePickerFragment();
                timePickerhour= getTime.returnHour();
                timePickerMinute= getTime.returnMinute();

                System.out.println("Values of selected time " + getTime.timePickerHour + " and " + timePickerMinute);
                System.out.println("Temp Value is"+ getTime.tempValue);
                System.out.println("Values of system time " + hours + " and " + min);*/
              /*while(timePickerhour !=hours && timePickerMinute!= min )
              {
                i++;

            }

                if(i!=0) {

                    Toast.makeText(getApplicationContext(), "Wearable device connected! ", Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }

    private void sendDataToPairedDevice(String message, BluetoothDevice device) {
        byte[] toSend = message.getBytes();
        try {
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(applicationUUID);
            OutputStream mmOutStream = socket.getOutputStream();
            mmOutStream.write(toSend);
            // Data is sent to  BT connected  device
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(ActionFoundReceiver);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBlueToothState();
        }
    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                btArrayAdapter.notifyDataSetChanged();
            }

           /* else
            {
                Toast.makeText(getApplicationContext(), "No nearby bluetooth device found! Exiting to home screen. ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }*/
        }
    };

    private void CheckBlueToothState() {
        if (bluetoothAdapter == null) {
            //  stateBluetooth.setText("Bluetooth NOT support");
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    // stateBluetooth.setText("Bluetooth is currently in device discovery process.");
                } else {
                    // stateBluetooth.setText("Bluetooth is Enabled.");
                    // btnScanDevice.setEnabled(true);
                }
            } else {
                // stateBluetooth.setText("Bluetooth is NOT Enabled!");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }


    // Toast.makeText(getApplicationContext(), "Test toast ", Toast.LENGTH_LONG).show();

}




package com.example.silentalarm.wearablealarm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    String toastValue;
  // private BluetoothAdapter mBluetoothAdapter;
   // private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();
    public int timePickerHour;
    public int timePickerMinute;
  //   public int tempValue=26;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Time dtNow = new Time();
        dtNow.setToNow();
        int hours = dtNow.hour;
        int min = dtNow.minute;
        timePickerHour = hourOfDay;
        timePickerMinute = minute;

      //  Intent intent = new Intent("BluetoothActivity");
     //   BluetoothActivity blueActiv = new BluetoothActivity();
       // blueActiv.

        toastValue = "Alarm set for " + Math.abs(hours - hourOfDay) + " Hours " + Math.abs(min - minute) + " Minutes from now";
        Toast.makeText(getActivity(), toastValue, Toast.LENGTH_LONG).show();
        System.out.println("Values of selected time" + returnHour() + " and " + returnMinute());
        setLayout();
    }

    void setLayout() {
        startActivity(new Intent(getActivity(),
                BluetoothActivity.class));

    }

    public int returnHour() {

        return timePickerHour;
    }



    public int returnMinute() {

        return timePickerMinute;
    }
}






























 /* // Toast.makeText(getActivity(), "is this running??", Toast.LENGTH_LONG).show();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mProgressDlg = new ProgressDialog(getActivity());
        mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();

        mProgressDlg 		= new ProgressDialog(getActivity());

        mProgressDlg.setMessage("Scanning...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                mBluetoothAdapter.cancelDiscovery();
            }
        });

        if (mBluetoothAdapter == null) {
          //fuck it
        }
        else {
            mBluetoothAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter();
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivityForResult(intent, 1000);


            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        //   registerReceiver(mReceiver, filter);

        }


        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();


                if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    mDeviceList = new ArrayList<BluetoothDevice>();

                    mProgressDlg.show();
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    mProgressDlg.dismiss();

                    Intent newIntent = new Intent(getActivity(), DeviceListActivity.class);

                    newIntent.putParcelableArrayListExtra("device.list", mDeviceList);

                    startActivity(newIntent);
                } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    mDeviceList.add(device);
                    String fndDevice = "Found device" + device.getName();
                    Toast.makeText(getActivity(), fndDevice, Toast.LENGTH_LONG).show();
                }
            }
        };

                   *//* String blueName = "Found device " + device.getName();
                    Toast.makeText(getActivity(), blueName, Toast.LENGTH_LONG).show();
                    *//*

        // code for bluetooth
    }*/
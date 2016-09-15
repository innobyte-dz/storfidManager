package wolfsoft.invincible;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.TextView;

import wolfsoft.invincible.minime.CMD_AntPortOp;
import wolfsoft.invincible.minime.CMD_PwrMgt;
import wolfsoft.invincible.minime.MtiCmd;
import wolfsoft.invincible.minime.UsbCommunication;
import wolfsoft.invincible.receivers.UsbReceiver;
import wolfsoft.invincible.utils.preferences.RfidAppPreferences;

import java.util.HashMap;
import java.util.Iterator;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by lamine on 4/27/2015.
 */
public class DeviceStateActivity extends BaseActivity {
    private static final String ACTION_USB_PERMISSION = "com.rfidreaderapp.USB_PERMISSION";
    private static final int PID = 49193;
    private static final int VID = 4901;
    TextView deviceState;
    RfidAppPreferences sharedPreferences;
    private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();
    private UsbManager mManager;

    /*BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = "";
            String action = intent.getAction();
            Boolean usbState = false;
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                msg = "USB Attached";
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                mUsbCommunication.setUsbInterface(mManager, device);
                usbState = true;
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                msg = "USB Detached";
                mUsbCommunication.setUsbInterface(null, null);
                usbState = false;
            } else if (ACTION_USB_PERMISSION.equals(action)) {
                msg = "USB Permission";
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        mUsbCommunication.setUsbInterface(mManager, device);
                        usbState = true;
                        setPowerLevel();
                        setPowerState();
                    } else {
                        finish();
                    }
                }
            }

            SuperToast superToast = new SuperToast(DeviceStateActivity.this);
            superToast.setText(msg);
            superToast.setDuration(SuperToast.Duration.LONG);
            superToast.setBackground(SuperToast.Background.BLUE);
            superToast.setTextColor(Color.WHITE);
            superToast.setAnimations(SuperToast.Animations.FLYIN);
            superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
            superToast.show();
            setUsbState(usbState);
        }
    };*/
    private PendingIntent mPermissionIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_state_layout);

        deviceState = (TextView) findViewById(R.id.device_state);

        mManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);

        UsbReceiver usbReceiver = new UsbReceiver();
        registerReceiver(usbReceiver, filter);

        sharedPreferences = RfidAppPreferences.getInstance(this);
    }

    private void setUsbState(boolean state) {
        sharedPreferences.setDeviceConnected(state);
        if (state) {
            deviceState.setText("Connecter");
            deviceState.setTextColor(android.graphics.Color.GREEN);
        } else {
            deviceState.setText("Déconnecter");
            deviceState.setTextColor(android.graphics.Color.RED);
        }
    }

    private void setPowerLevel() {
        MtiCmd mMtiCmd = new CMD_AntPortOp.RFID_AntennaPortSetPowerLevel(mUsbCommunication);
        CMD_AntPortOp.RFID_AntennaPortSetPowerLevel finalCmd = (CMD_AntPortOp.RFID_AntennaPortSetPowerLevel) mMtiCmd;

        finalCmd.setCmd((byte) 18);
    }

    private void setPowerState() {
        MtiCmd mMtiCmd = new CMD_PwrMgt.RFID_PowerEnterPowerState(mUsbCommunication);
        CMD_PwrMgt.RFID_PowerEnterPowerState finalCmd = (CMD_PwrMgt.RFID_PowerEnterPowerState) mMtiCmd;

        finalCmd.setCmd(CMD_PwrMgt.PowerState.Sleep);
        sleep(200);
    }

    private void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        HashMap<String, UsbDevice> deviceList = mManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            if (device.getProductId() == PID && device.getVendorId() == VID) {
                if (mManager.hasPermission(device))
                    mManager.requestPermission(device, mPermissionIntent);
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (deviceState.getText().equals("Connected"))
            mUsbCommunication.setUsbInterface(null, null);
    }
}

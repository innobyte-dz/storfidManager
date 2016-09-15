package wolfsoft.invincible.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.github.johnpersano.supertoasts.SuperToast;

import wolfsoft.invincible.minime.CMD_AntPortOp;
import wolfsoft.invincible.minime.CMD_PwrMgt;
import wolfsoft.invincible.minime.MtiCmd;
import wolfsoft.invincible.minime.UsbCommunication;
import wolfsoft.invincible.utils.preferences.RfidAppPreferences;

/**
 * Created by lamine on 5/16/2015.
 */
public class UsbReceiver extends BroadcastReceiver {
    private static final String ACTION_USB_PERMISSION = "com.rfidreaderapp.USB_PERMISSION";
    private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();
    private UsbManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
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
                }
            }
        }

        SuperToast superToast = new SuperToast(context);
        superToast.setText(msg);
        superToast.setDuration(SuperToast.Duration.LONG);
        superToast.setBackground(SuperToast.Background.BLUE);
        superToast.setTextColor(Color.WHITE);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        superToast.show();
        setUsbState(usbState, context);
    }

    private void setUsbState(boolean state, Context context) {
        RfidAppPreferences.getInstance(context).setDeviceConnected(state);
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
}

package com.example.BTClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ClsUtils {
    public static BluetoothDevice remoteDevice = null;

    /**
     * ���豸��� �ο�Դ�룺platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    @SuppressWarnings("unchecked")
    static public boolean createBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    /**
     * ���豸������ �ο�Դ�룺platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    @SuppressWarnings("unchecked")
    static public boolean removeBond(Class btClass, BluetoothDevice btDevice) throws Exception {
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    @SuppressWarnings("unchecked")
    static public boolean setPin(Class btClass, BluetoothDevice btDevice, String str) throws Exception {
        try {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin", new Class[] { byte[].class });
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice, new Object[] { str.getBytes() });
            Log.d("returnValue", "setPin is success " + btDevice.getAddress() + returnValue.booleanValue());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    // ȡ�����
    @SuppressWarnings("unchecked")
    public static byte[] convertPinToBytes(Class btClass, BluetoothDevice btDevice, String strPin) throws Exception {
        try {
            Method convertPinToBytes = btClass.getDeclaredMethod("convertPinToBytes", new Class[] { String.class });
            return (byte[]) convertPinToBytes.invoke(btDevice, new Object[] { strPin.getBytes() });
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{0,0,0,0};
    }
    
    // ȡ���û�����
    @SuppressWarnings("unchecked")
    static public boolean cancelPairingUserInput(Class btClass, BluetoothDevice device) throws Exception {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
        // cancelBondProcess()
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        Log.d("returnValue", "cancelPairingUserInput is success " + returnValue.booleanValue());
        return returnValue.booleanValue();
    }

    // ȡ�����
    @SuppressWarnings("unchecked")
    static public boolean cancelBondProcess(Class btClass, BluetoothDevice device) throws Exception {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    /**
     * 
     * @param clsShow
     */
    @SuppressWarnings("unchecked")
    static public void printAllInform(Class clsShow) {
        try {
            // ȡ�����з���
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++) {
                // Log.e("method name", hideMethod.getName() + ";and the i is:"
                // + i);
            }
            // ȡ�����г���
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++) {
                // Log.e("Field name", allFields.getName());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
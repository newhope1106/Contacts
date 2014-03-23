package com.newhope.contacts;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.provider.ContactsContract.CommonDataKinds.Phone;

public class QueryAsnycTask extends AsyncTask<Cursor, Void, ArrayList<ContactBean>> {
	 /** 开始整理 */
    public static final int DOWNLOADING_START_MESSAGE = 7;
    /** 整理结束 */
    public static final int DOWNLOAD_END_MESSAGE = 17;
    
    private Context mContext = null;
    private Handler mHandler = null;
    
    protected QueryAsnycTask(Context context, Handler handler){
    	mContext = context;
    	mHandler = handler;
    }
    
    @Override
    protected void onPreExecute() {
        sendStartMessage(DOWNLOADING_START_MESSAGE);
    }
    
    @Override
	protected ArrayList<ContactBean> doInBackground(Cursor... params) {
		// TODO Auto-generated method stub
		Cursor cursor = params[0];
        ArrayList<ContactBean> ciList = new ArrayList<ContactBean>();
        if (cursor != null && cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndexOrThrow(Phone.DATA1));
                    int contactId = cursor.getInt(cursor.getColumnIndexOrThrow(Phone.CONTACT_ID));
                    ContactBean contactInfo = new ContactBean();
                    contactInfo.setContactId(contactId);
                    contactInfo.setPhoneNum(number);
                    contactInfo.setDisplayName(name);
                    if (contactInfo.getDisplayName() == null) {
                        contactInfo.setDisplayName(contactInfo.getPhoneNum());
                    }
                                                                                        
                    ciList.add(contactInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ciList;
	}
    
    @Override
    protected void onPostExecute(ArrayList<ContactBean> result) {
        sendEndMessage(DOWNLOAD_END_MESSAGE, result);
    }
    
    /**
     * 发送开始整理消息
     *
     * @param messageWhat
     */
    private void sendStartMessage(int messageWhat) {
        Message message = new Message();
        message.what = messageWhat;
        if (mHandler != null) {
            mHandler.sendMessage(message);
        }
    }
    
    /**
     * 发送整理结束消息
     *
     * @param messageWhat
     */
    private void sendEndMessage(int messageWhat, ArrayList<ContactBean> result) {
        Message message = new Message();
        message.what = messageWhat;
        Bundle bundle = new Bundle();
        bundle.putSerializable("contactListData", result);
        message.setData(bundle);
        if (mHandler != null) {
            mHandler.sendMessage(message);
        }
    }
    
    public static void startRequestServerData(Context context, Handler handler,
            Cursor cursor){
    	new QueryAsnycTask(context, handler).execute(cursor);
    }
}

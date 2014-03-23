package com.newhope.contacts;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsListActivity extends Activity {
	private ListView contactsListView;
	private ContactsListAdapter mAdapter;
	private LayoutInflater inflater;
	private ContentResolver resolver;
	private QueryHandler queryHandler;
	
	private static final int QUERY_TOKEN = 42;
	
	@SuppressLint("InlinedApi") 
	private final static String PHONE_PROJECTION[] = {
		Phone._ID,
		Phone.DISPLAY_NAME,
		Phone.DATA1,
		Phone.SORT_KEY_PRIMARY,
		Phone.CONTACT_ID
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        resolver = getContentResolver();
        
        queryHandler = new QueryHandler(resolver);
        
        setupListView();
    }
    
    @SuppressLint("InlinedApi") 
    private void setupListView(){
    	contactsListView = (ListView)findViewById(R.id.contacts_list);
    	
    	queryHandler.startQuery(QUERY_TOKEN, null, Phone.CONTENT_URI, PHONE_PROJECTION, null, null, 
    			Phone.SORT_KEY_PRIMARY + " COLLATE LOCALIZED asc");
    }
    
    private class QueryHandler extends AsyncQueryHandler{

		public QueryHandler(ContentResolver cr) {
			super(cr);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			setupListAdapter(cursor);
		}
    	
    }
    
    
    private void setupListAdapter(final Cursor cursor){
    	Handler handler = new Handler(){
    		public void handleMessage(Message msg){
    			switch(msg.what){
    				case QueryAsnycTask.DOWNLOADING_START_MESSAGE:
    					Toast.makeText(getApplicationContext(), "initializing......", Toast.LENGTH_SHORT).show();
    					break;
    				
    				case QueryAsnycTask.DOWNLOAD_END_MESSAGE:
    					Bundle bundle = msg.getData();
    					mAdapter = new ContactsListAdapter(ContactsListActivity.this, (ArrayList<ContactBean>) bundle.get("contactListData"));
    					contactsListView.setAdapter(mAdapter);
    			}
    		}
    	};
    	
    	QueryAsnycTask.startRequestServerData(this, handler, cursor);
    }
    
    public class ContactsListAdapter extends BaseAdapter{
    	private Context mContext;
    	private ArrayList<ContactBean> mContactsList;
    	
    	public ContactsListAdapter(Context context, ArrayList<ContactBean> contactsList){
    		mContext = context;
    		mContactsList = contactsList;
    	}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mContactsList!=null ? mContactsList.size():0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup root) {
			// TODO Auto-generated method stub
			
			if(convertView == null){
				convertView = inflater.inflate(R.layout.contacts_list_item, null);
			}
			
			TextView name = (TextView)convertView.findViewById(R.id.name);
			TextView phoneNumber = (TextView)convertView.findViewById(R.id.phone_number);
			
			ContactBean contactBean = mContactsList.get(position);
			
			name.setText(contactBean.getDisplayName());
			phoneNumber.setText(contactBean.getPhoneNum());
			
			return convertView;
		}
    	
    }
    
}

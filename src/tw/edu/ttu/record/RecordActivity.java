package tw.edu.ttu.record;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivity extends ListActivity {
	private static final String TAG = "nfccheckin";
	ArrayList<HashMap<String, String>> arrayList;
	private DataBaseQuery dataBaseQuey;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBaseQuey = new DataBaseQuery(this.getApplicationContext());
		
		buildMap();
		
		SimpleAdapter simpleAdapter =
				new SimpleAdapter(this, arrayList, tw.edu.ttu.nfccheckin_2.R.layout.recordlist,
						new String[] {"id", "store_name", "checkin_datetime"},
						new int[] {tw.edu.ttu.nfccheckin_2.R.id.textView1, tw.edu.ttu.nfccheckin_2.R.id.textView2, tw.edu.ttu.nfccheckin_2.R.id.textView3});
		this.setListAdapter(simpleAdapter);
	}

	private void buildMap() {
		arrayList = new ArrayList<HashMap<String, String>>();
		
		Cursor cursor = dataBaseQuey.selectAll();
		
		cursor.moveToFirst();
		for(int i = 0; i < cursor.getCount(); i++){
			 HashMap<String, String> item = new HashMap<String, String>();
			 item.put("id", cursor.getString(cursor.getColumnIndex("id")));
			 item.put("store_name", cursor.getString(cursor.getColumnIndex("store_name")));
			 item.put("coupon_msg", cursor.getString(cursor.getColumnIndex("coupon_msg")));
			 item.put("feedback_url", cursor.getString(cursor.getColumnIndex("feedback_url")));
			 item.put("is_feedback", cursor.getString(cursor.getColumnIndex("is_feedback")));
			 item.put("checkin_datetime", cursor.getString(cursor.getColumnIndex("checkin_datetime")));
			 arrayList.add(item);
			 
			 cursor.moveToNext();
		}
	}
	
	@Override
	protected void onListItemClick(ListView listview, View view, int position, long id) {
		super.onListItemClick(listview, view, position, id);

		int is_feedback;
		TextView textView = (TextView) view.findViewById(tw.edu.ttu.nfccheckin_2.R.id.textView1);
		int _id = Integer.valueOf(textView.getText().toString());
		
		Cursor cursor = dataBaseQuey.selectById(_id);
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			is_feedback = cursor.getInt(cursor.getColumnIndex("is_feedback"));
			Log.d(TAG, "is_feedback: " + is_feedback);
		}
		else {
			Log.d(TAG, "dataBaseQuery Fail");
			return;
		}

		if(is_feedback == 0) {
			dataBaseQuey.setIsFeedback(_id, 1);
			Intent intent = new Intent(view.getContext(), FeedbackActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("feedback_url", arrayList.get((int)id).get("feedback_url"));
			intent.putExtras(bundle);
			startActivity(intent);		
		}
		else {
			Toast.makeText(this, "已填寫過回饋表單", Toast.LENGTH_SHORT).show();
		}
		

	}

	
}

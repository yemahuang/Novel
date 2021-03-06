package com.hwm.novelmz;


import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;


public class BookShelfActivity extends BaseActivity {
    private GridView bookShelf;
    private int[] data = {
			R.drawable.shuihu,R.drawable.sanguo,
			R.drawable.xiyou,R.drawable.honglou};
	private String[] bookName= new String[]{
			"shuihu","sanguo","xiyou","honglou"
	};
    private String[] name={
    		"水浒传","三国演义","西游记","红楼梦"
    };
	ImageView imageView1;
	BookPageFactory pagefactory;
	Canvas mCurPageCanvas;
	SharedPreferences ferences ;
    private GridView gv;  
    private SlidingDrawer sd;  
    private Button iv;  
    private List<ResolveInfo> apps;  
    

	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        bookShelf = (GridView) findViewById(R.id.bookShelf);
        ShlefAdapter adapter=new ShlefAdapter();
        bookShelf.setAdapter(adapter);
        bookShelf.setOnItemClickListener(new OnItemClickListener() {

		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2>=data.length){
					
				}else{
					Intent intent1 = new Intent();			
					intent1.setClass(BookShelfActivity.this, BookpageActivity.class);
					int g=0;
					ferences = getSharedPreferences("memory",0);
					g=ferences.getInt("note"+arg2, g);
					Bundle bl = new Bundle();
					//				bl.putString("path", p[arg2]);
					//				bl.putString("imageUrl", u[arg2]);
					bl.putString("fname",bookName[arg2]);
					bl.putInt("sha", g);
					bl.putInt("no", arg2);
					System.out.println("gggg"+g);
					//				bl.putString("fpath", fp);				
					intent1.putExtras(bl);
					startActivityForResult(intent1,1);
				}
			}
		});
        loadApps();  
        gv = (GridView) findViewById(R.id.allApps);  
        sd = (SlidingDrawer) findViewById(R.id.sliding);  
        iv = (Button) findViewById(R.id.imageViewIcon);  
        gv.setAdapter(new GridAdapter());  
        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()// 开抽屉  
        {  
            
            public void onDrawerOpened() {  
            	iv.setText("返回");
                iv.setBackgroundResource(R.drawable.btn_local);// 响应开抽屉事件  
                                                                // ，把图片设为向下的  
            }  
        });  
        sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {  
            
            public void onDrawerClosed() {  
            	iv.setText("本地");
                iv.setBackgroundResource(R.drawable.btn_local);// 响应关抽屉事件  
            }  
        });  
        menu();
        Button rightBtn = (Button)findViewById(R.id.btn_rightTop);
        rightBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent();			
				intent1.setClass(BookShelfActivity.this, MainActivity.class);
				startActivity(intent1);			
				}
		});
	  
        

    }
    public void menu(){
        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
        
//		  Set from XML, possible to programmatically set        
//        float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics());
//        menu.setSatelliteDistance((int) distance);
//        menu.setExpandDuration(500);
//        menu.setCloseItemsOnClick(false);
//        menu.setTotalSpacingDegree(60);
        
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(6, R.drawable.ic_1));
        items.add(new SatelliteMenuItem(5, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
        items.add(new SatelliteMenuItem(1, R.drawable.menu_ic3));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);        
        
        menu.setOnItemClickedListener(new com.hwm.novelmz.SatelliteMenu.SateliteClickedListener() {
			
			public void eventOccured(int id) {
				switch (id) { 
				case 1:
					Intent it_1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivity(it_1);
					break;
				case 2: 					
					Intent it_2 = new Intent(Intent.ACTION_DIAL);
					startActivity(it_2);
					break; 
				case 3:
					Intent it_3 = new Intent(BookShelfActivity.this,MyLocationOver.class);
					startActivity(it_3);
					break;
				case 4:
					Intent it_5 = new Intent(BookShelfActivity.this,MainActivity.class);    
					startActivity(it_5); 
					break; 
					
				case 5: 			        
					Intent it_4 =new Intent();
					it_4.setClass(BookShelfActivity.this, Transition3d.class);										
					startActivity(it_4);
					break;
				case 6:
					Uri uri_6 = Uri.parse("http://www.baidu.com");
					Intent it  = new Intent(Intent.ACTION_VIEW,uri_6);
					startActivity(it);
					
				} 
				Log.i("sat", "Clicked on " + id);
			}
		});
    }

    
    
    class ShlefAdapter extends BaseAdapter{

	
		public int getCount() {
			// TODO Auto-generated method stub
			return data.length+5;
		}
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		
		public View getView(int position, View contentView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			contentView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.item1, null);
			
			TextView view=(TextView) contentView.findViewById(R.id.imageView1);
			if(data.length>position){
				if(position<name.length){
				   view.setText(name[position]);
				}
				view.setBackgroundResource(data[position]);
			}else{
				view.setBackgroundResource(data[0]);
				view.setClickable(false);
				view.setVisibility(View.INVISIBLE);
			}
			return contentView;
		}
    	
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        int result = data.getExtras().getInt("result");
//        //得到新Activity 关闭后返回的数据
//        System.out.println("result"+result);
//    
//    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("你确定退出吗？")
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							})
					.setNegativeButton("返回",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
    
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AlarmReceiver.sendGetAdMessage(BookShelfActivity.this);
	}


	private void loadApps() {  
        Intent intent = new Intent(Intent.ACTION_MAIN, null);  
        intent.addCategory(Intent.CATEGORY_LAUNCHER);  
  
        apps = getPackageManager().queryIntentActivities(intent, 0);  
    }  
  
    public class GridAdapter extends BaseAdapter {  
        public GridAdapter() {  
  
        }  
  
        public int getCount() {  
            // TODO Auto-generated method stub  
            return apps.size();  
        }  
  
        public Object getItem(int position) {  
            // TODO Auto-generated method stub  
            return apps.get(position);  
        }  
  
        public long getItemId(int position) {  
            // TODO Auto-generated method stub  
            return position;  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            // TODO Auto-generated method stub  
            ImageView imageView = null;  
            if (convertView == null) {  
                imageView = new ImageView(BookShelfActivity.this);  
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);  
                imageView.setLayoutParams(new GridView.LayoutParams(50, 50));  
            } else {  
                imageView = (ImageView) convertView;  
            }  
  
            ResolveInfo ri = apps.get(position);  
            imageView.setImageDrawable(ri.activityInfo  
                    .loadIcon(getPackageManager()));  
  
            return imageView;  
        }  
  
    }  

}
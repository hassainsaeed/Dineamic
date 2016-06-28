package homePage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.hmkcode.android.sign.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//this class is to associate an a different icon to every row of the
//Listview. The constructor takes in the map that maps the  to the icon in the main activity
public class NavigationAdapter extends BaseAdapter {
    private Activity activity;
    //add a Context variable if I want to add this in a fragment
    LinkedHashMap <String, Integer > map;
	List<String> titles;
	List<Integer> icons;

   //for the main activity
	public NavigationAdapter(Activity activity,LinkedHashMap <String, Integer > listarry) {  
       super();  
       this.activity = activity;  
       this.map=listarry;
       titles = new ArrayList<String>( map.keySet());
       icons = new ArrayList<Integer>(map.values());
       }
	 
   @Override
   public Object getItem(int position) {       
       return titles.get(position);
   }   
    public int getCount() {  
      // TODO Auto-generated method stub  
        return titles.size();  
    }    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public static class Fila  
    {  
    		TextView title;
    		ImageView icon;
    }  
   public View getView(int position, View convertView, ViewGroup parent) {  
      // TODO Auto-generated method stub  
	   Fila view;  
       LayoutInflater inflator = activity.getLayoutInflater();  
      if(convertView==null)  
       {  
           view = new Fila();
           convertView = inflator.inflate(R.layout.home_list_view_row_layout, null);
           //
           view.title = (TextView) convertView.findViewById(R.id.title_item);
           //set the custom textview to the 
           view.title.setText(titles.get(position));           
           //icon
           view.icon = (ImageView) convertView.findViewById(R.id.icon);
           //set the image of the icon
           view.icon.setImageResource(icons.get(position));           
           convertView.setTag(view);  
        }  
        else  
        {  
           view = (Fila) convertView.getTag();  
        }  
        return convertView;  
    }
}

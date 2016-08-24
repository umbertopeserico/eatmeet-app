package com.example.eatmeet.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eatmeet.EatMeetApp;
import com.example.eatmeet.R;
import com.example.eatmeet.activities.EventActivity;
import com.example.eatmeet.backendstatuses.BackendStatusListener;
import com.example.eatmeet.backendstatuses.BackendStatusManager;
import com.example.eatmeet.dao.interfaces.EventDAO;
import com.example.eatmeet.entities.Event;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by sofia on 15/06/2016.
 */
public class EventsAdapter extends ArrayAdapter {

    private List<Event> mItems;
    private Context mContext;
    private int mListRowLayout;

    public EventsAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        mItems = objects;
        mContext = context;
        mListRowLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mListRowLayout, null);

        final Event event = mItems.get(position);

        Log.d("EVENTS: ", "Add element to listview "+event.toString());

        /*
        TextView text = (TextView) convertView.findViewById(R.id.textViewListItem);
        text.setText(event.getTitle());
        */
        final ImageView eventImage = (ImageView) convertView.findViewById(R.id.eventsImage);
        /*new Images(){
            @Override public void onPostExecute(Bitmap result){
                eventsImage.setImageBitmap(result);
            }
        }.execute((String) event.getUrlImageMedium());*/

        EventDAO eventDAO = EatMeetApp.getDaoFactory().getEventDAO();
        
        String tmpFileName = "events-list_" + event.getPhotos().get(0).getImageMedium().substring(event.getPhotos().get(0).getImageMedium().lastIndexOf("/")+1);

        final File file = new File(this.getContext().getCacheDir(), tmpFileName);
        if(!file.exists()) {
            System.out.println("Cache non esiste");
            BackendStatusManager imageStatusManager = new BackendStatusManager();
            imageStatusManager.setBackendStatusListener(new BackendStatusListener() {
                @Override
                public void onSuccess(Object response, Integer code) {
                    System.out.println("Immagine scaricata");
                    eventImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }

                @Override
                public void onFailure(Object response, Integer code) {
                    System.out.println("Immagine NON scaricata");
                }
            });
            eventDAO.getImage(event.getPhotos().get(0).getImageMedium(), tmpFileName, imageStatusManager, this.getContext().getCacheDir());
        }
        else
        {
            System.out.println("Cache esiste");
            if(!file.isDirectory()) {
                eventImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
        }

        TextView eventsTitle = (TextView) convertView.findViewById(R.id.eventsTitle);
        eventsTitle.setText(event.getTitle());

        TextView eventsDate = (TextView) convertView.findViewById(R.id.eventsDate);
        Date scheduleDate = (Date) event.getSchedule();
        String scheduleString="il ";
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(scheduleDate);   // assigns calendar to given date
        scheduleString+=calendar.get(Calendar.DAY_OF_MONTH) + "/";
        scheduleString+=calendar.get(Calendar.MONTH) + "/";
        scheduleString+=calendar.get(Calendar.YEAR) + " alle ";
        int hrs = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        int mnts = calendar.get(Calendar.MINUTE); // gets hour in 24h format
        String hrsMnts = String.format("%02d:%02d", hrs, mnts);
        scheduleString += hrsMnts;
        eventsDate.setText(scheduleString);

        TextView eventsPrice = (TextView) convertView.findViewById(R.id.eventsPrice);
        eventsPrice.setText(event.getActualPrice() + "€");

        TextView eventsParticipants = (TextView) convertView.findViewById(R.id.eventsParticipants);
        eventsParticipants.setText(event.getParticipantsCount() + " partecipanti");

        final LinearLayout eventLayout = (LinearLayout) convertView.findViewById(R.id.list_item_events);
        eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, EventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", event.getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}

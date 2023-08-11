package com.secrettalk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    //RecyclerView.Adapter<RecyclerView.ViewHolder>
    //ChatAdapter.MyViewHolder
    private ArrayList<ChatData> myDataList = null;
    private String nick;

    public ChatAdapter(ArrayList<ChatData> dataList, String nick)
    {
                this.myDataList = dataList;
                this.nick = nick;
    }

    public ChatAdapter(ArrayList<ChatData> dataList, Context context, String nick)
    {
        this.myDataList = dataList;
        this.nick = nick;
    }

    private OnLongItemClickListener mListener = null;

    public interface OnLongItemClickListener{
        void onLongItemClick(View v, int pos);
    }

    public void setOnLongItemClickListener(OnLongItemClickListener listener){
        this.mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View v){
            super(v);

            v.setClickable(true);
            v.setEnabled(true);

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onLongItemClick(v,position);
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == Code.ViewType.LEFT_CONTENT)
        {
            view = inflater.inflate(R.layout.chat_left_item, parent, false);
            return new LeftViewHolder(view);
        }
        else if(viewType == Code.ViewType.RIGHT_CONTENT)
        {
            view = inflater.inflate(R.layout.chat_right_item, parent, false);
            return new RightViewHolder(view);
        }
        else if(viewType == Code.ViewType.LEFT_IAMGE){
            view = inflater.inflate(R.layout.chat_left_image, parent, false);
            return new LeftImageHolder(view);
        }
        else if(viewType == Code.ViewType.RIGHT_IMAGE) {
            view = inflater.inflate(R.layout.chat_right_image, parent, false);
            return new RightImageHolder(view);
        }
        else {
            view = inflater.inflate(R.layout.chat_center_item, parent, false);
            return new CenterViewHolder(view);
        }
    }// 뷰타입에 따라 각자의 레이아웃, 함수 매칭

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position)
    {
        ChatData chat = myDataList.get(position);

        if(viewHolder instanceof CenterViewHolder)
        {
            ((CenterViewHolder) viewHolder).content.setText(myDataList.get(position).getContent());
        }
        else if(viewHolder instanceof LeftViewHolder)
        {
            ((LeftViewHolder) viewHolder).name.setText(myDataList.get(position).getDept()+"/"+myDataList.get(position).getSid()+"/"+myDataList.get(position).getName());
            ((LeftViewHolder) viewHolder).content.setText(myDataList.get(position).getContent());
            ((LeftViewHolder) viewHolder).sendTimeText.setText(myDataList.get(position).getSendTime());
        }
        else if (viewHolder instanceof RightViewHolder)
        {
            ((RightViewHolder) viewHolder).content.setText(myDataList.get(position).getContent());
            ((RightViewHolder) viewHolder).sendTimeText.setText(myDataList.get(position).getSendTime());
        }
        else if(viewHolder instanceof  LeftImageHolder)
        {
            ((LeftImageHolder) viewHolder).name.setText(myDataList.get(position).getDept()+"/"+myDataList.get(position).getSid()+"/"+myDataList.get(position).getName());
            ((LeftImageHolder) viewHolder).image_View.setImageBitmap(StringToBitmap(myDataList.get(position).getImg()));
            ((LeftImageHolder) viewHolder).sendTimeText.setText(myDataList.get(position).getSendTime());
        }
        else if(viewHolder instanceof  RightImageHolder)
        {
            ((RightImageHolder) viewHolder).image_View.setImageBitmap(StringToBitmap(myDataList.get(position).getImg()));
            ((RightImageHolder) viewHolder).sendTimeText.setText(myDataList.get(position).getSendTime());
        }

    }// 매칭된 뷰타입들에게 값을 넣어줌
    
    @Override
    public int getItemCount()
    {
        return myDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return myDataList.get(position).getViewType();
    }

    public ChatData getChat(int position){
        return myDataList != null ? myDataList.get(position) : null;
    }

    public class CenterViewHolder extends ChatAdapter.MyViewHolder{
        TextView content;

        CenterViewHolder(View itemView)
        {
            super(itemView);

            content = itemView.findViewById(R.id.content_text);
        }
    } // 센터뷰 ( 채팅방 입/퇴장 기능, 미구현 )

    public class LeftViewHolder extends ChatAdapter.MyViewHolder{
        TextView content;
        TextView name;
        TextView sendTimeText;

        LeftViewHolder(View itemView)
        {
            super(itemView);

            content = itemView.findViewById(R.id.msg_text);
            name = itemView.findViewById(R.id.name_text);
            sendTimeText = itemView.findViewById(R.id.send_time_text);

        }
    } // 왼쪽 텍스트, 받은 메세지

    public class RightViewHolder extends ChatAdapter.MyViewHolder{
        TextView content;
        TextView sendTimeText;

        RightViewHolder(View itemView)
        {
            super(itemView);

            content = itemView.findViewById(R.id.msg_text);
            sendTimeText = itemView.findViewById(R.id.send_time_text);
        }
    } // 오른쪽 텍스트, 보낸 메세지

    public class LeftImageHolder extends ChatAdapter.MyViewHolder{
        ImageView image_View;
        TextView name;
        TextView sendTimeText;

        LeftImageHolder(View itemView)
        {
            super(itemView);

            this.image_View = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name_text);
            sendTimeText = itemView.findViewById(R.id.send_time_text);

        }
    } // 왼쪽 이미지, 받은 사진

    public class RightImageHolder extends ChatAdapter.MyViewHolder{
        ImageView image_View;
        TextView sendTimeText;

        RightImageHolder(View itemView)
        {
            super(itemView);

            this.image_View = itemView.findViewById(R.id.image_view);
            sendTimeText = itemView.findViewById(R.id.send_time_text);
        }
    } // 오른쪽 이미지, 보낸 사진

    // 이미지 스트링 -> 비트맵 변경 함수
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);// String 화 된 이미지를  base64방식으로 인코딩하여 byte배열을 만듬
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);//byte배열을 bitmapfactory 메소드를 이용하여 비트맵으로 바꿔준다.
            return bitmap;//만들어진 bitmap을 return
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
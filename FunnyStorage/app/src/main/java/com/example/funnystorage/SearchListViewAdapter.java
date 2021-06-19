package com.example.funnystorage;

/*
public class SearchListViewAdapter extends MainAdapter {
    Context context;
    ArrayList<Contents> contents;
    List<Contents> searchList= null;
    LayoutInflater inflater;

    public SearchListViewAdapter(Context context, List<Contents>searchList){
     this.context=context;
     this.searchList=searchList;
     inflater = LayoutInflater.from(context);
     this.contents.addAll(searchList);
     this.contents=new ArrayList<Contents>();
    }

    public class ViewHolder{
        public TextView item_video_title;
        public TextView url_video;
        public ImageView item_video_image=null,thumbnailView;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent,null);
        return new ViewHolder(v);
    }
    @Override
    public int getCount(){
        return searchList.size();
    }

    @Override
    @Override
    public Contents getItem(int position) {
        return searchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final Contents contents = searchList.get(position);


        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_listview, null);
            // Locate the TextViews in listview_item.xml
            holder.item_video_title = (TextView) view.findViewById(R.id.item_video_title);
            holder.item_video_image = (ImageView) view.findViewById(R.id.item_video_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.item_video_title.setText(contents.name);
        Glide.with(context).load(contents.icon).into(holder.item_video_image);

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, BrewingActivity.class);

                context.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchList.clear();
        if (charText.length() == 0) {
            searchList.addAll(contents);
        } else {
            for (Contents contents : contents) {
                String name = context.getResources().getString(contents.name);
                if (name.toLowerCase().contains(charText)) {
                    searchList.add(contents);
                }
            }
        }
        notifyDataSetChanged();
    }
   }
*/


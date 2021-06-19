package com.example.funnystorage;

/*

public class SearchActivity {

    private SearchListViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        init();

        //검색창
        editsearch.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0){

                String text = editsearch.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
// TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
// TODO Auto-generated method stub
            }
        });
    }
    public void init() {
        ButterKnife.bind(this);
        this.setAdlibKey(FunnyVideo.ADLIB_API_KEY);
        this.setAdsContainer(R.id.ads);

        SearchList searchList = new SearchList(this);
        adapter = new SearchListViewAdapter(this, SearchList);
        listView.setAdapter(adapter);
    }
    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.editsearch)
    EditText editsearch;
    @Bind(R.id.Layout_Internet)
    RelativeLayout internetLayout;





}
*/
package com.example.fragmentbestpractice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsTitleFragment extends Fragment {
  private static final String TAG = "NewsTitleFragment";
  private boolean isTwoPane;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.news_title_frag, container, false);
    Log.d(TAG, "onCreateView");
    RecyclerView newsTitleRecyclerView = (RecyclerView)view.findViewById(R.id.news_title_recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    newsTitleRecyclerView.setLayoutManager(layoutManager);
    NewsAdapter adapter = new NewsAdapter(getNews());
    Log.d(TAG, "onCreateView: hello");
    newsTitleRecyclerView.setAdapter(adapter);
    return view;
  }

  private List<News> getNews(){
    Log.d(TAG, "getNews");
    List<News>newsList=new ArrayList<>();
    for(int i=0;i<=50;i++){
      News news =new News();
      news.setTitle("This is title "+i);
      news.setContent(getRandomLengthContent("This is news content "+i+"."));
      newsList.add(news);
    }
    Log.d(TAG, "getNews: "+newsList.size());
    return newsList;
  }

  private String getRandomLengthContent(String content){
    Log.d(TAG, "getRandomLengthContent");
    Random random =new Random();
    int length = random.nextInt(20)+1;
    StringBuilder stringBuilder = new StringBuilder();
    for(int i=0;i<length;i++){
      stringBuilder.append(content);
    }
    return stringBuilder.toString();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d(TAG, "onActivityCreated");
    if (getActivity().findViewById(R.id.news_content_layout) != null) {
      isTwoPane = true;
    } else {
      isTwoPane = false;
    }
  }

  class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> mNews;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Log.d(TAG, "onCreateViewHolder");
      View view =
          LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
      final ViewHolder viewHolder = new ViewHolder(view);
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          News news = mNews.get(viewHolder.getAbsoluteAdapterPosition());
          if (isTwoPane) {
            NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
                .findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(), news.getContent());
          } else {
            NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
          }
        }
      });
      return viewHolder;
    }

    public NewsAdapter(List<News> news) {
      mNews = news;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Log.d(TAG, "onBindViewHolder");
      News news = mNews.get(position);
      holder.newsTitleText.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
      Log.d(TAG, "getItemCount");
      return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
      TextView newsTitleText;

      public ViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.d(TAG, "ViewHolder");
        newsTitleText = (TextView) itemView.findViewById(R.id.news_title);
      }
    }
  }
}

package com.flyworkspace.godshome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;

import com.flyworkspace.godshome.R;
import com.flyworkspace.godshome.bean.Book;
import com.flyworkspace.godshome.util.IniReader;
import com.flyworkspace.godshome.util.PublicFunction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookListPageActivity extends BaseActivity {
    private ListView mLvBook;
    private List<Book> mBooksList = new ArrayList<Book>();
    private static boolean isExit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_page);
        mLvBook = (ListView) findViewById(R.id.listview_books);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] assetsList = null;
        try {
            assetsList = getAssets().list("books");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBooksList.clear();
        if (assetsList != null && assetsList.length > 0) {
            for (String path : assetsList) {
                Book book = new Book();
                book.setPath(path);
                InputStream inputStream = PublicFunction.getInputStreamFromAsset(BookListPageActivity.this, "books/" + path + "/read.ini");
                IniReader reader = null;
                try {
                    reader = new IniReader(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (reader != null){
                    book.setName(reader.getValue("info","name"));
                    book.setPath("books/" + path);
                    mBooksList.add(book);
                }
            }
        }
        BooksListAdapter adapter = new BooksListAdapter();
        mLvBook.setAdapter(adapter);
        mLvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookListPageActivity.this, MainActivity.class);
                intent.putExtra("PATH", mBooksList.get(position).getPath());
                startActivity(intent);
            }
        });
    }

    private class BooksListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public BooksListAdapter() {
            this.mInflater = LayoutInflater.from(BookListPageActivity.this);
        }

        @Override
        public int getCount() {
            return mBooksList.size();
        }

        @Override
        public Object getItem(int position) {
            return mBooksList;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.books_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Book book = mBooksList.get(position);
            viewHolder.tvName.setText(book.getName());
            return convertView;
        }
    }

    private final class ViewHolder {
        private TextView tvName;
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent();
            intent.setAction("ExitApp");
            sendBroadcast(intent);
            finish();
        }
    }
}

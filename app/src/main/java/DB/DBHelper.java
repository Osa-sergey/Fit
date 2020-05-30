package DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutionException;

import Pojo.NewsItem;

/**
 * Класс для работы с запросами
 * Использует паттерн 'одиночка'
 * Для работы необходимо вызвать getInstance в MainActivity::create для ининциализации
 * Дальше вызывать статические методы
 */
public class DBHelper {

    private static DBHelper instance;
    private static String dbName;
    private AppDatabase db;

    /**
     * Скрытый конструктор для 'одиночка'
     * @param context контекст активности
     * @param dbName имя базы к которой подключаемся, совпадает с конкатинацией имкни и фамилии пользователя
     **/
    private DBHelper(Context context, String dbName){
        DBHelper.dbName = dbName;
        db = Room.databaseBuilder(context,AppDatabase.class,dbName).build();
    }

    /**
     * Вызывать в MainActivity::create
     * @param context контекст активности
     * @param dbName имя базы к которой подключаемся, совпадает с конкатинацией имкни и фамилии пользователя
     */
    public static void getInstance(Context context, String dbName){
        if(instance == null || !dbName.equals(DBHelper.dbName)){
            instance = new DBHelper(context,dbName);
        }
    }

    //NewsDao

    public static void insertNews (final List<NewsItem> news){
        AsyncTask<List<NewsItem>,Void,Void> task = new AsyncTask<List<NewsItem>, Void, Void>() {
            @Override
            protected Void doInBackground(List<NewsItem>... lists) {
                instance.db.newsDao().insertNews(lists[0]);
                return null;
            }
        };
        task.execute(news);
    }

    public static List<NewsItem> getNews (){
        AsyncTask<Void,Void,List<NewsItem>> task = new AsyncTask<Void, Void, List<NewsItem>>() {
            @Override
            protected List<NewsItem> doInBackground(Void... voids) {
                return instance.db.newsDao().getNews();
            }
        };
        task.execute();
        try{
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } return null;
    }
}

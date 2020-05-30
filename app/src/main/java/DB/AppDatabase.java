package DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import DB.Dao.NewsDao;
import Pojo.NewsItem;

/**
 *  Класс со списком всех DB.Dao
 *  нужно создать экземпляр с помощью конструктора Room
 *  Использутся для обращения к БД
 */
@Database(entities = {NewsItem.class },version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

}

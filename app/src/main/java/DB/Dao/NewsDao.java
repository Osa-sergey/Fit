package DB.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Pojo.NewsItem;

@Dao
public interface NewsDao {

    /**
     * Метод для вставки новостей
     * @param news список новостей
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(List<NewsItem> news);

    /**
     * Меьод для вывода всех новостей отсортированных по id по убыванию
     * @return List с новостями
     */
    @Query("SELECT * FROM news ORDER BY id DESC")
    List<NewsItem> getNews();
}

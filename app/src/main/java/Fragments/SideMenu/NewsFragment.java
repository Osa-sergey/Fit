package Fragments.SideMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.serg.fit.R;

import java.util.Arrays;

import Adapters.NewsAdapter;
import Pojo.NewsItem;
import Utils.LinearItemDecoration;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new LinearItemDecoration(25));
        loadItems();
        return view;
    }

    private void loadItems() {
       adapter.addItems(Arrays.asList(new NewsItem(1,"Добро пожаловать в приложение BroFit","Спасибо, что скачали приложение BroFit! Теперь у вас есть возможность заниматься с тренером удаленно. Начните использование приложения с заполнения профиля и составления целей. После этого Вы можете найти тренера, который будет помогать Вам достичь их. Удачи и хороших тренировок!","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTCPEujLaBIaJmFQfGt-iToq6JGsIomVsmyaqPAaOKLoijYWRru&usqp=CAU"),
               new NewsItem(2,"Как использовать приложение?","Если у Вас возникли вопросы по использованию приложения, зайдите в раздел \"Помощь\". Там вы найдете ответы на самые важнае вопросы. Если там вы не нашли ответа на свой вопрос, задайте его в разделе \"Контакты\".","https://i.pinimg.com/originals/5f/2f/98/5f2f983b1d838ee26be6b93a38fa378c.jpg"),
               new NewsItem(3,"База упражнений в приложении BroFit","Вы можете посмотреть нашу базу упражнений в разделе \"Упражнения\". Поиск можно осуществлять по ключевым словам или выбрав группу мышц.","https://i.pinimg.com/originals/f5/22/d7/f522d75285b14211c34b024f061c63c3.jpg"),
               new NewsItem(4,"Зачем составлять цели?","Формулировка целей очень важна для достижения результата. Поэтому мы создали вкладку \"Цели \" в нижнем меню, чтобы вы могли поставить их для себя, определить срок их достижения и достигнуть с помощью тренера. Цель позволяет определить, какая квалификация должна быть у тренера, чтобы он смог Вас направлять.","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQgQ4tipHHCYSuA5RSbfP8gWP8KG8M1eyPfxhykp2x0h9YBE-YX&usqp=CAU"),
               new NewsItem(5,"Использование чатов","В нашем приложении общение с тренерами происходит во вкладке \"Чаты\". В них есть много дополнительных возможностей. Скорее приступайте к общению с тренером и пробуйте использовать наши \"Чаты\".","https://image.freepik.com/free-vector/_82574-8504.jpg")));
    }
}

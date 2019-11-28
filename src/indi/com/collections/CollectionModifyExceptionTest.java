package indi.com.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {

    public static void main(String[] args) {
//        ArrayList<User> users = new ArrayList<>();
        Collection users = new CopyOnWriteArrayList<>();
        users.add(new User("张三",1));
        users.add(new User("李四",2));
        users.add(new User("王五",3));

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if("张三".equals(user.getName())){
                users.remove(user);
            }
        }
        users.forEach(user->{
            System.out.println("==");
        });
    }
}

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tech.gaolinfeng.base.entity.User;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by gaolf on 16/9/20.
 */

public class MyBatisTest {

    private SqlSession session;

    @Before
    public void init() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
        System.out.println("<<<<<<<<<<<<<<<<<<init<<<<<<<<<<<<<<<<<<<");
    }

    @After
    public void destroy() {
        session.close();
    }

    @Test
    public void testInsert() throws IOException {
        //Create a new User object
//        Random random = new Random();
//        String name = "gaolf#" + random.nextInt();
//        User user = new User(name, "18611900120", "gaolinfeng@zuoyebang.com", new Date(1993, 2, 19), User.MALE, "");
//        //Insert User data
//        session.insert("entity.User.insert", user);
//        System.out.println("testInsert succeed");
//        session.commit();
    }

    @Test
    public void testSelectAll() throws IOException {
        List<User> User = session.selectList("entity.User.selectAll");
        for(User user : User ){
            System.out.println(user);
        }
        System.out.println("testSelectAll succeed");
        session.commit();
    }

    @Test
    public void testSelectOne() throws IOException {
        User user = session.selectOne("entity.User.selectOne", 1);
        System.out.println(user);
        session.commit();
        System.out.println("testSelectOne succeed");
    }

}

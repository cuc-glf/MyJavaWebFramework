package tech.gaolinfeng.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by gaolf on 16/9/29.
 */
@Service
public class TokenService {


    private HashMap<String, Integer> tokenMap = new HashMap<>();

    /**
     * 根据用户token获取id, 如果token不存在或已经注销, 那么id就是-1
     * @param token
     * @return
     */
    public int getIdByToken(String token) {
        Integer id = tokenMap.get(token);
        if (id == null) {
            return -1;
        } else {
            return id;
        }
    }

    /**
     * 更新id对应的token
     * @param id
     * @param token
     */
    public void updateToken(int id, String token) {
        tokenMap.put(token, id);
    }

    public void clearToken(String token) {
        tokenMap.remove(token);
    }
}

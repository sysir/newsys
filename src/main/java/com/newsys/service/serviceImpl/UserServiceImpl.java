package com.newsys.service.serviceImpl;

import com.newsys.dao.UserMapper;
import com.newsys.dao.UserinfoMapper;
import com.newsys.pojo.Result;
import com.newsys.pojo.User;
import com.newsys.pojo.Userinfo;
import com.newsys.service.UserService;
import com.newsys.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public Result getusers(Integer page, Integer limit) {
        Integer start = (page - 1) * limit;
        List<Userinfo> list = userinfoMapper.selectUsers(start, limit);
        Integer count = userinfoMapper.getCount();
        Result result = new Result();
        result.setTotal(count);
        result.setStatus(0);
        result.setItem(list);
        return result;
    }

    @Transactional
    @Override
    public Result updateById(Integer uid, String field, String value) {
        Result result = new Result();
        System.out.println(uid);
        System.out.println(field);
        System.out.println(value);
        try {
            int i = userinfoMapper.updateById(uid, field, value);
            result.setMessage("操作成功!");
            result.setStatus(200);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //手动设置事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("系统繁忙,请刷新页面在进行尝试");
            result.setStatus(500);
            return result;
        }
    }

    @Override
    public Userinfo selectUserById(Integer uid) {
        Userinfo userInfo = userinfoMapper.selectByPrimaryKey(uid);
        return userInfo;
    }

    @Transactional
    @Override
    public Result delById(Integer uid) {
        Result result = new Result();
        try {
            int res = userinfoMapper.deleteByPrimaryKey(uid);
            result.setStatus(200);
            result.setMessage("删除成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("未知异常!");
            result.setStatus(500);
            return result;
        }
    }

    @Transactional
    @Override
    public Result updateUser(Integer userId, String userName, String userPhone, String userEmail) {
        Result result = new Result();
        try {
            int i = userinfoMapper.updateUesr(userId, userName, userPhone, userEmail);
            if (i > 0) {
                result.setStatus(200);
                result.setMessage("更新成功");
            } else {
                result.setStatus(500);
                result.setMessage("更新失败!");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("未知异常!");
            result.setStatus(500);
            return result;
        }
    }

    @Override
    public Result search(Integer uid, String un, String up, Integer stu) {
        Result result = new Result();
        List<Userinfo> userinfo = userinfoMapper.search(uid, un, up, stu);
        int size = userinfo.size();
        result.setStatus(0);
        result.setItem(userinfo);
        result.setTotal(size);
        result.setMessage("查询到" + size + "个用户");
        return result;
    }

    @Transactional
    @Override
    public Result delByIds(Integer[] ids) {
        Result result = new Result();
        try {
            for (int i = 0; i < ids.length; i++) {
                //进行删除操作
                int res = userinfoMapper.deleteByPrimaryKey(ids[i]);
            }
            result.setStatus(200);
            result.setMessage("删除成功");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("未知异常!");
            result.setStatus(500);
            return result;
        }
    }

    @Override
    public Result login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Object codeValue = request.getSession().getAttribute("code");
        if (code.equalsIgnoreCase(String.valueOf(codeValue))) {
            User user = userMapper.selectUser(username);
            String encryption = Md5Util.encryption(password, username);
            if (user == null || !user.getUserPassword().equals(encryption)) {
                result.setStatus(500);
                result.setMessage("用户或密码错误！");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRoleId());
                session.setAttribute("username", user.getUserinfo().getUserName());
                // 判断cookie是否有username，如果有代表登陆过
                Cookie[] cookies = request.getCookies();
                boolean flag = false;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) {
                            flag = true;
                        }
                    }
                }else {
                    Cookie cookie = new Cookie("username", username);
                    cookie.setMaxAge(3*60*60); // 设置一分钟有效
                    response.addCookie(cookie);
                }



                result.setStatus(200);
                result.setMessage("登录成功!");
            }

        } else {
            result.setStatus(500);
            result.setMessage("验证码错误！");
        }
        return result;
    }

    @Transactional
    @Override
    public Result addUser(String userPhone, String password, String telCode, String codeValue) {
        Result result = new Result();
        Userinfo checkUser = userinfoMapper.selectByPhone(userPhone);
        if (telCode.equals(codeValue)) {
            if (checkUser != null) {
                result.setStatus(500);
                result.setMessage("该账户已被注册！");
            } else {
                try {
                    Userinfo userinfo = new Userinfo();
                    userinfo.setUserSex((byte) 1);
                    userinfo.setUserPhone(userPhone);
                    userinfo.setUserStatus(1);
                    userinfo.setUserRegtime(new Date());
                    userinfoMapper.insert(userinfo);
                    Integer lastUid = userinfoMapper.getLastUid();
                    User user = new User();
                    user.setUserId(lastUid);
                    user.setRoleId(1);
                    String encryption = Md5Util.encryption(password, userPhone);
                    user.setUserPassword(encryption);
                    userMapper.insert(user);
                    result.setMessage("注册成功!");
                    result.setStatus(200);
                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    result.setMessage("未知异常!");
                    result.setStatus(500);
                }
            }
        } else {
            result.setStatus(500);
            result.setMessage("验证码错误!");
        }
        return result;
    }
}

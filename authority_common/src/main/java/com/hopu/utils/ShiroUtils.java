package com.hopu.utils;

import com.hopu.domain.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

public class ShiroUtils {

	public static User encPass(User user){
		if (StringUtils.isEmpty(user.getSalt())) {
			user.setSalt(UUIDUtils.getID());
		}
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName() + user.getSalt());
		/*public interface ConstantUtils {
            public static final String MD5 = "md5";
            public static final int HASHITERATIONS = 1024;}*/
                        /*参数分别是:  加密算法、           密码、              盐值、            加密次数*/
        Object obj = new SimpleHash(ConstantUtils.MD5, user.getPassword(), credentialsSalt, ConstantUtils.HASHITERATIONS);
        user.setPassword(obj.toString());
        return user;
	}
    /**new SimpleHash()  参数分别是：加密算法、密码、盐值、加密次数*/
	public static void main(String[] args) {
		String salt = "nnnnnnnnnnnnnnnnnnnn";
        String username = "nnn";
        String hashAlgorithmName = "MD5";
        String password = "nnn";
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(username + salt);
        Object obj = new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);
        System.out.println(obj);
        System.out.println("salt："+ salt);
    }
}

package com.example.customglide.resource;

import com.example.customglide.Tool;

/**
 * key  -----> Value(即Bitmap的封装)
 */
public class Key {

    /**
     *  key是合格的字符串：是唯一的， 是加密后的的
     *  符合这种格式的字符串即可:ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668
     *  而不能是普通的http格式的url
     */
    private String key;


    public Key(String path) {
        this.key = Tool.getSHA256StrJava(path);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

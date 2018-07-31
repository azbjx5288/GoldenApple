package com.goldenapple.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 13.5.获得发件时，当前用户基本信息接口
 */
public class GetUserLetterInfoBean{


    /**
     * child : [{"id":15370,"is_agent":1,"username":"river002"}]
     * is_top_agent : false
     * parent : []
     * user_type : {"2":"所有下级","3":"单一下级"}
     */

    private boolean is_top_agent;
    private UserTypeBean user_type;
    private List<ChildBean> child;
    private List<?> parent;

    public boolean isIs_top_agent() {
        return is_top_agent;
    }

    public void setIs_top_agent(boolean is_top_agent) {
        this.is_top_agent = is_top_agent;
    }

    public UserTypeBean getUser_type() {
        return user_type;
    }

    public void setUser_type(UserTypeBean user_type) {
        this.user_type = user_type;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public List<?> getParent() {
        return parent;
    }

    public void setParent(List<?> parent) {
        this.parent = parent;
    }

    public static class UserTypeBean {
        /**
         * 2 : 所有下级
         * 3 : 单一下级
         */

        @SerializedName("2")
        private String _$2;
        @SerializedName("3")
        private String _$3;

        public String get_$2() {
            return _$2;
        }

        public void set_$2(String _$2) {
            this._$2 = _$2;
        }

        public String get_$3() {
            return _$3;
        }

        public void set_$3(String _$3) {
            this._$3 = _$3;
        }
    }

    public static class ChildBean {
        /**
         * id : 15370
         * is_agent : 1
         * username : river002
         */

        private int id;
        private int is_agent;
        private String username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_agent() {
            return is_agent;
        }

        public void setIs_agent(int is_agent) {
            this.is_agent = is_agent;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}

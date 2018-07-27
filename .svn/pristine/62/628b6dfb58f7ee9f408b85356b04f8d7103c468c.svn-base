package com.goldenasia.lottery.data;

import java.util.List;

/**
 * Created by Gan on 2017/11/17.
 * 4.5.1	发件箱
 */
public class SendBoxResponse {

    /**
     * list : [{"msg_id":"17","title":"123","create_time":"2017-11-13 14:17:15","from_user_id":"49220","type":"1","targets":[{"user_id":"30018","username":"QQ55888","nick_name":"小刘"}]}]
     * count : 3
     */

    private String count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * msg_id : 17
         * title : 123
         * create_time : 2017-11-13 14:17:15
         * from_user_id : 49220
         * type : 1
         * targets : [{"user_id":"30018","username":"QQ55888","nick_name":"小刘"}]
         */

        private String msg_id;
        private String title;
        private String create_time;
        private String from_user_id;
        private String type;
        private List<TargetsBean> targets;

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFrom_user_id() {
            return from_user_id;
        }

        public void setFrom_user_id(String from_user_id) {
            this.from_user_id = from_user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<TargetsBean> getTargets() {
            return targets;
        }

        public void setTargets(List<TargetsBean> targets) {
            this.targets = targets;
        }

        public static class TargetsBean {
            /**
             * user_id : 30018
             * username : QQ55888
             * nick_name : 小刘
             */

            private String user_id;
            private String username;
            private String nick_name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }
        }
    }
}

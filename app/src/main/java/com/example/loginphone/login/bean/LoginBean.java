package com.example.loginphone.login.bean;

public class LoginBean {

    /**
     * validateCode : 6**0
     * telephone : 182*******08
     */

    private String validateCode;
    private String telephone;
    /**
     * data : {"cdk":null,"ect4":null,"id":"460ab1ee7ce9436a8e03a2d75a9e39ad","idcard":null,"name":null,"phone":"18236850308","schoolid":null,"sex":null,"username":null,"userpic":null}
     * flag : true
     * message : 登录成功
     */

    private DataBean data;
    private boolean flag;
    private String message;



    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * cdk : null
         * ect4 : null
         * id : 460ab1ee7ce9436a8e03a2d75a9e39ad
         * idcard : null
         * name : null
         * phone : 18236850308
         * schoolid : null
         * sex : null
         * username : null
         * userpic : null
         */

        private Object cdk;
        private Object ect4;
        private String id;
        private Object idcard;
        private Object name;
        private String phone;
        private Object schoolid;
        private Object sex;
        private Object username;
        private Object userpic;

        public Object getCdk() {
            return cdk;
        }

        public void setCdk(Object cdk) {
            this.cdk = cdk;
        }

        public Object getEct4() {
            return ect4;
        }

        public void setEct4(Object ect4) {
            this.ect4 = ect4;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getIdcard() {
            return idcard;
        }

        public void setIdcard(Object idcard) {
            this.idcard = idcard;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(Object schoolid) {
            this.schoolid = schoolid;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getUserpic() {
            return userpic;
        }

        public void setUserpic(Object userpic) {
            this.userpic = userpic;
        }
    }
}

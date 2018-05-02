package com.tianxiabuyi.kit;

public class Constant {


    public static String BASE_URL = "";

    public static final String APP_TYPE = "hospital";
    public static final String HOSPITAL = "hospital";


    public static final String HOSPITAL_KEY = "hospital";
    public static final String HOSPITAL_VALUE = "1059";
    public static final String APP_TYPE_KEY = "app_type";
    public static final String APP_TYPE_VALUE = "patient";
    public static final String TOKEN_KEY = "token";

    /**
     * 统计key
     */
    public static final String RAZOR_APP_KEY = "9ac9e67c5a01eac8817d09c79bf66abd";

    /**
     * 存储/跳转等Key
     */
    public static final String key_1 = "key_1";
    public static final String key_2 = "key_2";
    public static final String key_3 = "key_3";

    public static final String HXPASSWORD = "123456";

    public static final int VALUE_1 = 1;
    public static final int VALUE_2 = 2;

    // 正则表达式：验证手机号
//    public static final String REGEX_MOBILE = "^1\\d{10}$";//34587
    public static final String REGEX_MOBILE = "^1[34587][0-9]{9}$";//34587
    // 正则表达式：验证汉字
    public static final String REGEX_CHINESE = "^[\\u4e00-\\u9fa5]+$";
    // 正则表达式：验证用户名
    public static final String REGEX_USER_NAME = "(_|[a-zA-Z])\\w{3,19}$";
    // 正则表达式：验证密码
    public static final String REGEX_PASSWORD = "[^\\u4e00-\\u9fa5]{6,20}";
    // 正则表达式：验证医保号码
    public static final String REGEX_MEDICAL_NUMBER = "[\\dA-Za-z]{4,31}";
    // activity请求码
    public static final int REQUEST_CODE = 0;
    // activity结果码
    public static final int RESULT_CODE_ONE = 1;
    public static final int RESULT_CODE_TWO = 2;

    public static final String SELF_PAY = "自费";
    public static final String CITIZEN_CARD = "市民卡";
    public static final String ID_CARD = "身份证";

    /**
     * API
     */
    // 本地测试接口地址
    // public static final String BASE = "http://192.168.2.25:18088/";              // 本地
    // 远程测试接口地址
    // public static final String BASE = "http://demoapi.eeesys.com:18088/";          // 外网
    // 正式接口地址
    public static final String BASE = "http://api.eeesys.com:18088/";              // 正式

    public static final String VERSION = "v2/";

    public static final String IP = BASE + VERSION;


    // 本地测试接口地址
    // public static final String IP_C = "http://192.168.2.25/";
    // 远程测试接口地址
    // public static final String IP_C = "http://demowechat.eeesys.com/";
    // 正式接口地址
    public static final String IP_C = "http://wechat.eeesys.com/";

    // =========================================== 特殊 ==========================================
    // 我的消息
    public final static String NOTICE = BASE + "manage/survey_push/list_receive_notification.jsp";
    // ===========================================================================================

    // 检查版本
    public final static String CHECK_VERSION = "http://api.eeesys.com:18088/v2/app/update.jsp";
    public final static String REVOKE_TOKEN = IP + "token/revoke";

    // 新闻
    public static final String NEWS_LIST = IP + "news/list.jsp";
    public static final String NEWS_DETAIL = IP + "news/article.jsp";

    // 科室介绍
    public static final String DEPT_LIST = IP + "dept/depts.jsp";
    public static final String DEPT_DETAIL = IP + "dept/show.jsp";

    // 专家介绍
    public static final String EXPERT_LIST = IP + "expert/experts";
    public static final String EXPERT_DETAIL = IP + "expert/show";

    // 预约挂号
    public static final String RESERVE_DEPT = IP + "register/depts";
    public static final String RESERVE_EXPERT = IP + "register/experts";
    public static final String RESERVE_SCHEDULE = IP + "register/show";
    public static final String NUMBER_SOURCE = IP + "register/schedule";
    public static final String MAKE_AN_APPOINTMENT = IP + "register/create";

    // 检查查询
    public static final String EXAMINE = IP + "examine/show.jsp";
    public static final String EXAMINE_TIMELIST = IP + "examine/query.jsp";

    // 体检查询
    public static final String PHYSICAL = IP + "physical_check/query.jsp";
    public static final String PHYSICAL_DETAILS = IP + "physical_check/show.jsp";

    // 修改密码
    public static final String MODIFY_PWD = IP + "user/password";

    // 修改资料
    public static final String MODIFY_DATA = IP + "user/modify";
    // 绑定住院号
    public static final String BINDING_DATA = IP + "user/fill";

    // 智能导诊
    public static final String DIAGNOSE_LIST = "http://api.eeesys.com:18088/v2/smart_triage/symptom";
    public static final String DISEASE_LIST = "http://api.eeesys.com:18088/v2/smart_triage/disease";

    // 用药记录
    public static final String DRUG_RECORD = IP + "drug/show.jsp";
    public static final String DRUG_RECORD_TIMELIST = IP + "drug/query";
    public static final String DRUGADD_CREATE = IP + "notification/create";

    // 每日清单
    public static final String DAY_COST = IP + "pay/show.jsp";
    public static final String DAY_COST_TIEMLIST = IP + "pay/query.jsp";

    // 我的预约
    public static final String MY_APPOINTMENT = IP + "register/my";
    // 取消预约
    public static final String MY_APPOINTMENTCANCEL = IP + "register/cancel";

    // 就诊记录
    public static final String VISIT_RECORD = IP + "clinic/show.jsp";
    public static final String VISIT_RECORD_TIMELIST = IP + "clinic/query.jsp";
    // 注册
    public static final String REGISTER = IP + "user/create.jsp";
    // 登录
    public static final String LOGIN = IP + "user/login";
    // 修改
    public static final String MODIFY = IP + "user/modify.jsp";

    // 刷新token
    public static final String REFRESH_TOKEN = IP + "token/refresh.jsp";
    // 添加就诊人
    public static final String ADD_USER = IP + "user/profile";
    public static final String DELETE_USER = IP + "user/profile_del";
    public static final String MODIFY_USER = IP + "user/profile_mod";

    // 修改头像
    public static final String UPLOAD_AVATAR = "http://cloud.eeesys.com/pu/upload.php";
    public static final String BIND_AVATAR = IP + "user/avatar";

    // 医生排班
    public static final String DOCTOR_SCHEDULING_LIST = IP + "schedule/query";

    // 我的医生
    public static final String MY_DOCTOR_LIST = IP + "user/query";

    // 问答
    public static final String QUES_CATEGORY = IP + "quest/group";
    public static final String QUES_LIST = IP + "quest/quests";
    public static final String QUESTION_DETAIL = IP + "quest/show";
    public static final String ASK_QUESTION = IP + "quest/create";
    public static final String ASK_DOCTORS = IP + "user/query";
    public static final String REPLY_QUESTION = IP + "quest/reply";
    public static final String PRAISE = IP + "operate/create";
    public static final String CANCEL_PRAISE = IP + "operate/cancel";
    public static final String THUMB = "http://cloud.eeesys.com/pu/thumb.php";
    public static final String UPLOAD_IMAGES = "http://cloud.eeesys.com/pu/upload.php";
    public static final String PRAISE_PERSON = IP + "quest/love";
    public static final String MY_QUESTION = IP + "quest/my";
    public static final String HOT_QUES = IP + "quest/hot";
    // 图片上传的最大张数
    public static final int MAX_IMG = 3;
    public static final int REQUEST_QUES_DETAIL = 10;
    public static final int RESULT_QUES_DETAIL = 11;
    public static final int REQUEST_ASK_QUESTION = 12;
    public static final int RESULT_ASK_QUESTION = 13;
    public static final int REQUEST_COMMENT = 14;
    public static final int RESULT_COMMENT = 15;
    // 用药助手
    public static final java.lang.String DRUG_HELPER = IP + "drug_helper/query";

    //杂志
    public static final String MAGAZINE_LIST = IP + "news/list";

    // 药品搜索
    public static final String DRUG_SEARCH = IP + "drug_helper/show";

    // 问卷随访列表
    public static final String QUESTION_NAIR = IP + "visit/query";
    // 问卷随访详情
    // public static final String QUESTION_NOCONFIRM = "http://192.168.2.117/module/survey/pages/survey/questionnaire.jsp";
    //public static final String QUESTION_CONFIRM = "http://192.168.2.117/module/survey/pages/survey/show_question.jsp";

    public static final String QUESTION_NOCONFIRM = IP_C + "module/survey/pages/survey/questionnaire.jsp";
    public static final String QUESTION_CONFIRM = IP_C + "module/survey/pages/survey/show_question.jsp";

    // 我的复诊随访
    public static final String VISITEQUERY = IP + "visit/query";
    // 随访复诊详情
    public static final String MYCHATQUERY_DETAIL = IP + "visit/show";
    // 是否参加复诊
    public static final String MYCHATQUERY_MODIFY = IP + "visit/modify";

    // 开始考试获取id
    public final static String EXAMINATION_START_GETID = IP + "exam/begin.jsp";
    // 历史答题记录
    public static final String EXAM_QURY = IP + "exam/query";
    // 开始考试html
    public final static String EXAMINATION_START_SHOW = IP_C + "module/exam/exam.jsp";
    // 历史答题html
    public final static String EXAMINATION_HISTORY_DETALIS = IP_C + "module/exam/history.jsp";

    // 开始考试列表
    public final static String SURVEY_QUERY = IP + "survey/query";


}

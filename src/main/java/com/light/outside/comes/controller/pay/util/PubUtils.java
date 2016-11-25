package com.light.outside.comes.controller.pay.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * 全局使用的公共常量与函数定义类
 */
public class PubUtils {


    //java系统使用的 utf编码
    public static final String CharsetEncoding_UTF = "utf8";

    //公共的getuniquesn函数的偏移量（1-99）  可作为全局  统一设置使用
    public static int UniqueSn_Offset = 0;


//	//全局控制变量 是否显示debug的输出
//	public static boolean showDebugInfo=true;
//	
//	//全局随机变量 
//	public static long curRandNum=0;


    /**
     * 信息显示的级别  级别依次递增    NoDisplay:不显示   Common:正常的  Warning:警告级别  Exception:异常级别   Error: 错误级别  Debug:调试级别(基本）  Debug_Detail 调试的详细信息.  All: 全部显示
     */
    public enum InfoDisplayLevel {
        NoDisplay,
        Common,
        Warning,
        Exception,
        Error,
        Debug,
        Debug_Detail,
        All;

        public static InfoDisplayLevel parseFromIntValue(int value) {
            switch (value) {
                case 0:
                    return NoDisplay;
                case 1:
                    return Common;
                case 2:
                    return Warning;
                case 3:
                    return Exception;
                case 4:
                    return Error;
                case 5:
                    return Debug;
                case 6:
                    return Debug_Detail;
                case 7:
                    return All;
            }
            return All;

        }

    }

    ;

    /**
     * 运行信息的最小显示级别    全局使用. 高级包含低级
     */
    public static InfoDisplayLevel showRunTimeStatusInfoLevel = InfoDisplayLevel.Debug;


    /**
     * 数据库保存记录时,如果用事务处理 则单次事务所处理的最大记录数量
     */
    public final static int DBTransaction_MaxRecordNum = 5;

    /**
     * 数据库查询时 使用in模式最大支持的查询条目数 1000
     */
    public final static int DBQuery_InModeMaxItemNum = 950;


//
//	 /**信息显示的级别  级别依次递增    NoDisplay:不显示   Common:正常的  Warning:警告级别  Exception:异常级别   Error: 错误级别  Debug:调试级别(基本）  Debug_Detail 调试的详细信息.  All: 全部显示*/
//	public e InfoDisplayLevel{
//		NoDisplay,
//		Common,
//		Warning,
//		Exception,
//		Error,
//		Debug,
//		Debug_Detail,
//		All;
//		
//		public static InfoDisplayLevel parseFromIntValue(int value)
//		{
//			switch(value)
//			{
//			   case 0:  return NoDisplay;
//			   case 1:  return Common;				
//			   case 2:  return Warning;
//			   case 3:   return Exception;
//			   case 4:  return Error;
//			   case 5:   return Debug;
//			   case 6:   return Debug_Detail;
//			   case 7:  return All;
//			}		
//			return All;				
//			
//		}
//		
//	};

//	/**运行信息的最小显示级别    全局使用. 高级包含低级 */
//	public static InfoDisplayLevel showRunTimeStatusInfoLevel=InfoDisplayLevel.Debug;
//	
//	
//	/**数据库保存记录时,如果用事务处理 则单次事务所处理的最大记录数量*/
//	public final static int DBTransaction_MaxRecordNum=5;
//	
//	/**数据库查询时 使用in模式最大支持的查询条目数 1000*/
//	public final static int DBQuery_InModeMaxItemNum=950;

    //---------------------------------------------------------

    /**
     * 全局流水序号计算 所需的1位数的随机偏移量   可在系统初始化时更改
     */


    //全局随机函数发生器
    private static Random randGenerator = new Random();
    //全局随机变量的附加偏移量
    private static int curRandNumOffset = 0;

    //全局随机变量的附加偏移量
    private static int curRandNumOffsetNew = 0;

    //随机变量的范围 0-1000
    private static final int RandNum_Scope = 1000;

    //随机变量的范围 0-100
    private static final int RandNum_ScopeNew = 10;


    /**
     * 打印信息到屏幕或者文件... 可控制行首尾的换行
     *
     * @param msg                        需要打印的消息
     * @param isDisplayTimeAtLine        是否在行首显示当前时间
     * @param lineSwitchNumAddBeforeLine 行首添加的回车换行数,  如果为0 则不换行.
     * @param lineSwitchNumAddAfterLine  行尾添加的回车换行数,  如果为0 则不换行.
     */
    public static void printOutMsg(String msg, boolean isDisplayTimeAtLine, int lineSwitchNumAddBeforeLine, int lineSwitchNumAddAfterLine) {


        String dateTimeStr = "";
        if (isDisplayTimeAtLine) dateTimeStr = "[" + getStdTimeString() + "]" + "";

        String lineStartSwitchStr = "";
        for (int i = 0; i < lineSwitchNumAddBeforeLine; i++)
            lineStartSwitchStr += "\n";

        String lineEndSwitchStr = "";
        for (int i = 0; i < lineSwitchNumAddAfterLine; i++)
            lineEndSwitchStr += "\n";

        System.out.print(lineStartSwitchStr + dateTimeStr + msg + lineEndSwitchStr);

    }


    /**
     * 打印信息到屏幕或者文件...  不控制行首的换行
     *
     * @param msg                       需要打印的消息
     * @param isDisplayTimeAtLine       是否在行首显示当前时间
     * @param lineSwitchNumAddAfterLine 行尾添加的回车换行数,  如果为0 则不换行.
     */
    public static void printOutMsg(String msg, boolean isDisplayTimeAtLine, int lineSwitchNumAddAfterLine) {
        printOutMsg(msg, isDisplayTimeAtLine, 0, lineSwitchNumAddAfterLine);

    }


    /**
     * 按设定显示级别 显示对应信息
     *
     * @param displayLevel              本信息的显示级别,高于PubDefine.showStatusInfoLevel全局定义时,对应消息方可显示
     * @param msg                       需要打印的消息
     * @param isDisplayTimeAtLine       是否在行首显示当前时间
     * @param lineSwitchNumAddAfterLine 行尾添加的回车换行数,  如果为0 则不换行.
     */
    public static void printOutMsg(InfoDisplayLevel displayLevel, String msg, boolean isDisplayTimeAtLine, int lineSwitchNumAddAfterLine) {

        if (displayLevel == null || PubUtils.showRunTimeStatusInfoLevel.ordinal() >= displayLevel.ordinal())
            printOutMsg(msg, isDisplayTimeAtLine, 0, lineSwitchNumAddAfterLine);
    }


    /**
     * 打印异常的堆栈跟踪信息信息  用于调试等目的
     *
     * @param displayLevel 限定的异常信息的显示级别  与PubDefine.showStatusInfoLevel全局定义相同,方可显示堆栈跟踪
     * @param ex           异常
     */
    public static void printExceptionStackTrace(InfoDisplayLevel displayLevel, Exception ex) {
        if (PubUtils.showRunTimeStatusInfoLevel.compareTo(displayLevel) == 0)
            ex.printStackTrace();

    }


    /**
     * 带异常处理的休眠函数   广泛应用于线程休眠
     *
     * @param sleepms 休眠的毫秒数 如不大于0则不休眠
     */
    public static void doSleep(int sleepms) {
        if (sleepms <= 0) return;
        try {
            Thread.sleep(sleepms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * 两long型数相加,避免null的出现*
     *
     * @param num1
     * @param num2
     * @return long的结果
     */
    public static long addTwoNumber(Long num1, Long num2) {
        long res = 0;
        if (num1 == null)
            res += 0;
        else
            res += num1;

        if (num2 == null)
            res += 0;
        else
            res += num2;

        return res;
    }


    //==========================字符串相关的转换、处理函数================================


    /**
     * 获得指定时间的字符串形式
     *
     * @param cTime        时间值
     * @param hasSeperator 是否返回的时间字符串有分隔符 有则为yyyy-MM-dd HH:mm:ss ，否则为yyyyMMddHHmmss:
     * @return 得到的时间字符串
     */
    public static String getStdTimeString(Date cTime, boolean hasSeperator) {
        if (cTime == null) return "";

        DateFormat format;
        if (hasSeperator == true)
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else
            format = new SimpleDateFormat("yyyyMMddHHmmss");


        return format.format(cTime);


    }

    /**
     * 获得指定时间的字符串形式
     *
     * @param tTime        时间片值
     * @param hasSeperator 是否返回的时间字符串有分隔符 有则为yyyy-MM-dd HH:mm:ss ，否则为yyyyMMddHHmmss
     * @return 得到的时间字符串
     */
    public static String getStdTimeString(long tTime, boolean hasSeperator) {
        Date curDate = new Date(tTime);
        return getStdTimeString(curDate, hasSeperator);

    }

    /**
     * 根据时间和时间格式获得时间字符串
     *
     * @param cTime
     * @param dateFormat 时间格式  如yyyy-MM-dd HH:mm:ss
     * @return 符合格式的时间
     */
    public static String getStdTimeString(Date cTime, String dateFormat) {

        if (cTime == null) return "";

        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(cTime);

    }

    /**
     * 获得当前的时间字符串 按YYYY-MM-DD HH:MM:SS格式
     *
     * @return 时间字符串
     */
    public static String getStdTimeString() {
        long t = System.currentTimeMillis();

        return getStdTimeString(t, true);

    }


    //==========字符串基本处理==========================================

    /**
     * 将字符串进行格式化处理 删除两侧的空格, null转换为''等
     *
     * @param srcStr 等待处理的源字符串
     * @return 处理后的结果字符串
     */
    public static String GetTrim(String srcStr) {
        if (srcStr == null) return "";
        return srcStr.trim();

    }


    /**
     * 将字符串中的关键字替换为新关键字 可用于删除原内容
     *
     * @param srcStr   源串
     * @param itemList hashlieb key为要替换的内容 content为替换的新内容
     * @return 替换后的结果字符串
     */
    public static String ReplaceByItemList(String srcStr, Hashtable itemList) {
        if (srcStr == null || itemList == null || itemList.size() < 1) return srcStr;
        String resStr = srcStr.trim();

        Enumeration en = itemList.keys();
        while (en.hasMoreElements()) {
            String oldItem = (String) en.nextElement();  //原关键字
            String newItem = (String) itemList.get(oldItem); //替换的新关键字
            if (newItem == null) newItem = "";

            //替换
            resStr = resStr.replace(oldItem, newItem);
        }

        return resStr;
    }


    /**
     * 判断一个字符串是否为满足有效长度的字符串  长度不足或者null均认为是无效
     *
     * @param oriString   检测字符串
     * @param validStrLen 字符串的有效最小长度
     * @return 有效与否
     */
    public static boolean IsValidString(String oriString, int validStrLen) {

        int len = 0;
        if (validStrLen > 0) len = validStrLen;

        if (oriString == null) return false;
        if (oriString.length() < len) return false;

        return true;
    }


    /**
     * 从内容中提取信息  使用正则表达式,支持大小写不敏感   注意，对跨行的匹配项  抽不出来！！ 须清除回车等标记
     *
     * @param sourceStr              需要提取的目标字符串
     * @param startPatternTag        需提取信息的起始模板标记
     * @param endPatternTag          需提取信息的结束模板标记
     * @param keepPatternTagInResult 是否在返回结果中保留起始结束patternTag
     * @return 提取的信息, 只提取一个  如果未提取到 则返回null;
     */
    public static String GetExtractedItemValue(String sourceStr, String startPatternTag, String endPatternTag, boolean keepPatternTagInResult) {

        if (sourceStr == null) return null;

        String regex = startPatternTag + ".*?" + endPatternTag;

        Pattern pt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher mt = pt.matcher(sourceStr);
        String item = null;
        if (mt.find()) {
            item = mt.group();
            if (keepPatternTagInResult == false)  //delete pattern
            {
                int iStart = 0;
                if (startPatternTag != null) iStart = startPatternTag.length();

                int iEnd = item.length();
                if (endPatternTag != null)
                    iEnd -= endPatternTag.length();
                if (iEnd < iStart)
                    iEnd = iStart;
                item = item.substring(iStart, iEnd);
                return item.trim();
            }

        }

        return item;

    }


    /**
     * 字符串到整型的转换,并可设置异常时的值
     *
     * @param intString             字符串类型的int值
     * @param defaultExceptionValue 转换异常时的默认返回值
     * @return 正常则返回正常结果 如果转换异常则设置为defaultExceptionValue，
     */
    public static int String_ConvertToInt(String intString, int defaultExceptionValue) {
        int num = 0;
        if (intString != null) {
            try {
                num = Integer.parseInt(intString.trim());
            } catch (Exception ex) {
                num = defaultExceptionValue;
            }

        }

        return num;

    }

    /**
     * 字符串到long型的转换,并可设置异常时的值
     *
     * @param longString            字符串类型的long值
     * @param defaultExceptionValue 转换异常时的默认返回值
     * @return 正常则返回正常结果 如果转换异常则设置为defaultExceptionValue，
     */
    public static long String_ConvertToLong(String longString, long defaultExceptionValue) {
        long num = 0;
        if (longString != null) {
            try {
                num = Long.parseLong(longString.trim());
            } catch (Exception ex) {

                num = defaultExceptionValue;
            }

        }

        return num;

    }

    /**
     * 字符串到double的转换,并可设置异常时的值
     *
     * @param doubleString          字符串类型的double值
     * @param defaultExceptionValue 转换异常时的默认返回值
     * @return 正常则返回正常结果 如果转换异常则设置为defaultExceptionValue
     */
    public static Double String_ConvertToDouble(String doubleString, Double defaultExceptionValue) {
        Double num = 0.0;
        if (doubleString != null) {
            try {
                num = Double.parseDouble(doubleString.trim());
            } catch (Exception ex) {

                num = defaultExceptionValue;
            }

        }

        return num;

    }


    /**
     * 字符串到日期时间型DateTime的转换,并可设置异常时的值
     *
     * @param dateString            源日期字符串
     * @param dateType              转换结果类型: 1: 时间DateTime （yyyy-MM-dd HH:mm:ss格式）   2:日期Date（yyyy-MM-dd格式）    默认为1
     * @param defaultExceptionValue 异常时的默认设置值
     * @return 日期对象。  如果出异常 则返回默认值
     */
    public static Date String_ConvertToDateTime(String dateString, int dateType, Date defaultExceptionValue) {
        Date curDate = null;
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        if (dateType == 2)
            dateFormat = "yyyy-MM-dd";
        if (dateString != null) {
            try {
                curDate = new SimpleDateFormat(dateFormat).parse(dateString.trim());
            } catch (Exception ex) {

                curDate = defaultExceptionValue;
            }

        }

        return curDate;

    }


    /**
     * 根据指定的日期格式转换生成日期对象
     *
     * @param dateString            源字符串
     * @param dateFormat            日期格式   类似于 yyyy-MM-dd HH:mm:ss
     * @param defaultExceptionValue 转换异常时的默认设置值
     * @return 对应日期对象。  如果出异常 则返回默认值
     */
    public static Date String_ConvertToDateTime(String dateString, String dateFormat, Date defaultExceptionValue) {
        Date curDate = null;
        if (dateString != null) {
            try {
                SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);
                curDate = dateFormater.parse(dateString.trim());
            } catch (Exception ex) {
                curDate = defaultExceptionValue;
            }

        }

        return curDate;


    }


    //===============HTML相关处理==========================================================


    /**
     * 从HTML的超链接内容中提取url和anchor
     *
     * @param htmlHrefLinkInfo 超链接的href定义
     * @return 2元字符串数组, 下标为0的是url  下标为1的是anchor文本
     */
    public static String[] Html_GetHtmlHrefInfoFromHyperLink(String htmlHrefLinkInfo) {
        if (htmlHrefLinkInfo == null) return null;
        String[] hyperHref = new String[2];

        hyperHref[0] = PubUtils.GetExtractedItemValue(htmlHrefLinkInfo, "a href=\"", "\"", false);
        hyperHref[1] = PubUtils.GetExtractedItemValue(htmlHrefLinkInfo, ">", "</a>", false);


        return hyperHref;

    }


    //&#38889;&#29256;&#20241;&#38386;&#39118; &#38738;&#26149;&#26080;&#25932;&#22810;&#24425;&#25758;&#33394;&#25340;&#24067;&#35774;&#35745;&#36830;&#24125;&#23485;&#26494;&#21355;&#34915;

    /**
     * 将HTML的unicode方式的汉字(&#38889;&#29256;)转为普通汉字,输出
     *
     * @param oriStr 原unicode方式的汉字串
     * @return 处理后的结果 普通汉字串
     */
    public static String Html_ConvertHtmlUnicodeToChinese(String oriStr) {
        String resStr = null;

        //html中的unicode
        String regex = "&#" + "[0-9]{4,5}" + ";";

        Pattern pt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher mt = pt.matcher(oriStr);

        StringBuffer sb = new StringBuffer();
        boolean result = mt.find();
        while (result) {
            String item = mt.group();
            String iNum = item.substring(2, item.length() - 1);
            int num = PubUtils.String_ConvertToInt(iNum, 0);

            if (num > 4000 && num < 65500) {
                try {
                    mt.appendReplacement(sb, "" + (char) num);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            result = mt.find();
        }
        mt.appendTail(sb);

        return sb.toString();


    }


    //==========数据库相关的处理===================================================


    /**
     * 根据Oracle连接配置获得Oracle的数据库连接
     *
     * @param dbConstr 连接字符串 （thin方式）
     * @param dbUser   用户名
     * @param dbPass   密码
     * @return 连接成功返回对应的连接 否则返回null
     */
    public static Connection getDBConnection_Oracle(String dbConstr, String dbUser, String dbPass) {

        Connection dbCon = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            dbCon = DriverManager.getConnection(dbConstr, dbUser, dbPass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return dbCon;
    }


    /**
     * 根据Mysql连接配置获得Mysq的数据库连接
     *
     * @param dbConstr 连接字符串
     * @param dbUser   用户名
     * @param dbPass   密码
     * @return 连接成功返回对应的连接 否则返回null
     */
    public static Connection getDBConnection_Mysql(String dbConstr, String dbUser, String dbPass) {

        Connection dbCon = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            dbCon = DriverManager.getConnection(dbConstr, dbUser, dbPass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return dbCon;
    }


    /**
     * 执行数据库的更新类操作 如update delete和insert
     *
     * @param dbCon     数据库连接
     * @param sqlString 更新操作的sql语句
     * @return 影响的记录数   如果出错为-1
     */
    public static int exeuteDBUpdateOperation(Connection dbCon, String sqlString) {
        int i = -1;

        try {
            Statement sm = dbCon.createStatement();

            i = sm.executeUpdate(sqlString); // 执行数据更新语句（delete、update、insert、drop等）;

            sm.close();
        } catch (Exception ex) {

            ex.printStackTrace();
            return -1;
        }

        return i;
    }

    /**
     * 执行数据库的查询 select
     *
     * @param dbCon           数据库连接
     * @param sqlString       查询操作的sql语句
     * @param maxReturnRecNum 希望返回的最大记录数 0则为不限
     * @return 返回的结果记录集 ResultSet 类型
     */
    public static ResultSet exeuteDBQueryOperation(Connection dbCon, String sqlString, int maxReturnRecNum) {
        try {

            Statement stmt = dbCon.createStatement();
            if (maxReturnRecNum > 0)
                stmt.setMaxRows(maxReturnRecNum);
            ResultSet rs = stmt.executeQuery(sqlString);

            //stmt.close();

            return rs;
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }

    }


    //

    /**
     * 按最大记录数设定，返回List列表形式的数据库查询结果
     *
     * @param dbCon           可用的数据库连接
     * @param sqlString       sql查询语句
     * @param maxReturnRecNum 设定的最大返回记录数 0为不限
     * @return List形式的查询结果，每元素为一行记录的字段对应的object[]对象
     */
    public static List getDBQueryOperationList(Connection dbCon, String sqlString, int maxReturnRecNum) {
        List itemList = null;

        try {
            ResultSet rs = exeuteDBQueryOperation(dbCon, sqlString, maxReturnRecNum);
            if (itemList == null) itemList = new LinkedList();
            if (rs != null) {
                int columns = rs.getMetaData().getColumnCount();

                while (rs.next()) {
                    Object[] curObj = new Object[columns];
                    for (int i = 0; i < columns; i++)
                        curObj[i] = (Object) rs.getObject(i + 1);

                    itemList.add(curObj);
                }


                rs.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }


        return itemList;
    }


//   /**
//    * 基于Hibernate 会话，执行标准数据库查询操作  返回list列表对应的数据库记录
//    * @param session 数据库连接Session会话 可以为null（内部自动创建）
//    * @param sqlString 更新操作的标准sql语句
//    * @param maxReturnRecNum 设定的最大返回记录数 0为不限
//    * @return List形式的查询结果，每元素为一行记录的字段对应的object[]对象
//    */
//   public static List exeuteDBQueryOperation(Session session,String sqlString,int maxReturnRecNum)
//   {
//   	List itemList=null;
//   	
//   	boolean newSession=false;
//   	
//   	 Session curSession=session;
//		 if (curSession==null)
//		 {	 curSession=DBSessionFactory.getCurrentSession();
//		 	 newSession=true;
//		 }
//		 
//		 itemList=getDBQueryOperationList(curSession.connection(),sqlString,maxReturnRecNum);
//		  
//		 
//   	if (newSession==true)
//   		DBSessionFactory.closeCurrentSession();
//   	 
//   	
//   	return itemList;
//   }
//   
//   

//   /**
//    * 基于Hibernate 批量执行标准数据库操作 如update delete  truncate
//    * @param session 数据库连接Session会话 可以为null
//    * @param sqlString 更新操作的标准sql语句
//    * @param useTransaction 是否使用事务处理  true：是     false0：否
//    * @return 影响的记录数  如果出错为-1 
//    */
//   public static int exeuteDBUpdateOperation(Session session,String sqlString, boolean useTransaction)
//   {
//   	int num=-1;
//   	
//   	Transaction tx = null;
//   	
//   	if (useTransaction==true)
//   		tx=session.beginTransaction();
//   	
//   	Connection dbCon=session.connection();
//   	
//   	try
//   	{
//   		PreparedStatement stmt=dbCon.prepareStatement(sqlString);
//
//   		num=stmt.executeUpdate(sqlString); // 执行数据更新语句（delete、update、insert、drop等）;
//   		
//   		stmt.close();
//   		
//   		if (tx!=null) tx.commit();
//   		
//   	}	
//   	catch(Exception ex)
//   	{
//   		ex.printStackTrace();
//   		num=-1;
//   	}
//   	
//   	return num;
//   	
//    	
//   }	
//   
//   
//   
//   /**
//    * 基于Hibernate Session 执行HQL查询语句   得到 List列表形式的查询结果, 
//    * @param session 数据库连接会话 如果为null 则由内部自动设置和清除
//    * @param HqlString 查询操作的Hql语句
//    * @param returnRecNum 希望返回的记录数,如果0    则不限 
//    * @return 返回查询结果 List列表,   每个元素为Object[]，对应各字段值
//    */
//   public static List executeDBHQLQuery(Session session,String HqlString,int returnRecNum)
//   {
//   	 List itemList=null;
//		 Query curQuery=null;
//		 boolean newSession=false;
//		 
//		 Session curSession=session;
//		 if (curSession==null)
//		 {	 curSession=DBSessionFactory.getCurrentSession();
//		 	newSession=true;
//		 }
//		 try
//		 {  
//			 curQuery=curSession.createQuery(HqlString);
//			 if (returnRecNum>0)
//				 curQuery.setMaxResults(returnRecNum);
//			 
//			 itemList=curQuery.list();
// 		 
//		 }
//		 catch(Exception ex)
//		 {
//			 ex.printStackTrace();
//			 
//		 }
//		
//   	if (newSession==true)
//   		DBSessionFactory.closeCurrentSession();
//		 
//   	return itemList;
//   }
//   
//   


    /**
     * 用于oracle插入前的字符串预处理 避免一些字符导致插入异常
     * ◎param itemString 原字符串
     *
     * @return 处理后的字符串
     */
    public static String getDBStringPreProcess_Oracle(String itemString) {
        if (itemString == null || itemString.trim().length() == 0) return "";

        String resString = itemString.trim();

        resString = resString.replaceAll("'", "''");
        resString = resString.replaceAll("?", " ");
        resString = resString.replaceAll("&amp;", " ");
        resString = resString.replaceAll("&", " ");
        resString = resString.replaceAll("<", " ");
        resString = resString.replaceAll(">", " ");

        return resString;

    }


//  /**
//   *将已有的事务进行提交    
//   * @param curTransaction  当前运行的事务的对象
//   * @param throwException  是否将异常抛出  让上级处理 true:是 false:否
//   * @return 成功则返回true, 错误则回滚 并返回false  如果curTransaction为null也返回false
//   * @throws Exception  出异常则抛出
//   */
//  //
//  public static boolean endCurrentTransction(Transaction curTransaction,boolean throwException )throws Exception 
//  {	
//  	if (curTransaction==null) return false;
//		try
//		{
//			curTransaction.commit();
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			curTransaction.rollback();
//			
//			if (throwException==true)
//				throw ex;
//			 
//		}
//		return true;
//	}
//  
//  


    //============时间相关的处理函数================================================

    /**
     * 返回日期的对应数字格式.
     *
     * @param curDate
     * @param format  具体的返回格式 1: YYYY, 2:YYYYMM  3:YYYYMMDD  默认为3
     * @return 日期字符串   异常则为0
     */
    public static long DateTime_getDateNumber(Date curDate, int format) {
        if (curDate == null) return 0;

        String formatStr = "yyyy";
        if (format == 2) formatStr = "yyyyMM";
        if (format == 3) formatStr = "yyyyMMdd";


        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String res = sdf.format(curDate);


        int finalnum = PubUtils.String_ConvertToInt(res, 0);
        return finalnum;

    }


    /**
     * 根据当前时间 偏移指定毫秒的时间，得到新时间
     *
     * @param curDate     当前时间
     * @param offsetScale 偏移时间单位. 1: 毫秒  2:秒  3：分钟  4:小时  5:天  如果为其他值 则默认为1
     * @param offsetValue 偏移的时间量，与偏移时间单位对应  大于0则向后来时间， 小于0则向以前
     * @return 偏移后的日期对象
     */
    public static Date DateTime_GetOffsetTime(Date curDate, int offsetScale, long offsetValue) {

        //默认毫秒
        long tSpanMilSec = offsetValue;

        if (offsetScale > 0 && offsetScale <= 5) {
            if (offsetScale > 1) //秒
                tSpanMilSec *= 1000;

            if (offsetScale > 2) //分
                tSpanMilSec *= 60;

            if (offsetScale > 3) //小时
                tSpanMilSec *= 60;

            if (offsetScale > 4) //天
                tSpanMilSec *= 24;
        }

        long t = curDate.getTime();
        t += tSpanMilSec;

        return new Date(t);
    }


    /**
     * 根据当前日期  返回当前所在月的第一秒和最后一秒对应的时间
     *
     * @param curDate 当前时间
     * @return date[0]:本月第一秒的时间   date[1]本月最后一秒的时间
     */
    public static Date[] DateTime_GetScopeOfCurMonth(Date curDate) {
        Date[] scope = new Date[2];
        scope[0] = new Date(curDate.getYear(), curDate.getMonth(), 1, 0, 0, 0);


        scope[1] = DateTime_GetStartOfNextMonth(curDate);

        long tempDate = scope[1].getTime();
        tempDate -= 15 * 1000;
        scope[1] = new Date(tempDate);
        scope[1].setSeconds(59);

        return scope;
    }


    /**
     * 根据当前日期  返回当前所在月的下一个月的起始第一秒的时间
     *
     * @param curDate 当前时间
     * @return 下月起始第一秒的时间
     */
    public static Date DateTime_GetStartOfNextMonth(Date curDate) {
        long tempDate = curDate.getTime();
        long daymilsec = 24 * 60 * 60 * 1000;
        long add = 31 * daymilsec;
        tempDate += add;
        Date newDate = new Date(tempDate);


        newDate.setDate(1);
        newDate.setHours(0);
        newDate.setMinutes(0);
        newDate.setSeconds(0);

        return newDate;
    }


    /**
     * 用于GMT 格式字符串(Fri, 21 May 2010 08:09:21 GMT)转换的处理器
     */
    private static DateFormat IETFDateFormater = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);

    /**
     * 将IETF 标准日期时间字符串(如Sat, 12 Aug 1995 13:30:00 GMT) 转换为标准时间  主要用于http下载的解析
     *
     * @param GMTDateStr GMT格式时间字符串
     * @return 转换后的时间 如果不能转换 则返回null
     */
    public static Date DateTime_GetFromIETFTimeString(String IETFTimeStr) {
        Date GDate = null;
        try {
            GDate = IETFDateFormater.parse(IETFTimeStr);

        } catch (Exception ex) {
            GDate = null;

        }
        return GDate;
    }


    //==============字符串转换=======================

    /**
     * 将数字转换为指定的字符串,如果指定长度 前面则补0
     *
     * @param number    指定的数字
     * @param returnLen 指定的长度,如果为0 则不限制长
     * @return 数字字符串
     */
    public static String convertNumToLimitedString(int number, int returnLen) {

        String NumStr = "" + number;
        if (returnLen > 0 && returnLen > NumStr.length()) {
            int oriLen = NumStr.length();
            for (int i = 0; i < returnLen - oriLen; i++)
                NumStr = "0" + NumStr;

        }

        return NumStr;

    }

    /**
     * 拆分字符串，得到不重复的item元素的列表（hash格式）
     *
     * @param itemListStr 源字符串
     * @param itemSep     分隔符
     * @return hashtable 列表，每个元素的key与value相同， 如果没有结果 则为null
     */
    public static Hashtable parseUniqueItemList(String itemListStr, String itemSep) {
        Hashtable uniqueItemList = null;

        if (itemListStr == null) return uniqueItemList;

        String[] newItemList = itemListStr.split(itemSep);
        if (newItemList != null && newItemList.length > 0) {
            uniqueItemList = new Hashtable();
            for (int i = 0; i < newItemList.length; i++) {
                String item = newItemList[i].trim();
                if (item.length() == 0) continue;

                if (uniqueItemList.contains(newItemList[i]) == false)
                    uniqueItemList.put(newItemList[i], newItemList[i]);
            }
        }

        return uniqueItemList;

    }


    /**
     * 从字符串中剔除指定的符号 并返回结果
     *
     * @param oriString   源字符串
     * @param tagToRemove 剔除的符号文字列表 每个为需要剔除的内容 String类型
     * @return null或者结果
     */
    public static String getString_RemoveTag(String oriString, List tagToRemove) {
        if (oriString == null) return null;
        if (tagToRemove == null) return oriString;

        String res = oriString;
        for (int i = 0; i < tagToRemove.size(); i++) {
            res = res.replaceAll((String) tagToRemove.get(i), "");
        }
        return res;
    }

    /**
     * 给字符串前后补特定字符，并返回结果
     *
     * @param oriString 源字符串
     * @param maxLen    结果的字符串最大长度
     * @param tagToAdd  添加的字符  必须为单字符 通常为0 空格等
     * @param tagPos    添加的字符位置 1：在前面加 2：在后面加 默认是前面
     * @return 补充后的长度
     */
    public static String getString_AddTag(String oriString, int maxLen, String tagToAdd, int tagPos) {
        if (oriString == null) return null;
        if (tagToAdd == null || tagToAdd.length() < 1) return oriString;


        String res = oriString;
        while (res.length() + tagToAdd.length() <= maxLen) {
            if (tagPos == 2) res += tagToAdd;
            else
                res = tagToAdd + res;
        }

        return res;
    }


    /**
     * 将字符串形式的拼接的item列表 拆分成单独的 以list形式显示
     *
     * @param itemStrList 字符串类型的item列表 有拼接符
     * @param splitSep    拼接的符 如； 注意某些符号需要转义 如. 变成 \\.
     * @param minItemLen  每个item最小长度 拆分的item小于最小长度 则不合法  如0则不检测
     * @return list列表 每个元素为item  如果没有 list可能为null
     */
    public static List splitItemStringToList(String itemStrList, String splitSep, int minItemLen) {


        if (itemStrList == null || itemStrList.length() == 0) return null;
        List resList = null;

        String[] itemArray = itemStrList.split(splitSep);
        if (itemArray.length > 0) {
            for (int i = 0; i < itemArray.length; i++) {
                String curItem = itemArray[i].trim().toLowerCase();
                if (minItemLen > 0 && curItem.length() < minItemLen)
                    continue;
                if (resList == null) resList = new LinkedList();
                resList.add(curItem);
            }

        }

        return resList;

    }

    /**
     * 将列表的item的值 拼接成字符串（带有分隔符）
     *
     * @param itemList 列表 每个元素为需拼接的元素(值为toSring()方法得到)
     * @param itemSep  item的分隔符
     * @return 字符串类型的结果 如列表为null 则结果为null
     */
    public static String combineItemListToString(List itemList, String itemSep) {
        if (itemList == null) return null;

        String res = "";
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                if (i > 0)
                    res += itemSep;
                res += itemList.get(i).toString();
            }
        }

        return res;
    }


    //============MD5 加密处理=========================================================

    //	全局共享的md5处理器
    private static MessageDigest MD5Processer = null;

    //全局共享的md5处理器
    private synchronized static MessageDigest getMD5Processer() {
        try {
            if (MD5Processer == null)
                MD5Processer = MessageDigest.getInstance("MD5");
        } catch (Exception ex) {
            MD5Processer = null;
        }

        return MD5Processer;

    }


    /**
     * 计算得到MD5的哈希散列
     *
     * @param bytesToHash 需计算散列值的源字节流
     * @return 正确时返回16字节的hash结果  否则为null
     */
    public static byte[] doMD5Hash(byte[] bytesToHash) {
        if (bytesToHash == null) return null;

        try {
            MessageDigest MD5 = getMD5Processer();

            byte[] b = MD5.digest(bytesToHash);

            return b;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 进行MD5散列处理 得到源二进制结果（16字节）
     *
     * @param oriMessage 源消息内容(默认以utf8编码为字节流）
     * @return 吹成功，返回结果（16字节的二进制），否则返回null
     */
    public static byte[] doMD5Hash(String oriMessage) {

        if (oriMessage == null) return null;

        try {
            byte[] b = doMD5Hash(oriMessage.getBytes(PubUtils.CharsetEncoding_UTF));
            return b;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    /**
     * 进行MD5散列处理 得到long型数组结果
     *
     * @param oriMessage 源消息内容(默认以utf8编码为字节流）
     * @return 成功返回结果long[2]数组 代表高低位   否则为null
     */
    public static long[] doMD5Hash_Long(String oriMessage) {

        if (oriMessage == null) return null;

        try {
            byte[] b = doMD5Hash(oriMessage.getBytes(PubUtils.CharsetEncoding_UTF));
            if (b == null) return null;


            long[] res = new long[2];
            res[0] = PubUtils.Bytes_To_Long(b, 0);
            res[1] = PubUtils.Bytes_To_Long(b, 8);

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 进行MD5散列处理 得到短字符串（非32位 而是两个long型值的拼接）
     *
     * @param oriMessage 源消息内容(默认以utf8编码为字节流）
     * @return 成功返回短字符串  否则为null
     */
    public static String doMD5Hash_ShortString(String oriMessage) {

        if (oriMessage == null) return null;

        try {
            long[] res = doMD5Hash_Long(oriMessage);
            if (res == null) return null;

            return res[0] + "" + res[1];

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * MD5散列 得到字符串格式的加密结果
     *
     * @param oriMessage 源消息内容  使用utf8做
     * @return 加密成功，返回加密结果 (32字符的字符串) ，否则返回null
     */
    public static String doMD5Encrypt(String oriMessage) {
        if (oriMessage == null) return null;
        try {
            byte[] b = doMD5Hash(oriMessage.getBytes(PubUtils.CharsetEncoding_UTF));

            return byteToHexString(b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //========================数字二进制转十六进制字符串表示=============================

    //十六进制表示数组
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     *
     * @param srcbyte 要转换的byte[]
     * @return 十六进制字符串表示形式 小写形式  如果srcbyte=null，则返回null
     */
    public static String byteToHexString(byte[] srcbyte) {
        if (srcbyte == null) return null;

        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = srcbyte[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }


    /**
     * long转为8位字节数组
     *
     * @param x
     * @return
     */
    public static byte[] Long_To_Bytes(long x) {
        byte[] v = new byte[8];

        v[0] = (byte) (x >> 56);
        v[1] = (byte) (x >> 48);
        v[2] = (byte) (x >> 40);
        v[3] = (byte) (x >> 32);
        v[4] = (byte) (x >> 24);
        v[5] = (byte) (x >> 16);
        v[6] = (byte) (x >> 8);
        v[7] = (byte) (x >> 0);

        return v;
    }


    /**
     * 将字节数组的8字节转为long
     *
     * @param x     字节数组
     * @param start 起始的字节下标（高位）
     * @return long
     */
    public static long Bytes_To_Long(byte[] x, int start) {
        long res = ((((long) x[start + 0] & 0xff) << 56) |
                (((long) x[start + 1] & 0xff) << 48) |
                (((long) x[start + 2] & 0xff) << 40) |
                (((long) x[start + 3] & 0xff) << 32) |
                (((long) x[start + 4] & 0xff) << 24) |
                (((long) x[start + 5] & 0xff) << 16) |
                (((long) x[start + 6] & 0xff) << 8) |
                (((long) x[start + 7] & 0xff) << 0));

        return res;
    }


    //================Properties配置文件相关操作===============================================
    //------


    /**
     * 根据配置文件生成propterties对象 支持文件包的类方式 用于后续处理，提取相关配置
     *
     * @param runtimeClass   配置文件所在的包的类
     * @param propertiesFile 配置文件 支持包文件路径方式
     * @return propertie对象  如果失败则为null
     */
    public static Properties Properties_InitFromFile(Class runtimeClass, String propertiesFile) {

        //先判断是否为绝对文件方式
        if (propertiesFile.indexOf("/com/") != 0 || runtimeClass == null) {
            Properties proptertie = Properties_InitFromFile(propertiesFile);
            return proptertie;

        }

        //否则为类路径资源模式


//		1 读取配置文件和生成系统配置对象
        Properties proptertie = new Properties();
        try {
            //FileInputStream inputFile = new FileInputStream(PubUtils.class.getResourceAsStream(propertiesFile));
            BufferedReader bReader = new BufferedReader(new InputStreamReader(runtimeClass.getResourceAsStream(propertiesFile)));
            proptertie.load(bReader);
            bReader.close();
        } catch (Exception ex) {
            PubUtils.printOutMsg("Error in Opening Properties File[" + propertiesFile + "][Exception:" + ex.getMessage() + "]... Exit", true, 2);
            ex.printStackTrace();
            return null;
        }


        return proptertie;

    }


    /**
     * 根据配置文件生成propterties对象  用于后续处理，提取相关配置
     *
     * @param propertiesFile 配置文件
     * @return propertie对象  如果失败则为null
     */
    public static Properties Properties_InitFromFile(String propertiesFile) {

//		1 读取配置文件和生成系统配置对象
        Properties proptertie = new Properties();
        try {
            FileInputStream inputFile = new FileInputStream(propertiesFile);

            proptertie.load(inputFile);
            inputFile.close();
        } catch (Exception ex) {
            PubUtils.printOutMsg("Error in Opening  Properties File[" + propertiesFile + "][Exception:" + ex.getMessage() + "]... Exit", true, 2);
            ex.printStackTrace();
            return null;
        }


        return proptertie;

    }


    /**
     * 根据配置文件生成propterties对象  用于后续处理，提取相关配置
     *
     * @param webFile web路径的配置文件
     * @return propertie对象  如果失败则为null
     */
    public static Properties Properties_InitFromWebFile(String webFile) {

//		1 读取配置文件和生成系统配置对象
        Properties proptertie = new Properties();
        try {
            URL url = new URL(webFile);
            InputStream input = url.openStream();
            //FileInputStream inputFile = new FileInputStream( propertiesFile );

            proptertie.load(input);
            input.close();
        } catch (Exception ex) {
            PubUtils.printOutMsg("Error in Opening  Web Properties File[" + webFile + "][Exception:" + ex.getMessage() + "]... Exit", true, 2);
            ex.printStackTrace();
            return null;
        }


        return proptertie;

    }


    /**
     * 根据记录配置信息的字符串, 生成propterties对象 用于后续处理，提取相关配置
     *
     * @param propertiesString 记录配置信息的字符串
     * @return propertie对象  如果失败则为null
     */
    public static Properties Properties_InitFromString(String propertiesString) {


//		1 读取配置文件和生成系统配置对象
        Properties proptertie = new Properties();
        try {
            InputStream stream = new StringBufferInputStream(propertiesString);
            proptertie.load(stream);
        } catch (Exception ex) {
            PubUtils.printOutMsg("Error in Initing Properties From String[Exception:" + ex.getMessage() + "]... Exit", true, 2);
            ex.printStackTrace();
            return null;
        }


        return proptertie;

    }


    /**
     * 基于动态配置文件  读取配置的properties
     *
     * @param runtimeClass  调用的基础类
     * @param activeCfgFile 动态配置文件的本地文件
     * @return Properties 配置  如果错误则为null
     */
    public static Properties Properties_ActiveLoad(Class runtimeClass, String activeCfgFile) {

        Properties propertie = null;
        //先load本地文件
        propertie = Properties_InitFromFile(runtimeClass, activeCfgFile);

        if (propertie == null) return null;

        //2 读取来源配置
        int config_srctype = PubUtils.String_ConvertToInt(PubUtils.Properties_GetCfgItemValue(propertie, "config_srctype", "1"), 1);
        String proFilePath = PubUtils.Properties_GetCfgItemValue(propertie, "config_src", "");

        // 		3 读取新配置文件
        if (config_srctype == 1) //本文件
            return propertie;

        if (config_srctype == 2)  //本地其他文件
        {
            propertie = Properties_InitFromFile(runtimeClass, proFilePath);
            return propertie;

        }

        if (config_srctype == 3)  //web
        {
            String webProFile = PubUtils.Properties_GetCfgItemValue(propertie, "config_src", "");
            propertie = Properties_InitFromWebFile(webProFile);

            return propertie;
        }


        return null;
    }


    /**
     * 从Propterties配置文件中获得某项配置的设置值，支持中文方式(iso-8859-1 转utf-8)
     *
     * @param propertie    propertie属性对象
     * @param cfgItemKey   配置项的名称
     * @param defaultValue 当配置项目不存在或者异常时，返回的默认配置值
     * @return 配置项的值 如果存在异常或者无，则返回默认配置值，这样可避免null值后续产生的异常
     */
    public static String Properties_GetCfgItemValue(Properties propertie, String cfgItemKey, String defaultValue) {

        if (propertie.containsKey(cfgItemKey)) {
            String value = propertie.getProperty(cfgItemKey);//得到某一属性的值
            if (value == null) return defaultValue;

            try {
                value = new String(value.getBytes("ISO-8859-1"), "UTF8");
            } catch (Exception ex) {
                value = defaultValue;
            }

            return value;
        } else
            return defaultValue;
    }


    //=======  文件读写相关的处理==================================

    /**
     * 创建文本文件的读接口
     *
     * @param filePath 读的文件路径
     * @return 读文件的流接口
     */
    public static BufferedReader TextFile_CreatReader(String filePath) {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        } catch (Exception ex) {
            ex.printStackTrace();

            PubUtils.printOutMsg("无法创建读文件接口.错误:" + ex.getMessage(), true, 1);
            return null;

        }

        return reader;

    }

    /**
     * 创建一个文本文件的写接口
     *
     * @param filePath 写入的文件的路径
     * @return 写文件的流接口
     */
    public static PrintStream TextFile_CreatWriter(String filePath) {
        PrintStream writer = null;
        try {

            writer = new PrintStream(new FileOutputStream(filePath));

        } catch (Exception ex) {
            ex.printStackTrace();

            PubUtils.printOutMsg("无法创建可写文件.错误:" + ex.getMessage(), true, 1);
            return null;

        }
        return writer;


    }


    /**
     * 读取文件 获得文本类型内容
     *
     * @param fileToRead 需要读取的文件的全路径
     * @param charSet    转换的字符集类型,如果为null  则用系统默认值
     * @return 文本的内容 string 类型 如果异常或者其他问题 可能返回null
     */
    public static String TextFile_ReadFromFile(String fileToRead, String charSet) {

        StringBuffer buffer = null;


        FileInputStream iStream = null;
        InputStreamReader reader = null;
        try {
            iStream = new FileInputStream(fileToRead);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //打开和读取
        BufferedReader bufReader = null;
        try {
            if (PubUtils.IsValidString(charSet, 1) == true)
                reader = new InputStreamReader(iStream, charSet);
            else
                reader = new InputStreamReader(iStream);

            bufReader = new BufferedReader(reader);

            buffer = new StringBuffer();
            String row;
            while ((row = bufReader.readLine()) != null) {
                buffer.append(row);
                buffer.append("\n");
            }

            iStream.close();
            reader.close();
            bufReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        if (buffer != null)
            return buffer.toString();

        return null;

    }


    /**
     * 把文本内容保存到一个文件
     *
     * @param textContent 要保存的文本内容
     * @param outputFile  保存的目标文件
     * @return 生成的所保存文件的文件对象  如为null 则说明未有效保存
     */
    public static File TextFile_WriteToFile(String textContent, String outputFile) {
        if (textContent == null || outputFile == null) return null;


        BufferedWriter bufWriter = null;
        File file = null;
        try {
            file = new File(outputFile);
            bufWriter = new BufferedWriter(new FileWriter(file, true));

            bufWriter.write(textContent);
            bufWriter.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufWriter != null) {
                try {
                    bufWriter.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 从文件中二进制读取得到字节数组
     *
     * @param fileToRead 需要读的文件路径
     * @return 二进制字节数组格式的文件内容 异常则为null
     */
    public static byte[] File_ReadBytesFromFile(String fileToRead) {
        File myFile = new File(fileToRead);
        return File_ReadBytesFromFile(myFile);

    }


    /**
     * 从文件中二进制读取得到字节数组
     *
     * @param f 文件对象
     * @return 二进制字节数组格式的文件内容 异常则为null
     */
    public static byte[] File_ReadBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 把字节数组保存到一个文件
     *
     * @param b          要保存的字节数组
     * @param fileToSave 保存的文件
     * @return 生成的所保存文件的文件对象
     */
    public static File File_WriteBytesToFile(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 进行http post的请求提交,并得到返回的response结果   utf8编解码  可用于测试
     *
     * @param requestUrl
     * @param postContent
     * @return 处理返回的结果 string
     */
    public static String postHttpRequest(String requestUrl, String postContent) {

        String response = "";
        try {
            byte[] postBytes = postContent.getBytes(PubUtils.CharsetEncoding_UTF);

            URL postUrl = new URL(requestUrl);
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
            // 设置访问超时时间
            conn.setReadTimeout(15 * 1000);
            // 设置请求方式
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置允许输出
            conn.setDoOutput(true);
            conn.setRequestProperty("Charset", PubUtils.CharsetEncoding_UTF);
            conn.setRequestProperty("Content-Length", postBytes.length + "");


            OutputStream out = conn.getOutputStream();
            //注意 utf8
            out.write(postBytes);
            out.flush();


            ByteArrayOutputStream out2;
            //读取结果
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                // 从输入流中读取内容放到内存中
                out2 = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    out2.write(buffer, 0, len);
                }

                response = new String(out2.toByteArray(), PubUtils.CharsetEncoding_UTF);
                out2.close();
                in.close();

            }
            out.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }


    //=============字节数组转换================================

    /** */
    /**
     * 从字节数组获取对象
     *
     * @param objBytes 二进制字节数组
     * @return 转换的对象Object
     */
    public static Object Object_FromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }


    /** */
    /**
     * 从对象获取一个字节数组
     *
     * @param obj 串行化对象
     * @return 生成的字节数组
     */
    public static byte[] Object_ToBytes(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }


    //==========系统网卡资源==================================================

    /**
     * 获得Linux系统本地的以太网卡的ip地址
     *
     * @param onlyEthCard 只提取以太网卡的地址(命名为ethxx的)
     * @return list 包含每个网卡的ip
     */
    public static List getLinuxLocalIPAddrList(boolean onlyEthCard) {
        List addrList = new LinkedList();
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();

                //只有以太网卡 eth命名的才符合
                if (onlyEthCard == true) {
                    String cardName = ni.getName();
                    if (cardName == null || cardName.toLowerCase().indexOf("eth") != 0)
                        continue;
                }

                //遍历网卡
                Enumeration cardipaddress = ni.getInetAddresses();
                while (cardipaddress.hasMoreElements()) {
                    InetAddress ip = (InetAddress) cardipaddress.nextElement();

                    if (!ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) //非loopback的
                    {
                        String allipaddress = ip.getHostAddress();
                        //	            while(cardipaddress.hasMoreElements())
                        //	             {
                        //	                 ip = (InetAddress) cardipaddress.nextElement();
                        //	                 allipaddress=allipaddress+&quot; , &quot;+ip.getHostAddress();
                        //
                        //	             }
                        addrList.add(allipaddress);
                    } else
                        continue;
                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return addrList;
    }


    /**
     * linux  根据本地网卡ip地址获得一个uid属性信息 主要用于区分服务器
     *
     * @param ipIndex 提取uid的ip所在地址列表的编号。1-n   1 从第一个ip取 2 从第二个ip取...  默认为第一个
     * @return ip地址的最后一个区段的3位值 如果无则返回null
     */
    public static String getLinuxUid(int ipIndex) {
        int index = ipIndex;
        if (index < 0) index = 1;
        //使用网卡最后一段地址 作为分隔
        String uid = null;
        List addrList = PubUtils.getLinuxLocalIPAddrList(false);
        if (addrList != null && addrList.size() >= ipIndex) {
            String addr = (String) addrList.get(ipIndex - 1);
            if (addr != null) {
                int pos = addr.lastIndexOf(".");
                if (pos > 0) uid = addr.substring(pos + 1);
            }
        }
        return uid;
    }


    //=============汉字首字母拼音处理相关====================


    private static final int GB_SP_DIFF = 160;
    private static final int[] secPosvalueList = {
            1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
            3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
            4390, 4558, 4684, 4925, 5249, 5600};
    private static final char[] firstLetter = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'w', 'x', 'y', 'z'};


    /**
     * 返回汉字字符串的对应拼音首字母序列
     *
     * @param oriStr 待转换的汉字字符串
     * @return 转换后的拼音首字母的字符串， 如果有英文或者数字则保留 如果为null 则返回null
     * @throws UnsupportedEncodingException
     */
    public static String getPinYinFirstLetter(String oriStr) throws UnsupportedEncodingException {
        String str = oriStr.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        char ch;
        char[] temp;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            temp = new char[]{ch};
            byte[] uniCode = new String(temp).getBytes("gbk");
            //非汉字区  直接使用
            if (uniCode[0] < 128 && uniCode[0] > 0)
                buffer.append(temp);
            else //汉字区
                buffer.append(getPinYinFirstLetter(uniCode));
        }
        return buffer.toString();
    }


    /**
     * 返回汉字的拼音的首字符
     *
     * @param wordBytes 汉字的unicode字符
     * @return 该字的拼音的首字母
     */
    private static char getPinYinFirstLetter(byte[] wordBytes) {

        char result = '-';
        int secPosvalue = 0;
        int i;
        for (i = 0; i < wordBytes.length; i++) {
            wordBytes[i] -= GB_SP_DIFF;
        }
        secPosvalue = wordBytes[0] * 100 + wordBytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosvalue >= secPosvalueList[i] &&
                    secPosvalue < secPosvalueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }


    //==============http =================

    /**
     * 拆分http请求参数中的key value 对
     *
     * @param reqQuery http中的请求参数部门内容（不包括文件
     * @return 哈希列表，key value 分别为参数和值
     */
    public static Hashtable parseRequestPair(String reqQuery) {
        Hashtable reqParas = null;
        if (reqQuery != null && reqQuery.length() > 0) {
            reqParas = new Hashtable();

            String[] reqPairs = reqQuery.split("&");

            if (reqPairs != null && reqPairs.length > 0) {
                String[] pair = null;
                for (int i = 0; i < reqPairs.length; i++) {
                    pair = reqPairs[i].split("=");
                    if (pair != null) {
                        reqParas.put(pair[0].trim().toLowerCase(), pair[1].trim().toLowerCase());

                    }
                }

            }


        }

        return reqParas;
    }


    //=================随机数生成处理=======================


    /**
     * @return 返回一个随机发生器
     */
    public static Random getRandGenerator() {
        return randGenerator;
    }


    /**
     * 返回当前的最新偏移量
     *
     * @return 返回值 0-999
     */
    public synchronized static int getRandOffset() {
        curRandNumOffset = (curRandNumOffset + 1) % RandNum_Scope;
        //if (curRandNumOffset==0) curRandNumOffset++;
        return curRandNumOffset;
    }

    /**
     * 返回当前的最新偏移量
     *
     * @return 返回值 0-999
     */
    public synchronized static int getRandOffsetNew() {
        curRandNumOffsetNew = (curRandNumOffsetNew + 1) % RandNum_ScopeNew;
        //if (curRandNumOffset==0) curRandNumOffset++;
        return curRandNumOffsetNew;
    }


    /**
     * 随机数产生方法
     * 两位年份-到10毫秒 + 2位机器分隔符 + 3位随机序列数
     * YYMMDDHHMMSSXX
     * *
     */


    //人工随机号的波动范围
    public static final int OffsetSep_Scope = 100;

    /**
     * 根据时间和人工随机数，返回长整型的唯一性id(基于时间） 多用于生成记录流水号
     *
     * @param time             时间 如果为null 则使用当前时间
     * @param randOffsetSepNum 人工设置的随机号（如可用网关编号后两位）,避免不同机器产生相同的序列流水号,须在[0, OffsetSep_Scope常量定义) ，如果不在此范围,则随机产生2位
     * @return 唯一性流水号 格式如 YYYYMMDDHHMMSSxxxxx  xxxxx为随机数
     */
    private synchronized static long getUniqueSn(Date time, int randOffsetSepNum) {

        //时间
        Date curTime = time;
        if (time == null) curTime = new Date(System.currentTimeMillis());

        //中间的分隔字符串
        int randSep = randOffsetSepNum;
        if (randSep < 1 || randSep >= OffsetSep_Scope) //不符合要求 则随机生成2位
        {
            randSep = randGenerator.nextInt(OffsetSep_Scope - 1);
        }


        //到毫秒的字符串
        String timeStr = getStdTimeString(curTime, "yyMMddHHmmssSSS");


        //long   然后到10毫秒单位
        long randSn = PubUtils.String_ConvertToLong(timeStr, System.currentTimeMillis()) / 10;

        // System.out.println(timeStr+"  "+randSn+"" );
        randSn = randSn * OffsetSep_Scope + randSep;
        //  System.out.println("1---"+randSn+"" );
        //添加纯随机数
        randSn *= RandNum_Scope;
        //  System.out.println("2---"+randSn+"" );
        randSn += getRandOffset();
        // System.out.println("3---"+randSn+"" );
        //System.out.println(randSn+"  "+tt);
        return randSn;
    }


    /**
     * 根据时间和随机数偏移量，得到唯一性的编号（多用于流水号）
     *
     * @param randOffsetSepNum 人工设置的随机分隔号（比如可用网关编号后两位）,避免不同机器产生相同的序列流水号,须在[0, OffsetSep_Scope常量定义) ，如果不在此范围,则不附加该随机参数
     * @return 时间相关的唯一流水号  格式如 YYMMDDHHMMSSxxxxx
     */
    public synchronized static long getUniqueSn(int randOffsetSepNum) {
        Date curTime = new Date(System.currentTimeMillis());

        long sn = getUniqueSn(curTime, randOffsetSepNum);

        return sn;
    }


    /**
     * 指定最高位数字和随机数偏移量，根据时间得到唯一性的编号（多用于流水号）
     *
     * @param preffixNum       指定的最高位数字（建议1位1-9）
     * @param randOffsetSepNum 人工设置的随机分隔号（比如可用网关编号后两位）,避免不同机器产生相同的序列流水号,须在[0, OffsetSep_Scope常量定义) ，如果不在此范围,则不附加该随机参数
     * @return 与高位设置和时间时间相关的唯一流水号  格式如 YYMMDDHHMMSSxxxxx
     */
    public synchronized static long getUniqueSn(int preffixNum, int randOffsetSepNum) {
        Date curTime = new Date(System.currentTimeMillis());

        long sn = getUniqueSn(curTime, randOffsetSepNum);

        //替换高位
        sn = replaceLongPreffix(sn, preffixNum);

        return sn;
    }


    /**
     * 根据当前时间和系统默认的随机偏移量 生成的唯一流水号
     *
     * @return 时间相关的唯一流水号  格式如 YYMMDDHHMMssSSxxxxx  xxx为随机数
     */
    public synchronized static long getUniqueSn() {
        long t = System.currentTimeMillis();
        int randSep = randGenerator.nextInt(OffsetSep_Scope - 1);
        long sn = getUniqueSn(new Date(t), randSep);

        return sn;
    }

    /**
     * 根据当前时间和系统默认的随机偏移量 生成的唯一流水号
     *
     * @return 时间相关的唯一流水号  格式如 YYMMDDHHMMssSSxxxxx  xxx为随机数
     */
    public synchronized static String getUniqueSnNew() {
        long t = System.currentTimeMillis();
        int randSep = randGenerator.nextInt(OffsetSep_Scope - 1);

        //时间
        Date curTime = new Date();
        if (curTime == null) curTime = new Date(System.currentTimeMillis());

        //中间的分隔字符串

        if (randSep < 1 || randSep >= OffsetSep_Scope) //不符合要求 则随机生成2位
        {
            randSep = randGenerator.nextInt(OffsetSep_Scope - 1);
        }


        //到毫秒的字符串
        String timeStr = getStdTimeString(curTime, "yyMMddHHmmssSSS");


        //long   然后到10毫秒单位
        long randSn = PubUtils.String_ConvertToLong(timeStr, System.currentTimeMillis()) / 10;

        // System.out.println(timeStr+"  "+randSn+"" );
        randSn = randSn * OffsetSep_Scope + randSep;
        //  System.out.println("1---"+randSn+"" );
        //添加纯随机数
        randSn *= RandNum_ScopeNew;
        //  System.out.println("2---"+randSn+"" );
        randSn += getRandOffsetNew();
        // System.out.println("3---"+randSn+"" );
        //System.out.println(randSn+"  "+tt);
        return String.valueOf(randSn).substring(1, String.valueOf(randSn).length());

    }

    /**
     * 将long型的高位数值替换
     *
     * @param targetToReplace 需要替换的long型数
     * @param 替换的新的高位数
     * @return 替换了高位的long型值
     */
    public synchronized static long replaceLongPreffix(long targetToReplace, int preffixNum) {
        if (targetToReplace < preffixNum)
            return targetToReplace;

        String longStr = targetToReplace + "";
        String numStr = preffixNum + "";

        String newStr = numStr + longStr.substring(numStr.length());
        return String_ConvertToLong(newStr, targetToReplace);

    }


    //=======================Base64===========================================


    private static sun.misc.BASE64Encoder Base64Encoder = new sun.misc.BASE64Encoder();
    private static sun.misc.BASE64Decoder Base64Decoder = new sun.misc.BASE64Decoder();

    /**
     * Base64 编码处理
     *
     * @param srcBytes 源二进制
     * @return 结果的base64字符串  源串为null或者处理异常
     */
    public static String Base64_Encode(byte[] srcBytes) {
        if (srcBytes == null) return null;

        String encodeResult = Base64Encoder.encode(srcBytes);
        return encodeResult;

    }

    /**
     * Base64解码 将base64字符串还原为原来的二进制字节流
     *
     * @param base64Str 需要还原的base64编码字符串
     * @return 二进制字节流  源串为null或者处理异常  则为null
     */
    public static byte[] Base64_Decode(String base64Str) {
        if (base64Str == null) return null;

        byte[] decodeRes = null;
        try {
            decodeRes = Base64Decoder.decodeBuffer(base64Str);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return decodeRes;
    }


    //---------------compress---------

    /**
     * 将字节流进行GZIP压缩 得到压缩后的字节流
     *
     * @param bytesToCompress 要压缩的字节流
     * @return 压缩后的字节流 源串为null或者处理异常  则为null
     */
    public static byte[] ZIP_GZip(byte[] bytesToCompress) {
        if (bytesToCompress == null) return null;

        byte[] compressResult = null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(bytesToCompress);
            gzip.close();

            compressResult = out.toByteArray();


        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return compressResult;


    }


    /**
     * 将压缩的GZIP流转换为未压缩的格式
     *
     * @param gzipBytes gzip压缩流
     * @return 解压缩后的字节流 源串为null或者处理异常则返回为null
     */
    public static byte[] ZIP_GUnzip(byte[] gzipBytes) {
        if (gzipBytes == null) return null;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(gzipBytes);

            GZIPInputStream gunzip = new GZIPInputStream(in);

            byte[] buffer = new byte[2048];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }

            return out.toByteArray();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return null;
    }


    /**
     * 将字符串进行GZIP压缩 得到压缩后的字节流
     *
     * @param strCoCompress 要压缩的字符串
     * @param charset       字符串的编码   如果设为null 则使用utf8
     * @return 压缩后的字节流  如果异常或者源串为null  则为null
     */
    public static byte[] ZIP_GZip(String strCompress, String charset) {
        if (strCompress == null) return null;
        String charsetType = PubUtils.CharsetEncoding_UTF;
        if (charset != null)
            charsetType = charset;

        try {
            byte[] strBytes = strCompress.getBytes(charsetType);
            return ZIP_GZip(strBytes);
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return null;

    }

    /**
     * 将压缩的字节流 解压缩为字符串（主要针对文本类）
     *
     * @param gzipBytes 要解压缩的字节流
     * @param charset   转换字符串的编码   如果设为null 则使用utf8
     * @return 解压缩后的字符串  如果异常或者源串为null  则为null
     */
    public static String ZIP_GUnzip(byte[] gzipBytes, String charset) {
        if (gzipBytes == null) return null;
        String charsetType = PubUtils.CharsetEncoding_UTF;
        if (charset != null)
            charsetType = charset;

        try {
            byte[] strBytes = PubUtils.ZIP_GUnzip(gzipBytes);

            return new String(strBytes, charsetType);
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return null;

    }


    /**
     * hash算法  bkdr算法
     *
     * @param str
     * @return
     */
    public static int doHash_BKDR(String str) {
        int seed = 131; // 31 131 1313 13131 131313 etc..
        int hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }

        return (hash & 0x7FFFFFFF);
    }


    //用于mongo转换的time格式化变量
//	private static SimpleDateFormat MongoTimeFormater = initMongoTimeFormat();
//	
//	private static final String DATE_TAG="\"$date\"";
//	
//	private static SimpleDateFormat initMongoTimeFormat()
//	{	SimpleDateFormat  d=new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" );
//		MongoTimeFormater.setTimeZone( TimeZone.getTimeZone("UTC") );
//		return d;
//	}

//	
//	
//	/**
//	 * 将mongodb ddbobject字符串中的日期格式转换为标准的日期格式 便于后续转换
//	 * @param strMongoDBObj  mongo的 dbobj对象
//	 * @return 包含标准日期格式（YYYY-MM-dd HH:mm:ss）的mongo dbobj对象
//	 */
//	public static String MongoDB_formatDateTimeToStd(String strMongoDBObj)
//	{	 
//		
//		String oriStr=strMongoDBObj;
//		while(true)
//		{ int pos=oriStr.indexOf(DATE_TAG);
//			if(pos<0) break;
//			  
//			int start=oriStr.lastIndexOf("{",pos);				
//			int finish=oriStr.indexOf("}",pos);
//			 	 
//			if (start>0&&finish>0)				
//			{	//确定日期串 
//				int dStart=oriStr.indexOf("\"",pos+DATE_TAG.length());
//				int dEnd=oriStr.lastIndexOf("\"",finish);
//				String dateStr=oriStr.substring(dStart+1,dEnd);
//				//转换
//			 	try {
//			 			
//			 			Date d=MongoTimeFormater.parse(dateStr);
//			 			dateStr=PubUtils.getStdTimeString(d,true);
//					
//			 		 
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			oriStr=oriStr.substring(0,start-1)+"\""+dateStr+"\" "+oriStr.substring(finish+1);	
//			}
//			 	
//		}	
//		
//		return oriStr;
//	}
//	
//	


    /**
     * @param args
     */
    public static void main(String[] args) {


        // TODO Auto-generated method stub

        //getPinYinFirstLetter

//		PubUtils.String_ConvertToLong("123",20);
//		
// 	System.out.print(getPinYinFirstLetter("111111"));
// 	System.out.print(getPinYinFirstLetter("马亮1"));
// 	System.out.print(getPinYinFirstLetter("东升 w1"));
////		 
//		 
//		String test=PubUtils.convertHtmlUnicodeToChinese("afwerd第三方孙大发&#38889;&#29256;&#20241;&#38386;&#39118; &#38738;&#26149;");
//		
//		
//		List itemList=getLocalIPAddrList(true);
//
//		//------------------
////		
////		System.out.println(InfoDisplayLevel.Common.compareTo(InfoDisplayLevel.Exception));
////		System.out.println(InfoDisplayLevel.NoDisplay.compareTo(InfoDisplayLevel.Common));
////		System.out.println(InfoDisplayLevel.All.compareTo(InfoDisplayLevel.Debug));
////		
//		
//		long t=PubUtils.getUniqueSn(1);
//		PubUtils.printOutMsg("Final:"+t,true,1);

        //PubUtils.printOutMsg( PubUtils.getLinuxUid(1),true,1);

        for (int i = 0; i < 1000; i++) {
            long tt = PubUtils.getUniqueSn(26);
            //PubUtils.printOutMsg(tt+"",true,1);
            System.out.println("ceshi:" + tt);
        }
        //tt=getUniqueSn(5,21);
        //PubUtils.printOutMsg(tt+"",true,1);


//		PubUtils.printOutMsg( "Start \n",true,1);

//		Hashtable hs=new Hashtable();
//		for(int i=0;i<15000;i++)
//		{
//			long t1=PubUtils.getUniqueSn(21);
//
//			//if(i%100==0)
//				PubUtils.printOutMsg(t1+"",true,1);
//
//			if (hs.contains(t1)==true)
//				PubUtils.printOutMsg(i+": Conflict:"+t1,true,1);
//			else
//				hs.put(t1,t1);
//		}
//
//		PubUtils.printOutMsg( "Finish \n",true,1);
    }

}

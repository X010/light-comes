package com.light.outside.comes.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具类
 * <P>File name : DateUtil.java </P>
 * <P>Author : b3st </P>
 * <P>Date : 2016年10月5日 </P>
 */
public class DateUtils {
    private static final transient Log log = LogFactory.getLog(DateUtils.class);

    public static final String datePattern = "yyyy-MM-dd";
    public static final String shortDatePattern = "M-d";
    public static final String datePatternChinese = "yyyy年M月d日";
    public static final String dateTimePatternChinese = "yyyy年M月d日 HH:mm";
    public static final String datePatternSimple = "yyyyMMdd";
    public static final String datePatternAllSimple = "yyyyMMddHHmmss";
    public static final String datePatternAllHH = "yyyy-MM-dd HH:mm:ss";

    public static final String timePattern = "HH:mm:ss";
    public static final String shortTimePattern = "HH:mm";

    public static final String dataTimePattern = datePattern + " " + timePattern;

    private static SimpleDateFormat dateFormat;

    private static SimpleDateFormat dateFormatSimple;
    private static SimpleDateFormat dateFormatAllSimple;

    private static SimpleDateFormat timeFormat;

    private static SimpleDateFormat dataTimeFormat;
    private static SimpleDateFormat dataPatternAllHH;
    private final static String FORMAT = "E";

    static {
        dateFormat = new SimpleDateFormat(datePattern);
        timeFormat = new SimpleDateFormat(timePattern);
        dataTimeFormat = new SimpleDateFormat(dataTimePattern);
        dateFormatSimple = new SimpleDateFormat(datePatternSimple);
        dateFormatAllSimple = new SimpleDateFormat(datePatternAllSimple);
        dataPatternAllHH = new SimpleDateFormat(datePatternAllHH);
    }

    /**
     * 输入日期取星期几的方法
     * DateUtil.getWeekDay()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param DateStr
     * @return
     */
    public static String getWeekDay(String DateStr) {
        SimpleDateFormat formatYMD = new SimpleDateFormat(datePattern);//formatYMD表示的是yyyy-MM-dd格式
        SimpleDateFormat formatD = new SimpleDateFormat(FORMAT, Locale.CHINESE);//"E"表示"day in week"
        Date d = null;
        String weekDay = "";
        try {
            d = formatYMD.parse(DateStr);//将String 转换为符合格式的日期
            weekDay = formatD.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("日期:"+DateStr+" ： "+weekDay);
        return weekDay.replace("星期", "周");
    }


    /**
     * 计算两个时间段中间的日期的方法
     * DateUtil.findDates()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param start_time
     * @param end_time
     * @return
     * @throws ParseException
     */
    public static String[][] findDates(String start_time, String end_time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        Date date1 = df.parse(start_time);
        Date date2 = df.parse(end_time);
        int s = (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
        String value[][] = new String[s + 1][1];
        if (s + 1 > 0) {
            for (int i = 0; i <= s; i++) {
                long todayDate = date1.getTime() + i * 24 * 60 * 60 * 1000;
                Date tmDate = new Date(todayDate);
                value[i][0] = new SimpleDateFormat("yyyy-MM-dd").format(tmDate);
                //System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(tmDate));
            }
        }
        return value;
    }


    /**
     * 字符串转DATE
     * DateUtil.toDate()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param string
     * @return
     * @throws ParseException
     */
    public static Date toDate(String string) throws ParseException {
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            if (log.isDebugEnabled()) {
                log.error(e.getMessage());
            }

        }
        return null;
    }

    public static Date toDateSimple(String date) throws ParseException {
        return dateFormatSimple.parse(date);
    }

    public static String toDateHMS(Date date) throws ParseException {
        return dataPatternAllHH.format(date);
    }

    public static String toDateString(Date date, int type) {
        return dateFormat.format(date);
    }

    public static String toDateStringSimple(Date date) {
        return dateFormatSimple.format(date);
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * 获取24小时的年月日时分秒
     * DateUtil.getHHday()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @return
     */
    public static Date getHHday() {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat(datePatternAllHH);

        String mDateTime = formatter.format(new Date());
        Date date = null;
        try {
            date = formatter.parse(mDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getNewDate(Date t, int month) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = f.format(t);
        month += Integer.parseInt(dateStr.split("-")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dateStr.split("-")[0]), month, Integer.parseInt(dateStr.split("-")[2]));
        Date d = calendar.getTime();
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        try {
            return DateFormat.getDateInstance().parse(new SimpleDateFormat("yyyy-MM-dd").format(d));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转Date、根据指定格式转换
     * DateUtil.toDate()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param string
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date toDate(String string, String format)
            throws ParseException {
        try {
            SimpleDateFormat simpleDataFormat = new SimpleDateFormat(format);
            return simpleDataFormat.parse(string);
        } catch (ParseException e) {
            // throw new ParseException(e.getMessage(), e.getErrorOffset());
            if (log.isDebugEnabled()) {
                log.error(e.getMessage());
            }

        }
        return null;
    }

    public static Date toTime(String string) throws ParseException {
        try {
            return timeFormat.parse(string);
        } catch (ParseException e) {
            // throw new ParseException(e.getMessage(), e.getErrorOffset());
            if (log.isDebugEnabled()) {
                log.error(e.getMessage());
            }

        }
        return null;
    }

    public static Date toDateTime(String string) throws ParseException {
        try {
            return dataTimeFormat.parse(string);
        } catch (ParseException e) {
            // throw new ParseException(e.getMessage(), e.getErrorOffset());
            if (log.isDebugEnabled()) {
                log.error(e.getMessage());
            }

        }
        return null;
    }


    /**
     * 将指定日期设置为最大值
     * DateUtil.toHMSForMax()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date
     * @return
     */
    public static Date toHMSForMax(Date date) {
        if (date != null) {
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
        }
        return date;
    }

    public static String toDateString(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        }
        return null;
    }

    public static String toDateSimpleString(Date date) {
        if (date != null) {
            return dateFormatSimple.format(date);
        }
        return null;
    }

    public static String toDateSimpleString(String date) {
        if (date != null) {
            String newStr = date.split("\\s")[0];
            java.sql.Date dates = java.sql.Date.valueOf(newStr);
            return dateFormatSimple.format(dates);
        }
        return null;
    }

    public static String toDateAllSimpleString(Date date) {
        if (date != null) {
            return dateFormatAllSimple.format(date);
        }
        return null;
    }

    public static String toTimeString(Date date) {
        return timeFormat.format(date);
    }

    public static String toDataTimeString(Date date) {
        return dataTimeFormat.format(date);
    }


    /**
     * 获取当前日期
     * DateUtil.getDate()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取参数日期与当前日期相减之间的天数
     * DateUtil.getDays()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param beginDate
     * @return
     */
    public static Long getDays(Date beginDate) {
        Date endDate = null;
        try {
            endDate = toDate(toDateString(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//		long day = (beginDate.getTime()-endDate.getTime())/(24*60*60*1000)>0 ? (beginDate.getTime()-endDate.getTime())/(24*60*60*1000):
//			   (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        long day = (beginDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);

        return day;
    }


    /**
     * 获取当前日期与参数日期相减之间的天数
     * DateUtil.betweenDays()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param beginDate
     * @return
     */
    public static Long betweenDays(Date beginDate) {
        Date endDate = new Date();
        long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 获取当前日期与参数日期相减之间的秒数
     *
     * @param beginDate
     * @return
     */
    public static Long betweenSeconds(Date beginDate) {
        Date endDate = new Date();
        long seconds = (endDate.getTime() - beginDate.getTime()) / (1000);
        return seconds;
    }

    /**
     * 获取参数日期于当前日期相减之间的秒数
     *
     * @param endDate
     * @return
     */
    public static Long endSeconds(Date endDate) {
        Date beginDate = new Date();
        long seconds = (endDate.getTime() - beginDate.getTime()) / (1000);
        return seconds;
    }

    public static Date AddDays(Date date, int days) {
        Date dt = date;
        if (dt != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            if (days != 0) {
                calendar.add(Calendar.DATE, days);
            }
            dt = calendar.getTime();
        }
        return dt;
    }

    /**
     * 判断两个日期间的时间\间隔多少天 时间差
     * DateUtil.getTimeDifference()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param y
     * @param m
     * @param d
     * @return
     */
    private Long getTimeDifference(String[] y, String[] m, String[] d) {
        // 开始时间
        Calendar xmas = new GregorianCalendar(Integer.parseInt(y[0]), Integer
                .parseInt(m[0]) - 1, Integer.parseInt(d[0]));
        Calendar newyears = null;
        // 如果有 第二个时间的时候 结束时间
        if (y.length > 1) {
            newyears = new GregorianCalendar(Integer.parseInt(y[1]), Integer
                    .parseInt(m[1]) - 1, Integer.parseInt(d[1]));
        } else {
            // 否则就是和第一个时间一样
            newyears = new GregorianCalendar(Integer.parseInt(y[0]), Integer

                    .parseInt(m[0]) - 1, Integer.parseInt(d[0]));
        }
        // Determine which is earlier
        boolean b = xmas.after(newyears); // false
        b = xmas.before(newyears); // true
        // Get difference in milliseconds
        long diffMillis = newyears.getTimeInMillis() - xmas.getTimeInMillis();

        // Get difference in seconds
        long diffSecs = diffMillis / (1000); // 604800

        // Get difference in minutes
        long diffMins = diffMillis / (60 * 1000); // 10080

        // Get difference in hours
        long diffHours = diffMillis / (60 * 60 * 1000); // 168

        // Get difference in days
        long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
        return diffDays;
    }

    public static int getTimeDifference(String date1, String date2, int type) {
        int ret = 0;
        if (date1 != null) {
            Calendar beginDate = new GregorianCalendar(Integer.parseInt(date1.substring(0, 4)), Integer
                    .parseInt(date1.substring(4, 6)) - 1, Integer.parseInt(date1.substring(6)));

            Calendar endDate = new GregorianCalendar(Integer.parseInt(date2.substring(0, 4)), Integer
                    .parseInt(date2.substring(4, 6)) - 1, Integer.parseInt(date2.substring(6)));
            long diffMillis = beginDate.getTimeInMillis() - endDate.getTimeInMillis();

            switch (type) {
                case 1:
                    long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
                    ret = Integer.parseInt(Long.toString(Math.abs(diffDays)));
                    break;
                case 2:

                case 3:
            }
        }
        return ret;
    }

    public static String getYesterDay() throws Exception {
        String yesterday = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
        return yesterday;

    }

    public static int getday(Date date1, String date2) {
        int ret = 0;
        ret = getTimeDifference(dateFormatSimple.format(date1), date2, 1);
        return ret;
    }

    public static SimpleDateFormat getDateFormatSimple() {
        return dateFormatSimple;
    }

    public static SimpleDateFormat getDateFormatAllSimple() {
        return dateFormatAllSimple;
    }


    /**
     * 求指定日期所在统计周期的开始日期
     * 5天一个统计周期，26号到月底
     * DateUtil.get5StartDate()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date
     * @return
     */
    public static Date get5StartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormatSimple.parse(dateFormatSimple.format(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int fromDay = 26;
        if (day <= 30) {
            fromDay = 1 + 5 * ((day - 1) / 5);
        }

        cal.set(Calendar.DAY_OF_MONTH, fromDay);
        return cal.getTime();
    }

    /**
     * 求指定日期所在统计周期的结束日期
     * 5天一个统计周期，26号到月底
     * DateUtil.get5EndDate()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date
     * @return
     */
    public static Date get5EndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormatSimple.parse(dateFormatSimple.format(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int toDay = 0;
        if (day < 26) {
            toDay = 5 * ((day - 1) / 5 + 1);
            cal.set(Calendar.DAY_OF_MONTH, toDay);
        } else {
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 取得上一个统计周期的开始时间
     * 如当前为17号，则上一统计周期开始时间为11号
     * DateUtil.getLast5Startday()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date 当前时间
     * @return
     */
    public static Date getLast5Startday(Date date) {
        Date fromDate = get5StartDate(date);
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(fromDate);
        fromCal.add(Calendar.DATE, -1);
        return get5StartDate(fromCal.getTime());
    }

    /**
     * 取得上一个统计周期的结束时间
     * 如当前为17号，则上一统计周期开始时间为15号
     * DateUtil.getLast5Endday()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date
     * @return
     */
    public static Date getLast5Endday(Date date) {
        Date fromDate = get5StartDate(date);
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(fromDate);
        fromCal.add(Calendar.DATE, -1);
        return get5EndDate(fromCal.getTime());
    }

    public static String getTomorrowDateString(String date) {
        String tomorromDate = null;
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(toDateSimple(date));
            cal.add(cal.DATE, 1);
            tomorromDate = toDateStringSimple(cal.getTime());
        } catch (Exception e) {
            log.error("获取明天日期错误", e);
        }
        return tomorromDate;
    }

    /**
     * 格式化日期成字符串
     * DateUtil.format()<BR>
     * <P>Author :  b3st </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    /**
     * 字符串格式化成日期格式
     * DateUtil.dateFormate()<BR>
     * <P>Author :  zenglijun </P>
     * <P>Date : 2016年1月5日 </P>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateFormate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return date;
        }
    }

    /**
     * 根据给定的时间获取去年第一天或者最后一天<br>
     * 如果flag=0,获取去年第一天<br>
     * 如果flag=1，获取去年最后一天<br>
     * ResblockServiceImpl.formatLastYear()<BR>
     * <P>Author :  xiehongyan </P>
     * <P>Date : 2016年4月15日 </P>
     *
     * @param date 日期
     * @param flag 标示：0:第一天，1:最后一天
     * @return
     */
    public static Date formatLastYear(Date date, int flag) {
        //获取当前年份
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        if (flag == 0) {
            //设置成当年第一天
            c.set(year, 0, 1);
        } else {
            //设置成当年最后一天
            c.set(year, 11, 31);
        }
        return c.getTime();
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 7; i++) {
            System.out.println(DateUtils.format(DateUtils.AddDays(new Date(), i), DateUtils.datePatternChinese));
        }
        getWeekDay("2016-08-29");

    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

}

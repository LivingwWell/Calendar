package com.example.calendar;


import java.util.List;

/**
 * 日历事件
 * <p>
 * Created by KYLE on 2019/3/4 - 9:53
 */
public class CalendarEvent {

    // ----------------------- 事件属性 -----------------------

    /**
     * 事件在表中的ID
     */
    private long id;
    /**
     * 事件所属日历账户的ID
     */
    private long calID;
    private String title;
    private String description;
    private String eventLocation;
    private int displayColor;
    private int status;
    private long start;
    private long end;
    private String duration;
    private String eventTimeZone;
    private String eventEndTimeZone;
    private int allDay;
    private int accessLevel;
    private int availability;
    private int hasAlarm;
    private String rRule;
    private String rDate;
    private int hasAttendeeData;
    private int lastDate;


    // ----------------------------------------------------------------------------------------
    /**
     * 注：此属性不属于CalendarEvent
     * 这里只是为了方便构造方法提供事件提醒时间
     */
    private int advanceTime;
    // ----------------------------------------------------------------------------------------


    // ----------------------- 事件提醒属性 -----------------------
    private List<EventReminders> reminders;

    public CalendarEvent() {
    }

    /**
     * 用于方便添加完整日历事件提供一个构造方法
     *
     * @param title         事件标题
     * @param description   事件描述
     * @param eventLocation 事件地点
     * @param start         事件开始时间
     * @param end           事件结束时间  If is not a repeat event, this param is must need else null
     * @param advanceTime   事件提醒时间
     *                      (If you don't need to remind the incoming parameters -2)
     * @param rRule         事件重复规则  {@code null} if dose not need
     */
    public CalendarEvent(String title, String description, String eventLocation,
                         long start, long end, int advanceTime, String rRule) {
        this.title = title;
        this.description = description;
        this.eventLocation = eventLocation;
        this.start = start;
        this.end = end;
        this.advanceTime = advanceTime;
        this.rRule = rRule;
    }

    public int getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(int advanceTime) {
        this.advanceTime = advanceTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCalID() {
        return calID;
    }

    public void setCalID(long calID) {
        this.calID = calID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(int displayColor) {
        this.displayColor = displayColor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEventTimeZone() {
        return eventTimeZone;
    }

    public void setEventTimeZone(String eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    public String getEventEndTimeZone() {
        return eventEndTimeZone;
    }

    public void setEventEndTimeZone(String eventEndTimeZone) {
        this.eventEndTimeZone = eventEndTimeZone;
    }

    public int getAllDay() {
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(int hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    public String getRRule() {
        return rRule;
    }

    public void setRRule(String rRule) {
        this.rRule = rRule;
    }

    public String getRDate() {
        return rDate;
    }

    public void setRDate(String rDate) {
        this.rDate = rDate;
    }

    public int getHasAttendeeData() {
        return hasAttendeeData;
    }

    public void setHasAttendeeData(int hasAttendeeData) {
        this.hasAttendeeData = hasAttendeeData;
    }

    public int getLastDate() {
        return lastDate;
    }

    public void setLastDate(int lastDate) {
        this.lastDate = lastDate;
    }


    public List<EventReminders> getReminders() {
        return reminders;
    }

    public void setReminders(List<EventReminders> reminders) {
        this.reminders = reminders;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "\n id=" + id +
                "\n calID=" + calID +
                "\n title='" + title + '\'' +
                "\n description='" + description + '\'' +
                "\n eventLocation='" + eventLocation + '\'' +
                "\n displayColor=" + displayColor +
                "\n status=" + status +
                "\n start=" + start +
                "\n end=" + end +
                "\n duration='" + duration + '\'' +
                "\n eventTimeZone='" + eventTimeZone + '\'' +
                "\n eventEndTimeZone='" + eventEndTimeZone + '\'' +
                "\n allDay=" + allDay +
                "\n accessLevel=" + accessLevel +
                "\n availability=" + availability +
                "\n hasAlarm=" + hasAlarm +
                "\n rRule='" + rRule + '\'' +
                "\n rDate='" + rDate + '\'' +
                "\n hasAttendeeData=" + hasAttendeeData +
                "\n lastDate=" + lastDate +
                "\n reminders=" + reminders +
                '}';
    }

    @Override
    public int hashCode() {
        return (int) (id * 37 + calID);
    }

    /**
     * 事件提醒
     */
    public static class EventReminders {

        // ----------------------- 事件提醒属性 -----------------------
        private long reminderId;
        private long reminderEventID;
        private int reminderMinute;
        private int reminderMethod;

        public long getReminderId() {
            return reminderId;
        }

        public void setReminderId(long reminderId) {
            this.reminderId = reminderId;
        }

        public long getReminderEventID() {
            return reminderEventID;
        }

        public void setReminderEventID(long reminderEventID) {
            this.reminderEventID = reminderEventID;
        }

        public int getReminderMinute() {
            return reminderMinute;
        }

        public void setReminderMinute(int reminderMinute) {
            this.reminderMinute = reminderMinute;
        }

        public int getReminderMethod() {
            return reminderMethod;
        }

        public void setReminderMethod(int reminderMethod) {
            this.reminderMethod = reminderMethod;
        }

    }

}
